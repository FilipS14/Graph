import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Vector;
import javax.swing.BorderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MyPanel extends Graf
{
	File fisier = new File("matriceAdiacenta.txt");
	private int nodeNr = 1;
	public int node_diam = 40;
	private Vector<Node> listaNoduri;
	private Vector<Arc> listaArce;
	Point pointStart = null;
	Point pointEnd = null;
	boolean isDragging = false;
	private Node draggedNode = null;
	public MyPanel()
	{
		listaNoduri = new Vector<Node>();
		listaArce = new Vector<Arc>();
		// borderul panel-ului
		setBorder(BorderFactory.createLineBorder(Color.black));
		addMouseListener(new MouseAdapter() 
		{
			//evenimentul care se produce la apasarea mousse-ului
			public void mousePressed(MouseEvent e) {
				pointStart = e.getPoint();
				for (Node node : listaNoduri) 
				{
					if(node.containsPoint(pointStart.getX(), pointStart.getY(), node_diam))
					{
						draggedNode = node;
						break;
					}
				}
			}
			public void moveArc(Node node)
			{
				for (int i = 0; i < node.edge.size(); i++) {
					node.edge.elementAt(i).getEndPoint().setLocation(node.getCoordX() + (node_diam / 2), node.getCoordY() + (node_diam / 2));
				}
			}
			//evenimentul care se produce la eliberarea mousse-ului
			public void mouseReleased(MouseEvent e) 
			{
				if(e.getClickCount() == 2)
				{
					if(draggedNode != null)
					{
						boolean ok = true;
						for (Node node : listaNoduri) {
							if(!isNodeFarEnough(e.getX(),e.getY(),node.getCoordX(),node.getCoordY(), 50))
								ok = false;
						}
						if(ok == true)
						{
							draggedNode.setCoordX(e.getX());
							draggedNode.setCoordY(e.getY());
							moveArc(draggedNode);
						}
						repaint();
						saveMatrixToFile();
						draggedNode = null;
					}
				}
				if (!isDragging && draggedNode == null) {
					addNode(e.getX(), e.getY());
				}
				else 
				{
					Node startNode = null;
					Node endNode = null;
					boolean start = false;
					boolean end = false;
					int raza = node_diam / 2;

					if(pointStart == null || pointEnd == null)
						return ;
					if(pointStart == pointEnd)
						return;
					for(Node existingNode : listaNoduri)
					{
						int distance1 =(int)Math.sqrt(Math.pow(existingNode.getCoordX() - pointStart.getX() + raza, 2) +
						 Math.pow(existingNode.getCoordY() - pointStart.getY() + raza, 2));
						int distance2 =(int)Math.sqrt(Math.pow(existingNode.getCoordX() - pointEnd.getX() + raza, 2) +
						 Math.pow(existingNode.getCoordY() - pointEnd.getY() + raza, 2));
						 
						 if(distance1 < raza && start == false)
						 {
							pointStart.setLocation(existingNode.getCoordX() + raza, existingNode.getCoordY() + raza);
							start = true;
							startNode = existingNode;
						} 
						 if(distance2 < raza && end == false)
						{
							end = true;
							pointEnd.setLocation(existingNode.getCoordX() + raza, existingNode.getCoordY() + raza);
							endNode = existingNode;
						}
					}
					
					if(start == true && end == true && startNode != endNode){
						startNode.neighbors.add(endNode);
						if(isOrientedGraph() == false)
						{
							endNode.neighbors.add(startNode);
						}
						Arc arc = new Arc(pointStart, pointEnd);
						startNode.edge.add(new Arc(pointEnd, pointStart));
						endNode.edge.add(new Arc(pointStart, pointEnd));
						listaArce.add(arc);
					}
				}
				pointStart = null;
				isDragging = false;
				saveMatrixToFile();
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			//evenimentul care se produce la drag&drop pe mousse
			public void mouseDragged(MouseEvent e) {
				pointEnd = e.getPoint();
				isDragging = true;
				repaint();
			}
		});
	}

	private void saveMatrixToFile() {
		try {
			FileWriter writer = new FileWriter(fisier);
			writer.write(nodeNr - 1 + "\n");
			int[][] adjacencyMatrix = new int[listaNoduri.size()][listaNoduri.size()];
	
			
			for (int i = 0; i < listaNoduri.size(); i++) {
				for (int j = 0; j < listaNoduri.size(); j++) {
					adjacencyMatrix[i][j] = 0;
				}
			}
	
			for (int i = 0; i < listaNoduri.size(); i++) {
				Node node = listaNoduri.elementAt(i);
				for (Node neighbor : node.neighbors) {
					int j = listaNoduri.indexOf(neighbor);
					adjacencyMatrix[i][j] = 1;
					
				}
			}

			for (int i = 0; i < listaNoduri.size(); i++) {
				for (int j = 0; j < listaNoduri.size(); j++) {
					writer.write(adjacencyMatrix[i][j] + " ");
				}
				writer.write("\n");
			}
	
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isNodeFarEnough(int Xa ,int Ya, int Xb ,int Yb ,int minDistance)
	{
		int distance = (int) Math.sqrt(Math.pow(Xa - Xb, 2) + Math.pow(Ya - Yb, 2));
		if(distance < minDistance)
			return false;
		return true;
	}

	private boolean isNodeFarEnough(int x ,int y, int minDistance)
	{
		for(Node existingNode : listaNoduri)
		{
			int distance = (int) Math.sqrt(Math.pow(existingNode.getCoordX() - x, 2) + Math.pow(existingNode.getCoordY() - y, 2));
			if(distance < minDistance)
				return false; 
		}
		return true;
	}
	//metoda care se apeleaza la eliberarea mouse-ului
	private void addNode(int x, int y) {
		int minDistance = 50;
		if(isNodeFarEnough(x, y, minDistance)){
		Node node = new Node(x, y, nodeNr);
		listaNoduri.add(node);
		nodeNr++;
		repaint();
		}
	}
	
	//se executa atunci cand apelam repaint()
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);//apelez metoda paintComponent din clasa de baza
		g.drawString("This is my Graph!", 10, 20);
		//deseneaza lista de noduri
		for(int i=0; i<listaNoduri.size(); i++)
		{
			listaNoduri.elementAt(i).drawNode(g, node_diam);
		}
		/*for (Node nod : listaNoduri)
		{
			nod.drawNode(g, node_diam, node_Diam);
		}*/


		//deseneaza arcele existente in lista
		/*for(int i=0;i<listaArce.size();i++)
		{
			listaArce.elementAt(i).drawArc(g);
		}*/
		for (Arc a : listaArce) {
			if (isOrientedGraph()) {
				a.drawArrow(g, 25, 30);
			} 
			else {
				a.drawArc(g);
			}
			
		}
		//deseneaza arcul curent; cel care e in curs de desenare
		if (pointStart != null)
		{
			g.setColor(Color.BLACK);
			g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
		}

	}
}
