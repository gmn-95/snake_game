package com.gmn;

import com.gmn.move.MovimentoSnake;
import com.gmn.view.Comida;
import com.gmn.view.Snake;
import com.gmn.view.TelaFrame;
import com.gmn.view.menu.MenuInicial;
import com.gmn.view.menu.MenuPause;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements Runnable, KeyListener {

    private final int widthTela;
    private final int heightTela;
    private final Snake snake;
    private final MovimentoSnake movimentoSnake;
    private Thread gameThread;
    private final Comida comida;
    private TelaFrame telaFrame;
    private MenuPause menuPause;
    private static boolean isPausado = false;
    private boolean continuaGame = true;

    public Game(int widthTela, int heightTela, Snake snake, MovimentoSnake movimentoSnake, Comida comida, TelaFrame telaFrame) {
        this.widthTela = widthTela;
        this.heightTela = heightTela;
        this.snake = snake;
        this.movimentoSnake = movimentoSnake;
        this.comida = comida;
        this.comida.posicionarComida();
        this.telaFrame = telaFrame;
        initConfigs();
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void initConfigs(){
        setPreferredSize(new Dimension(widthTela, heightTela));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this.movimentoSnake);
        addKeyListener(this);
    }

    private void addMenuPause() {
        isPausado = true;
        menuPause = new MenuPause(widthTela, heightTela);
        removeKeyListener(this.movimentoSnake);
        add(menuPause); // Adicione o menuPause ao Game JPanel
        revalidate(); // Revalide o Game JPanel para refletir as alterações
    }

    private void removeMenuPause(){
        isPausado = false;
        remove(menuPause);
        addKeyListener(this.movimentoSnake);
        revalidate();
    }

    @Override
    public void run() {
        //game loop
        long lastTime = System.nanoTime(); //tempo atual
        double FPS = 10.0; // FPS
        double ns = 1000000000 / FPS; // Calcula o intervalo de tempo entre atualizações
        double delta = 0; // Variável para controlar a acumulação de tempo
        boolean cacando = true;

        while(cacando && continuaGame) {
            long now = System.nanoTime(); // Obtém o tempo atual novamente
            delta += (now - lastTime) / ns; // Calcula quanto tempo passou desde a última atualização
            lastTime = now; // Atualiza o tempo da última atualização

            if (isPausado) {
                delta = 0; // Reinicia a contagem de tempo
                try {
                    Thread.sleep(10); // Pequena pausa para reduzir uso da CPU
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue; // Pula para a próxima iteração do loop
            }

            if(delta >= 1) {
                cacando = movimentoSnake.cacandoComida();
                // Atualizar a tela na thread de eventos
                SwingUtilities.invokeLater(this::repaint);
                delta--; // "Consome" o tempo acumulado para atualizações

            }
        }

        gameThread.interrupt();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintGame(g);
        //isso deixa o jogo 'liso' sem travamentos
        Toolkit.getDefaultToolkit().sync();
    }

    private JLabel createOptionLabel(String text){
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(Color.WHITE);
        return label;
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

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE && !isPausado){
            addMenuPause();
        }
        else if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE && isPausado) {
            removeMenuPause();
        }
        else if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE
                && Game.isPausado
                || (keyEvent.getKeyCode() == KeyEvent.VK_ENTER
                && menuPause != null
                && menuPause.isOpcaoContinueSelecionada())){
            menuPause.setButtonSelection(false, false);
            removeMenuPause();
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN && Game.isPausado) {
            menuPause.setButtonSelection(false, true);
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_UP && Game.isPausado) {
            menuPause.setButtonSelection(true, false);
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER && menuPause != null && menuPause.isOpcaoExitSelecionada()) {
            removeMenuPause();
            MenuInicial menu = new MenuInicial(widthTela, heightTela, telaFrame);
            JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            jFrame.getContentPane().removeKeyListener(this);
            jFrame.getContentPane().remove(this);
            jFrame.getContentPane().add(menu);
            jFrame.getContentPane().revalidate();
            jFrame.getContentPane().repaint();

            menu.requestFocusInWindow();

            continuaGame = false;
        }

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
