/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcbf;

import java.io.BufferedWriter;

import java.io.FileOutputStream;
import java.io.IOException;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import jmetal.core.Algorithm;
import jmetal.core.Operator;
import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.comparators.ObjectiveComparator;

import tcbf.busca.BuscaTabu;

/**
 *
 * @author Alexandre
 */
public class TCBF1 extends Algorithm {

    int mu_;
    int lambda_;

    public TCBF1(Problem problem, int mu, int lambda) {
        super(problem);
        mu_ = mu;
        lambda_ = lambda;
    }

    @Override
    public SolutionSet execute() throws JMException, ClassNotFoundException {
        int maxEvaluations, evaluations, blPorcentagem, valor;
        ArrayList<Double> melhoresIndividuos = new ArrayList();
        Operator mutationOperator;
        Operator mutationTime;
        Operator mutationRodada;
        Operator mutationRegional;
        Operator mutationTrocaOutroEstado;
        Operator mutationEntreClassicos;
        Operator mutationRodadaMando;
        Comparator dominance;
        BuscaTabu busca = new BuscaTabu(problem_);
        SolutionSet population;
        SolutionSet offspringPopulation;

        Random gerador = new Random();
        //  ArrayList< > cont = new ArrayList();
        //int cont[] = new int[8];

        //Le os parametros
        maxEvaluations = ((Integer) this.getInputParameter("maxEvaluations")).intValue();
//        blPorcentagem = ((Integer) this.getInputParameter("blPorcentagem")).intValue();

        // Initialize the variables
        population = new SolutionSet(mu_);
        offspringPopulation = new SolutionSet(mu_ + lambda_);

        //Read the operators        
        mutationOperator = this.operators_.get("mutation");
        mutationTime = this.operators_.get("troca");
        mutationRodada = this.operators_.get("rodada");
        mutationRegional = this.operators_.get("regional");
        mutationTrocaOutroEstado = this.operators_.get("outroestado");
        mutationEntreClassicos = this.operators_.get("entreclassicos");
        mutationRodadaMando = this.operators_.get("trocarodadamando");
        //Initialize the variables                
        evaluations = 0;
        dominance = new ObjectiveComparator(0);

        //-> Cria Solução Inicial
        Solution newIndividual;
        for (int i = 0; i < mu_; i++) {
            newIndividual = new Solution(problem_);
            //mutationRegional.execute(newIndividual);
            problem_.evaluate(newIndividual);
            problem_.evaluateConstraints(newIndividual);
            //TODO: implements contraints and evaluate.
            evaluations++;

            population.add(newIndividual);

            //mutationOperator.execute(newIndividual);
            //mutationTime.execute(newIndividual);
            //mutationRodada.execute(newIndividual);
        } //for 
        population.sort(dominance);

        int offsprings;
        offsprings = lambda_ / mu_;//Descendente
//        System.out.println("A partir daqui nao conta mais - - - - - - - - - - - - - - - - -- -  --  -- - - - - - ");
        while (evaluations < maxEvaluations) {
            // STEP 1. Gera MU + LAMBDA população
            for (int i = 0; i < mu_; i++) {//Mutação da População
                for (int j = 0; j < offsprings; j++) {
                    //   System.out.println("MUDOU  - - - - - - - -- - - - - - - - - - - -");
                  
                    Solution offspring = new Solution(population.get(i));
                    
                    //  mutationRegional.execute(offspring);

                    mutationRodadaMando.execute(offspring);
                    //mutationEntreClassicos.execute(offspring);
                    //mutationOperator.execute(offspring);
//                    mutationTime.execute(offspring);
                    //mutationRodada.execute(offspring);
                    mutationTrocaOutroEstado.execute(offspring);
                    //valor = gerador.nextInt(100);

                    problem_.evaluate(offspring);
                    problem_.evaluateConstraints(offspring);

//                   // System.out.println("OFFSPRING ANTES " + offspring.getObjective(0));
//                    if (valor < blPorcentagem){
//                        
//                   //  System.out.println("Busca Tabu -> " + offspring.getObjective(0));
//                    offspring =  busca.execute(offspring, population.get(0).getObjective(0));
//                    }
                    //System.out.println("OFFSPRING DEPOIS " + offspring.getObjective(0));
                    offspringPopulation.add(offspring);
                    evaluations++;
                } // for
            } // for

            // STEP 2. Junta toda população Descendentes + MU
            for (int i = 0; i < mu_; i++) {
                offspringPopulation.add(population.get(i));//Adiciona toda população para comparação
            } // for
            population.clear();

            // STEP 3. Sort the mu+lambda population
            offspringPopulation.sort(dominance); //Compara toda população

            //melhoresIndividuos.add(offspringPopulation.get(0).getObjective(0));
            // STEP 4. Cria a nova mu população
            for (int i = 0; i < mu_; i++) {
                population.add(offspringPopulation.get(i));//Salva os mu melhores da população
            }

            System.out.println("Evaluation: " + evaluations + " Fitness: "
                    + population.get(0).getObjective(0));
            //System.out.println(population.get(0).getDecisionVariables()[0].toString());

            // STEP 6. Delete the mu+lambda population
            offspringPopulation.clear();
        } // while
//        SolutionSet resultPopulation = new SolutionSet(1);
//        resultPopulation.add(population.get(0));

        //resultPopulation.printObjectivesToFile("Objetivo");
        //resultPopulation.printVariablesToFile("VAR"); 
//-----------------------------------------------------------------------\\        
        // Executa o Operador de Mutação Regional nos melhores resultados
//        for (int i = 0; i < population.size(); i++) {
//            Solution offspring = new Solution(population.get(i));
//            mutationRegional.execute(offspring);
//            problem_.evaluate(offspring);
//            problem_.evaluateConstraints(offspring);
//            offspringPopulation.add(offspring);
//            offspringPopulation.add(population.get(i));
//            System.out.println("Evaluation: " + evaluations + " Fitness: "
//                    + population.get(i).getObjective(0) + "Regional " + offspring.getObjective(0));
//        }
//        offspringPopulation.sort(dominance);
//        population.clear();
//        for (int i = 0; i < mu_; i++) {
//            population.add(offspringPopulation.get(i));//Salva os mu melhores da população
//        }
//
//        System.out.println("Evaluation: " + evaluations + " Fitness: "
//                + population.get(0).getObjective(0));
//        //System.out.println(population.get(0).getDecisionVariables()[0].toString());
//
//        // STEP 6. Delete the mu+lambda population
//        offspringPopulation.clear();

       // printMelhorIndividuo(melhoresIndividuos);

        return population;
    }  // execute 

    private void printMelhorIndividuo(ArrayList<Double> melhoresIndividuos) {

        try {
            /* Open the file */
            FileOutputStream fos = new FileOutputStream("Individuos.txt");
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);

            for (int i = 0; i < melhoresIndividuos.size(); i++) {
                //if (this.vector[i].getFitness()<1.0) {
                bw.write(melhoresIndividuos.get(i).toString());
                bw.newLine();
                //}
            }

            /* Close the file */
            bw.close();
        } catch (IOException e) {
            Configuration.logger_.severe("Error acceding to the file");
            e.printStackTrace();
        }

        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
