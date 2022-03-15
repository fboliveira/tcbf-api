/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcbf;

/**
 *
 * @author Alexandre
 */
public class Distancia {
    
    private String localizacaoAtual;
    private String localizacaoDestino;
    private int distancia_;
    
    public Distancia (String localizacaoAtual, String localizacaoDestino, int distancia_){
        
        this.localizacaoAtual = localizacaoAtual;
        this.localizacaoDestino = localizacaoDestino;
        this.distancia_ = distancia_;
    }

    /**
     * @return the localizacaoAtual
     */
    public String getLocalizacaoAtual() {
        return localizacaoAtual;
    }

    /**
     * @param localizacaoAtual the localizacaoAtual to set
     */
    public void setLocalizacaoAtual(String localizacaoAtual) {
        this.localizacaoAtual = localizacaoAtual;
    }

    /**
     * @return the localizacaoDestino
     */
    public String getLocalizacaoDestino() {
        return localizacaoDestino;
    }

    /**
     * @param localizacaoDestino the localizacaoDestino to set
     */
    public void setLocalizacaoDestino(String localizacaoDestino) {
        this.localizacaoDestino = localizacaoDestino;
    }

    /**
     * @return the distancia_
     */
    public int getDistancia_() {
        return distancia_;
    }

    /**
     * @param distancia_ the distancia_ to set
     */
    public void setDistancia_(int distancia_) {
        this.distancia_ = distancia_;
    }
    
    
    
}
