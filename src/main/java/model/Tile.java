package model;


public class Tile {
    private final Shape shape;
    private final Color color;

    public Tile(Shape shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    public boolean hasCommonProperty(Tile other) {
        return shape.equals(other.shape) || color.equals(other.color);
    }

    @Override
    public String toString() {
        return shape.toString() + color.toString();
    }

    public String getImageName() {
        return shape.getName() + "_" + color.getName() + ".png";
    }
}
