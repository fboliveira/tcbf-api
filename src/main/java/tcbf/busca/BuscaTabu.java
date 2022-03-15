/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcbf.busca;

import java.util.Random;
import jmetal.core.Algorithm;
import jmetal.core.Solution;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import tcbf.Rodada;
import tcbf.TCBFSolutionType;
import tcbf.Times;
import tcbf.Variable.ArrayTime;
import tcbf.Variable.MatrizVariable;
import jmetal.core.Problem;
import jmetal.core.SolutionSet;

/**
 *
 * @author Alexandre
 */
public class BuscaTabu {

    public ListaTabu ltabu;
    int tam;
    int maxiteracao;
    Problem problem_;

    public BuscaTabu(Problem problem) {
        this.problem_ = problem;
        this.tam = 10;
        this.maxiteracao = 5;

    }

    public Solution execute(Object object, double fitGlobal) throws JMException {
        Solution solution = (Solution) object;
        Solution solutionCorrente = new Solution(solution);
        int numeroIteracao = 0;
        int iteracaoSemMelhora = 0;
        int mfoc; //Melhor função objetivo corrente
        MovimentoTrocaTime mmtt; // Melhor MovimentoTrocaTime
        ltabu = new ListaTabu(tam);// Cria Lista Tabu
//        System.out.println(solutionCorrente.getObjective(0) + " Fitness Tabu");
        while (iteracaoSemMelhora < maxiteracao) {
            numeroIteracao++;
            iteracaoSemMelhora++;
            mmtt = melhortrocatime(solutionCorrente, fitGlobal);//Encontra o melhor movimento
            ltabu.add(mmtt);// add Movimento na Lista
            mfoc = trocatime(mmtt.getTimeA(), mmtt.getTimeB(), solutionCorrente);//Melhor resultado do movimento            
           // System.out.println(mfoc + " Fitness Tabu MELHOR RESULTADO");
            //System.out.println(solutionCorrente.getObjective(0) + " Corrente Antes");
            solutionCorrente = melhorSolucao(mmtt, solutionCorrente);
           // System.out.println(solutionCorrente.getObjective(0) + " Corrente Depois");
            if (mfoc < solution.getObjective(0)) { //se melhor resultado do movimento corrente for melhor que a soluçao global
                solution = solutionCorrente;
                //System.out.println("OI " + solution.getObjective(0) + "RESULTADO PARCIAL AKI");
                iteracaoSemMelhora = 0;
            }
        }
         //System.out.println("OI " + solution.getObjective(0) + "RESULTADO FINAL--------------- AKI");
          
              
        return solution;
    }//Fim Execute

    public MovimentoTrocaTime melhortrocatime(Solution corrente, double fitGlobal) throws JMException {
        double melhorfc; //melhor fitness corrente
        double melhorf = corrente.getObjective(0);
        int k = 1;//Variavel para controlar J, para não Repetir times
        MovimentoTrocaTime mmtt = new MovimentoTrocaTime(0, 0);
        MovimentoTrocaTime teste = new MovimentoTrocaTime(0, 0); // Verifica Tabu
        
        for (int i = 0; i < 19; i++) {
                
            for ( int j = k ; j < 20; j++) {
                if (i != j) {                   
                      
                    melhorfc = trocatime(i, j, corrente);
                    
                   
                    if (melhorfc < melhorf) {
                        teste.setTimeA(i);
                        teste.setTimeB(j);
                        if (!ltabu.contains(teste)) {
                           // System.out.println("ENTROU AKI");
                            //System.out.println(melhorf + " - " + melhorfc);

                            melhorf = melhorfc;
                            mmtt.setTimeA(i);
                            mmtt.setTimeB(j);
                        }   else if ( melhorfc < fitGlobal && ltabu.contains(teste)){
                            System.out.println("Movimento Aspirado");
                            
                            melhorf = melhorfc;
                            mmtt.setTimeA(i);
                            mmtt.setTimeB(j);
                        }
                    }

                }
            }
            k++;
        }
             return mmtt;
    }

    public int trocatime(int time1, int time2, Solution corrente) throws JMException {
        Times times[] = null;
        Times aux = null;
        int tamRodada;
        int QtdJogos;
        Rodada rodada[][] = null;
        int numero = time1;
        int numero2 = time2;
        Solution cf = new Solution(corrente);

        int permutationLength;
        if (cf.getType().getClass() == TCBFSolutionType.class) {
            permutationLength = ((ArrayTime) cf.getDecisionVariables()[0]).getLength();
            //  System.out.println("-------" + solution.getDecisionVariables()[1]);

            times = ((ArrayTime) cf.getDecisionVariables()[0]).getArray();
            tamRodada = ((MatrizVariable) cf.getDecisionVariables()[1]).getTamRodada();
            QtdJogos = ((MatrizVariable) cf.getDecisionVariables()[1]).getQtdJogos();
            rodada = ((MatrizVariable) cf.getDecisionVariables()[1]).matriz;

            //   System.out.println(numero + " --- " + numero2);
            //int cont = 0;
            aux = times[numero];
            times[numero] = times[numero2];

            //System.out.println(times[numero].getNome_() + times[numero].getidRef() + " x1 " + times[numero2].getNome_() + times[numero2].getidRef());
            times[numero].setidRef_(numero + 1); // Muda a REF do primeiro time
            times[numero2] = aux;

            //   System.out.println(times[numero].getNome_() + times[numero].getidRef() + " x2 " + times[numero2].getNome_() + times[numero2].getidRef());
            times[numero2].setidRef_(numero2 + 1); // Muda a REF do segundo time
            //   System.out.println("Numero1: " + numero + "Numero2: " + numero2);

            //System.out.println(times[numero].getNome_() + times[numero].getidRef() + " x3 " + times[numero2].getNome_() + times[numero2].getidRef());
            for (int i = 0; i < times.length; i++) {
                times[i].setidRef_(i + 1);
                //   System.out.println(times[i].setidRef_(i+1));
                //System.out.println(i + " - " + times[i].getidRef() + " --- " + times[i].getNome_());

                ((ArrayTime) cf.getDecisionVariables()[0]).setValue(i, times[i]);
                ((ArrayTime) cf.getDecisionVariables()[0]).setValue(i, times[i]);
            }

        } else {
            Configuration.logger_.severe("TrocaTimes.doMutation: invalid type. "
                    + "" + cf.getDecisionVariables()[0].getVariableType());

            Class cls = java.lang.String.class;
            String name = cls.getName();
            throw new JMException("Exception in " + name + ".trocatime()");
        }
    //    System.out.println(" -- " + corrente.getObjective(0));
        problem_.evaluate(cf);
        problem_.evaluateConstraints(cf);
      //  System.out.println(" ---- " + cf.getObjective(0));

        return (int) cf.getObjective(0);
    }

    public Solution melhorSolucao(MovimentoTrocaTime mtt, Solution corrente) throws JMException {
        Times times[] = null;
        Times aux = null;
        int tamRodada;
        int QtdJogos;
        Rodada rodada[][] = null;
        int numero = mtt.getTimeA();
        int numero2 = mtt.getTimeB();
        Solution cf = new Solution(corrente);

        int permutationLength;
        if (cf.getType().getClass() == TCBFSolutionType.class) {
            permutationLength = ((ArrayTime) cf.getDecisionVariables()[0]).getLength();
            //  System.out.println("-------" + solution.getDecisionVariables()[1]);

            times = ((ArrayTime) cf.getDecisionVariables()[0]).getArray();
            tamRodada = ((MatrizVariable) cf.getDecisionVariables()[1]).getTamRodada();
            QtdJogos = ((MatrizVariable) cf.getDecisionVariables()[1]).getQtdJogos();
            rodada = ((MatrizVariable) cf.getDecisionVariables()[1]).matriz;

            //   System.out.println(numero + " --- " + numero2);
            //int cont = 0;
            aux = times[numero];
            times[numero] = times[numero2];

            //System.out.println(times[numero].getNome_() + times[numero].getidRef() + " x1 " + times[numero2].getNome_() + times[numero2].getidRef());
            times[numero].setidRef_(numero + 1); // Muda a REF do primeiro time
            times[numero2] = aux;

            //   System.out.println(times[numero].getNome_() + times[numero].getidRef() + " x2 " + times[numero2].getNome_() + times[numero2].getidRef());
            times[numero2].setidRef_(numero2 + 1); // Muda a REF do segundo time
            //   System.out.println("Numero1: " + numero + "Numero2: " + numero2);

            //System.out.println(times[numero].getNome_() + times[numero].getidRef() + " x3 " + times[numero2].getNome_() + times[numero2].getidRef());
            for (int i = 0; i < times.length; i++) {
                times[i].setidRef_(i + 1);
                //   System.out.println(times[i].setidRef_(i+1));
                //System.out.println(i + " - " + times[i].getidRef() + " --- " + times[i].getNome_());

                ((ArrayTime) cf.getDecisionVariables()[0]).setValue(i, times[i]);
                ((ArrayTime) cf.getDecisionVariables()[0]).setValue(i, times[i]);
            }

        } else {
            Configuration.logger_.severe("TrocaTimes.doMutation: invalid type. "
                    + "" + cf.getDecisionVariables()[0].getVariableType());

            Class cls = java.lang.String.class;
            String name = cls.getName();
            throw new JMException("Exception in " + name + ".trocatime()");
        }
      //  System.out.println(" -- " + corrente.getObjective(0));
        problem_.evaluate(cf);
        problem_.evaluateConstraints(cf);
        //System.out.println(" ---- " + cf.getObjective(0));
        return cf;
    }

}
