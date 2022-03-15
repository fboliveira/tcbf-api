/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcbf.Variable;

import java.util.Arrays;
import jmetal.core.Problem;
import jmetal.core.Variable;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import tcbf.ManipuladorArquivo;
import tcbf.Rodada;
import tcbf.Times;

/**
 *
 * @author Alexandre
 */
public class MatrizVariable extends Variable {

    private Problem problem_;
    public Rodada[][] matriz;
    private int TamRodada;
    private int QtdJogos;
    private int cont;
    private int turno;
    ManipuladorArquivo manipula = new ManipuladorArquivo();
    private Rodada Turno;

    public MatrizVariable(int sizeRodada, int sizeJogos, Problem problem) {
        this.problem_ = problem;
        this.TamRodada = sizeRodada;
        this.QtdJogos = sizeJogos;
        this.matriz = new Rodada[TamRodada][QtdJogos];
        this.cont = 0;

        for (int i = 0; i < TamRodada / 2; i++) { // Cria Primeiro Turno buscando Jogos do Arquivo Externo
            for (int j = 0; j < QtdJogos; j++) {
                matriz[i][j] = manipula.PreencheRodada(cont);
                cont++;
            }
        }         
        
        cont = 0;
        for (int i = 19; i < TamRodada; i++) { //Cria Segundo turno com Jogos invertidos
            for (int j = 0; j < QtdJogos; j++) {
                Turno = new Rodada(matriz[cont][j].getFora(), matriz[cont][j].getCasa());
                matriz[i][j] = Turno;
            }
            cont++;
        }
//        
    }

    /*Construtor Tamanho da Matriz
    
     */
    public MatrizVariable(int sizeRodada, int sizeJogos) {
        this.TamRodada = sizeRodada;
        this.QtdJogos = sizeJogos;
        this.matriz = new Rodada[TamRodada][QtdJogos];
    }

    /*copy construtor
    
     */
    private MatrizVariable(MatrizVariable matrizVariable) {

        QtdJogos = matrizVariable.QtdJogos;
        TamRodada = matrizVariable.TamRodada;
        matriz = new Rodada[TamRodada][QtdJogos];
        Times[] time = new Times[20];

        for (int i = 0; i < TamRodada; i++) {
            for (int j = 0; j < QtdJogos; j++) {
                matriz[i][j] = matrizVariable.matriz[i][j].Copy();
//                int casa = matrizVariable.matriz[i][j].getCasa();
//                int fora = matrizVariable.matriz[i][j].getFora();
//                time = matrizVariable.matriz[i][j].getTime();
//                matriz[i][j] = new Rodada(casa,fora);
//                matriz[i][j].setTime(time);
            }
        } // for
    } // Copy Constructor

    @Override
    public Variable deepCopy() {

        return new MatrizVariable(this);

    }

    public int getTamRodada() {
        return TamRodada;
    }

    public int getQtdJogos() {
        return QtdJogos;
    }

    public String toString() {
        String string;

        string = "\n";
        for (int i = 0; i < TamRodada; i++) {
            for (int j = 0; j < QtdJogos; j++) {
                //  string += matriz[i][j].getCasa() + "x" + matriz[i][j].getFora() + " " ;
                //string += "\n";
                string += matriz[i][j].getCasa() + "x" + matriz[i][j].getFora() + " ";
            }
            string += "\n";
        }
        return string;
    } //toString

}
