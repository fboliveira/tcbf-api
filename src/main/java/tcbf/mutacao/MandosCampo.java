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
public class MandosCampo extends Mutation{
    
   
    
    public MandosCampo(HashMap<String, Object> parameters){
         super(parameters);

        
    }

    @Override
    public Object execute(Object object) throws JMException {
       Solution solution = (Solution)object;

       this.doMutation(solution);
        return solution;
    }

    private void doMutation(Solution solution) throws JMException {
        Rodada rodada[][] = null;
      //  Rodada troca;
        Times times[];
        int tamRodada;
        int QtdJogos;
        int casa;
        int fora;
        
        
        if (solution.getType().getClass() == TCBFSolutionType.class){
            tamRodada = ((MatrizVariable) solution.getDecisionVariables()[1]).getTamRodada();
            QtdJogos = ((MatrizVariable) solution.getDecisionVariables()[1]).getQtdJogos();
            rodada = ((MatrizVariable) solution.getDecisionVariables()[1]).matriz;
            times = ((ArrayTime) solution.getDecisionVariables()[0]).getArray();
            
            Random gerador = new Random();
           int numero = gerador.nextInt(19);
           int numero2 = gerador.nextInt(10);
            
           // System.out.println(numero + " --- " + numero2);
            
            casa = rodada[numero][numero2].getCasa();
            fora = rodada[numero][numero2].getFora();
            
            //troca = new Rodada(fora, casa);
            // Inverte Primeiro Turno
            rodada[numero][numero2].SetCasa(fora);
            rodada[numero][numero2].SetFora(casa);
            //Inverte Segundo Turno
            rodada[numero+19][numero2].SetCasa(casa);
            rodada[numero+19][numero2].SetFora(fora);
            //rodada[numero][numero2].setTime(times);
            
        }
        else {
            Configuration.logger_.severe("MandosCampo.doMutation: invalid type. "
                    + "" + solution.getDecisionVariables()[0].getVariableType());

            Class cls = java.lang.String.class;
            String name = cls.getName();
            throw new JMException("Exception in " + name + ".doMutation()");
        }   
        
    }
    
}
