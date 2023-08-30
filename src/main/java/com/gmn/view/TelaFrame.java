package com.gmn.view;

import com.gmn.Game;
import com.gmn.move.MovimentoSnake;

import javax.swing.*;
import java.awt.*;

public class TelaFrame extends JFrame {

    private static final int widthTela = 300;
    private static final int heightTela = 300;

    public TelaFrame() throws HeadlessException {
        super();
        setTitle("Snake-Game");
        setSize(new Dimension(widthTela, heightTela));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        Snake snake = new Snake();
        Comida comida = new Comida(widthTela, heightTela, snake);
        MovimentoSnake movimentoSnake = new MovimentoSnake(snake, comida, widthTela, heightTela);
        Game game = new Game(widthTela, heightTela, snake, movimentoSnake, comida);

        /**
         * {@link SwingUtilities.invokeLater} é um método da biblioteca Swing que é usado para garantir que algumas operações
         * relacionadas à GUI sejam executadas na thread de despacho de eventos do Swing,
         * também conhecida como Event Dispatch Thread (EDT). Isso é importante porque as operações de GUI devem ser
         * tratadas em uma única thread para evitar problemas de sincronização e atualizações concorrentes, que podem levar
         * a comportamentos indesejados ou até mesmo a erros.
         * */
        SwingUtilities.invokeLater(() -> {
            add(game);
            setVisible(true);
        });
    }
}
