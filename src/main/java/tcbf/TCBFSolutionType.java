/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcbf;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tcbf.Variable.MatrizVariable;
import tcbf.Variable.ArrayTime;
import jmetal.core.Problem;
import jmetal.core.SolutionType;
import jmetal.core.Variable;
import jmetal.encodings.variable.ArrayInt;

/**
 *
 * @author Alexandre
 */
public class TCBFSolutionType extends SolutionType {

    private int TamArrayTime;
    private Problem problem_;
    private int index1, index2;

    public TCBFSolutionType(Problem problem, int realVariables, int position1, int position2) {
        super(problem);
        this.TamArrayTime = realVariables;
        this.index1 = position1;
        this.index2 = position2;

    }

    @Override
    public Variable[] createVariables() {
        Variable[] variables = new Variable[2];

        try {
            variables[0] = new ArrayTime(TamArrayTime, problem_);
           
        } catch (IOException ex) {
            Logger.getLogger(TCBFSolutionType.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        variables[1] = new MatrizVariable(index1, index2, problem_);
        return variables;

    }
    
    /* FBO */
    public Variable[] createVariables(int times[]) {
        Variable[] variables = new Variable[2];

        try {
            variables[0] = new ArrayTime(TamArrayTime,times, problem_);
           
        } catch (IOException ex) {
            Logger.getLogger(TCBFSolutionType.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        variables[1] = new MatrizVariable(index1, index2, problem_);
        return variables;

    }    
    
    public Variable[] copyVariables(Variable[] vars) {
		Variable[] variables ;
		
		variables = new Variable[2];
	  variables[0] = vars[0].deepCopy();
          variables[1] = vars[1].deepCopy();
		
		return variables ;
	} // copyVariables

}
