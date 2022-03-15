/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcbf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.Variable;
import jmetal.util.JMException;
import tcbf.Variable.ArrayTime;
import tcbf.Variable.MatrizVariable;

/**
 *
 * @author Alexandre
 */
public class Problema extends Problem {

    // public Distancia distancia_[] = new Distancia[121];
    private MatrizTempo distancia;
    private ManipuladorArquivo manipula;
    private int cont[] = new int[9];

    public Problema(Integer numberOfVariables, Integer index1, Integer index2) {

        numberOfVariables_ = numberOfVariables;
        numberOfObjectives_ = 1;
        numberOfConstraints_ = 8;
        problemName_ = "TCBF";

        solutionType_ = new TCBFSolutionType(this, numberOfVariables_, index1, index2);
        
//        for (int i=0; i<8;i++){
//            cont[i] = 0;
//        }

    }
    
    @Override
    public void evaluate(Solution solution) throws JMException {
        manipula = new ManipuladorArquivo();
        int k;
        int l, y;
        int dist = 0;
        int timeLength = 0; //Qtd de times
        int rodadaLength = 0; // Qtd de Rodadas
        int jogoLength = 0; // Qtd de Jogos
        Times times[] = null;
        Rodada rod[][] = null;

        try {
            distancia = new MatrizTempo(12);
//            for (int i = 0; i < 121; i++) {
//                distancia_[i] = manipula.preencheDistancia(i);
//                //System.out.println(distancia_[i].getLocalizacaoAtual() + "-" + distancia_[i].getLocalizacaoDestino() + "-" + i);
//            }

            if (solution.getType().getClass() == TCBFSolutionType.class) {
                timeLength = ((ArrayTime) solution.getDecisionVariables()[0]).getLength();
                times = ((ArrayTime) solution.getDecisionVariables()[0]).getArray();
                rod = ((MatrizVariable) solution.getDecisionVariables()[1]).matriz;
                rodadaLength = ((MatrizVariable) solution.getDecisionVariables()[1]).getTamRodada();
                jogoLength = ((MatrizVariable) solution.getDecisionVariables()[1]).getQtdJogos();
            }
            
         

            //System.out.println(solution.getDecisionVariables()[0]);
            //System.out.println("RODADA: " + rod[0][9].getCasa() + "x" + rod[0][9].getFora());
            for (int i = 0; i < rodadaLength; i++) {// Rodada
                for (int j = 0; j < jogoLength; j++) {//jogos da rodada
                    k = 0;
                    l = 0;
                    y = 0;

                    //System.out.println(i + "-----------------" + j );
                    while (rod[i][j].getCasa() != times[k].getidRef()) {//Encontra o time da casa
                        //           System.out.println(k);
                        k++;
                    }

                    while (rod[i][j].getFora() != times[l].getidRef()) { //Encontra o time visitante
                        // System.out.println(rod[i][j].getFora() +  "--" + times[l].getidRef());
                        l++;
                    }

                    //    System.out.println(times[k].getNome_() + times[l].getNome_() );
//                    while (!distancia_[y].getLocalizacaoAtual().equals(times[k].getLocalizacao_()) && !distancia_[y].getLocalizacaoDestino().equals(times[k].getCasa_())) {
//                        y++;
//                    }
                    times[k].setTempo_(distancia.retornaTempo(times[k].getCasa_(), times[k].getLocalizacao_()) + times[k].getTempo_());// Atualiza o Tempo do time da casa
                    times[k].setLocalizacao_(times[k].getCasa_());
                    // y = 0;

//                    while (!distancia_[y].getLocalizacaoAtual().equals(times[l].getLocalizacao_()) && !distancia_[y].getLocalizacaoDestino().equals(times[k].getCasa_())) {
//                        y++;
//                    }
                    int temp = distancia.retornaTempo(times[k].getCasa_(), times[l].getLocalizacao_()) + times[l].getTempo_();

                    // int temp = distancia_[y].getDistancia_() + times[l].getTempo_();
                    times[l].setTempo_(temp);// Distancia do time de fora
                    times[l].setLocalizacao_(times[k].getCasa_());
                }//Fim for Jogos Rodada
            }// Fim for Rodada

            for (int i = 0; i < times.length; i++) {
                //System.out.println("Teste: " + distancia_[i].getDistancia_());
                dist += times[i].getTempo_();
                //System.out.println( times[i].getNome_() + times[i].getTempo_());

            }

            solution.setObjective(0, dist);
           // solution.setDecisionVariables(solution.getDecisionVariables());

        } catch (IOException ex) {
            Logger.getLogger(Problema.class.getName()).log(Level.SEVERE, null, ex);
        }

//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void evaluateConstraints(Solution solution) {

        int timeLength = 0; //Qtd de times
        int rodadaLength = 0; // Qtd de Rodadas
        int jogoLength = 0; // Qtd de Jogos
        Times times[] = null;
        Rodada rod[][] = null;

        if (solution.getType().getClass() == TCBFSolutionType.class) {
            timeLength = ((ArrayTime) solution.getDecisionVariables()[0]).getLength();
            times = ((ArrayTime) solution.getDecisionVariables()[0]).getArray();
            rod = ((MatrizVariable) solution.getDecisionVariables()[1]).matriz;
            rodadaLength = ((MatrizVariable) solution.getDecisionVariables()[1]).getTamRodada();
            jogoLength = ((MatrizVariable) solution.getDecisionVariables()[1]).getQtdJogos();
        }
        /*
         Restrição das 4 Primeiras Rodadas (Jogos Invertidos)
         */
        primeirasRodadas(rod, solution); // Primeira Restrição
        /*
         Restrição das duas ultimas rodadas (Jogos Invertidos)
         */
        ultimasRodadas(rod, solution);
        /*
        Restrição: os times so podem jogar 2 rodadas seguidas em casa ou fora de casa
         */
        jogosSeguidos(rod, solution);

        /*
        Restrição: Classicos nas 4 Ultimas rodadas
         */
        classicosQuatroUltimas(times,rod,solution);
        /*
        Não pode Haver mais de um classico no mesmo estado na mesma Rodada
         */
        classicosMesmaRodada(times, rod, solution);

        /*
        Restrição: Não pode haver classicos nas 3 primeiras rodadas
         */
        classicosTresPrimeiras(times, rod, solution);

        /*
         Restrição: Não pode haver jogos regionais nas 4 ultimas rodadas
         */
        regionaisQuatroUltimas(times, rod, solution);

        /*
         Restrição: Não pode haver 2 classicos seguidos do mesmo time
         */
        classicosSeguidos(times, rod, solution);
        
        /*
        Restriçao: Em umas das duas primeiras partidas o time deve jogar fora de seu ESTADO 
        Restrição Quebrada pela tabela de 2016.
        */
        duasPrimeirasPartidas(times,rod,solution);

        //System.out.println(solution.getObjective(0))
        

//
//        for(int i=0; i<9;i++){
//       System.out.println("Restrição: " + i + " = " + cont[i]);
//      }

    }

    public void primeirasRodadas(Rodada[][] rod, Solution solution) {

        ArrayList<Integer> _casa = new ArrayList();
        ArrayList<Integer> _fora = new ArrayList();
        int contc = 0;
        int contf = 0;
        int Penalidade = 500000;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                _casa.add(rod[i][j].getCasa());
                _fora.add(rod[i][j].getFora());
            }
            for (int k = 0; k < 10; k++) {
                if (_casa.contains(rod[i + 1][k].getCasa())) {
                    System.out.println(rod[i][k].getCasa());
                    contc++;
                    cont[0]++; 
                }
                if (_fora.contains(rod[i + 1][k].getFora())) {
                    System.out.println(rod[i][k].getFora());
                    contf++;
                    cont[0]++;
                }

            }
            _casa.clear();
            _fora.clear();
        }

        if (contc != 0 || contf != 0) { //Penalisa as Soluções Infactiveis
            Penalidade += solution.getObjective(0);
            solution.setObjective(0, Penalidade);
        }

    }

    public void ultimasRodadas(Rodada[][] rod, Solution solution) {
        ArrayList<Integer> _casa = new ArrayList();
        ArrayList<Integer> _fora = new ArrayList();
        int contc = 0;
        int contf = 0;
        int Penalidade = 500000;

        for (int j = 0; j < 10; j++) {
            _casa.add(rod[17][j].getCasa());
            _fora.add(rod[17][j].getFora());
        }
        for (int k = 0; k < 10; k++) {
            if (_casa.contains(rod[18][k].getCasa())) {
                contc++;
                cont[1]++;
            }
            if (_fora.contains(rod[18][k].getFora())) {
                contf++;
                cont[1]++;
            }
        }
        _casa.clear();
        _fora.clear();

        if (contc != 0 || contf != 0) { //Penalisa as Soluções Infactiveis
            Penalidade += solution.getObjective(0);
            solution.setObjective(0, Penalidade);
        }

    }

    public void jogosSeguidos(Rodada[][] rod, Solution solution) {
        int time;
        boolean seguido = false;
        int Penalidade = 0;

        for (time = 1; time < 21; time++) { // Troca o time

            for (int i = 0; i < 17; i++) { // Troca rodada apenas primeiro turno
                for (int j = 0; j < 10; j++) { // Jogos da Rodada
                    if (time == rod[i][j].getCasa()) { // Verifica se o time joga em casa
                        for (int k = 0; k < 10; k++) {
                            if (time == rod[i + 1][k].getCasa()) { // Verifica se na rodada seguinte ele joga em casa
                                for (int x = 0; x < 10; x++) {
                                    if (time == rod[i + 2][x].getCasa()) { // Verifica se na 3 rodada seguida ele joga em casa
                                        Penalidade += 500000;
                                        System.out.println(i);
                                        System.out.println(" - Casa Jogo 1: " + rod[i][j].getCasa()+ " x " + rod[i][j].getFora());
                                        System.out.println(" - Jogo 2: " + rod[i+1][k].getCasa()+ " x " + rod[i+1][k].getFora());
                                       System.out.println(" - Jogo 3: " + rod[i+2][x].getCasa()+ " x " + rod[i+2][x].getFora());
//                                        System.out.println("Penalidade" + Penalidade);
                                        seguido = true;
                                        cont[2]++;
                                        //System.out.println(i);
                                        //System.out.println(time +" - " + rod[i + 1][k].getCasa()+"x" + rod[i + 2][x].getCasa());
                                    }
                                }
                            }
                        }
                    }
                    
                    //Verifica se joga fora
                    
                     else if (time == rod[i][j].getFora()) { // Verifica se o time joga Fora
                        for (int k = 0; k < 10; k++) {
                            if (time == rod[i + 1][k].getFora()) { // Verifica se na rodada seguinte ele joga Fora
                                for (int x = 0; x < 10; x++) {
                                    if (time == rod[i + 2][x].getFora()) { // Verifica se na 3 rodada seguida ele joga Fora
                                        Penalidade += 500000;
                                        //System.out.println("Penalidade" + Penalidade);
                                        seguido = true;
                                        cont[2]++;
                                        
                                    }
                                }
                            }
                        }
                    }

                }
            }
//            for (int i = 0; i < 17; i++) { // Troca rodada
//                for (int j = 0; j < 10; j++) { // Roda o jogo
//                    if (time == rod[i][j].getFora()) { // Verifica se o time joga Fora
//                        for (int k = 0; k < 10; k++) {
//                            if (time == rod[i + 1][k].getFora()) { // Verifica se na rodada seguinte ele joga Fora
//                                for (int x = 0; x < 10; x++) {
//                                    if (time == rod[i + 2][x].getFora()) { // Verifica se na 3 rodada seguida ele joga Fora
//                                        Penalidade += 500000;
//                                        //System.out.println("Penalidade" + Penalidade);
//                                        seguido = true;
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                }
//            }

        }
        if (seguido == true) {
            Penalidade += solution.getObjective(0);
            solution.setObjective(0, Penalidade);
            System.out.println("Continua o Problema");
        }
    }

    public void classicosQuatroUltimas(Times[] times, Rodada[][] rod, Solution solution) {
        boolean classico = false;
        int Penalidade = 500000;

        for (int i = 15; i < 19; i++) {
            for (int j = 0; j < 10; j++) {
                //System.out.println(rod[i][j].getCasa());
                if (times[rod[i][j].getCasa() - 1].getClassico() == times[rod[i][j].getFora() - 1].getClassico()) {
                    classico = true;
                    cont[3]++;
                }
            }
        }

        if (classico == true) {
            Penalidade += solution.getObjective(0);
            solution.setObjective(0, Penalidade);
        }

    }

    public void classicosMesmaRodada(Times[] times, Rodada[][] rod, Solution solution) {
        ArrayList<Integer> classico = new ArrayList();
        boolean ocorre = false;
        int Penalidade = 500000;

        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 10; j++) {
                ocorre = classico.contains(times[rod[i][j].getCasa() - 1].getClassico()); // Verifica se ja ouve classico na cidade
                //System.out.println(times[rod[i][j].getCasa()-1].getClassico() + "Classico");
                /*
              Verifica se ja ouve classico na cidade e se o proximo jogo é um classico
              Se for o primeiro classico add no Array
                 */
                if (times[rod[i][j].getCasa() - 1].getClassico() == times[rod[i][j].getFora() - 1].getClassico() && !ocorre) {
                    classico.add(times[rod[i][j].getCasa() - 1].getCasa_());
                } /*
              Verifica se ja ouve classico na cidade e se o proximo jogo é um classico
              Se for o segundo classico na mesma rodada penalisa
                 */ else if (times[rod[i][j].getCasa() - 1].getClassico() == times[rod[i][j].getFora() - 1].getClassico() && ocorre) {
                    Penalidade += solution.getObjective(0);
                    solution.setObjective(0, Penalidade);
                    cont[4]++;
                }

                //ocorre = false;  
            }
            /*
            Limpa o Array para proxima rodada
             */
            classico.clear();
        }

    }

    public void classicosTresPrimeiras(Times[] times, Rodada[][] rod, Solution solution) {
        boolean classicos = false;
        int Penalidade = 500000;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                //System.out.println(rod[i][j].getCasa());
                if (times[rod[i][j].getCasa() - 1].getClassico() == times[rod[i][j].getFora() - 1].getClassico()) {
                   //System.out.println(i);
                    //System.out.println("Time 1: " + times[rod[i][j].getCasa() - 1].getNome_() + "Time2: " + times[rod[i][j].getFora() - 1].getNome_());
                    classicos = true;
                    cont[5]++;
                }
            }
        }

        if (classicos == true) {
            Penalidade += solution.getObjective(0);
            solution.setObjective(0, Penalidade);
        }

    }

    public void regionaisQuatroUltimas(Times[] times, Rodada[][] rod, Solution solution) {
        boolean regionais = false;
        int Penalidade = 500000;

        for (int i = 15; i < 19; i++) {
            for (int j = 0; j < 10; j++) {
                //System.out.println(rod[i][j].getCasa());
                if (times[rod[i][j].getCasa() - 1].getEstado() == times[rod[i][j].getFora() - 1].getEstado()) {
                    regionais = true;
                    cont[6]++;
                }
            }
        }

        if (regionais == true) {
            Penalidade += solution.getObjective(0);
            solution.setObjective(0, Penalidade);
        }

    }

    public void classicosSeguidos(Times[] times, Rodada[][] rod, Solution solution) {
        //int time;
        boolean classicoSeguido = false;
        int Penalidade = 500000;

        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 10; j++) {
                // Verifica se o Jogo é um Classico
                if (times[rod[i][j].getCasa() - 1].getClassico() == times[rod[i][j].getFora() - 1].getClassico()) {
                    //      System.out.println("Jogo1: " + times[rod[i][j].getCasa()-1].getNome_() + " x " + times[rod[i][j].getFora()-1].getNome_());

                    for (int k = 0; k < 10; k++) {
                        // Verifica se no proximo jogo, o time q jogou em casa joga um classico
                        if (times[rod[i][j].getCasa() - 1].getidRef() == rod[i + 1][k].getCasa() && times[rod[i][j].getCasa() - 1].getClassico() == times[rod[i + 1][k].getFora() - 1].getClassico()) {
                            classicoSeguido = true;
                            cont[7]++;
                            //   System.out.println(times[rod[i][j].getCasa()-1].getNome_() + " x " + times[rod[i + 1][k].getFora()-1].getNome_());
                        } else if (times[rod[i][j].getCasa() - 1].getidRef() == rod[i + 1][k].getFora() && times[rod[i][j].getCasa() - 1].getClassico() == times[rod[i + 1][k].getCasa() - 1].getClassico()) {
                            classicoSeguido = true;
                           cont[7]++;
                            //  System.out.println(  times[rod[i + 1][k].getCasa()-1].getNome_() + " x " + times[rod[i][j].getCasa()-1].getNome_());
                        }
                        //Verifica se no pro jogo do time que jogou fora é um Classico
                        if (times[rod[i][j].getFora() - 1].getidRef() == rod[i + 1][k].getCasa() && times[rod[i][j].getFora() - 1].getClassico() == times[rod[i + 1][k].getFora() - 1].getClassico()) {

                            classicoSeguido = true;
                            cont[7]++;
                             //  System.out.println(times[rod[i][j].getFora()-1].getNome_() + " x " + times[rod[i + 1][k].getFora()-1].getNome_());

                        } else if (times[rod[i][j].getFora() - 1].getidRef() == rod[i + 1][k].getFora() && times[rod[i][j].getFora() - 1].getClassico() == times[rod[i + 1][k].getCasa() - 1].getClassico()) {
                            classicoSeguido = true;
                            cont[7]++;
                            //  System.out.println( times[rod[i + 1][k].getCasa()-1].getNome_() + " x " +  times[rod[i][j].getFora()-1].getNome_());
                        }

                    }
                }

            }

        }

        if (classicoSeguido == true) {
            Penalidade += solution.getObjective(0);
            solution.setObjective(0, Penalidade);
        }
    }

    public void duasPrimeirasPartidas(Times[] times, Rodada[][] rod, Solution solution) {
        int Penalidade = 500000;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 10; j++) {
                /* se o time que joga fora de casa jogar contra um time de seu estado em uma
                das primeiras rodadas.
                */ 
                if (times[rod[i][j].getFora()-1].getEstado() == times[rod[i][j].getCasa()-1].getEstado()) {
                    //System.out.println(times[rod[i][j].getFora()-1].getNome_() + "x" + times[rod[i][j].getCasa()-1].getNome_());
                    Penalidade += solution.getObjective(0);
                    solution.setObjective(0, Penalidade);
                    cont[8]++;
                    
                }
            }
        }

    }

}
