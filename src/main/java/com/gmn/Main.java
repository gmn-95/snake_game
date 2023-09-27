package com.gmn;

import com.gmn.view.TelaFrame;
import com.gmn.view.score.RegistraPontuacaoGeral;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        RegistraPontuacaoGeral.criaCaminhoSave();
        SwingUtilities.invokeLater(TelaFrame::new);
    }
}