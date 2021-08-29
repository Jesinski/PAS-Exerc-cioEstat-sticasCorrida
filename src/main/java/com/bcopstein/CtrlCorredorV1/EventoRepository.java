package com.bcopstein.CtrlCorredorV1;

import java.util.List;

public interface EventoRepository {

    List<Evento> getEventos();
    boolean createEvento(Evento evento);
    
}
