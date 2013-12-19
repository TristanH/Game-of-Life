import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardDownRightHandler;

//consider representing the grid as an array of longs
class Game extends JPanel implements MouseListener, KeyListener, ActionListener, MouseMotionListener {
	int mouseX, mouseY;
	Grid grid = new Grid(60);
	int pixPerBox = 600 / grid.spots;
	int steps = 0;
	boolean paused = true;
	Slider timeSlider;
	long lastGridUpdate = 0;
	JButton play = new JButton("Start Simulation");
	
	public Game() {
		addMouseListener(this);
		addKeyListener(this);
		addMouseMotionListener(this);
		this.setFocusable(true);
		this.setLayout(null);
		
		play.setBounds(650,160, 160, 40);
		play.addActionListener(this);
		this.add(play);

		timeSlider = new Slider(1, 1000, new Rectangle2D.Double(625, 70, 200, 25));

		Thread t = new Thread(new Runnable() {
			public void run() {
				gameLoop();
			}
		});
		t.start();

	}

	private void gameLoop() {
		while (true) {
			updateInput();
			if (!paused && isNextUpdate())
				updateGrid();
			try {
				Thread.sleep(Math.min(timeSlider.getVal(),10));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			repaint();
		}
	}

	private void updateInput() {
		Point mousePos = MouseInfo.getPointerInfo().getLocation();
		mousePos.translate(-this.getLocationOnScreen().x,
				-this.getLocationOnScreen().y);
		timeSlider.updateMouse(mousePos.x, mousePos.y);
		grid.updateMouse(mousePos.x/pixPerBox,mousePos.y/pixPerBox);
	}

	private boolean isNextUpdate() {
		return System.nanoTime() - timeSlider.getVal() * 1000000 >= lastGridUpdate;
	}

	private void updateGrid() {
		grid.gameStep();
		steps++;
		lastGridUpdate = System.nanoTime();
	}

	private void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.blue);
		for (int i = 0; i <= grid.spots; ++i) {
			g2.drawLine(0, i * pixPerBox, 600, i * pixPerBox);
			g2.drawLine(i * pixPerBox, 0, i * pixPerBox, 600);
		}
		g2.setColor(Color.green);
		for (int x = 0; x < grid.spots; ++x) {
			for (int y = 0; y < grid.spots; ++y)
				if (grid.isAlive[x][y])
					g2.fillRect(x * pixPerBox + 1, y * pixPerBox + 1,
							pixPerBox - 1, pixPerBox - 1);
		}

		g2.setColor(Color.black);
		Font stepsFont = new Font("Arial", Font.PLAIN, 20);
		g2.setFont(stepsFont);
		g2.drawString("Total Steps: " + steps, 650, 35);
		g2.drawString("Time Step: "+timeSlider.getVal()+"ms", 650, 120);
		g2.fillRect(625, 80, 200, 5);
		g2.drawRect(625 + (int) (200 * timeSlider.getPercent()) - 3, 70, 6, 25);
		
		g2.drawString("Instructions:", 620,240);
		g2.setFont(new Font("Arial",Font.BOLD+Font.ITALIC,16));
		g2.drawString("-Press or drag the mouse to ", 620,265);
		g2.drawString("fill in the grid", 620,285);
		g2.drawString("-Use the above button to start ", 620,315);
		g2.drawString("or stop the game of life ", 620,335);
		g2.drawString("-Slide the slider to", 620,365);
		g2.drawString("change how fast time moves ", 620,385);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public boolean isInGrid(int x, int y) {
		return x >= 0 && x <= 602 && y >= 0 && y <= 600;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
			paused = !paused;

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if (isInGrid(e.getX(), e.getY())) 
			grid.addBlocks(true);
	    else if (timeSlider.containsClick(e.getX(),e.getY()))
			timeSlider.drag();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

		timeSlider.dragOff();
		grid.addBlocks(false);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==play){
			if (paused){
				paused = false;
				play.setText("Stop Simulation");
			}
			else{
				paused = true;
				play.setText("Start Simulation");
			}
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX=e.getX();
		mouseY=e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX=e.getX();
		mouseY=e.getY();
		
	}
}
