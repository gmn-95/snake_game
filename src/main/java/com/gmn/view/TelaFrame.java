package com.gmn.view;

import com.gmn.view.menu.MenuInicial;

import javax.swing.*;
import java.awt.*;

public class TelaFrame extends JFrame {

    private static final int widthTela = 300;
    private static final int heightTela = 300;
    private int score = 0;

    public TelaFrame() throws HeadlessException {
        super();
        setTitle("Snake-Game - Score: " + score);
        setSize(new Dimension(widthTela, heightTela));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        MenuInicial menuInicial = new MenuInicial(widthTela, heightTela, this);


        /**
         * {@link SwingUtilities.invokeLater} é um método da biblioteca Swing que é usado para garantir que algumas operações
         * relacionadas à GUI sejam executadas na thread de despacho de eventos do Swing,
         * também conhecida como Event Dispatch Thread (EDT). Isso é importante porque as operações de GUI devem ser
         * tratadas em uma única thread para evitar problemas de sincronização e atualizações concorrentes, que podem levar
         * a comportamentos indesejados ou até mesmo a erros.
         * */
        SwingUtilities.invokeLater(() -> {
            add(menuInicial);
            setVisible(true);
        });
    }

    public void updateTitle(int score) {
        this.score += score;
        setTitle("Snake-Game - Score: " + this.score);
    }

    public void setScore(int score) {
        this.score = score;
    }
}
