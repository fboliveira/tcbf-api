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
public class Times {
    
    private int idRef;
    private int id_;
    private String nome_;
    private int casa_; // ID da cidade Casa
    private int localizacao_; //ID da cidade onde ele esta
    private int tempo_;
    private int classico; //Classicos de Estados
    private int estado;
        
    public Times (int id, String nome, int casa,int estado, int localizacao, int tempo, int classico){
        
    this.id_ = id;
    this.nome_ = nome;
    this.casa_ = casa;
    this.localizacao_ = localizacao;
    this.tempo_ = tempo;
    this.classico = classico;
    this.estado = estado;
        
    }

    public Times(Times aThis) {
       this.casa_ = aThis.casa_;
       this.idRef = aThis.idRef;
       this.id_ = aThis.id_;
       this.localizacao_ = aThis.localizacao_;
       this.nome_ = aThis.nome_;
       this.tempo_ = aThis.tempo_;
       this.classico = aThis.classico;
       this.estado = aThis.estado;
       
      
    }

    /**
     * @return the id_
     */
    public int getId_() {
        return id_;
    }

    /**
     * @param id_ the id_ to set
     */
    public void setId_(int id_) {
        this.id_ = id_;
    }

    /**
     * @return the nome_
     */
    public String getNome_() {
        return nome_;
    }

    /**
     * @param nome_ the nome_ to set
     */
    public void setNome_(String nome_) {
        this.nome_ = nome_;
    }

    /**
     * @return the casa_
     */
    public int getCasa_() {
        return casa_;
    }

    /**
     * @param casa_ the casa_ to set
     */
    public void setCasa_(int casa_) {
        this.casa_ = casa_;
    }

    /**
     * @return the localizacao_
     */
    public int getLocalizacao_() {
        return localizacao_;
    }

    /**
     * @param localizacao_ the localizacao_ to set
     */
    public void setLocalizacao_(int localizacao_) {
        this.localizacao_ = localizacao_;
    }

    /**
     * @return the tempo_
     */
    public int getTempo_() {
        return tempo_;
    }

    /**
     * @param tempo_ the tempo_ to set
     */
    public void setTempo_(int tempo_) {
        this.tempo_ = tempo_;
    }    
    
    public void setidRef_(int idREF){
        this.idRef = idREF;
    }
    
    public int getidRef(){
        return idRef;
   
    }
    public int getClassico(){
        return classico;
    }
    public int getEstado(){
        return estado;
    }
    
    public Times Copy(){
        return new Times(this);
    }
    
}
