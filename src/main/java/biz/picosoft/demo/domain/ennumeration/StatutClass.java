package biz.picosoft.demo.domain.ennumeration;

public enum StatutClass {

    DRAFT("Brouillon"),
    CREATED("deposé"),
    ACCEPTED("Accepté"),
    VALIDATED("Validé"),
    VERIFIED("Réglé"),
    CANCELED("Annulé"),
    REFUSED("Réfusé"),
    CLOSED("Payé");

    private final String label;

    StatutClass(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
