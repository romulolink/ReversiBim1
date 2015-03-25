package br.com.bilac;

import javax.swing.*;

/**
 * Created by romulolink on 13/03/15.
 */
public class Janela extends JFrame {

    public Janela(){
        super("Reversi");
        getContentPane().add(new Tabuleiro());
        pack();
        setSize(500,500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Janela();
    }
}
