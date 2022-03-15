/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcbf;

import tcbf.Variable.ArrayTime;

/**
 *
 * @author Alexandre
 */
public class Rodada {
    
    private int casa;
    private int fora;
    private Times casa_;
    private Times fora_;
    Times [] time = new Times[20];
    
    public Rodada(int casa, int fora){
     this.casa = casa;
     this.fora = fora;
     
    }

    private Rodada(Rodada aThis) {
    this.casa = aThis.casa;
    this.fora = aThis.fora;
    this.casa_ = aThis.casa_;
    this.fora_ = aThis.fora_;
    
    }
    
    public void setTime(Times[] arraytime){
        casa_= arraytime[casa-1];
        fora_= arraytime[fora-1];
        //time = arraytime;
    }
    
    public Times[] getTime(){
        return time;
    }
    
    public void SetCasa (int time){
        this.casa = time;
    }
    
    public void SetFora (int time){
        this.fora = time;
    }
    
    public int getCasa(){
        return casa;
    }
    
     public int getFora(){
        return fora;
    }
     
     public String getTimeCasa(){
         return casa_.getNome_();
     }
     public String getTimeFora(){
         return fora_.getNome_();
     }
     
     public Rodada Copy(){
         return new Rodada(this);
     }
    
}
