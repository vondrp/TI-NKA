package main;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.Serial;

import javax.swing.JPanel;

/**
 * Class drawing panel takes care of drawing of the image
 */
public class DrawingPanel extends JPanel {

	//private final static String TITLE_IMAGE = "./end.jpg";

	@Serial
	private static final long serialVersionUID = 1L;

	/**	radius of the states circle */
	private final int STATES_R = 30;

	/**	how many space between center of two states */
	private final int SPACE_BETWEEN_SPACE_LINE = 100;

	/**	y-coordinate of the center of the image */
	private final int MIDST_Y = 200;

	/**	how much from midst can be center of the state draw*/
	private final int LIMIT_Y_FROM_MIDST = 100;

	/**	font size of the text */
	private final int FONT_SIZE = 20;
	/**	width necessary for drawing an image */
	private final int width;

	/**	height necessary for drawing an image */
	private final int height;

	/**	color of the states */
    public Color[] statesColor;
    
    /** Graphics2D  */
    public Graphics2D g2;

	/**	true - is an end state, otherwise false */
    //private boolean endState;

	/**	image used on the background during end state*/
    private BufferedImage endImage;


    /**
     * Create instance of the drawing panel
     */
    public DrawingPanel() {

		// how many states are going to be drawn
		int drawStates = 12;

		this.width = SPACE_BETWEEN_SPACE_LINE * (drawStates -1);
		this.height = MIDST_Y+ (LIMIT_Y_FROM_MIDST + STATES_R)*2;
    	this.setMinimumSize(new Dimension( this.width , this.height));
    	this.setSize(new Dimension( this.width , this.height));
    	statesColor = new Color[drawStates];

		int firstActive = 4;

    	for(int i = 0; i<firstActive; i++) {
    		statesColor[i] = Color.red;
    	}
    	for(int i = firstActive; i< statesColor.length; i++) {
    		statesColor[i] = Color.WHITE;
    	}
		/*
		try {
			endImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(TITLE_IMAGE));
		} catch (Exception e) {
			e.printStackTrace();
		}*/

    }

	/**
	 * @return	width of the image
	 */
	public int getWidth(){
		return this.width;
	}

	/**
	 * @return	height of the image
	 */
	public int getHeight(){
		return this.height;
	}

	/**
	 * @param endState	status of end state
	 */
	public void setEndState(boolean endState) {
		//this.endState = endState;
	}

	@Override
    public void paint(Graphics g) {
    	super.paint(g);
    	this.g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	/*if(endState)
    		g2.drawImage(endImage,0,0,this.getWidth(),this.getHeight()-40,null);
			*/

		int arrowsTip = 10;
    	int statesSize = STATES_R;

		int spaceBetweenStatesInLine = SPACE_BETWEEN_SPACE_LINE;
		int shortArrowLength = spaceBetweenStatesInLine-statesSize;

		int midstOfImage = MIDST_Y;

		int toTopFromMidst = -LIMIT_Y_FROM_MIDST;

		int toBottomOfImageFromTop = 200;

		int toMidstFromBottom = -100;

		//x is always +, when going up y is -, when going down y is +
		double xYFromBottomToTop = 22;

		//x is always +, when going from bottom y is -, when going from up y is +
		int xYFromTopToBottom = 79;

    	AffineTransform original = g2.getTransform();

		//first four states
		// representation of an empty transition
		String emptyTransition = "e";

		String a_transition = "a";
		String b_transition = "b";
		
		
		drawArrow(statesSize, midstOfImage, shortArrowLength, midstOfImage, arrowsTip, emptyTransition, g2);
    	g2.translate(spaceBetweenStatesInLine, midstOfImage);
    	drawArrow(xYFromBottomToTop, -xYFromBottomToTop, xYFromTopToBottom, -xYFromTopToBottom, arrowsTip, emptyTransition, g2);
    	drawArrow(xYFromBottomToTop, xYFromBottomToTop, xYFromTopToBottom, xYFromTopToBottom, arrowsTip, emptyTransition, g2);
    	drawState(g2, statesSize, 0);
    	    	
    	
    	g2.translate(spaceBetweenStatesInLine, toTopFromMidst);
    	drawArrow(xYFromBottomToTop, xYFromBottomToTop, xYFromTopToBottom, xYFromTopToBottom, arrowsTip, emptyTransition, g2);

		drawCycleArrow(g2, statesSize, a_transition, -statesSize, arrowsTip );
    	drawState(g2, statesSize, 1);
    	
    	g2.translate(0, toBottomOfImageFromTop);
    	drawArrow(xYFromBottomToTop, -xYFromBottomToTop, xYFromTopToBottom, -xYFromTopToBottom, arrowsTip, emptyTransition, g2);
		
		drawCycleArrow(g2, statesSize, b_transition, statesSize, arrowsTip );
    	drawState(g2, statesSize, 2);
    	
    	g2.translate(spaceBetweenStatesInLine, toMidstFromBottom);
    	drawState(g2, statesSize, 3);
    	//end of the first four states
    	

		//LONG LINE of states
    	drawArrow(statesSize, 0, shortArrowLength, 0, arrowsTip, a_transition, g2);
    	g2.translate(spaceBetweenStatesInLine, 0);
    	drawState(g2, statesSize, 4);
    	
    	drawArrow(statesSize, 0, shortArrowLength, 0, arrowsTip, b_transition, g2);
    	g2.translate(spaceBetweenStatesInLine, 0);
    	drawState(g2, statesSize, 5);
    	
    	drawArrow(statesSize, 0, shortArrowLength, 0, arrowsTip, a_transition, g2);
    	g2.translate(spaceBetweenStatesInLine, 0);
    	drawState(g2, statesSize, 6);
    	
    	drawArrow(statesSize, 0, shortArrowLength, 0,arrowsTip, b_transition, g2);
    	g2.translate(spaceBetweenStatesInLine, 0);
    	drawState(g2, statesSize, 7);
    	//end of the line of states
    
    	//last four states
    	drawArrow(statesSize, 0, shortArrowLength, 0, arrowsTip, a_transition, g2);
    	g2.translate(spaceBetweenStatesInLine, 0);
    	drawArrow(xYFromBottomToTop, -xYFromBottomToTop, xYFromTopToBottom, -xYFromTopToBottom, arrowsTip, emptyTransition, g2);
    	drawArrow(xYFromBottomToTop, xYFromBottomToTop, xYFromTopToBottom, xYFromTopToBottom, arrowsTip, emptyTransition, g2);
    	drawState(g2, statesSize, 8);

		//top state
    	g2.translate(spaceBetweenStatesInLine, toTopFromMidst);
    	drawArrow(xYFromBottomToTop, xYFromBottomToTop, xYFromTopToBottom, xYFromTopToBottom, arrowsTip, emptyTransition, g2);
    	drawCycleArrow(g2, statesSize, a_transition, -statesSize, arrowsTip );
    	drawState(g2, statesSize, 9);

		//bottom state
    	g2.translate(0, toBottomOfImageFromTop);
    	drawArrow(xYFromBottomToTop, -xYFromBottomToTop, xYFromTopToBottom, -xYFromTopToBottom, arrowsTip, emptyTransition, g2);
    	drawCycleArrow(g2, statesSize, b_transition, statesSize, arrowsTip );
    	drawState(g2, statesSize, 10);
    	
    	g2.translate(spaceBetweenStatesInLine, toMidstFromBottom);
    	drawState(g2, statesSize, 11);
    	drawArrow(statesSize, 0, shortArrowLength, 0, arrowsTip,"", g2);
    	//last four states

		//return to the original transformation
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
		//g2.setColor(endState?Color.white:Color.black);
		g2.setColor(Color.black);
    	Ellipse2D.Double state = new Ellipse2D.Double(-r, -r, 2*r, 2*r);
		g2.draw(state);
		g2.setColor(this.statesColor[index]);
		g2.fill(state);

		Font original = g2.getFont();
		g2.setFont(new Font("Calibri", Font.BOLD, FONT_SIZE));

		String text = ""+(index+1);
		int w = g2.getFontMetrics().stringWidth(text);
		int h = g2.getFontMetrics().getHeight();


		g2.setColor(this.statesColor[index]!=Color.WHITE?Color.white:Color.black);

		g2.drawString(text, -w/2, (h/4));

		g2.setFont(original);
		g2.setColor(origColor);
		g2.setStroke(origStroke);
    }
    
    /**
     * Draw a transition in form of circle (from a states to the same one)
     * @param g2			drawing library
     * @param r				radius of the circle	
     * @param symbol		symbol of transition
     * @param yMove			how much move circle up or down
     */
    private void drawCycleArrow(Graphics2D g2, double r, String symbol, double yMove, double tip_length) {
    		
    	g2.draw(new Ellipse2D.Double(-r,-r+yMove, 2*r, 2*r));
    	double c = tip_length * Math.cos(45);
    	if(yMove < 0 ) {
    		r = -r;
    	}
		Path2D tip = new Path2D.Double();
		
		tip.moveTo( (-c), (r+yMove +c ));
		tip.lineTo(0, r+yMove);
		tip.lineTo(( - c), (r+yMove -c));
		g2.draw(tip);

		Font originalFont = g2.getFont();
		int w = g2.getFontMetrics().stringWidth(symbol);

		//g2.setColor(endState?Color.white:Color.black);
		g2.setFont(new Font("Calibri", Font.BOLD, FONT_SIZE));
		g2.drawString(symbol, (float) -w/2, (float)(r +yMove -8) );
		g2.setFont(originalFont);
    }
    
    /**
     * Draw a transition in form of an arrow
     * @param x1		starting x-coordinate
     * @param y1		starting y-coordinate
     * @param x2		end x-coordinate
     * @param y2		end y-coordinate
     * @param tip_length	length of the tip
     * @param symbol		symbol of the transition
     * @param g2			drawing library
     */
	private void drawArrow(double x1, double y1, double x2, double y2, double tip_length, String symbol, Graphics2D g2) {
		//g2.setColor(endState?Color.white:Color.black);
		double u_x = x2-x1;
		double u_y = y2-y1;
		double u_len1 = 1 / Math.sqrt(u_x * u_x + u_y * u_y);
		
		u_x *= u_len1;
		u_y *= u_len1;
		final int strokeSize  = 1;
		Stroke original = g2.getStroke();
		
		g2.setStroke(new BasicStroke(strokeSize, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_MITER));
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

		Font originalFont = g2.getFont();
		g2.setFont(new Font("Calibri", Font.BOLD, FONT_SIZE));

		// magical numbers - find them out during testing
		if(x1 == x2) {
			xShift = -5;
		}else if (y1 == y2) {
			yShift = -5;
		}else if (x1<x2 && y1<y2) {
			yShift = -2;
		}else if (x1 < x2) {
			xShift = -20;
			yShift = 10;
		}
		g2.drawString(symbol, (float)stringX+xShift, (float)stringY+yShift);
		g2.setStroke(original);
		g2.setFont(originalFont);
	}

	/**
	 * Change set of colors
	 * and repaint the image
	 * @param colors	set of colors
	 */
	public void setColors(Color[] colors){
		if(colors.length == this.statesColor.length) {
			this.statesColor = colors;
			repaint();
		}
	}
    
}