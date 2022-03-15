package tcbf;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.core.Variable;
import jmetal.util.JMException;

public class CalculateCost {
    
    public static void main(String[] args) throws Exception {

        System.out.println("=".repeat(80));
        System.out.println("TCBF Api - Calculate solution cost:");
        System.out.println("=".repeat(80));
        
        if (args.length < 1) {
            throw new Exception("Informe o nome do arquivo com a solução.");
        }
        
        ManipuladorArquivo manipula = new ManipuladorArquivo();
        
        int times[] = null;
        try {
            times = manipula.readFile(args[0]);
        } catch (Exception ex) {
            Logger.getLogger(CalculateCost.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int sizeTimes = 20;
        
        Problem problem = new Problema(sizeTimes, 38, 10);
        SolutionSet population = new SolutionSet(1);
                
        Solution result;
        try {
        
            TCBFSolutionType solutionType = (TCBFSolutionType) problem.getSolutionType();
            Variable variables[] = solutionType.createVariables(times);
            
            result = new Solution(problem, variables);
            problem.evaluate(result);
            problem.evaluateConstraints(result);
            population.add(result);
        } catch (JMException ex) {
            Logger.getLogger(CalculateCost.class.getName()).log(Level.SEVERE, null, ex);
        }        
                
        System.out.println("Writing tcbf-api-result.txt ...");
        population.printVariablesToFile("tcbf-api-result.txt");
        System.out.println("Writing tcbf-api-cost.txt ...");
        population.printObjectivesToFile("tcbf-api-cost.txt");
        System.out.println("COST: " + population.get(0).getObjective(0));
        System.out.println("Done.");
        
    }
}
