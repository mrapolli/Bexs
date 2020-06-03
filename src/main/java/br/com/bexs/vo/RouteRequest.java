package br.com.bexs.vo;

import javax.validation.constraints.NotNull;


public class RouteRequest {


    @NotNull(message = "Por favor digite a rota de origem")
    private String rotaOrigem;
    @NotNull(message = "Por favor digite a rota de destino")
    private String rotaDestino;
    private int cost;

    public String getRotaOrigem() {
        return rotaOrigem;
    }

    public void setRotaOrigem(String rotaOrigem) {
        this.rotaOrigem = rotaOrigem;
    }

    public String getRotaDestino() {
        return rotaDestino;
    }

    public void setRotaDestino(String rotaDestino) {
        this.rotaDestino = rotaDestino;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
