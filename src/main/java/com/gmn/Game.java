package com.gmn;

import com.gmn.move.MovimentoSnake;
import com.gmn.view.Comida;
import com.gmn.view.Snake;

import javax.swing.*;
import java.awt.*;

public class Game extends JPanel implements Runnable {

    private final int widthTela;
    private final int heightTela;
    private Snake snake;
    private MovimentoSnake movimentoSnake;
    private Thread gameThread;
    private Comida comida;

    public Game(int widthTela, int heightTela, Snake snake, MovimentoSnake movimentoSnake, Comida comida) {
        this.widthTela = widthTela;
        this.heightTela = heightTela;
        this.snake = snake;
        this.movimentoSnake = movimentoSnake;
        this.comida = comida;
        this.comida.posicionarComida();
        initConfigs();
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void initConfigs(){
        setPreferredSize(new Dimension(widthTela, heightTela));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this.movimentoSnake);
    }

    @Override
    public void run() {
        //game loop
        long lastTime = System.nanoTime(); //tempo atual
        double FPS = 10.0; // FPS
        double ns = 1000000000 / FPS; // Calcula o intervalo de tempo entre atualizações
        double delta = 0; // Variável para controlar a acumulação de tempo
        boolean cacando = true;

        while(cacando) {
            long now = System.nanoTime(); // Obtém o tempo atual novamente
            delta += (now - lastTime) / ns; // Calcula quanto tempo passou desde a última atualização
            lastTime = now; // Atualiza o tempo da última atualização


            if(delta >= 1) {
                cacando = movimentoSnake.cacandoComida();
                repaint();
                delta--; // "Consome" o tempo acumulado para atualizações

            }
        }

        System.out.println("ACABOU");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintGame(g);
        //isso deixa o jogo 'liso' sem travamentos
        Toolkit.getDefaultToolkit().sync();
    }

    private void paintGame(Graphics g) {
        comida.draw(g);
        snake.draw(g);
//        bordas(g);

//        g.drawLine(widthTela, heightTela, 0, 0);
//        g.drawLine(heightTela, widthTela, 0, 0);
//        g.drawLine(0, 0, heightTela, widthTela);
//        g.drawLine(0, 0, widthTela, heightTela);
//        g.drawLine(widthTela, 0, 0, heightTela);
//        g.drawLine(0, widthTela, 0, heightTela);
//        g.drawLine(0, heightTela, 0, widthTela);


    }

    private void bordas(Graphics g){
//        g.drawLine(0, 0, 0, heightTela);
//        g.drawLine(0, 0, widthTela, 0);
//        g.drawLine(widthTela - 1, heightTela, widthTela - 1, 0);
//
//        g.drawLine(0, widthTela - 29, heightTela, heightTela - 29);
    }
}
