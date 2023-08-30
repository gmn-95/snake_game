package com.gmn;

import com.gmn.view.TelaFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaFrame::new);
    }
}