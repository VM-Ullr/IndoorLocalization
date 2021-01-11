package pir;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class Point {
	Color color;
	Ellipse2D ellipse;

	public Point(Color color, Ellipse2D ellipse) {
		super();
		this.color = color;
		this.ellipse = ellipse;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Ellipse2D getEllipse() {
		return ellipse;
	}

	public void setEllipse(Ellipse2D ellipse) {
		this.ellipse = ellipse;
	}
}
