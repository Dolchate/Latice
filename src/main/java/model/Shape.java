package model;

public enum Shape {
    Bird("bird"),
    Lizard("lizard"),
    Feather("feather"),
    Turtle("turtle"),
    Dolphin("dolphin"),
    Flower("flower");

    private final String name;

    Shape(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
