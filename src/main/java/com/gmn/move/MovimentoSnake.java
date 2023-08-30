package com.gmn.move;

import com.gmn.view.Comida;
import com.gmn.view.Snake;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MovimentoSnake implements KeyListener {

    private Snake snake;
    private Comida comida;
    private KeyEvent novaDirecao;
    private int direcaoAtual;
    private final Colisao colisao;
    private final int widthTela;
    private final int heightTela;

    public MovimentoSnake(Snake snake, Comida comida, int widthTela, int heightTela) {
        this.snake = snake;
        this.comida = comida;
        this.widthTela = widthTela;
        this.heightTela = heightTela;
        this.colisao = new Colisao(snake, this.widthTela, this.heightTela);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_UP && direcaoAtual != KeyEvent.VK_DOWN){
            novaDirecao = keyEvent;
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN && direcaoAtual != KeyEvent.VK_UP) {
            novaDirecao = keyEvent;
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT && direcaoAtual != KeyEvent.VK_RIGHT) {
            novaDirecao = keyEvent;
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT && direcaoAtual != KeyEvent.VK_LEFT) {
            novaDirecao = keyEvent;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }

    public boolean cacandoComida(){
        Point novaPosicaoCabeca = direcao();
        boolean colidiu = colisao.colisaoComParedes(novaPosicaoCabeca);
        if(direcaoAtual != 0){ // if só pra manter a cobra posicionada no inicio do jogo
            if(!colidiu && !colisao.colisaoComProprioCorpo(novaPosicaoCabeca)){

                if (colisao.colisaoComComida(novaPosicaoCabeca, comida)) {
                    snake.getCorpo().addFirst(novaPosicaoCabeca);
                    comida.posicionarComida();
                } else {
                    snake.getCorpo().addFirst(novaPosicaoCabeca);
                    snake.getCorpo().removeLast();
                }
                return true;
            }
            return false;
        }
        snake.getCorpo().addFirst(novaPosicaoCabeca);
        snake.getCorpo().removeLast();
        return true;
    }

    private Point direcao(){
        Point pair = snake.getCorpo().getFirst();
        int x = pair.x;
        int y = pair.y;
        if(novaDirecao != null){
            if(novaDirecao.getKeyCode() == KeyEvent.VK_UP){
                y -= 9;
                direcaoAtual = novaDirecao.getKeyCode();
            }
            else if(novaDirecao.getKeyCode() == KeyEvent.VK_DOWN ){
                y += 9;
                direcaoAtual = novaDirecao.getKeyCode();
            }
            else if(novaDirecao.getKeyCode() == KeyEvent.VK_RIGHT ){
                x += 9;
                direcaoAtual = novaDirecao.getKeyCode();
            }
            else if(novaDirecao.getKeyCode() == KeyEvent.VK_LEFT ){
                x -= 9;
                direcaoAtual = novaDirecao.getKeyCode();
            }
        }

        return new Point(x, y);
    }
}
