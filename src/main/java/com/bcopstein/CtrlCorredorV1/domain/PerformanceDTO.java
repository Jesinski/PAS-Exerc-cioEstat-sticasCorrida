package com.bcopstein.CtrlCorredorV1.domain;

import java.util.List;

public class PerformanceDTO {

    String corridaPassada;
    String corridaAtual;
    int melhoria;

    public PerformanceDTO(List<Evento> corridas) {
        String corrida1 = "";
        String corrida2 = "";

        int aumentoPerformance = 0;
        for (int i = 0; i < corridas.size(); i++) {
            if (i == 0) {
                break;
            }
            if (corridas.get(i).getMinutosTotais() - corridas.get(i-1).getMinutosTotais() > 0) {
                int performancePassada = corridas.get(i-1).getMinutosTotais();
                int performanceAtual = corridas.get(i).getMinutosTotais();
                if (performancePassada - performanceAtual > aumentoPerformance) {
                    aumentoPerformance = performancePassada - performanceAtual;
                    corrida1 = corridas.get(i-1).getNome();
                    corrida2 = corridas.get(i).getNome();
                }
            }
        }

        this.corridaAtual = corrida2;
        this.corridaPassada = corrida1;
        this.melhoria = aumentoPerformance;
    }
    
}
