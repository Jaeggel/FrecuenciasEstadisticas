/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VariableContinua;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author JL
 */
public class Procesos 
{
    ArrayList<Integer> lst=new ArrayList<Integer>();
    int vec[];
    BufferedImage img;

    public int contTipoDouble(double a[])
    {
        String aux;
        int cont=0;
        for(int i=0;i<a.length;i++)
        {
            aux=Double.toString(a[i]);
            int pos=aux.indexOf(".");
            int valorDec=Integer.parseInt(aux.substring(pos+1));
            if(valorDec!=0)
            cont++;
        }
    return cont;    
    }
    public int contTipoDouble2D(double a[])
    {
        String aux;
        int cont=0;
        for(int i=0;i<a.length;i++)
        {
            aux=Double.toString(a[i]);
            int pos=aux.indexOf(".");
            String valorDec=aux.substring(pos+2);
            if(!valorDec.equals(""))
            cont++;
        }
    return cont;    
    }
    public void imprimir(double []a)
    {
        for(int i=0;i<a.length;i++)
        {
            System.out.print("["+a[i]+"]");
        }
    }
    public double xmax(double vector[])
    {
        double valorMax=0;
        for (int i = 0; i < vector.length; i++) 
        {
            if(vector[i]>valorMax)
            {
                valorMax=vector[i];
            }
        }
    return valorMax;    
    }
    public double xmin(double vector[])
    {
        double valorMin=99999;
        for (int i = 0; i < vector.length; i++) 
        {
            if(vector[i]<valorMin)
            {
                valorMin=vector[i];
            }
        }
    return valorMin;       
    }
    public String[] columnaLimites(int m,double xmin,String c1,double c,double sumaVec2,int sw)
    {
        c=Double.parseDouble(c1.replace(",","."));
        String vector[]=new String[m];
        //--------------------------------Y'i-1
        double vect1[]=new double[m];
        double sumaVec1=xmin;
        for (int i = 0; i < m; i++) 
        {
            
            vect1[i]=sumaVec1;
            sumaVec1+=c;
        } 
        //---------------------------------Y'i
        double vect2[]=new double[m];
        
        for (int i = 0; i < m; i++) 
        {
            sumaVec2+=c;
            vect2[i]=sumaVec2;
        }
        //---------------------------------Unión
        for (int i = 0; i < m; i++)
        {
            if(sw==2)
            vector[i]=formato(vect1[i])+"   -   "+formato(vect2[i]);
            else if(sw==1)
                vector[i]=formato2D(vect1[i])+"   -   "+formato2D(vect2[i]);
            else
                vector[i]=vect1[i]+"   -   "+vect2[i];
        }
        
    return vector;    
    }      
    public double [] lim1(int m,double xmin,double c,String c1,int sw)
    {
        c=Double.parseDouble(c1.replace(",","."));
        double vect1[]=new double[m];
        double sumaVec1=xmin;
        String sv1=null;
        if(sw==2)
        {
            sv1=formato(sumaVec1);
        }
        else
            {
                sv1=formato2D(sumaVec1);
            }
        sumaVec1=Double.parseDouble(sv1.replace(",","."));
        for (int i = 0; i < m; i++) 
        {
            if(sw==2)
            {
                vect1[i]=Double.parseDouble(formato(sumaVec1).replace(",","."));
                sumaVec1+=c;
            }else
                {
                    vect1[i]=Double.parseDouble(formato2D(sumaVec1).replace(",","."));
                    sumaVec1+=c;
                }
            
        }
        return vect1;
    }
    public double[] lim2(int m,double c,double xmin,String c1,double sumaVec2,int sw)
    {
        c=Double.parseDouble(c1.replace(",","."));
        double vect2[]=new double[m];
        String sv2=null;
        if(sw==2)
        {
            sv2=formato(sumaVec2);
        }
        else
            {
                sv2=formato2D(sumaVec2);
            }
        
        sumaVec2=Double.parseDouble(sv2.replace(",","."));
        for (int i = 0; i < m; i++) 
        {
            sumaVec2+=c;
             if(sw==2)
                vect2[i]=Double.parseDouble(formato(sumaVec2).replace(",","."));
             else
                 vect2[i]=Double.parseDouble(formato2D(sumaVec2).replace(",","."));
        }
    return vect2;    
    }
    public void imprimirVectSt(String a[])
    {
        for (int i = 0; i < a.length; i++) 
        {
            System.out.println(a[i]);
        }
    }
    public ArrayList<Integer> columnani(double vectLim1[],double vectLim2[],double[] vect,int m)
    {
       int aux=0;
     
        for(int i=0;i<vectLim1.length;i++)
        { 
            for(int j=0;j<vect.length;j++)
            { 
                if(vect[j]>=vectLim1[i] && vect[j]<=vectLim2[i])
                { 
                    aux++;
                } 
            } 
            lst.add(aux);
        aux = 0;
        }
      
    return lst;
    }
    public int []columnani()
    {
        vec=new int[lst.size()];
        for (int i = 0; i < lst.size(); i++) 
        {
            vec[i]=lst.get(i);
        }
        
    return vec;
    }
    public double [] columnaNI(int m,int vec[])
    {
        double vect[]=new double [m];
        double sumaNI=0;
        
        for(int i=0;i<m;i++)
        {
            sumaNI+=vec[i];
            vect[i]=sumaNI;
        }
    return vect;
    }
    public double[] columnahi(int n,int[] vectni)
    {
        double  vect[]=new double[lst.size()];
       
        for(int i=0;i<lst.size();i++)
        {
            vect[i]=((double) (vectni[i])/n);
        }
    return vect;
    }
    public double [] columnaHI(int m, double[] vecthi)
    {
        double vect[]=new double [m];
        double sumaHI=0;
        
        for(int i=0;i<m;i++)
        {
            sumaHI+=vecthi[i];
            vect[i]=sumaHI;
        }
    return vect;
    }
    public double sumahi(double[] vecthi)
    {
        double sumaHI=0;
        
        for(int i=0;i<lst.size();i++)
        {
            sumaHI+=vecthi[i];
        }
        
    return sumaHI;
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
    public JFreeChart grafica(double [] vector,String vector2[]) 
    {  
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(int i = 0; i < vector.length; i++) 
        {
            dataset.setValue(vector[i],"Valor",vector2[i]);
        }
        JFreeChart chart = ChartFactory.createBarChart3D("Frecuencias",
        "Y'i-1  -  Y'i", "ni", dataset, PlotOrientation.VERTICAL,
        false, true, false);
        java.awt.Font MyFont = new java.awt.Font ("BankGothic Lt BT",Font.BOLD, 29) ; 
        
   

        try {
            img = ImageIO.read(new File("src/Multimedia/imgGraf2.jpg"));
        
        
        } catch (IOException ex) {
           
        }finally
        {
            chart.getTitle().setFont(MyFont);
        chart.setBackgroundImage(img);
        CategoryPlot p = chart.getCategoryPlot (); 
        p.setBackgroundImage(img);
        p.setRangeGridlinePaint (Color.black); 
        }
        
    return chart;
    }
}
