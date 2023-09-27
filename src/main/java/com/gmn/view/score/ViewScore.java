package com.gmn.view.score;

import com.gmn.view.TelaFrame;
import com.gmn.view.menu.MenuInicial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    private void initConfigs(){
        setSize(this.widthTela, this.heightTela);
        setBackground(Color.BLACK);
        setFocusable(true);
        setVisible(true);
        setLayout(new GridBagLayout());
        addKeyListener(this);
        initTexto();
    }

    private void initTexto(){
        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);
        editorPane.setFocusable(true);

        StringBuilder html = new StringBuilder();
        html.append("<html>");
        html.append("<body style='color: white'>");
        html.append("<h4>Melhores pontuações</h4>");
        html.append("<p>(Pressione ESC para sair)</p>");
        html.append("<table border='1' cellpadding='5'>");

        html.append("<tr>");
        html.append("<th>Pontuação</th>");
        html.append("<th>Data</th>");
        html.append("</tr>");

        List<ScoreVO> scoreVOList = RegistraPontuacaoGeral.listaPontuacao();
        DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        for(ScoreVO scoreVO : scoreVOList){
            html.append("<tr>");
            html.append("<td>");
            html.append(scoreVO.getScore());
            html.append("</td>");
            html.append("<td>");
            html.append(scoreVO.getData().format(formatoDataHora));
            html.append("</td>");
            html.append("</tr>");
        }
        html.append("</table>");
        html.append("</body>");
        html.append("</html>");

        editorPane.setText(html.toString());
        editorPane.setBackground(Color.BLACK);
        editorPane.addKeyListener(this);
        add(editorPane);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.requestFocusInWindow();
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
