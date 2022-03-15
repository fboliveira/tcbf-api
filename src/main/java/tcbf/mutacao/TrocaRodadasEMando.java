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
public class TrocaRodadasEMando extends Mutation {

    Random gerador = new Random();

    public TrocaRodadasEMando(HashMap<String, Object> parameters) {
        super(parameters);

    }

    @Override
    public Object execute(Object object) throws JMException {

        Solution solution = (Solution) object;

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

        if (solution.getType().getClass() == TCBFSolutionType.class) {
            tamRodada = ((MatrizVariable) solution.getDecisionVariables()[1]).getTamRodada();
            QtdJogos = ((MatrizVariable) solution.getDecisionVariables()[1]).getQtdJogos();
            rodada = ((MatrizVariable) solution.getDecisionVariables()[1]).matriz;

            // Troca rodadas da 5 até 17
            int numero = gerador.nextInt(13) + 4;
            int numero2 = gerador.nextInt(13) + 4;
            //Não pode ser a msma rodada
            while (numero == numero2) {
                numero2 = gerador.nextInt(13) + 4;
            }

          //         System.out.println(numero + "----" + numero2);
            //Faz a troca de rodadas do primeiro turno
            for (int i = 0; i < QtdJogos; i++) {
                casa = rodada[numero][i].getCasa();
                fora = rodada[numero][i].getFora();
                //Salva rodada 2 na Rodada 1
                rodada[numero][i].SetCasa(rodada[numero2][i].getCasa());
                rodada[numero][i].SetFora(rodada[numero2][i].getFora());
                //Salva rodada 1 na Rodada 2
                rodada[numero2][i].SetCasa(casa);
                rodada[numero2][i].SetFora(fora);

                //Troca Rodada Segundo Turno (Salva Segunda Rodada Escolhida na Primeira Rodada Escolhida)
                rodada[numero + 19][i].SetCasa(rodada[numero2 + 19][i].getCasa());
                rodada[numero + 19][i].SetFora(rodada[numero2 + 19][i].getFora());
                //(Salva Primeira Rodada Escolhida na Segunda Rodada Escolhida)
                rodada[numero2 + 19][i].SetFora(casa);
                rodada[numero2 + 19][i].SetCasa(fora);

            }

           
        }
            //Verifica se tem jogos seguidos
            jogosSeguidos(rodada, solution);
    }

    public void jogosSeguidos(Rodada[][] rod, Solution solution) {
        int time;
        int casa;
        int fora;
        boolean seguido = false;
       
        for (time = 1; time < 21; time++) { // Troca o time
            //System.out.println("Time: " + time);
            for (int i = 3; i < 17; i++) { // Troca rodada apenas primeiro turno
//                System.out.println("Time: " + i);
                for (int j = 0; j < 10; j++) { // Jogos da Rodada
                    if (time == rod[i][j].getCasa()) { // Verifica se o time joga em casa
                        for (int k = 0; k < 10; k++) {
                            if (time == rod[i + 1][k].getCasa()) { // Verifica se na rodada seguinte ele joga em casa
                                for (int x = 0; x < 10; x++) {
                                    if (time == rod[i + 2][x].getCasa()) { // Verifica se na 3 rodada seguida ele joga em casa
                                        seguido = true;
                                        
                                       // System.out.println("Esse e o time: " + i);
                                        if ( i== 3){
                                            int aleatorio = gerador.nextInt(2);
                                            if (aleatorio == 0) {
                                            //Muda 3 rodada com erro
                                            int roda = i + 1;
                                            casa = rod[i + 1][k].getCasa();
                                            fora = rod[i + 1][k].getFora();

                                            rod[i + 1][k].SetCasa(fora);
                                            rod[i + 1][k].SetFora(casa);
                                            //Muda os mandos no segundo turno

                                            rod[roda + 19][k].SetCasa(casa);
                                            rod[roda + 19][k].SetFora(fora);

                                        } else {
                                            //Muda 3 rodada com erro
                                            int roda = i + 2;
                                            casa = rod[i + 2][x].getCasa();
                                            fora = rod[i + 2][x].getFora();

                                            rod[i + 2][x].SetCasa(fora);
                                            rod[i + 2][x].SetFora(casa);
                                            //Muda os mandos no segundo turno

                                            rod[roda + 19][x].SetCasa(casa);
                                            rod[roda + 19][x].SetFora(fora);

                                        }
                                        }else {
                                        int aleatorio = gerador.nextInt(3);
                                       // System.out.println("Esse e o time: " + i);
//                                        System.out.println(" - Jogo 1: " + rod[i][j].getCasa()+ " x " + rod[i][j].getFora());
//                                        System.out.println(" - Jogo 2: " + rod[i+1][k].getCasa()+ " x " + rod[i+1][k].getFora());
//                                        System.out.println(" - Jogo 3: " + rod[i+2][x].getCasa()+ " x " + rod[i+2][x].getFora());
                                        if (aleatorio == 0) {
                                            //Muda os mandos no primeiro Turno
                                            //Muda primeira rodada q da erro
                                            casa = rod[i][j].getCasa();
                                            fora = rod[i][j].getFora();

                                            rod[i][j].SetCasa(fora);
                                            rod[i][j].SetFora(casa);
                                            rod[i + 19][j].SetCasa(casa);
                                            rod[i + 19][j].SetFora(fora);

                                        } else if (aleatorio == 1) {
                                            //Muda 3 rodada com erro
                                            int roda = i + 1;
                                            casa = rod[i + 1][k].getCasa();
                                            fora = rod[i + 1][k].getFora();

                                            rod[i + 1][k].SetCasa(fora);
                                            rod[i + 1][k].SetFora(casa);
                                            //Muda os mandos no segundo turno

                                            rod[roda + 19][k].SetCasa(casa);
                                            rod[roda + 19][k].SetFora(fora);

                                        } else {
                                            //Muda 3 rodada com erro
                                            int roda = i + 2;
                                            casa = rod[i + 2][x].getCasa();
                                            fora = rod[i + 2][x].getFora();

                                            rod[i + 2][x].SetCasa(fora);
                                            rod[i + 2][x].SetFora(casa);
                                            //Muda os mandos no segundo turno

                                            rod[roda + 19][x].SetCasa(casa);
                                            rod[roda + 19][x].SetFora(fora);

                                        }
                                        }
                                        x = 10;
                                        k = 10;
                                        i = 17;
                                        j = 10;
//                                        System.out.println(" - Casa Jogo 1: " + rod[i][j].getCasa()+ " x " + rod[i][j].getFora());
//                                        System.out.println(" - Jogo 2: " + rod[i+1][k].getCasa()+ " x " + rod[i+1][k].getFora());
//                                        System.out.println(" - Jogo 3: " + rod[i+2][x].getCasa()+ " x " + rod[i+2][x].getFora());
//                                   

                                    }
                                }
                            }
                        }
                    } //Verifica se joga fora
                    else if (time == rod[i][j].getFora()) { // Verifica se o time joga Fora
                        for (int k = 0; k < 10; k++) {
                            if (time == rod[i + 1][k].getFora()) { // Verifica se na rodada seguinte ele joga Fora
                                for (int x = 0; x < 10; x++) {
                                    if (time == rod[i + 2][x].getFora()) { // Verifica se na 3 rodada seguida ele joga Fora
                                        //  System.out.println("Esse e o time: " + time);
                                        
                                        
                                        if ( i== 3){
                                              int aleatorio = gerador.nextInt(2);
                                            if (aleatorio == 0) {
                                            //Muda 3 rodada com erro
                                            int roda = i + 1;
                                            casa = rod[i + 1][k].getCasa();
                                            fora = rod[i + 1][k].getFora();

                                            rod[i + 1][k].SetCasa(fora);
                                            rod[i + 1][k].SetFora(casa);
                                            //Muda os mandos no segundo turno

                                            rod[roda + 19][k].SetCasa(casa);
                                            rod[roda + 19][k].SetFora(fora);

                                        } else {
                                            //Muda 3 rodada com erro
                                            int roda = i + 2;
                                            casa = rod[i + 2][x].getCasa();
                                            fora = rod[i + 2][x].getFora();

                                            rod[i + 2][x].SetCasa(fora);
                                            rod[i + 2][x].SetFora(casa);
                                            //Muda os mandos no segundo turno

                                            rod[roda + 19][x].SetCasa(casa);
                                            rod[roda + 19][x].SetFora(fora);

                                        }
                                        }else {
                                        
                                        int aleatorio = gerador.nextInt(3);

                                        if (aleatorio == 0) {
                                            //Muda os mandos no primeiro Turno
                                            //Muda primeira rodada q da erro
                                            casa = rod[i][j].getCasa();
                                            fora = rod[i][j].getFora();

                                            rod[i][j].SetCasa(fora);
                                            rod[i][j].SetFora(casa);
                                            rod[i + 19][j].SetCasa(casa);
                                            rod[i + 19][j].SetFora(fora);

                                        } else if (aleatorio == 1) {
                                            //Muda 3 rodada com erro
                                            int roda = i + 1;
                                            casa = rod[i + 1][k].getCasa();
                                            fora = rod[i + 1][k].getFora();

                                            rod[i + 1][k].SetCasa(fora);
                                            rod[i + 1][k].SetFora(casa);
                                            //Muda os mandos no segundo turno

                                            rod[roda + 19][k].SetCasa(casa);
                                            rod[roda + 19][k].SetFora(fora);

                                        } else {
                                            //Muda 3 rodada com erro
                                            int roda = i + 2;
                                            casa = rod[i + 2][x].getCasa();
                                            fora = rod[i + 2][x].getFora();

                                            rod[i + 2][x].SetCasa(fora);
                                            rod[i + 2][x].SetFora(casa);
                                            //Muda os mandos no segundo turno

                                            rod[roda + 19][x].SetCasa(casa);
                                            rod[roda + 19][x].SetFora(fora);

                                        }}
                                        seguido = true;
                                        x = 10;
                                        k = 10;
                                        i = 17;
                                        j = 10;
//                                        System.out.println(" - Jogo 1: " + rod[i][j].getCasa()+ " x " + rod[i][j].getFora());
//                                        System.out.println(" - Jogo 2: " + rod[i+1][k].getCasa()+ " x " + rod[i+1][k].getFora());
//                                        System.out.println(" - Jogo 3: " + rod[i+2][x].getCasa()+ " x " + rod[i+2][x].getFora());

                                    }
                                }
                            }
                        }
                    }

                }
            }
            if (seguido == true) {
                time = 0;
                seguido = false;
                //System.out.println("Entrou aki..........");
            }
        }
    
    }

}
