import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

public class Canvas implements ActionListener
{
	private JFrame frame;
	
//	private boolean miniGameMode = false;
	
	private JPanel settings;
	private JPanel startPage;
	private JLayeredPane canvas;
	private JPanel menu;
	private JPanel instruc;
	
	//setting buttons
	private JButton toggleMode;
	private JButton toggleSound;
	private JButton toggleDisplay;
	private JButton hideSettings;
	
	//instruction button
	private JButton hideInstruc;
	
	//startPage buttons
	private JButton start;
	private JButton openSettings;
	private JButton openInstructions;
	
	private JButton exit; //have exit and setting swap places
	
	//canvas button
	private JButton openMenu;
	
	//menu buttons
	private JButton hideMenu;
	private JButton escapeCanvas;
	private JButton resetCanvas;
	private JButton toggleRandom;
	
	//hide random menu
	private JPanel hideRandom;
	
	//shapebuttons (menu)
	private JButton circle;
	private JButton square;
	private JButton triangle;
	
	//easingbuttons (menu)
	private JButton easeIn;
	private JButton easeOut;
	private JButton easeInOut;
	
	//shape making + menu stuff
	private int[] colors; //the 3 rgb values (controlled by slider)
	private int shape; // 0 is circle, 1 is square, 2 is tri
	private int easing;
	private boolean randomized;
	
	//sliders
	private JSlider rSlider;
	private JSlider gSlider;
	private JSlider bSlider;
	//slider labels
	private JLabel rValue;
	private JLabel gValue;
	private JLabel bValue;
	
	//preview of rgb color
	private JPanel colorPreview;
	
	//instruction labels
	private JLabel freeFormInstruc;
	private JLabel miniGameInstructions;
	private JLabel controls;

	//label images
	private JLabel title;
	private JLabel startBackg;
	private JLabel greyOverlay;
	private JLabel greyOverlayCanvas;
	
	//images
	private ImageIcon random;
	private ImageIcon custom;
	
	//textlabels
	private JLabel soundLabel; //has button and slider under it
	private JLabel displayLabel;
	private JLabel modeLabel;
	private JLabel instrucLabel;
	private JLabel shapeLabel;
	private JLabel easingLabel;
	private JLabel colorLabel;
	
	private JPanel testing;
	
	private ArrayList<Shape> shapeList;
		
	//TODO LIST
	//dark mode
	//option for bounds/collision (if shape hits the bounds of another shape it stops growing)
	//position offset
	//minigame
	
	//currently not working
	//settings panel
	//triangle shape
	//disappearing shapes
	
	public Canvas()
	{
		frame = new JFrame();
		frame.setSize(800, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		shapeList = new ArrayList<Shape>();
		
		playInstance();
	}
	
	public void playInstance()
	{
		randomized = true; //custom is broken right now
		
		colors = new int[3];
		colors[0] = 255;
		colors[1] = 255;
		colors[2] = 255;
		
		instructions();
		labels();
		panels();
		buttons();
		sliders();
		adding();

		//run makingshape before start to disregard delay
		//but dont acutally add it
		//make new thread when start so 2animation can happen at same time 
	}
	
	public void instructions()
	{
		instrucLabel = new JLabel("Welcome to Canvas!");
		instrucLabel.setFont(new Font("Garamond", Font.PLAIN, 30));
		instrucLabel.setBounds(30, 40, 400, 30);
		
		freeFormInstruc = new JLabel("<html>"
				+ "Canvas is a game where the user is the creator.<br/>"
				+ "When clicking on the screen, different shapes will appear, simulating natural motion in their appearance (through easing). By default, the color, shape, and easing type are randomly generated.<br/>"
				+ "However, there is a menu to customize these attributes, allowing for complete user control.<br/><br/><html>");
		freeFormInstruc.setFont(new Font("Garamond", Font.PLAIN, 20));
		freeFormInstruc.setBounds(30, 0, 400, 400);
		
		controls = new JLabel("<html>Press the 3 lines to access the menu that adjusts shape generation.<html>");
		controls.setFont(new Font("Garamond", Font.PLAIN, 20));
		controls.setBounds(30, 152, 400, 400);
	}
	
	public void labels()
	{
		//image labels
		title = new JLabel(new ImageIcon("assets/canvastext.png"));
		title.setBounds(150,130,500,160);
		
		startBackg = new JLabel(new ImageIcon("assets/watercolorsplash.png"));
		startBackg.setBounds(0,-10,800,700);
		
		greyOverlay = new JLabel(new ImageIcon("assets/overlay.png"));
		greyOverlay.setBounds(0,0,800,700);
		greyOverlay.setVisible(false);
		
		greyOverlayCanvas = new JLabel(new ImageIcon("assets/overlay.png"));
		greyOverlayCanvas.setBounds(0,0,800,700);
		greyOverlayCanvas.setVisible(false);
		
		//text labels
		soundLabel = new JLabel("Sound");
		soundLabel.setBounds(123, 130, 100, 20);
		soundLabel.setFont(new Font("Garamond", Font.PLAIN, 30));
		//sound toggle, display(dark mode light mode) toggle, mode toggle minigame freeform
		
		displayLabel = new JLabel("Display");
		displayLabel.setBounds(123, 215, 100, 30);
		displayLabel.setFont(new Font("Garamond", Font.PLAIN, 30));
		
		modeLabel = new JLabel("Mode");
		modeLabel.setBounds(123, 310, 100, 20);
		modeLabel.setFont(new Font("Garamond", Font.PLAIN, 30));
		
		rValue = new JLabel("R");
		rValue.setBounds(23, 340, 24, 30);
		rValue.setFont(new Font("Arial", Font.PLAIN, 24));
		
		gValue = new JLabel("G");
		gValue.setBounds(23, 377, 24, 30);
		gValue.setFont(new Font("Arial", Font.PLAIN, 24));
		
		bValue = new JLabel("B");
		bValue.setBounds(23, 413, 24, 30);
		bValue.setFont(new Font("Arial", Font.PLAIN, 24));
		
		shapeLabel = new JLabel("Shapes");
		shapeLabel.setBounds(23, 20, 100, 30);
		shapeLabel.setFont(new Font("Garamond", Font.PLAIN, 27));
		
		easingLabel = new JLabel("Easings");
		easingLabel.setBounds(23, 150, 100, 30);
		easingLabel.setFont(new Font("Garamond", Font.PLAIN, 27));
		
		colorLabel = new JLabel("Color");
		colorLabel.setBounds(23, 300, 100, 30);
		colorLabel.setFont(new Font("Garamond", Font.PLAIN, 27));
	}
	
	public void panels()
	{
		startPage = new JPanel();
		startPage.setBounds(0,0,800,700);
		startPage.setVisible(true);
		startPage.setBackground(new Color(255, 255, 255));
		startPage.setLayout(null);
		
		settings = new JPanel();
		settings.setBounds(133,110, 508, 450);
		settings.setVisible(false);
		settings.setBackground(new Color(255, 255, 255));
		settings.setLayout(null);
		
		instruc = new JPanel();
		instruc.setBounds(133,110, 508, 450);
		instruc.setVisible(false);
		instruc.setBackground(new Color(255, 255, 255));
		instruc.setLayout(null);
		
		canvas = new JLayeredPane();
		canvas.setBounds(0,0,800,700);
		canvas.setVisible(false);
		canvas.setBackground(new Color(255, 255, 255));
		canvas.setLayout(null);
		canvas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				if(!menu.isVisible())
				{
					makingShapeTM(e.getX()-230, e.getY()-120);
				}
				
			}
		}); //turn off enable when in menu
		canvas.setOpaque(true);
		
		menu = new JPanel();
		menu.setBounds(0,0, 275, 700);
		menu.setVisible(false);
		menu.setBackground(new Color(207, 236, 255));
		menu.setLayout(null);
		
		//the preview 
		colorPreview = new JPanel();
		colorPreview.setBounds(38, 472, 195, 80);
		colorPreview.setBackground(new Color(colors[0], colors[1], colors[2]));
		
		testing = new JPanel();
		testing.setBounds(200, 200, 45, 45);
		testing.setBackground(new Color(255, 255, 255));
		
		hideRandom = new JPanel();
		hideRandom.setBounds(0,0, 275, 560);
		hideRandom.setVisible(true);
		hideRandom.setBackground(new Color(122, 203, 255));
	}
	
	public void buttons()
	{
		//start page buttons
		start = new JButton();
		start.setBounds(310, 380, 170, 58);
		start.setText("Start");		
		start.setFont(new Font("Garamond", Font.PLAIN, 30));
//		start.setContentAreaFilled(false);
		start.setBackground(new Color(255, 255, 255));
		start.setOpaque(true);
		start.addActionListener(this);
		
		openInstructions = new JButton();
		openInstructions.setBounds(310, 480, 170, 60);
		openInstructions.setText("Instructions");	
		openInstructions.setFont(new Font("Garamond", Font.PLAIN, 27));
//		openInstructions.setContentAreaFilled(false);
		openInstructions.setBackground(new Color(255, 255, 255));
		openInstructions.setOpaque(true);
		openInstructions.addActionListener(this);
		
		openSettings = new JButton(new ImageIcon("assets/buttons/settings.png"));
		openSettings.setBounds(680,570,80,65);
		openSettings.setContentAreaFilled(false);
		openSettings.setBorderPainted(false);
		openSettings.addActionListener(this);
		
		exit = new JButton(new ImageIcon("assets/buttons/exit.png"));
		exit.setBounds(0,570,80,65);
		exit.setContentAreaFilled(false);
		exit.setBorderPainted(false);
		exit.addActionListener(this);
		
		
		//settings buttons
		hideSettings = new JButton(new ImageIcon("assets/buttons/ximage.png"));
		hideSettings.setBounds(450,20,40,40);
		hideSettings.setContentAreaFilled(false);
		hideSettings.setBorderPainted(false);
		hideSettings.addActionListener(this);
		
		toggleSound = new JButton();
		toggleSound.setBounds(260, 110, 120, 60);
		toggleSound.setText("On");	
		toggleSound.setFont(new Font("Garamond", Font.PLAIN, 20));
		toggleSound.setContentAreaFilled(false);
		toggleSound.addActionListener(this);
		
		toggleDisplay = new JButton();
		toggleDisplay.setBounds(260, 200, 120, 60);
		toggleDisplay.setText("Light");	
		toggleDisplay.setFont(new Font("Garamond", Font.PLAIN, 20));
		toggleDisplay.setContentAreaFilled(false);
		toggleDisplay.addActionListener(this);
		
		toggleMode = new JButton();
		toggleMode.setBounds(260, 290, 120, 60);
		toggleMode.setText("Freeform");	
		toggleMode.setFont(new Font("Garamond", Font.PLAIN, 20));
		toggleMode.setContentAreaFilled(false);
		toggleMode.addActionListener(this);
		
		//instruc button
		hideInstruc = new JButton(new ImageIcon("assets/buttons/ximage.png"));
		hideInstruc.setBounds(450,20,40,40);
		hideInstruc.setContentAreaFilled(false);
		hideInstruc.setBorderPainted(false);
		hideInstruc.addActionListener(this);
		
		//canvas buttons
		openMenu = new JButton(new ImageIcon("assets/buttons/menubutton.png"));
		openMenu.setBounds(20,20,40,40);
		openMenu.setContentAreaFilled(false);
		openMenu.setBorderPainted(false);
		openMenu.addActionListener(this);
		
		//menu buttons
		hideMenu = new JButton(new ImageIcon("assets/buttons/hideMenu.png"));
		hideMenu.setBounds(275,330,40,40);
		hideMenu.setContentAreaFilled(false);
		hideMenu.setBorderPainted(false);
		hideMenu.addActionListener(this);
		hideMenu.setVisible(false);
		
		escapeCanvas = new JButton(new ImageIcon("assets/buttons/exit.png"));
		escapeCanvas.setBounds(20,572,80,65);
		escapeCanvas.setContentAreaFilled(false);
		escapeCanvas.setBorderPainted(false);
		escapeCanvas.addActionListener(this);
		
		resetCanvas = new JButton(new ImageIcon("assets/buttons/refresh.png"));
		resetCanvas.setBounds(180,575,50,50);
		resetCanvas.setContentAreaFilled(false);
		resetCanvas.setBorderPainted(false);
		resetCanvas.addActionListener(this);
		
		random = new ImageIcon("assets/buttons/random.png");
		custom = new ImageIcon("assets/buttons/custom.png");
		toggleRandom = new JButton(random);
		toggleRandom.setBounds(110,575,50,50);
		toggleRandom.setContentAreaFilled(false);
		toggleRandom.setBorderPainted(false);
		toggleRandom.addActionListener(this);
		
		//shape menu buttons
		circle = new JButton(new ImageIcon("assets/buttons/circleIcon.png"));
		circle.setBounds(25,70,45,45);
		circle.setBorderPainted(true);
		circle.addActionListener(this);
		circle.setVisible(false);
		
		triangle = new JButton(new ImageIcon("assets/buttons/triangleIcon.png"));
		triangle.setBounds(109,70,45,45);
		triangle.setBorderPainted(true);
		triangle.addActionListener(this);
		triangle.setVisible(false);
		
		square = new JButton(new ImageIcon("assets/buttons/squareIcon.png"));
		square.setBounds(190,70,45,45);
		square.setBorderPainted(true);
		square.addActionListener(this);
		square.setVisible(false);
		
		//easing menu buttons
		easeIn = new JButton(new ImageIcon("assets/buttons/easeIn.png"));
		easeIn.setBounds(25,200,45,45);
		easeIn.setBorderPainted(true);
		easeIn.addActionListener(this);
		easeIn.setVisible(false);
		
		easeOut = new JButton(new ImageIcon("assets/buttons/easeOut.png"));
		easeOut.setBounds(109,200,45,45);
		easeOut.setBorderPainted(true);
		easeOut.addActionListener(this);
		easeOut.setVisible(false);
		
		easeInOut = new JButton(new ImageIcon("assets/buttons/easeInOut.png"));
		easeInOut.setBounds(190,200,45,45);
		easeInOut.setBorderPainted(true);
		easeInOut.addActionListener(this);
		easeInOut.setVisible(false);
	}
	
	public void sliders()
	{
		rSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);
		rSlider.setOpaque(false);
		rSlider.setBounds(45, 335, 200, 50);

		rSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e)
			{
				colors[0] = rSlider.getValue();
				colorPreview.setBackground(new Color(colors[0], colors[1], colors[2]));
				colorPreview.repaint();
			}
		});
		
		gSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);
		gSlider.setOpaque(false);
		gSlider.setBounds(45, 370, 200, 50);
		gSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e)
			{
				colors[1] = gSlider.getValue();
				colorPreview.setBackground(new Color(colors[0], colors[1], colors[2]));
				colorPreview.repaint();
			}
		});

		bSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);
		bSlider.setOpaque(false);
		bSlider.setBounds(45, 405, 200, 50); 
		bSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e)
			{
				colors[2] = bSlider.getValue();
				colorPreview.setBackground(new Color(colors[0], colors[1], colors[2]));
				colorPreview.repaint();
			}
		});

	}
	
	public void makingShapeTM(int x, int y)
	{
		//locals
		int[] coloring = new int[3];
		int typeShape;
		int typeEasing;
		
		if(randomized)
		{
			Random generator = new Random();
			for(int i = 0; i < coloring.length; i++)
			{
				coloring[i] = generator.nextInt((250 - 0) + 1) + 0;
			}
			
			typeShape = generator.nextInt((1 - 0) + 1) + 0;
			typeEasing = generator.nextInt((2 - 0) + 1) + 0;
		}
		else
		{
			coloring[0] = colors[0];
			coloring[1] = colors[1];
			coloring[2] = colors[2];
			
			typeShape = shape;
			typeEasing = easing;
		}

		Shape theShape = new Shape(coloring, typeShape, typeEasing); //change input to not be globals, but temp values that are put in each time otherwise custom won't work
		theShape.setSize(800,700);
		theShape.setLocation(x, y);
		
		canvas.setLayer(menu, canvas.getComponentCount()-1);
		canvas.setLayer(greyOverlayCanvas, canvas.getComponentCount()-1);
		canvas.setLayer(openMenu, canvas.getComponentCount()-1);
		canvas.setLayer(hideMenu, canvas.getComponentCount()-1);
		canvas.add(theShape,0);
		
		shapeList.add(theShape);
		for(int i = shapeList.size()-1; i > 0 ; i--)
		{
			int blah = 1;
			canvas.setLayer(shapeList.get(i), blah);
			blah++;
		}
		
		theShape.startTimer();
	}
	
	public void actionPerformed (ActionEvent e)
	{
		//startpage buttons
		if(e.getSource() == start)
		{
			startPage.setVisible(false);
			canvas.setVisible(true);
		}
		if(e.getSource() == openInstructions)
		{
			start.setEnabled(false);
			openInstructions.setEnabled(false);
			greyOverlay.setVisible(true);
			instruc.setVisible(true);
			//make x button on instruc so you can exit out from it
		}
		if(e.getSource() == openSettings)
		{
			start.setEnabled(false);
			openInstructions.setEnabled(false);
			greyOverlay.setVisible(true);
			settings.setVisible(true);
		}
		if(e.getSource() == exit)
		{
			System.exit(0);
		}
		
		
		//settings buttons
		if(e.getSource() == hideSettings)
		{
			settings.setVisible(false);
			greyOverlay.setVisible(false);
			start.setEnabled(true);
			openInstructions.setEnabled(true);
		}
		if(e.getSource() == toggleMode)
		{
			if(toggleMode.getText().equals("Freeform"))
			{
				toggleMode.setText("MiniGame");
			}
			else
			{
				toggleMode.setText("Freeform");
			}
		}
		if(e.getSource() == toggleDisplay)
		{
			if(toggleDisplay.getText().equals("Light"))
			{
				toggleDisplay.setText("Dark");
			}
			else
			{
				toggleDisplay.setText("Light");
			}
			//setbackground to dark colors
		}
		if(e.getSource() == toggleSound)
		{
			if(toggleSound.getText().equals("On"))
			{
				toggleSound.setText("Off");
			}
			else
			{
				toggleSound.setText("On");
			}
			//turn off sound effects
		}
		
		
		//instruc button
		if(e.getSource() == hideInstruc)
		{
			instruc.setVisible(false);
			greyOverlay.setVisible(false);
			start.setEnabled(true);
			openInstructions.setEnabled(true);
		}
		
	
		//canvas button
		if(e.getSource() == openMenu) //make this toggle Menu
		{
			menu.setVisible(true);
			hideMenu.setVisible(true);
			greyOverlayCanvas.setVisible(true);
			
			if(shapeList != null)
			{
				for(int i = 0; i < shapeList.size(); i++)
				{
					shapeList.get(i).stopTimer();
				}
			}
		}
		if(e.getSource() == hideMenu)
		{
			menu.setVisible(false);
			hideMenu.setVisible(false);
			greyOverlayCanvas.setVisible(false);
			//if timer isn't finished, continue them
			
			if(shapeList != null)
			{
				for(int i = 0; i < shapeList.size(); i++)
				{
					if(!shapeList.get(i).isFinished())
					{
						shapeList.get(i).startTimer();
					}
				}
			}
		}
		
		
		//menu buttons
		if(e.getSource() == resetCanvas)
		{
			reset();
		}
		if(e.getSource() == escapeCanvas)
		{
			reset();
			canvas.setVisible(false);
			startPage.setVisible(true);
			hideMenu.doClick();
			
			if(toggleRandom.getIcon() == custom)
			{
				toggleRandom.doClick();
			}
		}
		if(e.getSource() == toggleRandom)
		{
			//random should shut off top menu
			if(toggleRandom.getIcon() == random)
			{
				toggleRandom.setIcon(custom);
				randomized = false;
				rSlider.setEnabled(true);
				gSlider.setEnabled(true);
				bSlider.setEnabled(true);
				hideRandom.setVisible(false);
				
				circle.setVisible(true);
				triangle.setVisible(true);
				square.setVisible(true);
				colorPreview.repaint();
				
				easeIn.setVisible(true);
				easeOut.setVisible(true);
				easeInOut.setVisible(true);
			}
			else
			{
				toggleRandom.setIcon(random);
				randomized = true;
				rSlider.setEnabled(false);
				gSlider.setEnabled(false);
				bSlider.setEnabled(false);
				hideRandom.setVisible(true);

				circle.setVisible(false);
				triangle.setVisible(false);
				square.setVisible(false);
				colorPreview.repaint();
				
				easeIn.setVisible(false);
				easeOut.setVisible(false);
				easeInOut.setVisible(false);
			}
			
		}
		if(e.getSource() == circle)
		{
			shape = 0;
		}
		if(e.getSource() == square)
		{
			shape = 1;
		}
//		if(e.getSource() == square)
//		{
//			shape = 2;
//		}
		if(e.getSource() == easeIn)
		{
			easing = 0;
		}
		if(e.getSource() == easeOut)
		{
			easing = 1;
		}
		if(e.getSource() == easeInOut)
		{
			easing = 2;
		}
		
	}
	
	public void reset()
	{
		colors = new int[3];
		for(int i = 0; i < colors.length; i++)
		{
			colors[i] = 255;
		}
		
		rSlider.setValue(255);
		gSlider.setValue(255);
		bSlider.setValue(255);
		
		shape = 0;
		easing = 0;
		
		if(shapeList != null)
		{
			for(int i = 0; i < shapeList.size(); i++)
			{
				canvas.remove(shapeList.get(i));
				canvas.repaint();
			}
		}
		shapeList.clear();
	}
	
	public void adding()
	{
		startPage.add(settings); //settings is on top
		startPage.add(instruc);
		startPage.add(greyOverlay);
		startPage.add(title);
		startPage.add(start);
		startPage.add(openSettings);
		startPage.add(openInstructions);
		startPage.add(exit);
		startPage.add(startBackg);
		
		settings.add(soundLabel);
		settings.add(displayLabel);
		settings.add(modeLabel);
		settings.add(toggleDisplay);
		settings.add(toggleMode);
		settings.add(toggleSound);
		settings.add(hideSettings);
		
		instruc.add(hideInstruc);
		instruc.add(controls);
		instruc.add(freeFormInstruc);
		instruc.add(instrucLabel);
		
		canvas.add(menu);
		canvas.add(greyOverlayCanvas);
		canvas.add(openMenu);
		canvas.add(hideMenu);
		
		menu.add(hideRandom);
		menu.add(gSlider);
		menu.add(rSlider);
		menu.add(bSlider);
		menu.add(rValue);
		menu.add(gValue);
		menu.add(bValue);
		menu.add(colorPreview);
		menu.add(circle);
		menu.add(triangle);
		menu.add(square);
		menu.add(shapeLabel);
		menu.add(easingLabel);
		menu.add(colorLabel);
		menu.add(escapeCanvas);
		menu.add(resetCanvas);
		menu.add(toggleRandom);
		menu.add(easeIn);
		menu.add(easeOut);
		menu.add(easeInOut);
//		menu.add(testing);
		
		frame.add(startPage);
		frame.add(canvas);
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args) 
	{
		new Canvas();
	}
}
