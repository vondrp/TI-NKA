package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Main class of an project
 */
public class Main {

    /**
     * @param args  no entry parameters
     */
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
    
        DrawingPanel drawingPanel = new DrawingPanel();
        drawingPanel.setPreferredSize(new Dimension(drawingPanel.getWidth(), drawingPanel.getHeight()));

        frame.add(drawingPanel, BorderLayout.CENTER);

        frame.setMinimumSize(new Dimension(drawingPanel.getWidth(), drawingPanel.getHeight()));

        frame.setResizable(false);
        JPanel bottomPanel = new JPanel();

        JLabel textLabel = new JLabel();
        JTextField textField = new JTextField();
        textField.setColumns(10);


        Automat auto = new Automat();

        textField.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) { }

            @Override
            public void keyReleased(KeyEvent e) {
                switch(e.getKeyChar()){
                    case 'a':
                    case 'A':
                        auto.input(0);
                        break;
                    case 'b':
                    case 'B':
                        auto.input(1);
                        break;
                    case 'r':
                    case 'R':
                        auto.reset();
                        textField.setText("");
                        textLabel.setText("");
                        break;
                    case  KeyEvent.VK_BACK_SPACE:
                        auto.backwards();
                        break;
                    default:
                        if(textField.getText().length() == 1) {
                            textField.setText("");
                        }else {
                            textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
                        }
                        break;
                }
                textLabel.setText(textField.getText());
                drawingPanel.setColors(convertToColors(auto.getActivations()));
                drawingPanel.setEndState(auto.isEndState());
            }
        	
        });

        bottomPanel.add(textLabel);
        bottomPanel.add(textField);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.pack();
	}

    /**
     * According to array of booleans set colors for the drawing
     * @param activations   activations of the states
     * @return              color representation of active/inactive states
     */
    private static Color[] convertToColors(boolean[] activations){
        Color[] out = new Color[activations.length];
        for (int i = 0; i < activations.length; i++)
            out[i] = activations[i]?Color.RED:Color.WHITE;
        return out;
    }

}
