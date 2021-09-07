package com.bcopstein.CtrlCorredorV1.access;

import java.util.List;

import com.bcopstein.CtrlCorredorV1.domain.Corredor;

public interface CorredorRepository {

    List<Corredor> getCorredores();
    boolean createCorredor(Corredor corredor);
}
