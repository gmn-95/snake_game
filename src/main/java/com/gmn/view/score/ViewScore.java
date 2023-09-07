package com.gmn.view.score;

import com.gmn.view.TelaFrame;
import com.gmn.view.menu.MenuInicial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ViewScore extends JPanel implements KeyListener {

    private final int widthTela;
    private final int heightTela;
    private final TelaFrame telaFrame;

    public ViewScore(int widthTela, int heightTela, TelaFrame telaFrame) {
        this.widthTela = widthTela;
        this.heightTela = heightTela;
        this.telaFrame = telaFrame;
        initConfigs();
    }

    public void initConfigs(){
        setSize(this.widthTela, this.heightTela);
        setBackground(Color.BLACK);
        setFocusable(true);
        setVisible(true);
        setLayout(new GridBagLayout());
        addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE){
            voltaParaMenu();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    private void voltaParaMenu(){
        MenuInicial menu = new MenuInicial(widthTela, heightTela, telaFrame);

        JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        jFrame.getContentPane().removeKeyListener(this);
        jFrame.getContentPane().remove(this);
        jFrame.getContentPane().add(menu);
        jFrame.getContentPane().revalidate();
        jFrame.getContentPane().repaint();

        menu.requestFocusInWindow();
    }
}
