import javax.swing.JFrame;

//testing in a smaller frame for the customization of shape
//completely disregard, not important for game
public class moreTesting {
	public static void main(String[] args)  {
		int[] colores = {220, 100, 7};
		int shape = 0; //sometimes has things disappear
		int easing = 1;
		
		Shape blah = new Shape(colores, shape, easing);
		blah.startTimer();
		JFrame f = new JFrame();
		f.add(blah);
		f.setVisible(true);
		f.setSize(800,700);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("shmovin");
		
		//add to panel
	}

}
