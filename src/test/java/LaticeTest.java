import model.*;
import org.junit.jupiter.api.Test;

public class LaticeTest {

    private void consolePrintBoard() {
        StringBuilder board = new StringBuilder();
        for (Frame[] frames : TableBoard.getInstance().getBoard()) {
            for (Frame frame : frames) {
                board.append(frame.getTile()).append("\t");
            }
            board.append("\n");
        }
        System.out.println(board);
    }

    @Test
    public void testInitialization() {
        GameManager.createGame();
        consolePrintBoard();
    }

    @Test
    public void testPuttingTile() {
        GameManager.createGame();
        TableBoard.getInstance().putTile(new Tile(Shape.Dolphin, Color.Blue), 0, 0);
        TableBoard.getInstance().putTile(new Tile(Shape.Dolphin, Color.Red), 1, 0);
        //todo: try to put tile where it is not possible
        consolePrintBoard();
    }


}
