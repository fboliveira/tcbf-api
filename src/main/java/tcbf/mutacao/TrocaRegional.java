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
public class TrocaRegional extends Mutation {

    public TrocaRegional(HashMap<String, Object> parameters) {
        super(parameters);
    }

    @Override
    public Object execute(Object object) throws JMException {
        Solution solution = (Solution)object;

       this.doMutation(solution);
        return solution;        

//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
          //  System.out.println("-------" + solution.getDecisionVariables()[1]);
            
            times = ((ArrayTime) solution.getDecisionVariables()[0]).getArray();
            tamRodada = ((MatrizVariable) solution.getDecisionVariables()[1]).getTamRodada();
            QtdJogos = ((MatrizVariable) solution.getDecisionVariables()[1]).getQtdJogos();
            rodada = ((MatrizVariable) solution.getDecisionVariables()[1]).matriz;
          
              
            Random gerador = new Random();
            int numero = gerador.nextInt(20);
            int numero2 = gerador.nextInt(20);
            //times[numero].getCasa_() == times[numero2].getCasa_()
            while (times[numero].getEstado() != times[numero2].getEstado() && numero == numero2){
                numero = gerador.nextInt(20);
                numero2 = gerador.nextInt(20);
            }
         
           
            aux = times[numero];
            times[numero] = times[numero2];
            
            //System.out.println(times[numero].getNome_() + times[numero].getidRef() + " x1 " + times[numero2].getNome_() + times[numero2].getidRef());
           
            times[numero].setidRef_(numero+1); // Muda a REF do primeiro time
            times[numero2] = aux;
           
              //   System.out.println(times[numero].getNome_() + times[numero].getidRef() + " x2 " + times[numero2].getNome_() + times[numero2].getidRef());
         
            times[numero2].setidRef_(numero2+1); // Muda a REF do segundo time
         //   System.out.println("Numero1: " + numero + "Numero2: " + numero2);
          
            //System.out.println(times[numero].getNome_() + times[numero].getidRef() + " x3 " + times[numero2].getNome_() + times[numero2].getidRef());
             
 for(int i=0; i<times.length;i++){
            times[i].setidRef_(i+1);
            //   System.out.println(times[i].setidRef_(i+1));
                //System.out.println(i + " - " + times[i].getidRef() + " --- " + times[i].getNome_());
                
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
    
}
