/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcbf.mutacao;

import java.util.HashMap;
import jmetal.operators.mutation.Mutation;
import jmetal.util.Configuration;
import jmetal.util.JMException;

/**
 *
 * @author Alexandre
 */
public class EscolherMutacao {
    
     public static Mutation getMutationOperator(String name, HashMap parameters) throws JMException{
 
    if (name.equalsIgnoreCase("trocatimes"))
      return new TrocaTimes(parameters);
    else if (name.equalsIgnoreCase("mandoscampo"))
      return new MandosCampo(parameters);
    else if (name.equalsIgnoreCase("trocarodada"))
      return new TrocaRodadas(parameters);
    else if (name.equalsIgnoreCase("trocaregional"))
      return new TrocaRegional(parameters);
     else if (name.equalsIgnoreCase("outroestado"))
      return new TrocaOutroEstado(parameters);
     else if (name.equalsIgnoreCase("entreclassicos"))
      return new TrocaEntreClassicos(parameters);
     else if (name.equalsIgnoreCase("trocarodadamando"))
      return new TrocaRodadasEMando(parameters);

    else
    {
      Configuration.logger_.severe("Operator '" + name + "' not found ");
      Class cls = java.lang.String.class;
      String name2 = cls.getName() ;    
      throw new JMException("Exception in " + name2 + ".getMutationOperator()") ;
    }        
  } // getMutationOperator
    
}
