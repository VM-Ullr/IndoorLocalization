package pir;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Canvas extends JPanel {
	
	List<Point> pointList;
	
	public Canvas()
    {
        pointList = new ArrayList<Point>();
        setBackground(Color.white);
    }
	
	public void addPoint(double x, double y)
    {	
        Ellipse2D e = new Ellipse2D.Double(x - 3, y - 3, 6, 6);
        Color color = Color.decode("#1A9988");
        pointList.add(new Point(color,e));
        repaint();
    }
	
	public void addPoint(double x, double y, Color color)
    {	
        Ellipse2D e = new Ellipse2D.Double(x - 3, y - 3, 6, 6);
        pointList.add(new Point(color,e));
        repaint();
    }
	
	protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        Ellipse2D e;
        
        for(int j = 0; j < pointList.size(); j++)
        {
            e = pointList.get(j).getEllipse();
            g2.setPaint(pointList.get(j).getColor());
            g2.fill(e);
        }
    }
}
