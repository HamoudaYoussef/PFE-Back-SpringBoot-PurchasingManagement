package biz.picosoft.demo.domain.enumeration;

/**
 * The UniteSurface enumeration.
 */
public enum UniteSurface {
    CARRE_METRE("m²"),
    CARRE_CENTIMETRE("cm²"),
    CARRE_ft("ft²");

    private final String value;

    UniteSurface(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
