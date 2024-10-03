package biz.picosoft.demo.domain.ennumeration;

public enum StatutBonCommande {
    EN_COURS("En cours"),
    VALIDE("Validé"),
    ANNULE("Annulé"),
    LIVRE("Livré");

    private final String displayName;

    StatutBonCommande(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
