import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private static String[] tileValues = {
        "Kia", "Kia", "Hyundai", "Hyundai",
        "Genesis", "Genesis", "Peugeot", "Peugeot",
        "Citroen", "Citroen", "Skoda", "Skoda",
        "Nissan", "Nissan", "Porsche", "Porsche",
        "Audi", "Audi", "BMW", "BMW",
        "Opel","Opel", "Renault", "Renault",
        "Volvo", "Volvo", "Honda", "Honda"
    };

    private Tile[][] gameboard = new Tile[4][7];

    /**  
     * Constructor for the game. Creates the 2D gameboard
     * by populating it with shuffled card values
     */
    public Board() {
        List<String> shuffledTiles = new ArrayList<>();
        Collections.addAll(shuffledTiles, tileValues);
        Collections.shuffle(shuffledTiles);

        int index = 0;
        for (int i = 0; i < gameboard.length; i++) {
            for (int j = 0; j < gameboard[i].length; j++) {
                gameboard[i][j] = new Tile(shuffledTiles.get(index));
                index++;
            }
        }
    }

    /**
     * Returns a string representation of the board, showing hidden or revealed tiles.
     */
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < gameboard.length; i++) {
            output.append("\n");
            for (int j = 0; j < gameboard[i].length; j++) {
                if (gameboard[i][j].matched()) {
                    output.append(" * \t");
                } else if (gameboard[i][j].isShowingValue()) {
                    output.append(gameboard[i][j].getValue()).append("\t");
                } else {
                    output.append("_____\t");
                }
            }
        }
        return output.toString();
    }

    /**
     * Determines if all tiles have been matched, meaning the game is over.
     */
    public boolean allTilesMatch() {
        for (Tile[] row : gameboard) {
            for (Tile tile : row) {
                if (!tile.matched()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Reveals the tile at the given position.
     */
    public void showValue(int row, int column) {
        gameboard[row][column].show();
    }

    /**
     * Checks if two tiles match and updates their states accordingly.
     */
    public String checkForMatch(int row1, int col1, int row2, int col2) {
        Tile tile1 = gameboard[row1][col1];
        Tile tile2 = gameboard[row2][col2];

        if (tile1.equals(tile2)) {
            tile1.foundMatch();
            tile2.foundMatch();
            return "Match found!";
        } else {
            tile1.hide();
            tile2.hide();
            return "Not a match!";
        }
    }

    /**
     * Validates if a tile selection is within bounds and not already matched.
     */
    public boolean validateSelection(int row, int col) {
        return row >= 0 && row < gameboard.length && col >= 0 && col < gameboard[0].length
                && !gameboard[row][col].matched();
    }
}
