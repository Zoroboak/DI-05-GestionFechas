/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import fechas.*;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Pedro Pérez
 */
public class Interfaz extends javax.swing.JFrame {

    //Atributos Globales
    Fecha fechaDeHoy = new Fecha();
    Fecha FechaIntroducida = new Fecha();

    //lista de DocumentListeners para los dias introducidos por el usuario: 1 campoDia, 2 campoMes, 3 campoAño
    DocumentListener listaDocumentListeners[] = new DocumentListener[4];

    /**
     * Creamos nueva instancia de la interfaz
     */
    public Interfaz() {
        initComponents();
        declaracionAtributosIniciales();
    }

    /**
     * Metodo que le asigna a la interfaz una serie de atributos iniciales
     */
    private void declaracionAtributosIniciales() {
        //Por defecto, centramos la ventana
        setLocationRelativeTo(null);
        //Activamos la propiedad LineWrap de la consola de errores en la interfaz
        jTextAreaConsolaDeErrores.setLineWrap(true);

        //Inicializamos cada uno de los listener para los dias introducidos por el usuario: 1 campoDia, 2 campoMes, 3 campoAño
        for (int i = 1; i <= 3; i++) {
            listaDocumentListeners[i] = new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    try {
                        comprobarDatos();
                    } catch (Exception ex) {
                        jTextAreaConsolaDeErrores.setText(ex.getLocalizedMessage());
                        jTextAreaConsolaDeErrores.setForeground(Color.red);
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    try {
                        comprobarDatos();
                    } catch (Exception ex) {
                        jTextAreaConsolaDeErrores.setText(ex.getLocalizedMessage());
                        jTextAreaConsolaDeErrores.setForeground(Color.red);
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    try {
                        comprobarDatos();
                    } catch (Exception ex) {
                        jTextAreaConsolaDeErrores.setText(ex.getLocalizedMessage());
                        jTextAreaConsolaDeErrores.setForeground(Color.red);
                    }
                }
            };

        }
        //Le añadimos nuestros documentListeners a los campos que el usuario puede editar
        jTextFieldDiaIntroducido.getDocument().addDocumentListener(listaDocumentListeners[1]);
        jTextFieldMesIntroducido.getDocument().addDocumentListener(listaDocumentListeners[2]);
        jTextFieldAñoIntroducido.getDocument().addDocumentListener(listaDocumentListeners[3]);

        //La primera vez que arranca el programa, le asignamos la fecha de hoy
        establecerFechaHoy();

        //Una vez le asignamos una fecha, comprobamos los datos por primera vez
        comprobarDatos();
    }

    /**
     * Metodo que obtiene la fecha de hoy y se la asigna a la interfaz
     */
    public void establecerFechaHoy() {
        //Inicializamos un Objeto gregoranCalendar para obtener los datos
        Calendar fecha = new GregorianCalendar();
        String dia = Integer.toString(fecha.get(Calendar.DAY_OF_MONTH));
        String mes = Integer.toString(fecha.get(Calendar.MONTH) + 1);
        String anio = Integer.toString(fecha.get(Calendar.YEAR));
        jTextFieldDiaHoy.setText(dia);
        jTextFieldMesHoy.setText(mes);
        jTextFieldAñoHoy.setText(anio);

        //Le asignamos los mismos datos a la fecha inicial del usuario
        jTextFieldDiaIntroducido.setText(dia);
        jTextFieldMesIntroducido.setText(mes);
        jTextFieldAñoIntroducido.setText(anio);

        //Mostramos el valor del dia de la semana, según la fecha de hoy
        String[] diasDeLaSemana = new String[]{"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
        String nombreDia = diasDeLaSemana[fecha.get(Calendar.DAY_OF_WEEK) - 1];
        jTextFieldDiaSemanaHoy.setText(nombreDia);
    }

    /**
     * Metodo que comprueba los datos introducidos
     *
     * @throws Exception
     */
    public void comprobarDatos() {
        String dia = jTextFieldDiaIntroducido.getText();
        String mes = jTextFieldMesIntroducido.getText();
        String año = jTextFieldAñoIntroducido.getText();
        Fecha fecha = new Fecha();

        jTextFieldAñoIntroducido.setEnabled(true);
        jTextFieldMesIntroducido.setEnabled(true);
        jTextFieldDiaIntroducido.setEnabled(true);
        jTextAreaConsolaDeErrores.setText("");

        try {
            FechaIntroducida = new Fecha(Integer.parseInt(dia), Integer.parseInt(mes), Integer.parseInt(año));
            jTextFieldDiaSemanaIntroducido.setText(String.valueOf(FechaIntroducida.diaSemana()));
            jTextFieldDiasEntreFechas.setText(String.valueOf(calcularFechas(jTextFieldAñoHoy.getText()+"-"+jTextFieldMesHoy.getText()+"-"+jTextFieldDiaHoy,año+"-"+mes+"-"+dia)));
        } catch (Exception j) {
            jTextFieldDiasEntreFechas.setText("");
            jTextFieldDiaSemanaIntroducido.setText("");

            if (jTextFieldDiaIntroducido.isFocusOwner()) {
                try {
                    new Fecha(Integer.parseInt(dia), Integer.parseInt(mes), Integer.parseInt(año));
                } catch (Exception e) {
                    if (e.getCause() == null) {
                        jTextAreaConsolaDeErrores.setText("Debes escribir un dia valido");
                    } else {
                        jTextAreaConsolaDeErrores.setText(e.getLocalizedMessage());
                    }
                    jTextFieldDiaIntroducido.setEnabled(true);
                    jTextFieldMesIntroducido.setEnabled(false);
                    jTextFieldAñoIntroducido.setEnabled(false);
                }
            } else if (jTextFieldMesIntroducido.isFocusOwner()) {

                try {
                    new Fecha(Integer.parseInt(dia), Integer.parseInt(mes), Integer.parseInt(año));
                } catch (Exception e) {
                    if (e.getCause() == null) {
                        jTextAreaConsolaDeErrores.setText("Debes escribir un mes valido");
                    } else {
                        jTextAreaConsolaDeErrores.setText(e.getLocalizedMessage());
                    }
                    jTextFieldDiaIntroducido.setEnabled(false);
                    jTextFieldMesIntroducido.setEnabled(true);
                    jTextFieldAñoIntroducido.setEnabled(false);
                }
            } else if (jTextFieldAñoIntroducido.isFocusOwner()) {

                try {
                    new Fecha(Integer.parseInt(dia), Integer.parseInt(mes), Integer.parseInt(año));
                } catch (Exception e) {
                    if (e.getCause() == null) {
                        jTextAreaConsolaDeErrores.setText("Debes escribir un año valido");
                    } else {
                        jTextAreaConsolaDeErrores.setText(e.getLocalizedMessage());
                    }
                    jTextFieldDiaIntroducido.setEnabled(false);
                    jTextFieldMesIntroducido.setEnabled(false);
                    jTextFieldAñoIntroducido.setEnabled(true);
                }
            }
        }

    }

    /**
     * Metodo que calcula los dias entre fechas
     *
     * @return
     */
    public int calcularFechas(String stringfechaInicial, String stringfechaIntroducida) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date fechaInicial = dateFormat.parse(stringfechaInicial); //"2016-02-14"
            Date fechaFinal = dateFormat.parse(stringfechaIntroducida);//"2016-03-22"
            
            return (int) ((fechaFinal.getTime() - fechaInicial.getTime()) / 86400000);
        } catch (Exception e) {
            //Si falla, no hacemos nada, el metodo devolverá un 0
        }

        return 0;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabelProyectoFechas = new javax.swing.JLabel();
        jLabelFechaHoy = new javax.swing.JLabel();
        jLabelDiaFechaHoy = new javax.swing.JLabel();
        jLabelMesFechaHoy = new javax.swing.JLabel();
        jLabelAnoFechaHoy = new javax.swing.JLabel();
        jLabelDiaSemanaFechaHoy = new javax.swing.JLabel();
        jLabelDiaSemanaFechaIntroducida = new javax.swing.JLabel();
        jLabelFechaIntroducida = new javax.swing.JLabel();
        jLabelDiaFechaIntroducida = new javax.swing.JLabel();
        jLabelMesFechaIntroducida = new javax.swing.JLabel();
        jLabelAñoFechaIntroducida = new javax.swing.JLabel();
        jLabelDiasEntreFechas = new javax.swing.JLabel();
        jLabelConsolaErrores = new javax.swing.JLabel();
        jScrollPaneConsolaErrores = new javax.swing.JScrollPane();
        jTextAreaConsolaDeErrores = new javax.swing.JTextArea();
        jTextFieldDiaHoy = new javax.swing.JTextField();
        jTextFieldAñoHoy = new javax.swing.JTextField();
        jTextFieldMesHoy = new javax.swing.JTextField();
        jTextFieldDiaSemanaHoy = new javax.swing.JTextField();
        jTextFieldDiaSemanaIntroducido = new javax.swing.JTextField();
        jTextFieldDiaIntroducido = new javax.swing.JTextField();
        jTextFieldMesIntroducido = new javax.swing.JTextField();
        jTextFieldAñoIntroducido = new javax.swing.JTextField();
        jTextFieldDiasEntreFechas = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N

        jLabelProyectoFechas.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabelProyectoFechas.setText("PROYECTO FECHAS");
        jLabelProyectoFechas.setMaximumSize(new java.awt.Dimension(35, 35));
        jLabelProyectoFechas.setMinimumSize(new java.awt.Dimension(35, 35));
        jLabelProyectoFechas.setName(""); // NOI18N

        jLabelFechaHoy.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelFechaHoy.setText("FECHA DE HOY:");

        jLabelDiaFechaHoy.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelDiaFechaHoy.setText("DÍA");

        jLabelMesFechaHoy.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelMesFechaHoy.setText("MES");

        jLabelAnoFechaHoy.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelAnoFechaHoy.setText("AÑO");

        jLabelDiaSemanaFechaHoy.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelDiaSemanaFechaHoy.setText("Dia de la Semana");

        jLabelDiaSemanaFechaIntroducida.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelDiaSemanaFechaIntroducida.setText("Dia de la Semana");

        jLabelFechaIntroducida.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelFechaIntroducida.setText("FECHA INTRODUCIDA:");

        jLabelDiaFechaIntroducida.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelDiaFechaIntroducida.setText("DÍA");

        jLabelMesFechaIntroducida.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelMesFechaIntroducida.setText("MES");

        jLabelAñoFechaIntroducida.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelAñoFechaIntroducida.setText("AÑO");

        jLabelDiasEntreFechas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelDiasEntreFechas.setText("DÍAS ENTRE FECHAS");

        jLabelConsolaErrores.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelConsolaErrores.setText("Consola de errores");

        jTextAreaConsolaDeErrores.setColumns(20);
        jTextAreaConsolaDeErrores.setForeground(new java.awt.Color(255, 0, 0));
        jTextAreaConsolaDeErrores.setRows(5);
        jTextAreaConsolaDeErrores.setEnabled(false);
        jScrollPaneConsolaErrores.setViewportView(jTextAreaConsolaDeErrores);

        jTextFieldDiaHoy.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldDiaHoy.setEnabled(false);

        jTextFieldAñoHoy.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldAñoHoy.setEnabled(false);

        jTextFieldMesHoy.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldMesHoy.setEnabled(false);

        jTextFieldDiaSemanaHoy.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldDiaSemanaHoy.setEnabled(false);

        jTextFieldDiaSemanaIntroducido.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldDiaSemanaIntroducido.setEnabled(false);

        jTextFieldDiaIntroducido.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTextFieldMesIntroducido.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTextFieldAñoIntroducido.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTextFieldDiasEntreFechas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldDiasEntreFechas.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelDiaFechaIntroducida)
                        .addGap(30, 30, 30)
                        .addComponent(jLabelMesFechaIntroducida)
                        .addGap(25, 25, 25)
                        .addComponent(jLabelAñoFechaIntroducida))
                    .addComponent(jLabelFechaIntroducida))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabelFechaHoy)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelDiaSemanaFechaIntroducida, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelDiaSemanaFechaHoy, javax.swing.GroupLayout.Alignment.TRAILING)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelDiasEntreFechas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldDiasEntreFechas))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneConsolaErrores)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelConsolaErrores)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextFieldDiaIntroducido, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jTextFieldMesIntroducido, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldAñoIntroducido))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelDiaFechaHoy)
                            .addComponent(jTextFieldDiaHoy, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldMesHoy, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelMesFechaHoy))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelAnoFechaHoy)
                            .addComponent(jTextFieldAñoHoy, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldDiaSemanaIntroducido, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldDiaSemanaHoy, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelProyectoFechas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelProyectoFechas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelFechaHoy)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelAnoFechaHoy, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelDiaFechaHoy)
                        .addComponent(jLabelMesFechaHoy)
                        .addComponent(jLabelDiaSemanaFechaHoy)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldDiaHoy, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAñoHoy, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldMesHoy, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldDiaSemanaHoy, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(jLabelFechaIntroducida)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDiaFechaIntroducida)
                    .addComponent(jLabelMesFechaIntroducida)
                    .addComponent(jLabelAñoFechaIntroducida)
                    .addComponent(jLabelDiaSemanaFechaIntroducida))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldDiaSemanaIntroducido, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldDiaIntroducido, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAñoIntroducido, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldMesIntroducido, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDiasEntreFechas)
                    .addComponent(jLabelConsolaErrores))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneConsolaErrores, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldDiasEntreFechas, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelAnoFechaHoy;
    private javax.swing.JLabel jLabelAñoFechaIntroducida;
    private javax.swing.JLabel jLabelConsolaErrores;
    private javax.swing.JLabel jLabelDiaFechaHoy;
    private javax.swing.JLabel jLabelDiaFechaIntroducida;
    private javax.swing.JLabel jLabelDiaSemanaFechaHoy;
    private javax.swing.JLabel jLabelDiaSemanaFechaIntroducida;
    private javax.swing.JLabel jLabelDiasEntreFechas;
    private javax.swing.JLabel jLabelFechaHoy;
    private javax.swing.JLabel jLabelFechaIntroducida;
    private javax.swing.JLabel jLabelMesFechaHoy;
    private javax.swing.JLabel jLabelMesFechaIntroducida;
    private javax.swing.JLabel jLabelProyectoFechas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPaneConsolaErrores;
    private javax.swing.JTextArea jTextAreaConsolaDeErrores;
    private javax.swing.JTextField jTextFieldAñoHoy;
    private javax.swing.JTextField jTextFieldAñoIntroducido;
    private javax.swing.JTextField jTextFieldDiaHoy;
    private javax.swing.JTextField jTextFieldDiaIntroducido;
    private javax.swing.JTextField jTextFieldDiaSemanaHoy;
    private javax.swing.JTextField jTextFieldDiaSemanaIntroducido;
    private javax.swing.JTextField jTextFieldDiasEntreFechas;
    private javax.swing.JTextField jTextFieldMesHoy;
    private javax.swing.JTextField jTextFieldMesIntroducido;
    // End of variables declaration//GEN-END:variables
}
