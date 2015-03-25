package br.com.bilac;

import javax.swing.*;
import java.awt.*;

public class Tabuleiro extends JPanel {


    private Peca[][] tabuleiro;
    private Peca.Estado Estado;

    public Tabuleiro(){
        setLayout(new GridLayout(8,8));
        tabuleiro = new Peca[8][8];

        for (int l=0; l<8;l++)
            for (int c=0;c<8;c++){

                tabuleiro[c][l] = new Peca();
                add(tabuleiro[c][l]);
            }


          tabuleiro[3][3].setEstado(Estado.BRANCO);
          tabuleiro[4][4].setEstado(Estado.BRANCO);
          tabuleiro[3][4].setEstado(Estado.PRETO);
          tabuleiro[4][3].setEstado(Estado.PRETO);


    }


}
