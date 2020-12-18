package Connect4;

public class Board {

	enum gridColor {//TODO fix enums
		  RED,
		  YELLOW,
		  EMPTY
		  }	
	private int rows;
	private int cols;
	private gridColor[][] grid;//TODO enum
	
	public Board(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.grid = new gridColor[rows][cols];
		this.initialize();
	}
	
	public void insert(int row,int col,gridColor color) {
		grid[row][col] = color;
	}
	
	public void initialize() {
		for (int i=0;i<rows;i++)
			for (int j=0;j<cols;j++)
				grid[i][j] = gridColor.EMPTY;
	}
	
	public gridColor[][] getGrid() {
		return grid;
	}
	
	

	private static final long serialVersionUID = 1L;
	private static int frameWidth = 10;
	private static int frameHeight = 10;

	int onTower;
	int diskSize;
	int diskXPosition;
	int diskYPosition;
	boolean isUp;
	boolean isMoved;
	static final int diskWidth = 30; //50
	
	
	
	
	
	
	
	
	
	
	
}
