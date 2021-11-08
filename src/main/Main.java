package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

/**
 * Main class of a project
 */
public class Main {

    /** length of last processed string */
    private static int stringLength = 0;
    /**
     * @param args  no entry parameters
     */
	public static void main(String[] args) {
		JFrame frame = new JFrame();

        frame.setTitle("TI - NKA (a*+b*)ababa(a*+b*)");
        frame.setSize(800, 400);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // relocate window to center of the monitor
        frame.setLocationRelativeTo(null);    

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

        JLabel descTextLabel = new JLabel();
        descTextLabel.setText("Ovládání: vstup: aA/bB | reset: r/R");

        JLabel accepted = new JLabel("NEAKCEPTOVÁN");
        Automat auto = new Automat();

        textField.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) { }

            @Override
            public void keyReleased(KeyEvent e) {

                int toProcess = textField.getText().length() - stringLength;

                String substring = "";
                if(toProcess>0) {
                    substring = textField.getText().substring(stringLength);
                }
                while(Math.abs(toProcess)>0) {
                    char symbol;
                    if(toProcess>0) {
                         symbol = substring.charAt(toProcess - 1);
                    }else{
                        symbol = KeyEvent.VK_BACK_SPACE;
                    }
                    switch (symbol) {
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
                        case KeyEvent.VK_BACK_SPACE:
                            auto.backwards();
                            break;
                        default:
                            if (textField.getText().length() == 1) {
                                textField.setText("");
                            } else {
                                textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
                            }
                            break;
                    }
                    if(toProcess>0) toProcess--;
                    else toProcess++;
                }

                stringLength = textField.getText().length();
                if(auto.isEndState()){
                    accepted.setText("AKCEPTOVÁN");
                }else{
                    accepted.setText("NEAKCEPTOVÁN");
                }
                textLabel.setText(textField.getText());
                drawingPanel.setColors(convertToColors(auto.getActivations()));
                drawingPanel.setEndState(auto.isEndState());
            }
        	
        });
        JLabel output = new JLabel(" Výstup: ");

        Box unitePanel = Box.createVerticalBox();

        JPanel bottomPanel2 = new JPanel();
        bottomPanel2.add(accepted);

        JPanel bottomPanel3 = new JPanel();
        bottomPanel3.add(descTextLabel);
        bottomPanel2.add(output);
        bottomPanel2.add(textLabel);
        bottomPanel.add(textField);


        unitePanel.add(bottomPanel3);
        unitePanel.add(bottomPanel2);
        unitePanel.add(bottomPanel);
        frame.add(unitePanel, BorderLayout.SOUTH);
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
