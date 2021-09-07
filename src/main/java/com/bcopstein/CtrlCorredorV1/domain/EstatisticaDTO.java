package com.bcopstein.CtrlCorredorV1.domain;

import java.util.Collections;
import java.util.List;

public class EstatisticaDTO {
    List<Evento> eventos;
    int distancia;
    double media;
    int mediana;
    double desvioPadrao;

    public EstatisticaDTO(List<Evento> eventos, int distancia) {
        this.eventos = eventos;
        this.distancia = distancia;
        this.media = calculaMedia(eventos);
        this.mediana = calculaMediana(eventos);
        this.desvioPadrao = calculaDesvioPadrao(eventos);
    }

    public int getDistancia() {
        return distancia;
    }

    public double getMedia() {
        return media;
    }

    public int getMediana() {
        return mediana;
    }

    public double getDesvioPadrao() {
        return desvioPadrao;
    }

    @Override
    public String toString() {
        return "Estatistícas [" + "quantidade de corridas=" + eventos.size() + ", distancia=" + distancia + ", media="
                + media + ", mediana=" + mediana + ", desvio padrao=" + desvioPadrao + "]";
    }

    private double calculaMedia(List<Evento> eventos) {
        double soma = 0;
        for (int i = 0; i < eventos.size(); i++) {
            Evento evento = eventos.get(i);
            int tempoMin = evento.getMinutosTotais();
            soma += tempoMin;
        }
        return soma / eventos.size();
    }

    private int calculaMediana(List<Evento> eventos) {
        Collections.sort(eventos, (e1, e2) -> e1.getMinutosTotais() - e2.getMinutosTotais());
        int posMediana = eventos.size()/2;
        return eventos.get(posMediana).getMinutosTotais();
    }

    private double calculaDesvioPadrao(List<Evento> eventos) {

        Double media = calculaMedia(eventos);
        int tam = eventos.size();
        double desvPadrao = 0;
        for (Evento evento : eventos) {
            Double aux = evento.getMinutosTotais() - media;
            desvPadrao += aux * aux;
        }
        return Math.sqrt(desvPadrao / (tam - 1));
    }
}
