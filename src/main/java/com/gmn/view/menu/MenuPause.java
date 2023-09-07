package com.gmn.view.menu;

import javax.swing.*;
import java.awt.*;

public class MenuPause  extends JPanel {

    private final int widthTela;
    private final int heightTela;
    private JLabel btContinueGame;
    private JLabel btExitGame;
    private boolean opcaoExitSelecionada = false;
    private boolean opcaoContinueSelecionada = true;

    public MenuPause(int widthTela, int heightTela) {
        this.widthTela = widthTela;
        this.heightTela = heightTela;
        initConfigs();
    }

    public void initConfigs() {
        setSize(this.widthTela, this.heightTela);
        setOpaque(false);
        setFocusable(true);
        setVisible(true);
        setLayout(new GridBagLayout());
        initButtons();
    }

    public void initButtons(){
        btContinueGame = createOptionLabel("CONTINUE");
        btExitGame = createOptionLabel("VOLTAR PARA MENU");

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0; // Ocupa todo o espaço horizontal disponível
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets((heightTela / 2) - 50, 0, 10, 0);

        btContinueGame.setForeground(Color.YELLOW);
        add(btContinueGame, constraints);

        constraints.insets = new Insets(10, 0, 10, 0);
        constraints.gridy = 1;
        add(btExitGame, constraints);
    }

    public JLabel createOptionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(Color.WHITE);
        return label;
    }


    public void setButtonSelection(boolean continua, boolean exit) {
        opcaoContinueSelecionada = continua;
        opcaoExitSelecionada = exit;

        btContinueGame.setForeground(opcaoContinueSelecionada ? Color.YELLOW : Color.WHITE);
        btExitGame.setForeground(opcaoExitSelecionada ? Color.YELLOW : Color.WHITE);
    }

    public boolean isOpcaoExitSelecionada() {
        return opcaoExitSelecionada;
    }

    public boolean isOpcaoContinueSelecionada() {
        return opcaoContinueSelecionada;
    }

}
