package Connect4;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import Connect4.Board.gridColor;
import tau.smlab.syntech.controller.executor.ControllerExecutor;
import tau.smlab.syntech.controller.jit.BasicJitController;

public class Connect4 extends JComponent{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	ControllerExecutor ctrlExec;
	final static int frameHeight = 500; //1000
	final static int frameWidth = 800; //1600
	static Board gameBoard;
	static final int gameCols = 3;
	static final int gameRows = 3;
	static final String envColor = "RED";
	static final String sysColor = "YELLOW";

	static boolean systemPlays;
	Player sysPlayer;
	Player envPlayer;
	
	static int envPlayerRow;
	static int envPlayerCol;
	static int sysPlayerRow;
	static int sysPlayerCol;
	final int dim = 100;
	boolean run = true;
	boolean finished = false;
	static int rows = 3;
	static int cols = 3;
	
	public Connect4() {
		Thread animationThread = new Thread(new Runnable() {
			public void run() {
				try {
					ctrlExec = new ControllerExecutor(new BasicJitController(), "out");
					ctrlExec.initState(new HashMap<String,String>());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Map<String, String> envValues;
				Map<String, String> sysValues;
				
				gameBoard = new Board(rows,cols);//TODO take dimensions from spectra
				systemPlays = false;
				
				sysPlayer = new Player(sysColor);
				envPlayer = new Player(envColor);
				repaint();
				
				
				while (run) {

					try {
						ctrlExec.updateState(new HashMap<String,String>());
					} catch (Exception ce) {
						System.err.println(ce.getMessage());
					}

					envValues = ctrlExec.getCurrInputs();
					sysValues = ctrlExec.getCurrOutputs();

					// Update board according to state
					for (int i=0;i<rows;i++)
						for (int j=0;j<cols;j++) {
							String key = "stateOfGrid"+"["+i+"]"+"["+j+"]";
							String value = envValues.get(key);
							gameBoard.insert(Utilities.getRowIndexFromSpec(key),Utilities.getColIndexFromSpec(key),Utilities.getValueAsColor(value));
						}
					//read rest of variables
					envPlayerRow = Integer.parseInt(envValues.get("envRow"));
					envPlayerCol = Integer.parseInt(envValues.get("envCol"));
					sysPlayerRow = Integer.parseInt(sysValues.get("sysRow"));
					sysPlayerCol = Integer.parseInt(sysValues.get("sysCol"));
					systemPlays = Boolean.parseBoolean(envValues.get("sysTurn"));
					repaint();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					
				}
				finished = true;//TODO if someone wins end game, need to take from spectra
			}});		
		
			animationThread.start();
			
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				
				@Override
				public void run() {
					run = false;
					while (!finished) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					ctrlExec.free();
					System.out.println("Controller is freed");
				}
			}));
		}
		
	
	@Override
    public void paintComponent(Graphics g) {
		int row;
		int col;
		gridColor[][] grid = gameBoard.getGrid();
		for (row = 0; row < gameRows; row++) {
			for (col = 0; col < gameCols; col++) {
				//if ((row % 2) == (col % 2))
					//g.setColor(Color.WHITE);
				//else
					//g.setColor(Color.LIGHT_GRAY);
				if( grid[row][col] == Board.gridColor.YELLOW)
					g.setColor(Color.YELLOW);
				else if (grid[row][col] == Board.gridColor.RED)
					g.setColor(Color.RED);
				else 
					g.setColor(Color.WHITE);
				
				g.fillRect(col * dim, row * dim, dim, dim);
			}
		}
	/**	
		if(systemPlays)	
		{
			g.setColor(Color.YELLOW); //TODO add sysPlayer.getColor()
			g.fillRect(sysPlayerRow * dim,sysPlayerCol * dim, dim, dim);
		}
		else {
			g.setColor(Color.RED); //TODO add sysPlayer.getColor()
			g.fillRect(envPlayerRow * dim,envPlayerCol * dim, dim, dim);
		}
 */
   
    }

	public static void main(String args[]) throws Exception {
		JFrame f = new JFrame("Spectra Simulation: Connect4");
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setSize(frameWidth, frameHeight + 39 + 50);
		Connect4 gtoh = new Connect4();
		f.setContentPane(gtoh);
		f.setVisible(true);
	}
}



/*
 * package Connect4;
 * 
 * import java.awt.Color; import java.awt.Graphics; import java.io.IOException;
 * import java.util.HashMap; import java.util.Map;
 * 
 * import javax.swing.JComponent; import javax.swing.JFrame; import
 * javax.swing.WindowConstants;
 * 
 * import tau.smlab.syntech.controller.executor.ControllerExecutor; import
 * tau.smlab.syntech.controller.jit.BasicJitController; import
 * towersOfHanoi.Disk;
 * 
 * public class Connect4 extends JComponent { //board //cell //
 * 
 //*
	* 
	*/
/*
 * private static final long serialVersionUID = 1L; ControllerExecutor ctrlExec;
 * final static int frameHeight = 500; // 1000 final static int frameWidth =
 * 800; // 1600 static Board gameBoard; static final int gameCols; static final
 * int gameRows; static final String envColor; static final String sysColor;
 * //static int current
 * 
 * static boolean systemPlays; Player sysPlayer; Player envPlayer;
 * 
 * int envPlayerRow; int envPlayerCol; int sysPlayerRow; int sysPlayerCol;
 * 
 * 
 * static int[] diskPosition; static Disk[] disksArray; static final String
 * moveDiskToTowerString = "moveDiskToTower"; static final String
 * moveDiskNumberString = "moveDiskNumber"; static int moveDiskNumber; static
 * int moveDiskToTower; static final int towerHeight = frameHeight / 2; static
 * final int towerWidth = 50; static int[] numOfDisksOnTowerArray; static final
 * int towerPosition[] = new int[] { frameWidth / 5, frameWidth / 2, frameWidth
 * * 4 / 5 }; Thread thread; static final Color colorArray[] = new Color[] {
 * Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE,
 * Color.MAGENTA, Color.PINK, Color.GRAY, Color.WHITE };
 * 
 * boolean run = true; boolean finished = false;
 * 
 * public Connect4(String envColor, String sysColor, int cols, int rows) {
 * Thread animationThread = new Thread(new Runnable() { public void run() { try
 * { ctrlExec = new ControllerExecutor(new BasicJitController(), "out");
 * ctrlExec.initState(new HashMap<String, String>()); } catch (IOException e1) {
 * e1.printStackTrace(); } Map<String, String> envValues; Map<String, String>
 * sysValues;
 * 
 * gameBoard = new Board(rows, cols);// TODO take dimensions from spectra
 * systemPlays = false;
 * 
 * sysPlayer = new Player(sysColor); envPlayer = new Player(envColor);
 * 
 //*
	 * Update the board locations with the proper images after a new disc is added
	 * to the board.
	 //
		 * 
		 * //repaint();
		 * 
		 * while (run) {
		 * 
		 * try { ctrlExec.updateState(new HashMap<String, String>()); } catch (Exception
		 * ce) { System.err.println(ce.getMessage()); }
		 * 
		 * envValues = ctrlExec.getCurrInputs(); sysValues = ctrlExec.getCurrOutputs();
		 * 
		 * // Update board according to state for (int i = 0; i < rows; i++) for (int j
		 * = 0; j < cols; j++) { String key = "stateOfGrid" + "[" + i + "]" + "[" + j +
		 * "]"; String value = envValues.get(key);
		 * gameBoard.insert(Utilities.getRowIndexFromSpec(key),
		 * Utilities.getColIndexFromSpec(key), Utilities.getValueAsColor(value)); }
		 * repaint(); try { Thread.sleep(10); } catch (InterruptedException e) {
		 * e.printStackTrace(); } finished = true;// TODO if someone wins end game, need
		 * to take from spectra } } });
		 * 
		 * animationThread.start();
		 * 
		 * Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		 * 
		 * @Override public void run() { run = false; while (!finished) { try {
		 * Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
		 * } ctrlExec.free(); System.out.println("Controller is freed"); } })); }
		 * 
		 * @Override public void paintComponent( Graphics g ) { int row; int col; for
		 * (row = 0; row < gameRows; row++) { for (col = 0; col < gameCols; col++) { if
		 * ((row % 2) == (col % 2)) g.setColor(Color.WHITE); else
		 * g.setColor(Color.LIGHT_GRAY);
		 * 
		 * g.fillRect(col * dim, row * dim, dim, dim); } }
		 * 
		 * if(systemPlays) { g.setColor(Color.Yellow); //TODO add sysPlayer.getColor()
		 * g.fillRect(sysPlayerRow * dim,sysPlayerCol * dim, dim, dim); } else {
		 * g.setColor(Color.RED); //TODO add sysPlayer.getColor()
		 * g.fillRect(envPlayerRow * dim,envPlayerCol * dim, dim, dim); }
		 * 
		 * 
		 * }
		 * 
		 * 
		 * public static void main(String args[]) throws Exception { JFrame f = new
		 * JFrame("Spectra Simulation: Towers Of Hanoi");
		 * f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		 * f.setSize(frameWidth, frameHeight + 39 + 50); Connect4 gtoh = new
		 * Connect4(args[0], args[1], Integer.parseInt(args[2]),
		 * Integer.parseInt(args[3])); f.setContentPane(gtoh); f.setVisible(true); } }
		 */