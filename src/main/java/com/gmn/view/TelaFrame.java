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
        add(game);
        setVisible(true);
    }
}
