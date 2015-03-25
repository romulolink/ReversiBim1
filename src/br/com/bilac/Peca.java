package br.com.bilac;

import javax.swing.*;
import java.awt.*;

/**
 * Created by romulolink on 13/03/15.
 */
public class Peca extends JButton{



    public enum Estado { VAZIO, PRETO, BRANCO }
    private static int tamanho = 64;
    private Estado estado;

    public Peca(){
        super();
        estado = Estado.VAZIO;
    }


    public void setEstado(Estado estado){
        this.estado = estado;
    }


    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        if(estado != Estado.VAZIO){
            if (estado == Estado.BRANCO)
                g2d.setColor(Color.WHITE);
            else if (estado == Estado.PRETO)
                g2d.setColor(Color.BLACK);
            g2d.fillOval(6,6,getWidth()-12,getHeight()-12);

        }

        g2d.setColor(Color.GRAY);
        g2d.drawOval(6,6,getWidth()-12,getHeight()-12);

    }



}
