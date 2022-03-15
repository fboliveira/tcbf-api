/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcbf.Variable;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
// import javafx.print.Collation;
import jmetal.core.Problem;
import jmetal.core.Variable;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import tcbf.ManipuladorArquivo;
import tcbf.Times;

/**
 *
 * @author Alexandre
 */
public class ArrayTime extends Variable {
    
    

    	/* Problem using the type
	 */
  private Problem problem_ ;
	
	/**
   * Stores an array of integer values
   */
  private Times [] array_;
  private Times [] CopiaArray_;
  
  ManipuladorArquivo manipula = new ManipuladorArquivo();
  
  /**
   * Stores the length of the array
   */
  private int size_;

    public ArrayTime (int size, Problem problem) throws IOException {
        problem_ = problem ;
        size_   = size;
        array_ = new Times[size_];
        //Obter array variavel de times embaralhada;
        
        /*Construir Lista de Times*/        
        for (int i = 0; i<size_;i++){
           array_[i] = manipula.Preenche(i);
           array_[i].setidRef_(i);
           //array_[i] = ArrayEmbaralhado.get(i).clone() ou .copia());
            //ArrayList<Integer> lista = new ArrayList<>();
        }
           Collections.shuffle(Arrays.asList(array_));// Mistura os Times(Cria tabela Inicial)
          //  System.out.println("Estou aki");
           
        for (int i = 0; i<size_;i++){
           array_[i].setidRef_(i+1);
        }
        CopiaArray_= array_;
        
    }

    /* FBO */
    public ArrayTime (int size, int times[], Problem problem) throws IOException {
        problem_ = problem ;
        size_   = size;
        array_ = new Times[size_];
        //Obter array variavel de times embaralhada;
        
        /*Construir Lista de Times*/        
        for (int i = 0; i< size_;i++){
           array_[i] = manipula.Preenche(times[i]);
           array_[i].setidRef_(i + 1);
           //array_[i] = ArrayEmbaralhado.get(i).clone() ou .copia());
            //ArrayList<Integer> lista = new ArrayList<>();
        }
                  
        CopiaArray_= array_;
        
    }
    
    /*copy construtor
    
     */
    private ArrayTime(ArrayTime arrayTime) throws IOException, JMException {
        //Times aux[];
          size_   = arrayTime.size_;
          array_ = new Times[size_];
         //aux = new Times[size_];
        // array_ = manipula.getTimes();
          //System.arraycopy(arrayTime.array_, 0, array_, 0, size_);
         
         // array_ = arrayTime.getArray();
         
          for (int i=0; i<array_.length;i++){
              array_[i] = arrayTime.array_[i].Copy();
//              int id = arrayTime.array_[i].getId_();
//              String nome = arrayTime.array_[i].getNome_();
//              int casa = arrayTime.array_[i].getCasa_();
//              int loc = arrayTime.array_[i].getLocalizacao_();
//             array_[i] = new Times(id,nome,casa,loc,0);
//              array_[i] = arrayTime.getValue(i);
              array_[i].setidRef_(i+1);
              this.array_[i].setTempo_(0);
          }
          
//          for (int i=0; i<array_.length;i++){
//              this.array_[i] = arrayTime.array_[i];
//              this.array_[i].setTempo_(0);
//          }
//        
//System.arraycopy(aux, 0, array_, 0, size_);
    } // Copy Constructor
    
    

    @Override
    public Variable deepCopy() {

      try {
      
          return new ArrayTime(this);
      
      } catch (IOException ex) {
          Logger.getLogger(ArrayTime.class.getName()).log(Level.SEVERE, null, ex);
      } catch (JMException ex) {
          Logger.getLogger(ArrayTime.class.getName()).log(Level.SEVERE, null, ex);
      }
     return null;
    }

    /* getValue
    * @param index1 and index2 Index of value to be returned
    * @return the value in position index
     */
    public Times getValue(int index) throws JMException {
        if ((index >= 0) && (index < size_))
 		  return array_[index] ;
 	  else {
      Configuration.logger_.severe(jmetal.encodings.variable.ArrayInt.class+".getValue(): index value (" + index + ") invalid");
      throw new JMException(jmetal.encodings.variable.ArrayInt.class+": index value (" + index + ") invalid") ;
    } // if
    } // getValue

    
    public Times[] getArray(){
        CopiaArray_= array_;
        return array_;
    }
    /**
     * setValue
     *
     * @param index Index of value to be returned
     * @param value The value to be set in position index
     */
    public void setValue(int index, Times value) throws JMException {
        if ((index >= 0) && (index < size_))
      array_[index] = value;
   else {
     Configuration.logger_.severe(jmetal.encodings.variable.ArrayInt.class+".setValue(): index value (" + index + ") invalid");
     throw new JMException(jmetal.encodings.variable.ArrayInt.class+": index value (" + index + ") invalid") ;
    } // else
    } // setValue
    
     public String toString(){
    String string ;

    string = "" ;
    for (int i = 0; i < size_ ; i ++)
      string += array_[i].getidRef() + "-" + array_[i].getNome_() + " " + array_[i].getTempo_() + " " ;
    return string;
  } //toString

    public int getLength() {
        return size_;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
    
    
    
    

