/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcbf;
import java.io.IOException;
import tcbf.ManipuladorArquivo;
/**
 *
 * @author Alexandre
 */
public class MatrizTempo {
    public int tempo[][];
    ManipuladorArquivo manipula = new ManipuladorArquivo();

    public MatrizTempo(int QtdCidades) throws IOException{
        tempo = new int [QtdCidades][QtdCidades];
        int cont = 0;
        
        for (int i = 0; i<QtdCidades;i++){
            for(int j = 0; j<QtdCidades;j++){
                //Preenche a Matriz de Tempo            
                tempo[i][j] = manipula.preencheDistancia(cont);
                        cont ++;
            }
        }
    }
    
    
    public int retornaTempo(int LAtual, int LDest){   
        return tempo[LAtual][LDest];    
    }
    
}
