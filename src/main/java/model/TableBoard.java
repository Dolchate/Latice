package model;

import java.util.ArrayList;
import java.util.List;

public class TableBoard {
    private static TableBoard instance;
    private final Frame[][] board;


    public TableBoard() {
        board = new Frame[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (i == 4 && j == 4) {
                    board[i][j] = new Frame(FrameType.Moon, null);
                } else if ((i == j && i != 3 && i != 5) || (8 - i == j && i != 3 && i != 5)
                        || (i == 4 && (j == 0 || j == 8)) || (j == 4 && (i == 0 || i == 8))) {
                    board[i][j] = new Frame(FrameType.Sun, null);
                } else {
                    board[i][j] = new Frame(FrameType.None, null);
                }
            }
        }
        instance = this;
    }

    public static TableBoard getInstance() {
        return instance;
    }


    public boolean putTile(Tile tile, int xPos, int yPos) {
        if (!canPutTile(tile, xPos, yPos))
            return false;

        board[xPos][yPos].setTile(tile);
        return true;
    }

    private boolean isBoardEmpty() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!board[i][j].isEmpty())
                    return false;
            }
        }
        return true;
    }


    private boolean canPutTile(Tile tile, int xPos, int yPos) {
        if (isBoardEmpty()) {
            return board[xPos][yPos].getType() == FrameType.Moon;
        }

        if (!board[xPos][yPos].isEmpty()) {
            return false;
        }

        List<Frame> frames = getFramesNear(xPos, yPos);
        for (Frame frame : frames) {
            if (!frame.canBeNextTo(tile)) {
                return false;
            }
        }
        return !frames.isEmpty();
    }

    public FrameType getFrameType(int xPos, int yPos) {
        return board[xPos][yPos].getType();
    }

    public Frame[][] getBoard() {
        return board;
    }

    public List<Frame> getFramesNear(int xPos, int yPos) {
        List<Frame> frames = new ArrayList<>();

        if (xPos != 8)
            if (!board[xPos + 1][yPos].isEmpty()) {
                frames.add(board[xPos + 1][yPos]);
            }

        if (xPos != 0)
            if (!board[xPos - 1][yPos].isEmpty()) {
                frames.add(board[xPos - 1][yPos]);
            }
        if (yPos != 8)
            if (!board[xPos][yPos + 1].isEmpty()) {
                frames.add(board[xPos][yPos + 1]);
            }
        if (yPos != 0)
            if (!board[xPos][yPos - 1].isEmpty()) {
                frames.add(board[xPos][yPos - 1]);
            }

        return frames;
    }

    private int getNumberOfFrameNear(int xPos, int yPos) {
        return getFramesNear(xPos, yPos).size();
    }

    public int getPointsOnFrame(int xPos, int yPos) {
        int points;
        switch (getNumberOfFrameNear(xPos, yPos)) {
            case 2:
                points = 1;
                break;
            case 3:
                points = 2;
                break;
            case 4:
                points = 4;
                break;
            default:
                points = 0;
                break;
        }

        if (getFrameType(xPos, yPos) == FrameType.Sun) {
            points += 2;
        }

        return points;
    }

}
