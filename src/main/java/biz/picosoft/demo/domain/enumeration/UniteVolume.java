package biz.picosoft.demo.domain.enumeration;

/**
 * The UniteVolume enumeration.
 */
public enum UniteVolume {
    CUBE_METRE("m³"),
    LITRE("L"),
    CENTIMETRE_CUBE("cm³"),
    FT_CUBE("ft³");

    private final String value;

    UniteVolume(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
