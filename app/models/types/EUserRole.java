package models.types;

public enum EUserRole {


    ADMIN("Administrateur"),USER("User Normal");

    private String label;

    EUserRole(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
