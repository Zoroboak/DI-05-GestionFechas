/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fechas;

/**
 *
 * @author enrique
 */
public class FechaE extends Fecha {

    public enum idiomas {
        INGLES, ESPAÑOL
    };

    public FechaE() {
        System.out.println("CONSTRUCTOR FECHA_E SIN PARÁMETROS");
        //super();
    }

    public FechaE(String fecha, idiomas id) {
        System.out.println("CONSTRUCTOR FECHA_E CON PARÁMETROS");
        String partesFecha[] = fecha.split("-|/");
        if (partesFecha.length != 3) {
            throw new IllegalArgumentException("formato de fecha no reconocible");
        }
        setDia(Integer.parseInt(partesFecha[Math.abs(id.ordinal() - 1)]));
        setMes(Integer.parseInt(partesFecha[id.ordinal()]));
        setAño(Integer.parseInt(partesFecha[2]));
    }

    @Override
    public String verFecha() {
        String meses[] = {"enero", "febrero", "marzo", "abril", "mayo", "junio",
             "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};
        return getDia() + " de " + meses[getMes() - 1] + " de " + getAño();
    }
}
