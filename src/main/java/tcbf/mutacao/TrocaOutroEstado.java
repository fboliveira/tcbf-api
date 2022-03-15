/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcbf.mutacao;

import java.util.HashMap;
import java.util.Random;
import jmetal.core.Solution;
import jmetal.operators.mutation.Mutation;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import tcbf.Rodada;
import tcbf.TCBFSolutionType;
import tcbf.Times;
import tcbf.Variable.ArrayTime;
import tcbf.Variable.MatrizVariable;

/**
 *
 * @author Alexandre
 */
public class TrocaOutroEstado extends Mutation {
     Random gerador = new Random();

    public TrocaOutroEstado(HashMap<String, Object> parameters) {
        super(parameters);
    }

    @Override
    public Object execute(Object object) throws JMException {
            Solution solution = (Solution)object;

       this.doMutation(solution);
        return solution;
    
    }

    private void doMutation(Solution solution) throws JMException {
        
        Times times[] = null;
        Times aux = null;
        int tamRodada;
        int QtdJogos;
        Rodada rodada[][] = null;
     
       
        int permutationLength;
        if (solution.getType().getClass() == TCBFSolutionType.class) {
            permutationLength = ((ArrayTime) solution.getDecisionVariables()[0]).getLength();
            
            times = ((ArrayTime) solution.getDecisionVariables()[0]).getArray();
            tamRodada = ((MatrizVariable) solution.getDecisionVariables()[1]).getTamRodada();
            QtdJogos = ((MatrizVariable) solution.getDecisionVariables()[1]).getQtdJogos();
            rodada = ((MatrizVariable) solution.getDecisionVariables()[1]).matriz;
          
            //Gera dois times aleatorios
            int numero = gerador.nextInt(20);
            int numero2 = gerador.nextInt(20);
            while (numero == numero2){
                numero2 = gerador.nextInt(20) ;
            }       
            //Faz a troca
            aux = times[numero];
            times[numero] = times[numero2];   
            times[numero].setidRef_(numero+1); // Muda a REF do primeiro time
            times[numero2] = aux;
            times[numero2].setidRef_(numero2+1); // Muda a REF do segundo time
             
           
          classicosTresPrimeiras(times,  rodada);
            
           
            for(int i=0; i<times.length;i++){
            times[i].setidRef_(i+1);                
            ((ArrayTime) solution.getDecisionVariables()[0]).setValue(i, times[i]);
            ((ArrayTime) solution.getDecisionVariables()[0]).setValue(i, times[i]);
            }        
            
        } else {
            Configuration.logger_.severe("TrocaTimes.doMutation: invalid type. "
                    + "" + solution.getDecisionVariables()[0].getVariableType());

            Class cls = java.lang.String.class;
            String name = cls.getName();
            throw new JMException("Exception in " + name + ".doMutation()");
        }

    }


    
    
    
    public void classicosTresPrimeiras(Times[] times, Rodada[][] rod) {
       Times aux = null;
       int k = 0;
       int j = 0;
       int i = 0;
       int indexTime; 
      while (i<3){
            
           while(j<10)    {
                k = gerador.nextInt(10);
                //System.out.println("I: " + i + " J: " + j);
                if (times[rod[i][j].getCasa() - 1].getClassico() == times[rod[i][j].getFora() - 1].getClassico()) {
                   //Sorteia se vai troca time da casa ou time fora de casa
                    indexTime = gerador.nextInt(2);  
                    
                  //  System.out.println("-");
                   if (indexTime == 0){ // Troca times que jogam em casa por outros times que jogam em casa na msma rodada
                       aux = times[rod[i][j].getCasa()-1];
                       while (times[k].getEstado() == aux.getEstado()){
                               k = gerador.nextInt(20);
                       }
                       
                       
                       times[rod[i][j].getCasa()-1] = times[k];
                       times[rod[i][j].getCasa()-1].setidRef_(k+1);
                       times[k] = aux;
                       times[k].setidRef_(rod[i][j].getCasa());
                       j=0;//Verificar Rodada de novo
                       i=0;
                    //   System.out.println("Depois Time 1: " + times[rod[i][j].getCasa() - 1].getNome_() + "Time2: " + times[rod[i][j].getFora() - 1].getNome_());
                  
                       
                   } else if (indexTime == 1){ //Troca times que jogam em fora por outros times que jogam em fora na msma rodada
                       aux = times[rod[i][j].getFora()-1];
                       while (times[rod[i][k].getFora()-1].getEstado() == aux.getEstado()){
                               k = gerador.nextInt(10);
                       }
                       times[rod[i][j].getFora()-1] = times[k];
                       times[rod[i][j].getFora()-1].setidRef_(k+1);
                       times[k] = aux;
                       times[k].setidRef_(rod[i][j].getFora());
                       j=0; //Verificar Rodada de novo
                       i=0;
                  //      System.out.println("Depois Time 1: " + times[rod[i][j].getCasa() - 1].getNome_() + "Time2: " + times[rod[i][j].getFora() - 1].getNome_());
                  
                   } 
                } else{
                    j++;
                }
                     
            }
           i++;
           j=0;
        }
    }
    
}
