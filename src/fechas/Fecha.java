/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fechas;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author enrique
 */
public class Fecha {

    public static String fechaHoy() {
        Calendar fechaSys = Calendar.getInstance();
        int año = fechaSys.get(Calendar.YEAR);
        int mes = fechaSys.get(Calendar.MONTH) + 1;
        int dia = fechaSys.get(Calendar.DATE);
        return String.format("%02d", dia) + "-"
                + String.format("%02d", mes) + "-" + año;
    }
    private int dia;
    private int mes;
    private int año;

    public Fecha(int dia, int mes, int año) {
        //System.out.println("CONSTRUCTOR FECHA CON PARÁMETROS");
        setAño(año);
        setMes(mes);
        setDia(dia);
    }

    public Fecha() {
        //System.out.println("CONSTRUCTOR FECHA SIN PARÁMETROS");
        Calendar fechaSys = Calendar.getInstance();
        setAño(fechaSys.get(Calendar.YEAR));
        setMes(fechaSys.get(Calendar.MONTH) + 1);
        setDia(fechaSys.get(Calendar.DATE));
    }

    public final void setDia(int dia) {
        int diasMes[] = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (año % 4 == 0) {
            diasMes[2] = 29;
        }
        if (dia < 1 || dia > diasMes[mes]) {
            //si el valor de día es incorrecto por cambio de mes o año, hay que recuperar los valores previos de mes o año
            throw new ExcepciónFechaIlegal(
                    "valor de día debe estar entre 1 y " + diasMes[mes] + ", para los valores de año(" + año + ") y mes(" + mes + ")",1);
        }
        this.dia = dia;
    }

    public final void setMes(int mes) {
        if (mes < 1 || mes > 12) {
            throw new ExcepciónFechaIlegal("valor de mes incorrecto",2);
        }
        int oldMes = this.mes;
        this.mes = mes;
        if (this.dia != 0) {
            try {
                setDia(getDia());
            } catch (ExcepciónFechaIlegal e) {
                this.mes = oldMes;
                throw new ExcepciónFechaIlegal("no se puede cambiar el mes a " + mes + " por inconsistencia con el día actual(" + dia + ") ",2);
            }

        }
    }

    public final void setAño(int año) {
        if (año >= 0 && año <= 30) {
            año += 2000;
        }
        if (año > 30 && año < 100) {
            año += 1900;
        }
        if (año < 1931 || año > 2050) {
            throw new ExcepciónFechaIlegal("valor de año incorrecto",3);
        }
        int oldAño = this.año;
        this.año = año;
        if (this.dia != 0) {
            try {
                setDia(getDia());
            } catch (ExcepciónFechaIlegal e) {
                this.año = oldAño;
                throw new ExcepciónFechaIlegal("no se puede cambiar el año a " + año + " por inconsistencia con mes(" + mes + ") y día(" + dia + ") ",3);
            }
        }
    }

    public String verFecha() {
        return String.format("%02d", getDia()) + "-"
                + String.format("%02d", getMes()) + "-" + getAño();
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAño() {
        return año;
    }
    
    /**
     * Metodo que devuelve el día de la semana de la fecha hoy
     * @return 
     */
    public String diaSemana(){
        Calendar fecha=new GregorianCalendar();
        fecha.set(año, mes, dia);
        String[]nombres={"Domingo","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
        int i=fecha.get(Calendar.DAY_OF_WEEK)-1;
        return nombres[i];
    }

}
