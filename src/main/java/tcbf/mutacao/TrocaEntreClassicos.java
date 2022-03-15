/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcbf.mutacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
public class TrocaEntreClassicos extends Mutation {

    Random gerador = new Random();

    public TrocaEntreClassicos(HashMap<String, Object> parameters) {
        super(parameters);
    }

    @Override
    public Object execute(Object object) throws JMException {
        Solution solution = (Solution) object;

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

            int numero = gerador.nextInt(20);
            int numero2 = gerador.nextInt(20);
            while (numero == numero2) {
                numero2 = gerador.nextInt(20);
            }

            aux = times[numero];
            times[numero] = times[numero2];
            times[numero].setidRef_(numero + 1); // Muda a REF do primeiro time
            times[numero2] = aux;
            times[numero2].setidRef_(numero2 + 1); // Muda a REF do segundo time

            classicosTresPrimeiras(times, rodada);

            for (int i = 0; i < times.length; i++) {
                times[i].setidRef_(i + 1);
                ((ArrayTime) solution.getDecisionVariables()[0]).setValue(i, times[i]);
                ((ArrayTime) solution.getDecisionVariables()[0]).setValue(i, times[i]);
            }

            // classicosTresPrimeirasTeste(times,rodada);
        } else {
            Configuration.logger_.severe("TrocaTimes.doMutation: invalid type. "
                    + "" + solution.getDecisionVariables()[0].getVariableType());

            Class cls = java.lang.String.class;
            String name = cls.getName();
            throw new JMException("Exception in " + name + ".doMutation()");
        }

    }

    private void classicosTresPrimeiras(Times[] times, Rodada[][] rod) {
        //Auxiliar para trocar time
        Times aux = null;
        //Array para armzenar times dos classicos
        ArrayList<Integer> classicos = new ArrayList();
        int k = 0;
        int x = 0;
        int j = 0;
        int i = 0;
        
        // Variavel usada para não deixar o programa entrar em looping
        int cont = 0; 
        //Verificar se os classicos sao do msmo estado
        boolean clas = false; 

        //Rodada            
        while (i < 3) {
            //Jogo
            while (j < 10) {

                //System.out.println("I: " + i + " J: " + j);
                
                //Condição que verifica se é classico
                if (times[rod[i][j].getCasa() - 1].getClassico() == times[rod[i][j].getFora() - 1].getClassico()) {
                        //pega posiçoes dos times
                        x = rod[i][j].getCasa() - 1;
                        k = rod[i][j].getFora() - 1;
                    
                        //Adiciona posiçoes dos times em um array
                        classicos.add(x);                    
                        classicos.add(k);
                    

                }
                //Verifica se ja rodou as 3 rodadas e se o array com as posiçoes dos times classicos nao esta vazia
                if (i == 2 && j == 9 && !classicos.isEmpty()) {
                    
                    // Enquanto houver time no ArrayList
                    while (!classicos.isEmpty()) {
                        //System.out.println("Entrei aki");
                        //Verifica se os classicos sao do mesmo estado    
                        clas = classicodomsmoestado(times, classicos);

                        // Se tiver mais que dois times no Array é pq tem mais de um classico 
                        // Se for false e pq Existe Classicos de estados diferentes
                        // Cont é para não entrar em looping
                        if (classicos.size() > 2 && clas == false && cont < 5) {

                            // System.out.println("Estou aki " + classicos.size());
                            // x=2 para que não troque com o primeiro classico
                            x = 2;
                            // Enquanto não trocar todos os times -1
                            // Abrange toda troca possivel
                            while (x < classicos.size() - 1) {
                                //Verifica se é classico
                                if (times[classicos.get(0)].getClassico() != times[classicos.get(x)].getClassico()) {
                                    //Troca os times
                                    //System.out.println(x + " Time1: " + times[classicos.get(0)].getNome_() + " Time2: "+ times[classicos.get(1)].getNome_() + classicos.size());
                                    
                                     //Sorteia se vai troca time da casa ou time fora de casa
                                    int indexTime = gerador.nextInt(2);  
                                   //Troca time da casa
                                    if(indexTime == 0){                            
                                    
                                    aux = times[classicos.get(0)];
                                    times[classicos.get(0)] = times[classicos.get(x)];
                                    times[classicos.get(0)].setidRef_(x);
                                    times[classicos.get(x)] = aux;
                                    times[classicos.get(x)].setidRef_(x);
                                    int h = 0;
                                    
                                    //Verifica quais times ainda são classicos
                                    while(h<classicos.size()-1){
                                        if (times[classicos.get(h)].getClassico() != times[classicos.get(h+1)].getClassico()){
                                         //System.out.println("Antes -- " + "Time1: " + times[classicos.get(h)].getNome_() + "Time2: "+ times[classicos.get(h+1)].getNome_());
                                            
                                         classicos.remove(h+1);
                                         classicos.remove(h);
                                         
                                         //System.out.println("Depois -- " + "Time1: " + times[classicos.get(h)].getNome_() + "Time2: "+ times[classicos.get(h+1)].getNome_());
                                            
                                  
                                        }else{
                                            //Passa para o proximo jogo
                                            h+=2;
                                        }
                                    }
                                    }
                                    //Troca os times fora de casa 
                                    else if(indexTime == 1){
                                    aux = times[classicos.get(1)];
                                    times[classicos.get(1)] = times[classicos.get(x+1)];
                                    times[classicos.get(1)].setidRef_(x+1);
                                    times[classicos.get(x+1)] = aux;
                                    times[classicos.get(x+1)].setidRef_(x+1);
                                    int h = 0;
                                    
                                    //Verifica quais times ainda são classicos
                                    while(h<classicos.size()-1){
                                        if (times[classicos.get(h)].getClassico() != times[classicos.get(h+1)].getClassico()){
                                         //System.out.println("Antes -- " + "Time1: " + times[classicos.get(h)].getNome_() + "Time2: "+ times[classicos.get(h+1)].getNome_());
                                            
                                         classicos.remove(h+1);
                                         classicos.remove(h);
                                         
                                         //System.out.println("Depois -- " + "Time1: " + times[classicos.get(h)].getNome_() + "Time2: "+ times[classicos.get(h+1)].getNome_());
                                            
                                  
                                        }else{
                                            //Passa para o proximo jogo
                                            h+=2;
                                        }
                                    }
                                        
                                        
                                    }
                                    
                                    }else{
                                x+=2;
                                }
                            }
                            
                          //  System.out.println("Passei aki");
                            
                            //Contador para evitar Looping
                            // Times trocando entre si sempre.
                            cont++;

                        }//Fim IF classicos.size() > 2
                        //Se houver apenas um classico ou Se houver apenas classicos do msmo estado ou se houver mais de cinco trocas
                        else if ( classicos.size() == 2 ||cont >= 5 || classicos.size() != 1 && clas == true ) {
                            //Gera um time aleatorio
                            k = gerador.nextInt(20);
                           // System.out.println("Estou aki");
                            for (x = 0; x < classicos.size() - 1; x++) {
                                //Enquanto não encontrar um time que não seja classico
                                while (times[classicos.get(x)].getClassico() == times[k].getClassico()) {
                                    k = gerador.nextInt(20);
                                }
                                //Troca os times
                                aux = times[classicos.get(x)];
                                times[classicos.get(x)] = times[k];
                                times[k] = aux;
                            }// Fim do FOR
                            //Apaga a lista
                            classicos.removeAll(classicos);
                            cont = 0;
                        //   System.out.println("Aki sempre 0: " + classicos.size());

                        } else {
                            //Caso sobrar somente 1 time
                        //    System.out.println("Entrou aki - - - " + classicos.size());
                            classicos.removeAll(classicos);
                        }

                    } //Fim do While

                    i = 0;
                    j = 0;
                } //Fim do IF
                else {
                    j++;
                }
            }
            i++;
            j = 0;
        }

    }

    private boolean classicodomsmoestado(Times[] times, ArrayList<Integer> classicos) {
        boolean clas = true;

        for (int l = 0; l < classicos.size(); l++) {
            if (times[classicos.get(l)].getClassico() != times[classicos.get(0)].getClassico()) {
                clas = false;
            }
        }

        return clas;
    }

}
