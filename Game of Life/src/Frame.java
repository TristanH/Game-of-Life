import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class Frame extends JFrame{
	
	public Frame() {
		setTitle("The Game of Life");
		setSize(607 + 250,636);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		this.setVisible(true);
		add(new Game());
	}
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){			
			public void run(){
				Frame window = new Frame();
				window.setVisible(true);
			}
		});
	}
}

