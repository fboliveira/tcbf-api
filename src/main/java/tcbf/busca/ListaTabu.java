/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcbf.busca;

import java.util.ArrayList;

/**
 *
 * @author Alexandre
 */
public class ListaTabu {

    private ArrayList<MovimentoTrocaTime> lmtt;
    private int tam;

    public ListaTabu(int tam) {
        this.tam = tam;
        lmtt = new ArrayList<MovimentoTrocaTime>(tam);
    }

    public void add(MovimentoTrocaTime mtt) {
        if (lmtt.size() == tam) {
            removeoprimeiro();
        }
        lmtt.add(mtt);
    }

    public void removeoprimeiro() {
        lmtt.remove(0);
    }
    
    public int getSize(){
        return lmtt.size();
    }

    public boolean contains(MovimentoTrocaTime mtt) {
        MovimentoTrocaTime movimento = null;
        for (int i = 0; i < lmtt.size(); i++) {
            movimento = lmtt.get(i);
            if (movimento.getTimeA() == mtt.getTimeA() && movimento.getTimeB() == mtt.getTimeB()) {
                return true;
            }
        }
        return false;
    }
}
