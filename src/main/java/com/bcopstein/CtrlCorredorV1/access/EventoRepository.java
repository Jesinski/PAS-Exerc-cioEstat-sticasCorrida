package com.bcopstein.CtrlCorredorV1.access;

import java.util.List;

import com.bcopstein.CtrlCorredorV1.domain.Evento;

public interface EventoRepository {

    List<Evento> getEventos();
    boolean createEvento(Evento evento);
    
}
