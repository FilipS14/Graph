import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Graf extends JPanel implements ActionListener {
    private JButton neorientatButton;
    private JButton orientatButton;
    private JFrame firstPageFrame;

    private static boolean isOrientedGraph = false;

    public static boolean isOrientedGraph() 
    {
        return isOrientedGraph;
    }

    private static void firstPage() {
        JFrame f = new JFrame("Alege opțiunea");
        Graf graf = new Graf();

        // Creare butoane
        graf.neorientatButton = new JButton("neorientat");
        graf.orientatButton = new JButton("orientat");

        //pentru design
        Font font = new Font("Verdana", Font.BOLD, 12);
        graf.neorientatButton.setFont(font);
        graf.orientatButton.setFont(font);

        // Adaugare ascultător de acțiuni la butoane
        graf.neorientatButton.addActionListener(graf);
        graf.orientatButton.addActionListener(graf);

        graf.add(graf.neorientatButton);
        graf.add(graf.orientatButton);

        f.add(graf);
        f.setSize(400, 150);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        graf.firstPageFrame = f;
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent event)
    {
            if(event.getSource() == orientatButton)
            {
                isOrientedGraph = true;
                initUI("orientat");
            }
            if(event.getSource() == neorientatButton)
            {
                isOrientedGraph = false;
                initUI("neorientat");
            }
            if(firstPageFrame != null)
            {
                firstPageFrame.dispose();
            }
        
    }

	private static void initUI(String tipGraf) {
        JFrame f = new JFrame("Algoritmica Grafurilor - " + tipGraf);
        //sa se inchida aplicatia atunci cand inchid fereastra
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //imi creez ob MyPanel
        f.add(new MyPanel());
        //setez dimensiunea ferestrei
        f.setSize(500, 500);
        //fac fereastra vizibila
        f.setVisible(true);
    }
	
	public static void main(String[] args)
	{
		//pornesc firul de executie grafic
		//fie prin implementarea interfetei Runnable, fie printr-un ob al clasei Thread
		SwingUtilities.invokeLater(new Runnable() //new Thread()
		{
            public void run() 
            {
                firstPage();       
            }
        });
	}

}






