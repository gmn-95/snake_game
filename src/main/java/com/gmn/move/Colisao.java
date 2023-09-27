package com.gmn.move;

import com.gmn.view.Comida;
import com.gmn.view.Snake;

import java.awt.*;

public class Colisao {

    private final Snake snake;
    private final int widthTela;
    private final int heightTela;

    public Colisao(Snake snake, int widthTela, int heightTela) {
        this.snake = snake;
        this.widthTela = widthTela;
        this.heightTela = heightTela;
    }

    /**
     * Verifica a distancia da cobra e da comida para verificar se no próximo frame havera colisão
     * */
    public boolean colisaoComComida(Point novaPosicaoCabeca, Comida comida){
        int distanciaX = Math.abs(novaPosicaoCabeca.x - comida.getPontoComida().x);
        int distanciaY = Math.abs(novaPosicaoCabeca.y - comida.getPontoComida().y);
        return distanciaX <= snake.getWidth() && distanciaY <= snake.getHeight();
    }

    public boolean colisaoComParedes(Point novaPosicaoCabeca){
        return novaPosicaoCabeca.x >= widthTela - 2
                || novaPosicaoCabeca.x <= 0
                || novaPosicaoCabeca.y >= heightTela - 30
                || novaPosicaoCabeca.y <= 0;
    }

    public boolean colisaoComProprioCorpo(Point novaPosicaoCabeca){
        return snake.getCorpo().stream().anyMatch(
                corpoSnake -> {
                    int distanciaX = Math.abs(corpoSnake.x - novaPosicaoCabeca.x);
                    int distanciaY = Math.abs(corpoSnake.y - novaPosicaoCabeca.y);
                    return distanciaX <= snake.getWidth() && distanciaY <= snake.getHeight();
                }
        );
    }

}
