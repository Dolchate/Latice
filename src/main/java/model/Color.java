package model;

public enum Color {
    Red("red"),
    Blue("blue"),
    Green("green"),
    Yellow("yellow"),
    LightBlue("lBlue"),
    Purple("purple");

    private final String name;

    Color(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
