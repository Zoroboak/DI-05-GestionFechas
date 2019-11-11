/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fechas;

/**
 *
 * @author enrique
 */
public class ExcepciónFechaIlegal extends RuntimeException{
    
    //1 = dia, 2 = mes, 3 = año
    int tipodeExcepcion;
    
    public ExcepciónFechaIlegal(String texto, int tipoException){
        super(texto);
        tipodeExcepcion = tipoException;
    }

    public int getTipodeExcepcion() {
        return tipodeExcepcion;
    }
    
    
    
    
}
