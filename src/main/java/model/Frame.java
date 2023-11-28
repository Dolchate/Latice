package model;


public class Frame {
    private final FrameType type;
    private Tile tile;

    public Frame(FrameType type, Tile tile) {
        this.type = type;
        this.tile = tile;
    }

    public boolean isEmpty() {
        return tile == null;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }


    public Tile getTile() {
        return tile;
    }

    public boolean canBeNextTo(Tile tile) {
        if (isEmpty()) {
            return true;
        }
        return this.tile.hasCommonProperty(tile);
    }

    public FrameType getType() {
        return type;
    }
}
