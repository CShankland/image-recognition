package frontend;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextField;
import java.net.URL;

import javax.imageio.ImageIO;

import algorithm.DecompositionResult;
import algorithm.Preprocessing;

public class Driver {
	private SketchPanel panel;

	public Driver() {
		Frame f2 = new Frame();
		panel = new SketchPanel(f2);

		MenuBar menubar = new MenuBar();
		Menu calc = new Menu("Calculate");
		MenuItem decompose = new MenuItem("Decompose");
		decompose.setActionCommand("decompose");
		decompose.addActionListener(panel);
		calc.add(decompose);
		menubar.add(calc);

		Frame f = new Frame();
		f.setSize(800, 800);
		f.setLayout(new BorderLayout());
		f.add(panel, BorderLayout.CENTER);
		TextField red = new TextField("0", 3);
		TextField green = new TextField("0", 3);
		TextField blue = new TextField("255", 3);
		TextField radius = new TextField("20", 3);

		red.setName("red");
		green.setName("green");
		blue.setName("blue");
		radius.setName("radius");

		red.addTextListener(panel);
		green.addTextListener(panel);
		blue.addTextListener(panel);
		radius.addTextListener(panel);

		f2.setLayout(new FlowLayout());
		f2.add(new Label("Red: "));
		f2.add(red);
		f2.add(new Label("Green: "));
		f2.add(green);
		f2.add(new Label("Blue: "));
		f2.add(blue);
		f2.add(new Label("Radius: "));
		f2.add(radius);
		f2.setVisible(true);

		f.setMenuBar(menubar);
		panel.setSize(800, 800);
		f.setVisible(true);
	}

	public static void main(String[] args) {
		String puppy = "http://oh2btigger.files.wordpress.com/2008/06/black_labrador_puppy_b2.jpg";
		DecompositionResult dr = null;
		try {
			Image i = ImageIO.read(new URL(puppy));
			dr = Preprocessing.decomposeImage(i);
		} catch (Exception e) {

		}

		if (dr != null) {
			System.out.println(dr.toString());
		}
		Driver d = new Driver();
	}
}
