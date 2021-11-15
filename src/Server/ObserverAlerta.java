/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import javax.swing.Timer;

/**
 *
 * @author martin
 */
public class ObserverAlerta extends Thread {

    public static Timer timer;
    private final String empresa;
    private final Float precio;
    private final int tipo;           // 0 compra   1 venta
    
    public ObserverAlerta(String empresa,Float precio, int tipo) {
        this.empresa = empresa;
        this.precio = precio;
        this.tipo =tipo;
    }
    
    @Override
    public void run(){
        
        
    }
    
}
