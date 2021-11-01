package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
        
        // Titulek okna
        frame.setTitle("TI - NKA");
        frame.setSize(800, 400);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Umisti okno doprostred obrazovky
        frame.setLocationRelativeTo(null);    
        
        // Zobrazi okno
        frame.setVisible(true);  
    
        DrawingPanel drawingPanel = new DrawingPanel(1150, 300);
        drawingPanel.setPreferredSize(new Dimension(1150, 480));
        frame.add(drawingPanel, BorderLayout.CENTER);;
        
        JPanel bottomPanel = new JPanel();

        JLabel textLabel = new JLabel("text");
        JTextField textField = new JTextField();
        textField.setColumns(10);
        
        textField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textLabel.setText(textField.getText());
				drawingPanel.changeColor(textField.getText());
			}
        	
        });

        bottomPanel.add(textLabel);
        bottomPanel.add(textField);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.pack();
		
	}

}
