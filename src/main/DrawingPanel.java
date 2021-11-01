package main;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Ellipse2D.Double;
import java.text.AttributedString;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel {

    // Bez nasledujici definice hlasi prekladac "warning".
    // Tzv. serializaci resit nebudeme, vyuzijeme standardni reseni.
    private static final long serialVersionUID = 1L;

    /**	color of the states */
    public Color[] statesColor;
    
    /** Graphics2D  */
    public Graphics2D g2;
    
    /**	entry string */
    public String entryString;
    
    /**	length of the entered string*/
    private int entryStringLenght;
    
    /**	current state*/
    private int currentState;
    
    /**
     * 
     * @param width		width of the panel
     * @param height	height of the drawing panel
     */
    public DrawingPanel(int width, int height) {
    	this.setMinimumSize(new Dimension(800, 400));
    	this.setSize(new Dimension(width, height));
    	statesColor = new Color[12];
    	
    	this.currentState = 0;
    	for(int i = 0; i<4; i++) {
    		statesColor[i] = Color.red;
    	}
    	for(int i = 4; i< statesColor.length; i++) {
    		statesColor[i] = Color.WHITE;
    	}
    	
    }
    @Override
    public void paint(Graphics g) {
    	super.paint(g);
    	
    	this.g2 = (Graphics2D) g;
    	
    	System.out.println("Vstup: "+this.entryString);
    	int arrowsTip = 10;
    	int statesSize = 30;
    	AffineTransform original = g2.getTransform();
    	//prvni ctyri vztahy
    	drawArrow(10, 200, 100-statesSize, 200, arrowsTip,"e", g2);
    	g2.translate(100, 200);
    	drawArrow(22, -22, 79, -79, arrowsTip,"e", g2);
    	drawArrow(22, 22, 79, 79, arrowsTip,"e", g2);
    	drawState(g2, statesSize, 0);
    	    	
    	
    	g2.translate(100, -100);
    	drawArrow(22, 22, 79, 79, arrowsTip,"e", g2);
    	drawCycleArrow(g2, statesSize, "a", -statesSize );
    	drawState(g2, statesSize, 1);
    	
    	g2.translate(0, 200);
    	drawArrow(22, -22, 79, -79, arrowsTip,"e", g2);
    	drawCycleArrow(g2, statesSize, "b", statesSize );
    	drawState(g2, statesSize, 2);
    	
    	g2.translate(100, -100);
    	drawState(g2, statesSize, 3);
    	//konec prvnich 4 vztahu
    	
    	//DLOUHA LINE
    	drawArrow(30, 0, 70, 0, arrowsTip, "a", g2);
    	g2.translate(100, 0);
    	drawState(g2, statesSize, 4);
    	
    	drawArrow(30, 0, 70, 0, arrowsTip, "b", g2);
    	g2.translate(100, 0);
    	drawState(g2, statesSize, 5);
    	
    	drawArrow(30, 0, 70, 0, arrowsTip, "a", g2);
    	g2.translate(100, 0);
    	drawState(g2, statesSize, 6);
    	
    	drawArrow(30, 0, 70, 0,arrowsTip, "b", g2);
    	g2.translate(100, 0);
    	drawState(g2, statesSize, 7);
    	
    
    	//posledni 4
    	//prvni ctyri vztahy
    	drawArrow(30, 0, 100-statesSize, 0, arrowsTip,"a", g2);
    	g2.translate(100, 0);
    	drawArrow(22, -22, 79, -79, arrowsTip,"e", g2);
    	drawArrow(22, 22, 79, 79, arrowsTip,"e", g2);
    	drawState(g2, statesSize, 8);
    	    	
    	
    	g2.translate(100, -100);
    	drawArrow(22, 22, 79, 79, arrowsTip,"e", g2);
    	drawCycleArrow(g2, statesSize, "a", -statesSize );
    	drawState(g2, statesSize, 9);
    	
    	g2.translate(0, 200);
    	drawArrow(22, -22, 79, -79, arrowsTip,"e", g2);
    	drawCycleArrow(g2, statesSize, "b", statesSize );
    	drawState(g2, statesSize, 10);
    	
    	g2.translate(100, -100);
    	drawState(g2, statesSize, 11);
    	drawArrow(30, 0, 100-statesSize, 0, arrowsTip,"", g2);	
    	//konec prvnich 4 vztahu
    	
    	g2.setTransform(original);
    }
    
    /**
     * Draw a state in form of circle
     * @param g2		drawing library
     * @param r			radius of the circle
     * @param index		index of the state
     */
    public void drawState(Graphics2D g2, double r, int index) {
    	Stroke origStroke = g2.getStroke();
    	g2.setStroke(new BasicStroke(5));
    	
    	Color origColor = g2.getColor();
    	g2.setColor(Color.BLACK);
    	Ellipse2D.Double state = new Ellipse2D.Double(-r, -r, 2*r, 2*r);
		g2.draw(state);
		g2.setColor(this.statesColor[index]);
		g2.fill(state);
		
		g2.setColor(origColor);
		g2.setStroke(origStroke);
		g2.drawString(""+(index+1), 0, 0);
    }
    
    /**
     * Draw a transition in form of circle (from a states to the same one)
     * @param g2			drawing library
     * @param r				radius of the circle	
     * @param symbol		symbol of transition
     * @param yMove			how much move circle up or down
     */
    private void drawCycleArrow(Graphics2D g2, double r, String symbol, double yMove) {
    		
    	g2.draw(new Ellipse2D.Double(-r,-r+yMove, 2*r, 2*r));
    	int tip_lenght = 10;
    	double c = tip_lenght * Math.cos(45);
    			
    	if(yMove < 0 ) {
    		r = -r;
    	}
		Path2D tip = new Path2D.Double();
		
		tip.moveTo( (-c), (r+yMove +c ));
		tip.lineTo(0, r+yMove);
		tip.lineTo(( - c), (r+yMove -c));
		g2.draw(tip);
		g2.drawString(symbol, (float) 0.0, (float)(r +yMove -8) );
    }
    
    /**
     * Draw a transition in form of an arrow
     * @param x1		starting x-coordinate
     * @param y1		starting y-coordinate
     * @param x2		end x-coordinate
     * @param y2		end y-coordinate
     * @param tip_length	lenght of the tip
     * @param symbol		symbol of the transition
     * @param g2			drawing library
     */
	private void drawArrow(double x1, double y1, double x2, double y2, double tip_length, String symbol, Graphics2D g2) {
		// TODO Auto-generated method stub
		double scale_x = this.getWidth()/ x2;		
		double scale_y = this.getHeight() / y2;
		
		double scale = Math.min(scale_x, scale_y); //spolecne meritko
		
		
		g2.setColor(Color.BLACK);
		double u_x = x2-x1;
		double u_y = y2-y1;
		double u_len1 = 1 / Math.sqrt(u_x * u_x + u_y * u_y);
		
		u_x *= u_len1;
		u_y *= u_len1;
		final int strokeSize  = 1;
		Stroke original = g2.getStroke();
		
		g2.setStroke(new BasicStroke(strokeSize, BasicStroke.CAP_ROUND,
				BasicStroke.CAP_BUTT));
		g2.draw(new Line2D.Double(x1 ,y1 , (x2 - u_x*3), (y2 - u_y*3)));
		
		// smer kolmy (jednotkova delka)
		double v_x = u_y;
		double v_y = -u_x;
		
		//smer kolmy - delka o 1/2 sirky hrotu
		v_x *= 0.5*tip_length;
		v_y *= 0.5* tip_length;
		
		double c_x = x2 - u_x * tip_length;
		double c_y = y2 - u_y * tip_length;
		
		Path2D tip = new Path2D.Double();
		tip.moveTo( (c_x + v_x), (c_y + v_y));
		tip.lineTo(x2, y2);
		tip.lineTo((c_x - v_x), (c_y - v_y));
		g2.draw(tip);
		
		g2.setStroke(new BasicStroke(strokeSize));
		
		double stringX = Math.abs(x1-x2);
		double stringY = y2-y1;
		int xShift = 0;
		int yShift = 0;
		
		if(x1 == x2) {
			xShift = -5;
		}else if (y1 == y2) {
			yShift = -5;
		}else if (x1<x2 && y1<y2) {
			yShift = -2;
		}else if (x1 < x2 && y1 > y2) {
			xShift = -20;
			yShift = 10;
		}
		
		g2.drawString(symbol, (float)stringX+xShift, (float)stringY+yShift);
		g2.setStroke(original);
		
	}
	
	/**
	 * According to input string change active states
	 * @param inputString	string of the input
	 */
	public void changeColor(String inputString) {
		
		this.entryString = inputString;
		
		this.entryStringLenght = inputString.length();
		
		String newChar = inputString.substring(inputString.length() - 1);
		
		switch(inputString) {
		case "a":
			for(int i = 0; i< this.statesColor.length; i++) {
				this.statesColor[i] = Color.WHITE;
			}
			this.statesColor[0] = Color.RED;
			this.statesColor[1] = Color.RED;
			this.statesColor[3] = Color.RED;
			break;
		case "b":
			for(int i = 0; i< this.statesColor.length; i++) {
				this.statesColor[i] = Color.WHITE;
			}
			this.statesColor[0] = Color.RED;
			this.statesColor[2] = Color.RED;
			this.statesColor[3] = Color.RED;
			break;
		case "ab":
			for(int i = 0; i< this.statesColor.length; i++) {
				this.statesColor[i] = Color.WHITE;
			}
			this.statesColor[0] = Color.RED;
			this.statesColor[2] = Color.RED;
			this.statesColor[3] = Color.RED;
			this.statesColor[4] = Color.RED;
			break;
		default:
			for(int i = 0; i< this.statesColor.length; i++) {
				this.statesColor[i] = Color.WHITE;
			}
			break;
			
		}
		repaint();
	}
    
}