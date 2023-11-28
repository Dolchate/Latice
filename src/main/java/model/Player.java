package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    private final Tile[] rack;
    private List<Tile> stack;
    private int points;
    private boolean hasFreeFrame;

    public Player() {
        rack = new Tile[5];
        points = 0;
        stack = new ArrayList<>();
        hasFreeFrame = true;
    }

    public void setTiles(List<Tile> tiles1) {
        stack = tiles1;
        fillRack();
    }

    private boolean isRackFull() {
        for (Tile tile : rack) {
            if (tile == null)
                return false;
        }
        return true;
    }


    public boolean changeRack() {
        if (stack.isEmpty())
            return false;

        if (!isRackFull()) {
            if (points < 2)
                return false;
            else{
                points -= 2;
            }
        }

        for (int i = 0; i < rack.length; i++) {
            if (rack[i] != null) {
                stack.add(rack[i]);
                rack[i] = null;
            }
        }

        for (int i = 0; i < rack.length; i++) {
            rack[i] = takeRandomTileInStack();
        }

        return true;
    }

    public void fillRack() {
        for (int i = 0; i < rack.length; i++) {
            if (rack[i] == null) {
                rack[i] = takeRandomTileInStack();
            }
        }
    }

    private Tile takeRandomTileInStack() {
        if (stack.isEmpty())
            return null;

        int randomIndex = new Random().nextInt(stack.size());
        Tile temp = stack.get(randomIndex);
        stack.remove(randomIndex);
        return temp;
    }

    public boolean putTile(int rackIndex, int xPos, int yPos) {
        if (!hasFreeFrame && points < 2) {
            return false;
        }

        if (TableBoard.getInstance().putTile(rack[rackIndex], xPos, yPos)) {
            rack[rackIndex] = null;

            if (hasFreeFrame) {
                hasFreeFrame = false;
            } else {
                points -= 2;
            }
            points += TableBoard.getInstance().getPointsOnFrame(xPos, yPos);
            return true;
        }    
        return false;
    }

    public Tile[] getRack() {
        return rack;
    }

    public int getPoints() {
        return points;
    }


    public void setFreeFrame() {
        hasFreeFrame = true;
    }

    public Integer getTileLeftInStack() {
        return stack.size();
    }
}
