/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VariableContinua;

import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import Frecuencias.*;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.JTableHeader;

/**
 *
 * @author JL
 */
public class VentanaVContinua extends javax.swing.JDialog {

    double vector[]=new double[200];
    DefaultTableModel modelo;
    Object datos[][]={};
    public static int mC=0;
    /**
     * Creates new form VContinua
     */
    public VentanaVContinua(java.awt.Frame parent, boolean modal) 
    {
        super(parent, modal);
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Multimedia/iconoVC.jpg"));
        setIconImage(icon);
        this.setTitle("Variable Continua");
        
        initComponents();
        insertImage();
        String cabecera[]={"i","Y'i-1 - Y'i","ni","Ni","hi","Hi"};
        
        modelo=new DefaultTableModel(datos,cabecera);
        Tabla.setModel(modelo); 
        this.setLocationRelativeTo(null);
        generarVariableContinua();
    }
    public void generarVariableContinua()
    {
        Procesos vc=new Procesos();
        ArrayList<Integer> lst=new ArrayList<Integer>();
        double vector2[]=VentanaPrincipal.vect;
        
        //-------------------------------------------Numero de Datos
        int n=VentanaPrincipal.valorN();
        vc.imprimir(vector2);
        //-------------------------------------------Determinar Xmax y Xmin
        txtXmax.setText(vc.xmax(vector2)+"");
        txtXmin.setText(vc.xmin(vector2)+"");
        //-------------------------------------------Determinar Rango
        double rango=Double.parseDouble(txtXmax.getText())-Double.parseDouble(txtXmin.getText());
        txtRango.setText(formato2D(rango).replace(",","."));
        //-------------------------------------------Determinar m
        double mAux=Math.rint((double)1+(3.3*(Math.log10(n))));
        int m=(int)mAux;
        txtM.setText(m+"");
        //-------------------------------------------Determinar C
        double c=rango/((double)m);
        String cF = null;
        
        //-------------------------------------------Determinar Integer o Double
        int contDouble=vc.contTipoDouble(vector2);
        double sumaVec2=0;//=xmin-0.1;
        int sw = 0;
        if(contDouble<3)
        {
            c=Math.rint(c);
            cF=formato(c);
            int pos=cF.indexOf(",");
            String aux=cF.substring(pos,pos+2);
            System.out.println(aux);
            String aux2=cF.replace(aux,"");
            aux2.trim();
            cF=aux2;
            System.out.println(cF);
            txtC.setText(cF);
            
            //-------------------------------------------Error Distribucion Datos
            int cX=Integer.parseInt(txtC.getText());
            //--------------------------------------------
            //----------------------------------------XMIN ENTERO
            String valorXMin=txtXmin.getText();
            int pos2=valorXMin.indexOf(".");
            String aux3=valorXMin.substring(pos2,pos2+2);
            String aux4=valorXMin.replace(aux3,"");
            aux4.trim();
            valorXMin=aux4;
            sumaVec2=Integer.parseInt(valorXMin)-1;
            sw=0;
        }else
        {
            if(vc.contTipoDouble2D(vector2)>=2)
            {
                sw=1;
                cF=formato2D(c);
                txtC.setText(cF);
                sumaVec2=Double.parseDouble(txtXmin.getText())-0.01;
            }
            else
                {
                    sw=2;
                    cF=formato(c);
                    txtC.setText(cF);
                    sumaVec2=Double.parseDouble(txtXmin.getText())-0.1;
                }
        }
        //-------------------------------------------Tabla
        String vecSt[]=vc.columnaLimites(m, Double.parseDouble(txtXmin.getText()),cF,c,sumaVec2,sw);
        System.out.println("\n\n\nLimite");
        vc.imprimirVectSt(vecSt);
        double lim1[]=vc.lim1(m, Double.parseDouble(txtXmin.getText()),c,cF,sw);
        System.out.println("\n\nVector1");
        vc.imprimir(lim1);
        System.out.println("\n\nVector2");
        double lim2[]=vc.lim2(m, c, Double.parseDouble(txtXmin.getText()),cF,sumaVec2,sw);
        vc.imprimir(lim2);
        
        System.out.println("\n\nArrayList");
        lst=vc.columnani(lim1,lim2,vector2,m);
        
        //------------------------------------------Columnas
        int columnaNi[]=vc.columnani();
        double columnaNI[]=vc.columnaNI(m, columnaNi);
        double columnahi[]=vc.columnahi(n, columnaNi);
        double columnaHI[]=vc.columnaHI(m, columnahi);
        
        //------------------------------------------Cambiar Tamaño Columna Limite
        TableColumnModel columnModel = Tabla.getColumnModel();
        columnModel.getColumn(1).setPreferredWidth(100);
        //------------------------------------------Modelo Tabla
        for(int j=1;j<=m;j++)
        {    
            Object[]fila= new Object[6];
            for(int i=0;i<6;i++)
            {
                fila[0]=j;
                fila[1]=vecSt[j-1];
                fila[2]=lst.get(j-1);
                fila[3]=columnaNI[j-1];
                fila[4]=formato3D(columnahi[j-1]).toString().replace(",", ".");;
                fila[5]=formato3D(columnaHI[j-1]).toString().replace(",", ".");;
            }
            modelo.addRow(fila);
        }
        //----------------------------------------------
        editarCabecera();
        txtIndice.setText(modelo.getValueAt(0,0)+"");
        double hi=Double.parseDouble(modelo.getValueAt(0,4).toString().replace(",","."))*100;
        txtValorhi.setText(formato2D(hi).replace(",",".")+" %");
        double Hi=Double.parseDouble(modelo.getValueAt(0,5).toString().replace(",","."))*100;
        txtValorHi.setText(formato2D(Hi).replace(",",".")+" %");
        
    }
    public String formato(double valor)
    {
        DecimalFormat dfTotal=new DecimalFormat("0.0");
        return dfTotal.format(valor);
    }
    public String formato2D(double valor)
    {
        DecimalFormat dfTotal=new DecimalFormat("0.00");
        return dfTotal.format(valor);
    }
    public String formato3D(double valor)
    {
        DecimalFormat dfTotal=new DecimalFormat("0.000");
        return dfTotal.format(valor);
    }
    public void editarCabecera()
    {
        JTableHeader th; 
        th = Tabla.getTableHeader(); 
        Font fuente = new Font("Consolas", Font.BOLD, 12); 
        th.setFont(fuente); 
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtXmax = new javax.swing.JTextField();
        txtXmin = new javax.swing.JTextField();
        txtRango = new javax.swing.JTextField();
        txtM = new javax.swing.JTextField();
        txtC = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtIndice = new javax.swing.JTextField();
        txtValorhi = new javax.swing.JTextField();
        txtValorHi = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        btnGrafica = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("BankGothic Lt BT", 0, 24)); // NOI18N
        jLabel1.setText("VARIABLE CONTINUA");

        jLabel2.setFont(new java.awt.Font("BankGothic Lt BT", 0, 13)); // NOI18N
        jLabel2.setText("Valor Máximo (Xmax): ");

        jLabel3.setFont(new java.awt.Font("BankGothic Lt BT", 0, 13)); // NOI18N
        jLabel3.setText("Valor Mínimo (Xmin): ");

        jLabel4.setFont(new java.awt.Font("BankGothic Lt BT", 0, 13)); // NOI18N
        jLabel4.setText("N° de Datos Diferentes (m): ");

        jLabel5.setFont(new java.awt.Font("BankGothic Lt BT", 0, 13)); // NOI18N
        jLabel5.setText("Rango (R):");

        jLabel6.setFont(new java.awt.Font("BankGothic Lt BT", 0, 13)); // NOI18N
        jLabel6.setText("Ancho del Intervalo (C): ");

        txtXmax.setEditable(false);
        txtXmax.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N

        txtXmin.setEditable(false);
        txtXmin.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N

        txtRango.setEditable(false);
        txtRango.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N

        txtM.setEditable(false);
        txtM.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N

        txtC.setEditable(false);
        txtC.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N

        Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        Tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Tabla);

        jLabel7.setFont(new java.awt.Font("BankGothic Lt BT", 0, 14)); // NOI18N
        jLabel7.setText("Indice N°: ");

        jLabel8.setFont(new java.awt.Font("BankGothic Lt BT", 0, 14)); // NOI18N
        jLabel8.setText("Valor hi: ");

        jLabel9.setFont(new java.awt.Font("BankGothic Lt BT", 0, 14)); // NOI18N
        jLabel9.setText("Valor Hi: ");

        txtIndice.setEditable(false);
        txtIndice.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N

        txtValorhi.setEditable(false);
        txtValorhi.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N

        txtValorHi.setEditable(false);
        txtValorHi.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N

        btnGrafica.setBackground(new java.awt.Color(255, 255, 255));
        btnGrafica.setFont(new java.awt.Font("BankGothic Lt BT", 0, 14)); // NOI18N
        btnGrafica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimedia/icoG.png"))); // NOI18N
        btnGrafica.setText("Gráfica");
        btnGrafica.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGrafica.setIconTextGap(-3);
        btnGrafica.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnGrafica.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGrafica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGraficaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtXmax, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtXmin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtM, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtC, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(txtRango))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jSeparator1)
            .addComponent(jSeparator2)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtIndice)
                    .addComponent(txtValorhi)
                    .addComponent(txtValorHi, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGrafica)
                .addGap(66, 66, 66))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(157, 157, 157))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(txtXmax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRango, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtXmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(txtM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIndice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtValorhi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnGrafica)
                        .addGap(0, 16, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtValorHi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaMouseClicked
        txtIndice.setText(modelo.getValueAt(Tabla.getSelectedRow(),0)+"");
        double hi=Double.parseDouble(modelo.getValueAt(Tabla.getSelectedRow(),4).toString().replace(",","."))*100;
        txtValorhi.setText(formato2D(hi).replace(",",".")+" %");
        double Hi=Double.parseDouble(modelo.getValueAt(Tabla.getSelectedRow(),5).toString().replace(",","."))*100;
        txtValorHi.setText(formato2D(Hi).replace(",",".")+" %");
    }//GEN-LAST:event_TablaMouseClicked

    private void btnGraficaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGraficaActionPerformed
       
        try {
            GraficaVC dialog=new GraficaVC(new javax.swing.JFrame(),true);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(VentanaVContinua.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnGraficaActionPerformed

    public void insertImage()
    {
        ImagePanel Imagen = new ImagePanel();
        jPanel1.add(Imagen);
        jPanel1.repaint();
    }
    public class ImagePanel extends JPanel 
    {
        public ImagePanel()
        {
            //Se crea un método cuyo parámetro debe ser un objeto Graphics
            this.setSize(625,560);
        }
    @Override
        public void paint(Graphics grafico)
        {
            Dimension height = getSize();
            //Se selecciona la imagen que tenemos en el paquete de la //ruta del programa
            ImageIcon Img = new ImageIcon(getClass().getResource("/Multimedia/fios.jpeg")); 
            //se dibuja la imagen que tenemos en el paquete Images //dentro de un panel
            grafico.drawImage(Img.getImage(), 0, 0, height.width, height.height, null);
            setOpaque(false);
            super.paintComponent(grafico);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTable Tabla;
    private javax.swing.JButton btnGrafica;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtC;
    private javax.swing.JTextField txtIndice;
    private javax.swing.JTextField txtM;
    private javax.swing.JTextField txtRango;
    private javax.swing.JTextField txtValorHi;
    private javax.swing.JTextField txtValorhi;
    private javax.swing.JTextField txtXmax;
    private javax.swing.JTextField txtXmin;
    // End of variables declaration//GEN-END:variables
}
