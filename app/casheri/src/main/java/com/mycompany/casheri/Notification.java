package com.mycompany.casheri;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Notification {
    public Notification(String message, int ms) {
        JDialog dialog = new JDialog();
        dialog.setUndecorated(true); 
        dialog.setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        messageLabel.setOpaque(true);
        messageLabel.setBackground(new Color(255, 255, 224)); 
        messageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dialog.add(messageLabel, BorderLayout.CENTER);
        dialog.setSize(200, 70);
        dialog.setLocationRelativeTo(null); 
        dialog.setVisible(true);
        
        Timer timer = new Timer(ms, e->{ dialog.dispose(); });
        timer.setRepeats(false); 
        timer.start();
    }
}
