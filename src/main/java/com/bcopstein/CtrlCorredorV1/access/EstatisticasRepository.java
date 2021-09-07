package com.bcopstein.CtrlCorredorV1.access;

import java.util.List;

import com.bcopstein.CtrlCorredorV1.domain.EstatisticaDTO;

public interface EstatisticasRepository {
    
    EstatisticaDTO getEstatisticasByDistance(int distance);

}
