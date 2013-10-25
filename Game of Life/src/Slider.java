import java.awt.geom.Rectangle2D;

public class Slider {

	private int min, max;
	private int currentVal;
	private boolean isDragged = false;
	private Rectangle2D.Double boundingBox;
	private double percent = 0;

	public Slider(int min, int max, Rectangle2D.Double boundingBox) {
		this.min = min;
		this.max = max;
		this.boundingBox = boundingBox;
		currentVal = (min + max) / 2;
		percent = 0.5;
	}

	public void drag() {
		isDragged = true;
	}

	public void dragOff() {
		isDragged = false;
	}

	public Rectangle2D.Double getBounds() {
		return boundingBox;
	}

	public void setVal(int val) {
		currentVal = val;
	}

	public int getVal() {
		return currentVal;
	}

	public void updateMouse(int mouseX, int mouseY) {
		if (!isDragged)
			return;

		if (mouseX < boundingBox.x)
			percent = 0.00001;
		else if (mouseX > boundingBox.x + boundingBox.width)
			percent = 1;
		else {
			percent = (mouseX - boundingBox.x) / (boundingBox.width);
		}

		currentVal = (int) Math.ceil((max - min) * percent);
	}
	

	public double getPercent() {
		return percent;
	}
	
	public boolean containsClick(int x, int y){
		return x>=boundingBox.x-15 && x<=boundingBox.x+boundingBox.width+15
				&& y>=boundingBox.y-5 && y<=boundingBox.y+boundingBox.height+5;
	}
}
