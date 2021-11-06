package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {

    private static Color[] convertToColors(boolean[] activations){
        Color[] out = new Color[activations.length];
        for (int i = 0; i < activations.length; i++)
            out[i] = activations[i]?Color.RED:Color.WHITE;
        return out;
    }
	public static void main(String[] args) {
		JFrame frame = new JFrame();
        
        // Titulek okna
        frame.setTitle("TI - NKA (a*+b*)ababa(a*+b*)");
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


        Automat auto = new Automat();

        textField.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                textLabel.setText(textField.getText());

                if(e.getKeyChar()=='a')
                    auto.input(0);
                else if(e.getKeyChar()=='b')
                    auto.input(1);
                else if(e.getKeyChar()=='r'){
                    auto.reset();
                    textField.setText("");
                    textLabel.setText("");
                }
                else if(e.getKeyCode()== KeyEvent.VK_BACK_SPACE){
                    auto.reset();
                    textField.setText("");
                    textLabel.setText("");
                }

                drawingPanel.setColors(convertToColors(auto.getActivations()));
                drawingPanel.setEndState(auto.isEndState());
            }

            @Override
            public void keyPressed(KeyEvent e) { }

            @Override
            public void keyReleased(KeyEvent e) { }
        	
        });

        bottomPanel.add(textLabel);
        bottomPanel.add(textField);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.pack();
		
	}

}
