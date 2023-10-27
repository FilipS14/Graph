import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Vector;

public class Node
{
	private int coordX;
	private int coordY;
	private int number;
	public Vector<Node>neighbors = new Vector<>();
	public Vector<Arc>edge = new Vector<>();

	public Node(int coordX, int coordY, int number)
	{
		this.coordX = coordX;
		this.coordY = coordY;
		this.number = number;
	}
	
	public int getCoordX() {
		return coordX;
	}
	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}
	public int getCoordY() {
		return coordY;
	}
	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	public boolean containsPoint(double coordX, double coordY , int node_diam)
	{
		int raza = node_diam / 2;
		double CentruX = this.coordX + raza;
		double CentruY = this.coordY + raza;
		double distance = Math.sqrt(Math.pow(coordX - CentruX, 2) + Math.pow(coordY - CentruY, 2));
		return distance <= raza;
	}

	public void drawNode(Graphics g, int node_diam)
	{
		g.setColor(Color.RED);
		g.setFont(new Font("TimesRoman", Font.BOLD, 15));
        g.fillOval(coordX, coordY, node_diam, node_diam);
        g.setColor(Color.WHITE);
        g.drawOval(coordX, coordY, node_diam, node_diam);
        if(number < 10)
        	g.drawString(((Integer)number).toString(), coordX+12, coordY+20);
        else
        	g.drawString(((Integer)number).toString(), coordX+8, coordY+20);	
	}

}
