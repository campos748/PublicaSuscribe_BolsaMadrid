/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author martin
 */
public class Alerta {
    private String empresa;
    private Float precio;
    private int tipo;
    private ClientInterface cliente;
    
    public Alerta(String empresa, Float precio, int tipo, ClientInterface cliente) {
        this.empresa = empresa;
        this.precio = precio;
        this.tipo = tipo;
        this.cliente = cliente;
    }

    public String getEmpresa() {
        return empresa;
    }

    public Float getPrecio() {
        return precio;
    }

    public int getTipo() {
        return tipo;
    }

    public ClientInterface getCliente() {
        return cliente;
    }
    
    
}
