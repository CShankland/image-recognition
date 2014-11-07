package frontend;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import algorithm.DecompositionResult;
import algorithm.Preprocessing;

public class SketchPanel extends Component implements MouseListener,
											MouseMotionListener,
											ActionListener, TextListener {

	private Color paintColor;
	private BufferedImage img;
	private Frame colorFrame;
	private int radius = 20;

	public SketchPanel(Frame colorFrame) {
		super();
		paintColor = Color.blue;
		this.colorFrame = colorFrame;
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void setSize(int width, int height) {
		BufferedImage tmpImg = new BufferedImage(width, height,
													BufferedImage.TYPE_INT_RGB);
		tmpImg.getGraphics().setColor(Color.WHITE);
		tmpImg.getGraphics().fillRect(0, 0, width, height);
		if (img != null) {
			Raster r = tmpImg.getRaster();
			tmpImg.setData(r);
		}
		img = tmpImg;
		super.setSize(width, height);
	}

	public void setSize(Dimension d) {
		setSize(d.width, d.height);
	}

	public void paint(Graphics g) {
		if (img != null) {
			g.drawImage(img, 0, 0, null);
		}
	}

	public void setColor(Color c) {
		paintColor = c;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();

		if (mouseX < 0 || mouseX >= getWidth()) {
			return;
		}

		if (mouseY < 0 || mouseY >= getHeight()) {
			return;
		}

		for (int i = radius; i >= -radius; i--) {
			int y = mouseY + i;
			if (y < 0 || y >= getHeight()) {
				continue;
			}
			double theta = Math.acos(i / (double) radius);
			int width = (int) (radius * Math.abs(Math.sin(theta)));
			for (int j = -width; j <= width; j++) {
				int x = mouseX + j;
				if (x < 0 || x >= getWidth()) {
					continue;
				}
				img.setRGB(x, y, paintColor.getRGB());
			}
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	// Ignored
	}

	@Override
	public void mouseExited(MouseEvent e) {
	// Ignored
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();

		if (mouseX < 0 || mouseX >= getWidth()) {
			return;
		}

		if (mouseY < 0 || mouseY >= getHeight()) {
			return;
		}

		for (int i = radius; i >= -radius; i--) {
			int y = mouseY + i;
			if (y < 0 || y >= getHeight()) {
				continue;
			}
			double theta = Math.acos(i / (double) radius);
			int width = (int) (radius * Math.abs(Math.sin(theta)));
			for (int j = -width; j <= width; j++) {
				int x = mouseX + j;
				if (x < 0 || x >= getWidth()) {
					continue;
				}
				img.setRGB(x, y, paintColor.getRGB());
			}
		}
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("decompose")) {
			new Thread(new Runnable() {
				public void run() {
					DecompositionResult dr = Preprocessing.decomposeImage(img);
					System.out.println(dr.toString());
				}
			}).start();
		}
	}

	@Override
	public void textValueChanged(TextEvent e) {
		TextField c = (TextField) e.getSource();
		String name = c.getName();
		try {
			int value = Integer.parseInt(c.getText());
			if (name.equals("red")) {
				paintColor = new Color(value, paintColor.getGreen(),
										paintColor.getBlue());
			} else if (name.equals("green")) {
				paintColor = new Color(paintColor.getRed(), value,
										paintColor.getBlue());
			} else if (name.equals("blue")) {
				paintColor = new Color(paintColor.getRed(),
										paintColor.getGreen(), value);
			} else if (name.equals("radius")) {
				radius = value;
			}

			colorFrame.setBackground(paintColor);
		} catch (Exception ex) {}
	}

	public void setRadius(int r) {
		radius = r;
	}
}
