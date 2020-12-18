package Connect4;


public class Utilities {

	public static int getRowIndexFromSpec(String key) {
		return Character.getNumericValue(key.charAt(key.indexOf('[') + 1));
	}
	
	public static int getColIndexFromSpec(String key) {
		return Character.getNumericValue(key.charAt(key.lastIndexOf('[') + 1));
	}
	
	public static Board.gridColor getValueAsColor(String value) {
		switch (value) {
		case ("P1"):
			return Board.gridColor.RED;
		case ("P2"):
			return Board.gridColor.YELLOW;
		default:
			return Board.gridColor.EMPTY;
		}
	}
}
