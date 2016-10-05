package models.types;

public enum Categorie {
    RED("red", "rouge"),
    BLACK("black", "noir"),
    BLUE("blue", "bleu"),
    CYAN("cyan", "cyan"),
    GREEN("green", "vert"),
    MAGENTA("magenta", "magenta");

    private String labelEN, labelFR;

    Categorie(String labelEN, String labelFR) {
        this.labelEN = labelEN;
        this.labelFR = labelFR;
    }

    public String getLabel() {
        return labelEN;
    }

    public String getLabelEN() {
        return labelEN;
    }

    public String getLabelFR() {
        return labelFR;
    }
}
