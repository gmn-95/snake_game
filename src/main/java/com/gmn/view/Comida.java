package com.gmn.view;

import java.awt.*;
import java.util.Random;

public class Comida {

    private Point pontoComida;
    private Snake snake;
    private int widthTela;
    private int heightTela;
    private int raio = 8;

    public Comida(int widthTela, int heightTela, Snake snake) {
        this.widthTela = widthTela;
        this.heightTela = heightTela;
        this.snake = snake;
    }

    public void posicionarComida(){
        while (true){
            Random randomX = new Random();
            int x = randomX.nextInt(widthTela - (raio * 2));

            Random randomY = new Random();
            int y = randomY.nextInt(heightTela - (raio * 2)) - 20;

            if((x <= 2 || x >= widthTela) || (y <= 2 || y >= heightTela) || evitarNascerNoCorpoDaCobra(x, y)){
                continue;
            }

            pontoComida = new Point(x, y);
            break;
        }
    }

    private boolean evitarNascerNoCorpoDaCobra(int x, int y){
        return snake.getCorpo().stream().anyMatch(
                corpoSnake -> {
                    int distanciaX = Math.abs(corpoSnake.x - x);
                    int distanciaY = Math.abs(corpoSnake.y - y);
                    return distanciaX <= snake.getWidth() && distanciaY <= snake.getHeight();
                }
        );
    }

    public void draw(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(pontoComida.x, pontoComida.y, raio, raio);
    }

    public Point getPontoComida() {
        return pontoComida;
    }

}
