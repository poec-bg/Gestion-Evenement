package models.types;

public enum Categorie {
    RED("red"), BLACK("black"), BLUE("blue"), CYAN("cyan"), GREEN("green"), MAGENTA("mengenta");

    private String label;

    Categorie(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
