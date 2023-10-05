import java.awt.event.*;
import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
//import java.awt.geom.*;
import java.util.Random;
import java.awt.Graphics2D;

//an animated shape.
public class Shape extends JPanel implements ActionListener
{
	Timer timer = new Timer(5, this);
	int x = 230, y = 250;
	int length = 0, width = 0;
	int iteration = 0;
	int[] color;
	int whatShape;
	int easingType;
	boolean finished = false;
	Random g = new Random();
	int size = g.nextInt((300-100)+1) + 100;
	
	//randomize size that's not their choice, no more communison only tyranny
	
	public Shape(int[] colors, int shape, int easing)
	{
		color = colors;
		whatShape = shape;
		easingType = easing;
		this.setOpaque(false);
//		this.setBackground(new Color (20, 30, 50));
		this.setBounds(410, 410, 800, 700);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		if(whatShape == 0)
		{
			g2.drawOval(x, y, width, length);
			g2.setPaint(new Color(color[0], color[1], color[2]));
			g2.fillOval(x, y, width, length);
		}
		if(whatShape == 1)
		{
			g2.drawRect(x, y, width, length);
			g2.setPaint(new Color(color[0], color[1], color[2]));
			g2.fillRect(x, y, width, length);
		}	
		if(whatShape == 2)
		{
		}
		if(whatShape == 3)
		{
			
		}
	}
	
	public void startTimer()
	{
		timer.start();
	}
	public void stopTimer()
	{
		timer.stop();
	}
	public boolean isFinished()
	{
		return finished;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if(width > size) //range from 300 to 200 random value
		{
			timer.stop();
			finished = true;
		}
		if(width < 500)
		{
			x -= 1;
			y -= 1;
			if(easingType == 0)
			{
				width = easeInOut(iteration, 40, 300, 300);
				length = easeInOut(iteration, 40, 300, 300);
			}
			if(easingType == 1)
			{
				width = easeIn(iteration, 40, 230, 230);
				length = easeIn(iteration, 40, 230, 230);
			}
			if(easingType == 2)
			{
				width = easeOut(iteration, 40, 300, 300);
				length = easeOut(iteration, 40, 300, 300);
			}
			
			iteration++;
			System.out.println(iteration);
			System.out.println("width and length" + width + length);
			System.out.println("blaaaa" + easeOut(iteration, 40, 300, 130));
			//adjust location of start but with the function, because it wont work otherwise
			//NOTES IN MSGS ON FUNC OFFSET
		}
//		System.out.println(iteration);
		repaint();
	}
	
	//robert penner easing functions
	public static int easeOut(float currentIteration,float b , float c, float d) 
	{
		double blah = -c *(currentIteration/=d)*(currentIteration-2) + b;
		int returning = (int)Math.round(blah);
		return returning;
	}
	
	public static int easeInOut(float t,float b , float c, float d) 
	{
		if ((t/=d/2) < 1)
		{
			double blah = c/2*t*t + b;
			int returning = (int)Math.round(blah); 
			return returning;
		}
		double blah = -c/2 * ((--t)*(t-2) - 1) + b;
		int returning = (int)Math.round(blah); 
		return returning;
	}
	
	public static int easeIn(float t,float b , float c, float d) 
	{
		double blah = -c * ((float)Math.sqrt(1 - (t/=d)*t) - 1) + b;
		int returning = (int)Math.round(blah); 
		return returning;
	}

}

//add all ongoing shapes to an array to stop if needed