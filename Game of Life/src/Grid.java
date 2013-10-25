public class Grid {

	public int spots;
	public boolean[][] isAlive;
	private boolean addingBlocks = false;

	public Grid(int spots) {
		this.spots = spots;
		isAlive = new boolean[spots][spots];
	}

	public void gameStep() {

		boolean[][] newAlive = new boolean[spots][spots];

		for (int x = 0; x < spots; ++x) {
			for (int y = 0; y < spots; ++y) {
				if ((numNeighbours(x, y) == 2 || numNeighbours(x, y) == 3)
						&& isAlive[x][y])
					newAlive[x][y] = true; // the spot stays alive with
													// 2 or 3 neighbours
				else if (numNeighbours(x, y) == 3 && !isAlive[x][y])
					newAlive[x][y] = true; // bring a dead spot to life
													// if it has 3 neighbours
				// for all other cases the spot is dead, the default state
			}
		}

		isAlive = newAlive;
	}

	private int numNeighbours(int x, int y) {
		int num = 0;

		if (x - 1 >= 0 && y - 1 >= 0 && isAlive[x - 1][y - 1])
			num++;
		if (x - 1 >= 0 && isAlive[x - 1][y])
			num++;
		if (x - 1 >= 0 && y + 1 < spots && isAlive[x - 1][y + 1])
			num++;
		if (y - 1 >= 0 && isAlive[x][y - 1])
			num++;
		if (y + 1 < spots && isAlive[x][y + 1])
			num++;
		if (x+1<spots && y-1>=0 && isAlive[x + 1][y - 1])
			num++;
		if (x+1<spots && isAlive[x + 1][y])
			num++;
		if (x+1<spots && y+1<spots && isAlive[x + 1][y + 1])
			num++;

		return num;
	}

	public void updateMouse(int gridX, int gridY) {
		if(gridX<0 || gridX>=spots || gridY<0 || gridY>=spots)
			addingBlocks = false;
		
		if(addingBlocks)
			isAlive[gridX][gridY] = true;
	}
	
	public void addBlocks(boolean val){
		addingBlocks = val;
	}
	
	public Grid changeSize(int newSpots){
		Grid out = new Grid(newSpots);
		
		for(int x=0;x<Math.min(this.spots, newSpots);++x)
			for(int y=0;y<Math.min(this.spots, newSpots);++y)
					out.isAlive[x][y]=this.isAlive[x][y];
		
		return out;
	}
	
}
