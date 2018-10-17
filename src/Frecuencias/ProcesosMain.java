/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frecuencias;

import javax.swing.JOptionPane;

/**
 *
 * @author JL
 */
public class ProcesosMain 
{
    double vectorD[];
    public static int Mvalue;
    
    public double[] ingresoDatos(int n) 
    {
        vectorD=new double[n];
        int cont=0;
        String y;
        for (int i = 0; i <n; i++) 
        {
            do
            {
                try
                {
                    cont=1;
                    y=(JOptionPane.showInputDialog(null,"Dato N°: "+i));
                    vectorD[i]=Double.parseDouble(y);
                }catch(NumberFormatException e)
                {
                    JOptionPane.showMessageDialog(null,"Error: Ingresar Solo Números","Mensaje",JOptionPane.ERROR_MESSAGE);
                    cont=0;
                }
            }while(cont==0);
        }
    return vectorD;
    }
    public int valorM(double vectD[])//Determinar el numero de datos diferentes...
    {
        int cont=0;
        int sw=0;
        for(int i = 0; i < vectD.length; i++)
        {
            for(int j = i + 1; j < vectD.length; j++)
            {
		if(i!=j && vectD[i] == vectD[j])
                {
                    Mvalue++;
                    sw=1;
		}
            }
            if(sw!=1)
            {
                cont++;
            }
            sw=0;
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
    public double []vectorDATOS()//Cargar Vector Original
    {
        return vectorD;
    }
    
}
