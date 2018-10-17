package VariableDiscreta;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTable;
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
    int valorI=0;
     BufferedImage img=null;
    ArrayList<VDiscreta> lstVd=new ArrayList<>();
    File f=new File("src/Multimedia/imgGraf2.jpg");
    public void imprimir(double a[])
    {
        for(int i=0;i<a.length;i++)
        {
            System.out.print("["+a[i]+"]");
        }
    }
    public ArrayList<VDiscreta> variableDiscreta(double vectorDatos[])
    {
        int aux=0;
        for(int i=0;i<vectorDatos.length;i++)
        { 
            VDiscreta vd=new VDiscreta();
            for(int j=0;j<vectorDatos.length;j++)
            { 
                if(i!=j && vectorDatos[i] == vectorDatos[j])
                { 
                    vectorDatos[j] = -9999;
                    aux++;
                } 
            } 
            if(vectorDatos[i] != -9999)
            {   
                System.out.println("El número: "+vectorDatos[i]+" se repite: "+(aux+1)+" veces"); 
                vd.setXi(vectorDatos[i]);
                vd.setNi((aux+1));
                lstVd.add(vd);
                
                valorI++;
            } 
        aux = 0;
        }
    return lstVd;    
    }    
    public int valorM()
    {
        return valorI;
    }
    public VDiscreta[] vectVd(int n,VDiscreta [] vect)
    {
        for(int i=0;i<lstVd.size();i++)
        {
            vect[i]=new VDiscreta();
            vect[i].setNi(lstVd.get(i).getNi());
            vect[i].setXi(lstVd.get(i).getXi());
        }
        return vect;
    } 
    public void imprimirVD(int n,VDiscreta [] vect)
    {
        for(int i=0;i<lstVd.size();i++)
        {
            System.out.println("Xi: "+vect[i].getXi()+"Ni: "+vect[i].getNi());
        }
    }
    public VDiscreta[] vectOrd(int n,VDiscreta [] vect)
    {
        int salto=vect.length/2; //(inicialmente será: n/2) 
        while(salto>=1)
        { 
            for(int rec=salto;rec<vect.length;rec++)
            { 
                double temp=vect[rec].getXi(); 
                int c=vect[rec].getNi();
                int j=rec-salto; 
                while(j>=0&&vect[j].getXi()>temp)
                { 
                        vect[j+salto].xi=vect[j].getXi(); 
                        vect[j+salto].ni=vect[j].getNi();
                        j-=salto; 

                } 
                vect[j+salto].xi=temp;
                vect[j+salto].ni=c;
            } 
            salto/=2; 
        }
        return vect;
    } 
    public double sumani(JTable tabla)
    {
        double sumatoria=0;
        int totalRow = tabla.getRowCount();
        totalRow -= 1;
        for (int i = 0; i <= (totalRow); i++) 
        {
            sumatoria += Double.parseDouble(String.valueOf(tabla.getValueAt(i, 2)));    
        }
    return sumatoria;    
    }
    public double [] columnaNI(int m, VDiscreta[] vectni)
    {
        double vect[]=new double [m];
        double sumaNI=0;
        
        for(int i=0;i<m;i++)
        {
            sumaNI+=vectni[i].getNi();
            vect[i]=sumaNI;
        }
    return vect;
    }
    public double[] columnahi(int n,VDiscreta[] vectni)
    {
        double  vect[]=new double[lstVd.size()];
       
        for(int i=0;i<lstVd.size();i++)
        {
            vect[i]=((double) (vectni[i].getNi())/n);
            System.out.println("division:"+vectni[i].getNi());
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
        
        for(int i=0;i<lstVd.size();i++)
        {
            sumaHI+=vecthi[i];
        }
        
    return sumaHI;
    }
    public JFreeChart grafica(double [] vector,String vector2[]) 
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(int i = 0; i < vector.length; i++) 
        {
            dataset.setValue(vector[i],"Valor",vector2[i]);
        }
        JFreeChart chart = ChartFactory.createBarChart3D("Frecuencias",
        "Xi", "ni", dataset, PlotOrientation.VERTICAL,
        false, true, false);
        java.awt.Font MyFont = new java.awt.Font ("BankGothic Lt BT",Font.BOLD, 29) ; 
        
       
           
        
        
        try {
            
            img = ImageIO.read(f);
        
        
            
            
        } catch (Exception ex) {
            
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
    

