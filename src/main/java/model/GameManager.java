package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {
    public static final int NB_CYCLE = 20;
    private final Player[] players;

    private int currentPlayerIndex;

    private static GameManager instance;
    private int turnNumber;

    private GameManager() {
        new TableBoard();
        players = new Player[2];
    }

    public static void createGame() {
        instance = new GameManager();
        instance.initialize();
    }

    public static GameManager getInstance() {
        return instance;
    }

    private void initialize() {
        players[0] = new Player();
        players[1] = new Player();

        List<Tile> tiles = generateTiles();
        tiles = shuffleTiles(tiles);

        setTilesToPlayer(tiles);
    }

    public void passTurn() {
        getActualPlayer().fillRack();
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
        getActualPlayer().setFreeFrame();
        turnNumber++;
    }

    private void setTilesToPlayer(List<Tile> tiles) {
        List<Tile> tiles2 = new ArrayList<>();
        int size = tiles.size() / 2;
        for (int i = 0; i < size; i++) {
            tiles2.add(tiles.get(i));
            tiles.remove(i);
        }
        players[0].setTiles(tiles);
        players[1].setTiles(tiles2);
    }

    private List<Tile> shuffleTiles(List<Tile> tiles) {
        List<Tile> tiles2 = new ArrayList<>();
        Random random = new Random();

        int size = tiles.size();
        for (int i = 0; i < size; i++) {
            int randomNumber = random.nextInt(tiles.size());
            tiles2.add(tiles.get(randomNumber));
            tiles.remove(randomNumber);
        }

        return tiles2;
    }

    private List<Tile> generateTiles() {
        List<Tile> tiles = new ArrayList<>();

        for (Shape value : Shape.values()) {
            for (Color color : Color.values()) {
                for (int i = 0; i < 2; i++) {
                    tiles.add(new Tile(value, color));
                }
            }
        }

        return tiles;
    }


    public Player getPlayer(int index) {
        return players[index];
    }

    public Player getActualPlayer() {
        return getPlayer(currentPlayerIndex);
    }
    public Integer getActualPlayerIndex() {
        return currentPlayerIndex;
    }


    public boolean isGameOver() {
        return turnNumber >= NB_CYCLE;
    }
}
