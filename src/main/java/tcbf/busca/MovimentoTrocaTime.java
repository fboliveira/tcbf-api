/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcbf.busca;

/**
 *
 * @author Alexandre
 */
public class MovimentoTrocaTime {
    private int timeA;
    private int timeB;
    
    public MovimentoTrocaTime(int timeA, int timeB){
        this.timeA = timeA;
        this.timeB = timeB;
    }

    /**
     * @return the timeA
     */
    public int getTimeA() {
        return timeA;
    }

    /**
     * @param timeA the timeA to set
     */
    public void setTimeA(int timeA) {
        this.timeA = timeA;
    }

    /**
     * @return the timeB
     */
    public int getTimeB() {
        return timeB;
    }

    /**
     * @param timeB the timeB to set
     */
    public void setTimeB(int timeB) {
        this.timeB = timeB;
    }
    
    
    
}
