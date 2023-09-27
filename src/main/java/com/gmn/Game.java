package com.gmn;

import com.gmn.move.MovimentoSnake;
import com.gmn.view.Comida;
import com.gmn.view.Snake;
import com.gmn.view.TelaFrame;
import com.gmn.view.menu.EndGame;
import com.gmn.view.menu.MenuInicial;
import com.gmn.view.menu.MenuPause;
import com.gmn.view.score.RegistraPontuacaoGeral;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements Runnable, KeyListener {

    private final int widthTela;
    private final int heightTela;
    private Snake snake;
    private MovimentoSnake movimentoSnake;
    private Thread gameThread;
    private Comida comida;
    private TelaFrame telaFrame;
    private MenuPause menuPause;
    private EndGame endGame;
    private static boolean isPausado = false;
    private boolean continuaGame = true;
    private int telaEndGameAdicionada = 0;
    private boolean jaAdicionou = false;
    private boolean cacando = true;

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

    private void addEndGame(){
        endGame = new EndGame(widthTela, heightTela);
        removeKeyListener(this.movimentoSnake);
        add(endGame);
        revalidate();
    }

    private void removeMenuPause(){
        isPausado = false;
        remove(menuPause);
        addKeyListener(this.movimentoSnake);
        revalidate();
    }

    private void removeEndGameMenu(){
        remove(endGame);
        revalidate();
    }

    @Override
    public void run() {
        //game loop
        long lastTime = System.nanoTime(); //tempo atual
        double FPS = 10.0; // FPS
        double ns = 1000000000 / FPS; // Calcula o intervalo de tempo entre atualizações
        double delta = 0; // Variável para controlar a acumulação de tempo


        while(continuaGame) {

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
                if(!cacando){
                    if(!jaAdicionou){
                        addEndGame();
                        RegistraPontuacaoGeral.registraPontuacao(telaFrame.getScore());
                        jaAdicionou = true;
                        telaEndGameAdicionada = 1;
                    }
                    delta = updateDelta(delta);
                    continue;
                }
                cacando = movimentoSnake.cacandoComida();
                delta = updateDelta(delta);

            }
        }

        gameThread.interrupt();
    }

    private double updateDelta(double deltaAtual){
        // Atualizar a tela na thread de eventos
        SwingUtilities.invokeLater(this::repaint);
        deltaAtual--;// "Consome" o tempo acumulado para atualizações
        return deltaAtual;
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
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(telaEndGameAdicionada > 0){
            if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN && endGame.isOpcaoRecomecarSelecionada()){
                endGame.setButtonSelection(false, true);
            }
            else if (keyEvent.getKeyCode() == KeyEvent.VK_UP && endGame.isOpcaoExitSelecionada()) {
                endGame.setButtonSelection(true, false);
            }
            else if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER && endGame != null && endGame.isOpcaoRecomecarSelecionada()){
                removeEndGameMenu();
                telaFrame.setScore(0);
                telaFrame.updateTitle(0);
                telaEndGameAdicionada = 0;
                snake = new Snake();
                comida.setSnake(snake);
                movimentoSnake = new MovimentoSnake(snake, comida, widthTela, heightTela, telaFrame);
                addKeyListener(movimentoSnake);
                cacando = true;
                jaAdicionou = false;
                SwingUtilities.invokeLater(this::repaint);
            }
            else if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER && endGame != null && endGame.isOpcaoExitSelecionada()){
                removeEndGameMenu();
                retornarMenuInicial();
                continuaGame = false;
            }

            return;
        }

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
            retornarMenuInicial();
            RegistraPontuacaoGeral.registraPontuacao(telaFrame.getScore());

            continuaGame = false;
        }


    }

    private void retornarMenuInicial(){
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

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
