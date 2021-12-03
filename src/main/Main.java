package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

/**
 * Main class of a project
 */
public class Main {

    /**
     * @param args  no entry parameters
     */
	public static void main(String[] args) {
		JFrame frame = new JFrame();

        frame.setTitle("TI - NKA (a*+b*)ababa(a*+b*)");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLocationRelativeTo(null); //relocate window to center of the monitor
        frame.setVisible(true);

        Box topPanel = Box.createVerticalBox();
        JLabel titleLabel = new JLabel("Simulace " +
                "činnosti nedeterministického rozpoznávacího automatu");
        titleLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 25));
        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        JTextArea descLabel = new JTextArea();
        descLabel.setText("Nedeterministický rozpoznávací automat rozhoduje, zda zadaný vstupní řetězec vyhovuje zadanému regulárnímu výrazu.   \n" +
                "Červeně vybarvené stavy znázorňují množinu stavů, do kterých může být automat v různých výpočtech převeden.\n" +
                "V jednom z těchto stavů se tedy v konkrétním výpočtu v dané fázi zpracování řetězce automat bude nacházet. \n" +
                "Řetězec vyhovuje regulárnímu výrazu právě tehdy, existuje-li výpočet, který jej převede do koncového stavu, tedy\n" +
                "je-li koncový stav vybarven.");
        descLabel.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        descLabel.setMargin(new Insets(2,50,2,2));

        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(descLabel, BorderLayout.SOUTH);
        frame.add(topPanel, BorderLayout.NORTH);

        DrawingPanel drawingPanel = new DrawingPanel();
        drawingPanel.setPreferredSize(new Dimension(drawingPanel.getWidth(), drawingPanel.getHeight()));
        frame.add(drawingPanel, BorderLayout.CENTER);
        frame.setMinimumSize(new Dimension(drawingPanel.getWidth(), drawingPanel.getHeight()));
        frame.setResizable(false);

        JLabel textLabel = new JLabel();
        JTextField textField = new JTextField();
        textField.setColumns(10);

        JLabel descTextLabel = new JLabel();
        descTextLabel.setText("Ovládání: vstup: aA/bB | reset: r/R");

        JLabel accepted = new JLabel("NEAKCEPTOVÁN");
        accepted.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
        Automat auto = new Automat();

        textField.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                char validChar = 0;
                switch (e.getKeyChar()) {
                    case 'a':
                    case 'A':
                        validChar=e.getKeyChar();
                        auto.input(0);
                        break;
                    case 'b':
                    case 'B':
                        validChar=e.getKeyChar();
                        auto.input(1);
                        break;
                    case 'r':
                    case 'R':
                        auto.reset();
                        textField.setText("");
                        textLabel.setText("");
                        e.consume();
                        break;
                    case 8:
                        auto.backwards();
                        break;
                    default:
                        e.consume();
                        return;
                }

                if(auto.isEndState()){
                    accepted.setText("AKCEPTOVÁN");
                    accepted.setForeground(Color.GREEN);
                }else if(auto.isRejectedState()){
                    accepted.setText("ZAMÍTNUT");
                    accepted.setForeground(Color.RED);
                    auto.reset();
                    textLabel.setText(textField.getText()+(validChar!=0?validChar:""));
                    textField.setText("");
                    e.consume();
                    return;
                }else{
                    accepted.setText("NEAKCEPTOVÁN");
                    accepted.setForeground(Color.BLACK);
                }

                textLabel.setText(textField.getText()+(validChar!=0?validChar:""));
                drawingPanel.setColors(convertToColors(auto.getActivations()));

            }

            @Override
            public void keyPressed(KeyEvent e) { }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        	
        });
        JLabel output = new JLabel(" Zpracovaný řetězec: ");

        Box unitePanel = Box.createVerticalBox();

        JPanel bottomPanel2 = new JPanel();
        bottomPanel2.add(output);
        bottomPanel2.add(textLabel);
        bottomPanel2.add(accepted);

        JPanel bottomPanel3 = new JPanel();
        bottomPanel3.add(descTextLabel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(textField);

        JLabel authors = new JLabel("Autoři: Petr Vondrovic, Matěj Černý\n");
        authors.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        JLabel yearOfProduction = new JLabel("2021");
        yearOfProduction.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        JPanel footer = new JPanel();
        footer.setBackground(Color.GRAY);
        footer.add(authors);
        //footer.add(yearOfProduction, BorderLayout.AFTER_LAST_LINE);

        JPanel footer2 = new JPanel();
        footer2.setBackground(Color.GRAY);
        footer2.add(yearOfProduction, BorderLayout.AFTER_LAST_LINE);

        unitePanel.add(bottomPanel3);
        unitePanel.add(bottomPanel2);
        unitePanel.add(bottomPanel);
        unitePanel.add(footer);
        unitePanel.add(footer2);
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
