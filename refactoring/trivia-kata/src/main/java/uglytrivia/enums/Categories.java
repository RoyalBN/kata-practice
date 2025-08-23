package uglytrivia.enums;

public enum Categories {
    POP("Pop"),
    SCIENCE("Science"),
    SPORTS("Sports"),
    ROCK("Rock");

    private final String category;

    Categories(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
