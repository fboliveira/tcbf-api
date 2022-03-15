/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcbf;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import jmetal.core.Algorithm;
import jmetal.core.Operator;
import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.operators.mutation.MutationFactory;
import jmetal.qualityIndicator.QualityIndicator;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import tcbf.mutacao.EscolherMutacao;

/**
 *
 * @author Alexandre
 */
public class TCBF_main {
  public static Logger logger_; // Logger object
  public static FileHandler fileHandler_; // FileHandler object

  /**
   * @param args Command line arguments. The first (optional) argument specifies
   *             the problem to solve.
   * @throws JMException
   * @throws IOException
   * @throws SecurityException
   *                           Usage: three options
   *                           - jmetal.metaheuristics.mocell.MOCell_main
   *                           - jmetal.metaheuristics.mocell.MOCell_main
   *                           problemName
   *                           - jmetal.metaheuristics.mocell.MOCell_main
   *                           problemName ParetoFrontFile
   */
  public static void main(String[] args) throws JMException, IOException, ClassNotFoundException {
    Problem problem; // The problem to solve
    Algorithm algorithm; // The algorithm to use
    Operator mutation; // Mutation operator
    Operator mutation2;
    Operator mutation3;
    Operator mutation4;
    Operator mutation5;
    Operator mutation6;
    Operator mutation7;
    QualityIndicator indicators; // Object to get quality indicators

    HashMap parameters; // Operator parameters
    int mu;
    int lambda;

    // Logger object and file to store log messages
    logger_ = Configuration.logger_;
    fileHandler_ = new FileHandler("TCBF_main.log");
    logger_.addHandler(fileHandler_);

    indicators = null;

    problem = new Problema(20, 38, 10);

    mu = 40;// Tamanho da População
    lambda = 120;
    // Passar mu e Lambda...
    // algorithm = new TCBF(problem);
    algorithm = new TCBF1(problem, mu, lambda);
    // Parametros do Algoritmo
    algorithm.setInputParameter("maxEvaluations", 200);
    // algorithm.setInputParameter("blPorcentagem",10);

    // Variaveis da Mutação
    parameters = new HashMap();
    mutation = EscolherMutacao.getMutationOperator("mandoscampo", parameters);
    mutation2 = EscolherMutacao.getMutationOperator("trocatimes", parameters);
    mutation3 = EscolherMutacao.getMutationOperator("trocarodada", parameters);
    mutation4 = EscolherMutacao.getMutationOperator("trocaregional", parameters);
    mutation5 = EscolherMutacao.getMutationOperator("outroestado", parameters);
    mutation6 = EscolherMutacao.getMutationOperator("entreclassicos", parameters);
    mutation7 = EscolherMutacao.getMutationOperator("trocarodadamando", parameters);

    // Adicionando os operadores ao algoritmo
    algorithm.addOperator("mutation", mutation);
    algorithm.addOperator("troca", mutation2);
    algorithm.addOperator("rodada", mutation3);
    algorithm.addOperator("regional", mutation4);
    algorithm.addOperator("outroestado", mutation5);
    algorithm.addOperator("entreclassicos", mutation6);
    algorithm.addOperator("trocarodadamando", mutation7);

    // Execute the Algorithm
    long initTime = System.currentTimeMillis();
    SolutionSet population = algorithm.execute();
    long estimatedTime = System.currentTimeMillis() - initTime;

    // Mensagens de Resultado
    // STEP 8. Imprime os Resultados
    logger_.info("Tempo total de execução: " + estimatedTime + "ms");
    logger_.info("Variaveis foram escuitos no arquivo Resultado");

    population.printVariablesToFile("Resultado.txt");

    logger_.info("Melhores resultados foram escritos no arquivo FUN");
    population.printObjectivesToFile("FUN.txt");

    if (indicators != null) {
      logger_.info("Quality indicators");
      logger_.info("Hypervolume: " + indicators.getHypervolume(population));
      logger_.info("GD         : " + indicators.getGD(population));
      logger_.info("IGD        : " + indicators.getIGD(population));
      logger_.info("Spread     : " + indicators.getSpread(population));
      logger_.info("Epsilon    : " + indicators.getEpsilon(population));
    } // if
  }// main
}
