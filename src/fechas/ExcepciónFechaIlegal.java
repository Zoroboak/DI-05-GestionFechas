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
    public ExcepciónFechaIlegal(String texto){
        super(texto);
    }
    
}
