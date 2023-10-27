import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Arc
{
	private Point start;
	private Point end;
	public Arc(Point start, Point end)
	{
		this.start = start;
		this.end = end;
	}

	public Point getStartPoint()
	{
		return start;
	}

	public Point getEndPoint()
	{
		return end;
	}

	public void drawArc(Graphics g)
	{
		if (start != null)
		{
            g.setColor(Color.RED);
            g.drawLine(start.x, start.y, end.x, end.y);
        }
	}

	public void drawArrow(Graphics g, int arrowSize , int node_Diam) {
		if (start != null && end != null) {
			g.setColor(Color.BLACK);
			g.drawLine((int)start.x, (int)start.y, (int)end.x, (int)end.y);
			
			// Calculăm unghiul dintre linia start-end și axa x
			double angle = Math.atan2(end.y - start.y, end.x - start.x);
	
			// Desenăm vârful săgeții
			int arrowX = (int) (end.x  - arrowSize * Math.cos(angle - Math.PI / 6));
			int arrowY = (int) (end.y - arrowSize * Math.sin(angle - Math.PI / 6));
			g.drawLine(end.x, end.y, arrowX , arrowY );
	
			// Desenăm celălalt vârf al săgeții
			arrowX = (int) (end.x - arrowSize * Math.cos(angle + Math.PI / 6));
			arrowY = (int) (end.y  - arrowSize * Math.sin(angle + Math.PI / 6));
			g.drawLine(end.x , end.y , arrowX , arrowY );
		}
	}
}

