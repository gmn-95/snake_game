package com.gmn.view.menu;

import com.gmn.Game;
import com.gmn.move.MovimentoSnake;
import com.gmn.view.Comida;
import com.gmn.view.Snake;
import com.gmn.view.TelaFrame;
import com.gmn.view.score.ViewScore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MenuInicial extends JPanel implements KeyListener {

    private final int widthTela;
    private final int heightTela;
    private JLabel btStartGame;
    private JLabel btExitGame;
    private JLabel btHelpGame;
    private JLabel btScore;
    private boolean opcaoStartSelecionada = true;
    private boolean opcaoScoreSelecionada = true;
    private boolean opcaoExitSelecionada = false;
    private boolean opcaoHelpSelecionada = false;
    private final TelaFrame telaFrame;

    public MenuInicial(int widthTela, int heightTela, TelaFrame telaFrame) {
        this.widthTela = widthTela;
        this.heightTela = heightTela;
        this.telaFrame = telaFrame;
        this.telaFrame.setTitle("Snake-Game");
        this.telaFrame.setScore(0);
        initConfigs();
    }

    private void initConfigs(){
        setSize(this.widthTela, this.heightTela);
        setBackground(Color.BLACK);
        setFocusable(true);
        setVisible(true);
        setLayout(new GridBagLayout());
        addKeyListener(this);
        initButtons();
    }

    private void initButtons(){

        btStartGame = createOptionLabel("INICIAR");
        btScore = createOptionLabel("SCORE");
        btHelpGame = createOptionLabel("AJUDA");
        btExitGame = createOptionLabel("SAIR");

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0; // Ocupa todo o espaço horizontal disponível
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 0, 10, 0);

        btStartGame.setForeground(Color.YELLOW);
        add(btStartGame, constraints);

        constraints.gridy = 1;
        add(btScore, constraints);

        constraints.gridy = 2;
        add(btHelpGame, constraints);

        constraints.gridy = 3;
        add(btExitGame, constraints);
    }

    private JLabel createOptionLabel(String text){
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(Color.WHITE);
        return label;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN && opcaoStartSelecionada){
            setButtonSelection(false, true, false, false);
        }
        else if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN && opcaoScoreSelecionada){
            setButtonSelection(false, false, true, false);
        }
        else if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN && opcaoHelpSelecionada){
            setButtonSelection(false, false, false, true);
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_UP && opcaoExitSelecionada) {
            setButtonSelection(false, false, true, false);
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_UP && opcaoHelpSelecionada) {
            setButtonSelection(false, true, false, false);
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_UP && opcaoScoreSelecionada) {
            setButtonSelection(true, false, false, false);
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            if(opcaoExitSelecionada){
                SwingUtilities.getWindowAncestor(this).dispose();
                System.exit(0);
            }
            else if(opcaoStartSelecionada){
                iniciaPartida();
            }
            else if(opcaoHelpSelecionada){
                iniciaTelaDeAjuda();
            }
            else if(opcaoScoreSelecionada){
                iniciaTelaScore();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }

    private void iniciaPartida(){
        this.telaFrame.updateTitle(0);
        Snake snake = new Snake();
        Comida comida = new Comida(widthTela, heightTela, snake);
        MovimentoSnake movimentoSnake = new MovimentoSnake(snake, comida, widthTela, heightTela, telaFrame);
        Game game = new Game(widthTela, heightTela, snake, movimentoSnake, comida, telaFrame);

        JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        jFrame.getContentPane().removeKeyListener(this);
        jFrame.getContentPane().remove(this);
        jFrame.getContentPane().add(game);
        jFrame.getContentPane().revalidate();
        jFrame.getContentPane().repaint();

        game.requestFocusInWindow();
    }

    private void iniciaTelaDeAjuda(){
        MenuAjuda menuAjuda = new MenuAjuda(widthTela, heightTela, telaFrame);

        JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        jFrame.getContentPane().removeKeyListener(this);
        jFrame.getContentPane().remove(this);
        jFrame.getContentPane().add(menuAjuda);
        jFrame.getContentPane().revalidate();
        jFrame.getContentPane().repaint();

        menuAjuda.requestFocusInWindow();
    }

    private void iniciaTelaScore(){
        ViewScore viewScore = new ViewScore(widthTela, heightTela, telaFrame);

        JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        jFrame.getContentPane().removeKeyListener(this);
        jFrame.getContentPane().remove(this);
        jFrame.getContentPane().add(viewScore);
        jFrame.getContentPane().revalidate();
        jFrame.getContentPane().repaint();

        viewScore.requestFocusInWindow();
    }

    private void setButtonSelection(boolean start, boolean score, boolean help, boolean exit) {
        opcaoStartSelecionada = start;
        opcaoScoreSelecionada = score;
        opcaoHelpSelecionada = help;
        opcaoExitSelecionada = exit;

        btStartGame.setForeground(opcaoStartSelecionada ? Color.YELLOW : Color.WHITE);
        btScore.setForeground(opcaoScoreSelecionada ? Color.YELLOW : Color.WHITE);
        btHelpGame.setForeground(opcaoHelpSelecionada ? Color.YELLOW : Color.WHITE);
        btExitGame.setForeground(opcaoExitSelecionada ? Color.YELLOW : Color.WHITE);
    }
}
