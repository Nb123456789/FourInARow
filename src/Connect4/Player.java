package Connect4;

public class Player {
	static enum playerColor {
		  RED,
		  YELLOW
		  }
	private playerColor playerColor;
	
	public Player(String playerString) {//TODO fix
		this.playerColor = playerColor.valueOf(playerString);
	}
	public playerColor getPlayerColor() {
		return playerColor;
	}

}
