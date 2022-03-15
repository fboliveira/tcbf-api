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
import jmetal.util.JMException;
import tcbf.Rodada;
import tcbf.TCBFSolutionType;
import tcbf.Variable.MatrizVariable;

/**
 *
 * @author Alexandre
 */
public class TrocaRodadas extends Mutation{
    
   
    
    public TrocaRodadas(HashMap<String, Object> parameters){
        super(parameters);

       
    }

    @Override
    public Object execute(Object object) throws JMException {
       
        Solution solution = (Solution)object;

       this.doMutation(solution);
        return solution;

// throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void doMutation(Solution solution) {
         Rodada rodada[][] = null;
         Rodada auxTroca[] = new Rodada[10];
         int tamRodada;
         int QtdJogos;
         int casa, fora;
    
          if (solution.getType().getClass() == TCBFSolutionType.class){
            tamRodada = ((MatrizVariable) solution.getDecisionVariables()[1]).getTamRodada();
            QtdJogos = ((MatrizVariable) solution.getDecisionVariables()[1]).getQtdJogos();
            rodada = ((MatrizVariable) solution.getDecisionVariables()[1]).matriz;
          
            Random gerador = new Random();
            int numero = gerador.nextInt(19);
            int numero2 = gerador.nextInt(19);
            
            while( numero == numero2){
              numero2 = gerador.nextInt(19);
            }
            
    //        System.out.println(numero + "----" + numero2);
            
          for(int i = 0; i<QtdJogos;i++){
              casa = rodada[numero][i].getCasa();
              fora = rodada[numero][i].getFora();
              //Salva rodada 2 na Rodada 1
              rodada[numero][i].SetCasa(rodada[numero2][i].getCasa()); 
              rodada[numero][i].SetFora(rodada[numero2][i].getFora()); 
              //Salva rodada 1 na Rodada 2
              rodada[numero2][i].SetCasa(casa);
              rodada[numero2][i].SetFora(fora);
              
              //Troca Rodada Segundo Turno (Salva Segunda Rodada Escolhida na Primeira Rodada Escolhida)
              rodada[numero+19][i].SetCasa(rodada[numero2+19][i].getCasa()); 
              rodada[numero+19][i].SetFora(rodada[numero2+19][i].getFora()); 
              //(Salva Primeira Rodada Escolhida na Segunda Rodada Escolhida)
              rodada[numero2+19][i].SetFora(casa);
              rodada[numero2+19][i].SetCasa(fora);

                  
          }
                
          }
    
    }
    
}
