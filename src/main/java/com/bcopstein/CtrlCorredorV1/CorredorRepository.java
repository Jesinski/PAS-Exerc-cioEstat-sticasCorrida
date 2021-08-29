package com.bcopstein.CtrlCorredorV1;

import java.util.List;

public interface CorredorRepository {

    List<Corredor> getCorredores();
    boolean createCorredor(Corredor corredor);
}
