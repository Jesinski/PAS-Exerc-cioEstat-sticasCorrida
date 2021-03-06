package com.bcopstein.CtrlCorredorV1.access;

import java.util.List;

import javax.websocket.server.PathParam;

import com.bcopstein.CtrlCorredorV1.domain.Corredor;
import com.bcopstein.CtrlCorredorV1.domain.EstatisticaDTO;
import com.bcopstein.CtrlCorredorV1.domain.Evento;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ctrlCorridas")
public class CorridasControlerRepository implements CorredorRepository, EventoRepository, EstatisticasRepository {
        private JdbcTemplate jdbcTemplate;

        @Autowired
        public CorridasControlerRepository(JdbcTemplate jdbcTemplate) {
                this.jdbcTemplate = jdbcTemplate;

                this.jdbcTemplate.execute("DROP TABLE corredores IF EXISTS");
                this.jdbcTemplate.execute("CREATE TABLE corredores("
                                + "cpf VARCHAR(255), nome VARCHAR(255), genero VARCHAR(255), diaDn int, mesDn int, anoDn int, PRIMARY KEY(cpf))");

                this.jdbcTemplate.batchUpdate(
                                "INSERT INTO corredores(cpf,nome,genero,diaDn,mesDn,anoDn) VALUES ('10001287','Luiz','masculino',22,5,1987)",
                                "INSERT INTO corredores(cpf,nome,genero,diaDn,mesDn,anoDn) VALUES ('54378796','Gustavo','masculino',28,12,1998)",
                                "INSERT INTO corredores(cpf,nome,genero,diaDn,mesDn,anoDn) VALUES ('41241244','Carla','feminino',02,8,1995)");

                this.jdbcTemplate.execute("DROP TABLE eventos IF EXISTS");
                this.jdbcTemplate.execute("CREATE TABLE eventos("
                                + "id int, nome VARCHAR(255), dia int, mes int, ano int, distancia int, horas int, minutos int, segundos int,PRIMARY KEY(id))");

                this.jdbcTemplate.batchUpdate(
                                "INSERT INTO eventos(id,nome,dia,mes,ano,distancia,horas,minutos,segundos) VALUES ('1','Poa Day Run',22,5,2019,5,0,35,32)",
                                "INSERT INTO eventos(id,nome,dia,mes,ano,distancia,horas,minutos,segundos) VALUES ('2','Poa Night Run',02,7,2019,5,1,10,43)",
                                "INSERT INTO eventos(id,nome,dia,mes,ano,distancia,horas,minutos,segundos) VALUES ('3','Poa All Day Run',28,9,2019,5,0,55,44)");

        }

        @GetMapping("/corredor")
        @CrossOrigin(origins = "*")
        public List<Corredor> consultaCorredor() {
                return getCorredores();
        }

        @PostMapping("/corredor")
        @CrossOrigin(origins = "*")
        public boolean cadastraCorredor(@RequestBody final Corredor corredor) {
                return createCorredor(corredor);
        }

        @GetMapping("/eventos")
        @CrossOrigin(origins = "*")
        public List<Evento> consultaEventos() {
                return getEventos();
        }

        @PostMapping("/eventos") // adiciona evento no ??nico corredor
        @CrossOrigin(origins = "*")
        public boolean informaEvento(@RequestBody final Evento evento) {
                return createEvento(evento);
        }

        @GetMapping("/estatisticas")
        @CrossOrigin(origins = "*")
        public EstatisticaDTO estatisticas(@RequestParam final int distancia) {
                return getEstatisticasByDistance(distancia);
        }

        @GetMapping("/aumentoPerformance")
        @CrossOrigin(origins = "*")
        public List<Evento> aumentoPerformance(@RequestParam final int distancia, @RequestParam final int ano) {
                String query = String.format("SELECT * from eventos WHERE distancia = %d AND ano = %d", distancia, ano);
                List<Evento> resp = this.jdbcTemplate.query(query,
                                (rs, rowNum) -> new Evento(rs.getInt("id"), rs.getString("nome"), rs.getInt("dia"),
                                                rs.getInt("mes"), rs.getInt("ano"), rs.getInt("distancia"),
                                                rs.getInt("horas"), rs.getInt("minutos"), rs.getInt("segundos")));
                return resp;
        }

        @Override
        public List<Corredor> getCorredores() {
                List<Corredor> resp = this.jdbcTemplate.query("SELECT * from corredores",
                                (rs, rowNum) -> new Corredor(rs.getString("cpf"), rs.getString("nome"),
                                                rs.getInt("diaDn"), rs.getInt("mesDn"), rs.getInt("anoDn"),
                                                rs.getString("genero")));
                return resp;
        }

        @Override
        public boolean createCorredor(Corredor corredor) {
                // Limpa a base de dados
                this.jdbcTemplate.batchUpdate("DELETE from Corredores");
                // Ent??o cadastra o novo "corredor ??nico"
                this.jdbcTemplate.update(
                                "INSERT INTO corredores(cpf,nome,diaDn,mesDn,anoDn,genero) VALUES (?,?,?,?,?,?)",
                                corredor.getCpf(), corredor.getNome(), corredor.getDiaDn(), corredor.getMesDn(),
                                corredor.getAnoDn(), corredor.getGenero());
                return true;
        }

        @Override
        public List<Evento> getEventos() {
                List<Evento> resp = this.jdbcTemplate.query("SELECT * from eventos",
                                (rs, rowNum) -> new Evento(rs.getInt("id"), rs.getString("nome"), rs.getInt("dia"),
                                                rs.getInt("mes"), rs.getInt("ano"), rs.getInt("distancia"),
                                                rs.getInt("horas"), rs.getInt("minutos"), rs.getInt("segundos")));
                return resp;
        }

        @Override
        public boolean createEvento(Evento evento) {
                this.jdbcTemplate.update(
                                "INSERT INTO eventos(id,nome,dia,mes,ano,distancia,horas,minutos,segundos) VALUES (?,?,?,?,?,?,?,?,?)",
                                evento.getId(), evento.getNome(), evento.getDia(), evento.getMes(), evento.getAno(),
                                evento.getDistancia(), evento.getHoras(), evento.getMinutos(), evento.getSegundos());
                return true;
        }

        @Override
        public EstatisticaDTO getEstatisticasByDistance(int distance) {
                String query = String.format("SELECT * from eventos WHERE distancia = %d", distance);
                List<Evento> resp = this.jdbcTemplate.query(query,
                                (rs, rowNum) -> new Evento(rs.getInt("id"), rs.getString("nome"), rs.getInt("dia"),
                                                rs.getInt("mes"), rs.getInt("ano"), rs.getInt("distancia"),
                                                rs.getInt("horas"), rs.getInt("minutos"), rs.getInt("segundos")));
                EstatisticaDTO estatistica = new EstatisticaDTO(resp, distance);
                return estatistica;
        }
}
