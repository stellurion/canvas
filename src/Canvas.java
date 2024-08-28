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
	private JFrame frame; //make this all in seperate file
	
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
	
	public Canvas() {
		frame = new JFrame();
		frame.setSize(800, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		shapeList = new ArrayList<Shape>();
		
		playInstance();
	}
	
	public void playInstance() {
		randomized = true;
		
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
	}

	public JLabel createImageLabel(String file, int[] bounds, Boolean visible) {
		JLabel label = new JLabel(new ImageIcon(file));
		label.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
		label.setVisible(visible);
		return label;
	}
	public JLabel createTextLabel(String name, int[] bounds, String font, int fontsize) {
		JLabel label = new JLabel(name);
		label.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
		label.setFont(new Font(name, Font.PLAIN, fontsize));
		return label;
	}
	public JPanel createJPanel(int[] bounds, Boolean visible, Color color, Boolean layout) {
		JPanel panel = new JPanel();
		panel.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
		panel.setVisible(visible);
		panel.setBackground(color);
		
		if(!layout)
			panel.setLayout(null);

		return panel;
	}
	
	public void instructions() {
		instrucLabel = createTextLabel("Welcome to Canvas!", new int[]{30, 40, 400, 30}, "Garamond", 30);
		freeFormInstruc = createTextLabel("<html>"
		+ "When clicking on the screen, different shapes will appear, simulating natural motion in their appearance (through easing)."
		+ " By default, the color, shape, and easing type are randomly generated.<br/>"
		+ "However, there is a menu to customize these attributes, allowing for complete user control.<br/><br/><html>", new int[]{30, 20, 400, 400}, "Garamond", 20);
		controls = createTextLabel("<html>Press the 3 lines to access the menu that adjusts shape generation.<html>", new int[]{30, 160, 400, 400}, "Garamond", 20);
	}

	public void labels() {
		title = createImageLabel("assets/canvastext.png", new int[]{150, 130, 500, 160}, true);
		startBackg = createImageLabel("assets/watercolorsplash.png", new int[]{0, -10, 800, 700}, true);
		greyOverlay = createImageLabel("assets/overlay.png", new int[]{0, 0, 800, 700}, false);
		greyOverlayCanvas = createImageLabel("assets/overlay.png", new int[]{0, 0, 800, 700}, false);
		soundLabel = createTextLabel("Sound", new int[]{123, 130, 100, 20,}, "Garamond", 30); 
		displayLabel = createTextLabel("Display", new int[]{123, 215, 100, 30}, "Garamond", 30);
		modeLabel = createTextLabel("Mode", new int[]{123, 310, 100, 20}, "Garamond", 30);
		rValue = createTextLabel("R", new int[]{23, 340, 24, 30}, "Arial", 24);
		gValue = createTextLabel("G", new int[]{23, 377, 24, 30}, "Arial", 24);
		bValue = createTextLabel("B", new int[]{23, 413, 24, 30}, "Arial", 24);
		shapeLabel = createTextLabel("Shapes", new int[]{23, 20, 100, 30}, "Garamond", 27);
		easingLabel = createTextLabel("Easings", new int[]{23, 150, 100, 30}, "Garamond", 27);
		colorLabel = createTextLabel("Color", new int[]{23, 300, 100, 30}, "Garamond", 27);
	}
	
	public void panels() {
		startPage = createJPanel(new int[]{0, 0, 800, 700}, true, new Color(255, 255, 255), false);
		settings = createJPanel(new int[]{133, 110, 508, 450}, false, new Color(255, 255, 255), false);
		instruc = createJPanel(new int[]{133, 110, 508, 450}, false, new Color(255, 255, 255), false);
		
		canvas = new JLayeredPane();
		canvas.setBounds(0,0,800,700);
		canvas.setVisible(false);
		canvas.setBackground(new Color(255, 255, 255));
		canvas.setLayout(null);
		canvas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) { if(!menu.isVisible()) {generateShape(e.getX()-230, e.getY()-120);}}
		}); //turn off enable when in menu
		canvas.setOpaque(true);
		
		menu = createJPanel(new int[]{0, 0, 275, 700}, false, new Color(207, 236, 255), false);
		colorPreview = createJPanel(new int[]{38, 472, 195, 80}, true, new Color(colors[0], colors[1], colors[2]), false);
		testing = createJPanel(new int[]{200, 200, 45, 45}, true, new Color(255, 255, 255), false);
		hideRandom = createJPanel(new int[]{0, 0, 275, 560}, true, new Color(122, 203, 255), false);
	}

	public JButton createJButton(int[] bounds, String text, String font, int fontsize, Boolean opaque) {
		JButton button = new JButton();
		button.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
		button.setText(text);		
		button.setFont(new Font(font, Font.PLAIN, fontsize));
		if(opaque) {button.setOpaque(opaque);}
		button.addActionListener(this);
		return button;
	}
	public JButton createJButton(String file, int[] bounds) {
		JButton button = new JButton(new ImageIcon(file));
		button.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.addActionListener(this);
		return button;
	}
	public JSlider createJSlider(int y, int color) {
		JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);
		slider.setOpaque(false);
		slider.setBounds(45, 335, 200, 50);

		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				colors[0] = slider.getValue();
				colorPreview.setBackground(new Color(colors[0], colors[1], colors[2]));
				colorPreview.repaint();
			}
		});
		return slider;
	}

	public void buttons() {
		start = createJButton(new int[]{310, 380, 170, 58}, "Start", "Garamond", 30, true);
		start.setBackground(new Color(255, 255, 255));
		openInstructions = createJButton(new int[]{310, 480, 170, 60}, "Instructions", "Garamond", 27, true);
		openInstructions.setBackground(new Color(255, 255, 255));
		openSettings = createJButton("assets/buttons/settings.png", new int[]{680,570,80,65});
		exit = createJButton("assets/buttons/exit.png", new int[]{0,570,80,65});

		//settings buttons
		hideSettings = createJButton("assets/buttons/ximage.png", new int[]{450,20,40,40});
		toggleSound = createJButton(new int[]{260,110,120,60}, "On", "Garamond", 20, false);
		toggleSound.setContentAreaFilled(false);
		toggleDisplay = createJButton(new int[]{260,200,120,60}, "Light", "Garamond", 20, false);
		toggleDisplay.setContentAreaFilled(false);
		toggleMode = createJButton(new int[]{260, 290, 120, 60}, "Freeform", "Garamond", 20, false);
		toggleMode.setContentAreaFilled(false);
		
		hideInstruc = createJButton("assets/buttons/ximage.png", new int[]{450,20,40,40});
		openMenu = createJButton("assets/buttons/menubutton.png", new int[]{20,20,40,40});
		
		//menu buttons
		hideMenu = createJButton("assets/buttons/hideMenu.png", new int[]{275,330,40,40});
		hideMenu.setVisible(false);
		escapeCanvas = createJButton("assets/buttons/exit.png", new int[]{20,572,80,65});
		resetCanvas = createJButton("assets/buttons/refresh.png", new int[]{180,575,50,50});
		random = new ImageIcon("assets/buttons/random.png");
		custom = new ImageIcon("assets/buttons/custom.png");
		toggleRandom = createJButton("assets/buttons/random.png", new int[]{110,575,50,50});
		
		//game menu buttons
		circle = createJButton("assets/buttons/circleIcon.png", new int[]{25,75,45,45});
		circle.setVisible(false);
		triangle = createJButton("assets/buttons/triangleIcon.png", new int[]{109,70,45,45});
		triangle.setVisible(false);
		square = createJButton("assets/buttons/squareIcon.png", new int[]{190,70,45,45});
		square.setVisible(false);
		easeIn = createJButton("assets/buttons/easeIn.png", new int[]{25,200,45,45});
		easeIn.setVisible(false);
		easeOut = createJButton("assets/buttons/easeOut.png", new int[]{109,200,45,45});
		easeOut.setVisible(false);
		easeInOut = createJButton("assets/buttons/easeInOut.png", new int[]{190,200,45,45});
		easeInOut.setVisible(false);
	}
	
	public void sliders() {
		rSlider = createJSlider(335, 0);
		gSlider = createJSlider(370, 1);
		bSlider = createJSlider(405, 2);
	}
	
	public void generateShape(int x, int y)
	{
		int[] coloring = new int[3];
		int typeShape;
		int typeEasing;
		
		if(randomized) {
			Random generator = new Random();
			for(int i = 0; i < coloring.length; i++) {coloring[i] = generator.nextInt((250 - 0) + 1) + 0;}
			
			typeShape = generator.nextInt((1 - 0) + 1) + 0;
			typeEasing = generator.nextInt((2 - 0) + 1) + 0;
		}
		else {
			for(int i = 0; i < 3; i++) {coloring[i] = colors[i];}
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
		for(int i = shapeList.size()-1; i > 0 ; i--) {
			int blah = 1;
			canvas.setLayer(shapeList.get(i), blah);
			blah++;
		}
		
		theShape.startTimer();
	}
	
	public void actionPerformed (ActionEvent e) {
		//startpage buttons
		if(e.getSource() == start) {
			startPage.setVisible(false);
			canvas.setVisible(true);
		}
		if(e.getSource() == openInstructions) {
			start.setEnabled(false);
			openInstructions.setEnabled(false);
			openInstructions.setVisible(false);
			greyOverlay.setVisible(true);
			instruc.setVisible(true);
			//make x button on instruc so you can exit out from it
		}
		if(e.getSource() == openSettings) {
			start.setEnabled(false);
			openInstructions.setEnabled(false);
			greyOverlay.setVisible(true);
			settings.setVisible(true);
		}
		if(e.getSource() == exit) {System.exit(0);}
		
		
		//settings buttons
		if(e.getSource() == hideSettings) {
			settings.setVisible(false);
			greyOverlay.setVisible(false);
			start.setEnabled(true);
			openInstructions.setEnabled(true);
		}
		if(e.getSource() == toggleMode) {
			if(toggleMode.getText().equals("Freeform"))
				toggleMode.setText("MiniGame");
			else
				toggleMode.setText("Freeform");
		}
		if(e.getSource() == toggleDisplay) {
			if(toggleDisplay.getText().equals("Light"))
				toggleDisplay.setText("Dark");
			else
				toggleDisplay.setText("Light");
		}
		if(e.getSource() == toggleSound) {
			if(toggleSound.getText().equals("On"))
				toggleSound.setText("Off");
			else
				toggleSound.setText("On");
			//turn off sound effects
		}	
		
		//instruc button
		if(e.getSource() == hideInstruc) {
			instruc.setVisible(false);
			greyOverlay.setVisible(false);
			start.setEnabled(true);
			openInstructions.setEnabled(true);
			openInstructions.setVisible(true);
		}
		

		//canvas button
		if(e.getSource() == openMenu) { //make this toggle Menu
			menu.setVisible(true);
			hideMenu.setVisible(true);
			greyOverlayCanvas.setVisible(true);
			
			if(shapeList != null) {
				for(int i = 0; i < shapeList.size(); i++)
					shapeList.get(i).stopTimer();
			}
		}
		if(e.getSource() == hideMenu) {
			menu.setVisible(false);
			hideMenu.setVisible(false);
			greyOverlayCanvas.setVisible(false);
			//if timer isn't finished, continue them
			
			if(shapeList != null) {
				for(int i = 0; i < shapeList.size(); i++) {
					if(!shapeList.get(i).isFinished())
						shapeList.get(i).startTimer();
				}
			}
		}
		
		
		//menu buttons
		if(e.getSource() == resetCanvas) {reset();}
		if(e.getSource() == escapeCanvas) {
			reset();
			canvas.setVisible(false);
			startPage.setVisible(true);
			hideMenu.doClick();
			
			if(toggleRandom.getIcon() == custom) {toggleRandom.doClick();}
		}
		if(e.getSource() == toggleRandom) {
			//random should shut off top menu
			if(toggleRandom.getIcon() == random) {
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
			else {
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
		if(e.getSource() == circle) {shape = 0;}
		if(e.getSource() == square) {shape = 1;}
//		if(e.getSource() == square) {shape = 2;}
		if(e.getSource() == easeIn) {easing = 0;}
		if(e.getSource() == easeOut) {easing = 1;}
		if(e.getSource() == easeInOut) {easing = 2;}
	}
	
	public void reset() {
		colors = new int[3];
		for(int i = 0; i < colors.length; i++) {colors[i] = 255;}
		
		rSlider.setValue(255);
		gSlider.setValue(255);
		bSlider.setValue(255);
		
		shape = 0;
		easing = 0;
		
		if(shapeList != null) {
			for(int i = 0; i < shapeList.size(); i++) {
				canvas.remove(shapeList.get(i));
				canvas.repaint();
			}
		}
		shapeList.clear();
	}
	
	public void adding() {
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
	
	public static void main(String[] args) {
		new Canvas();
	}
}
