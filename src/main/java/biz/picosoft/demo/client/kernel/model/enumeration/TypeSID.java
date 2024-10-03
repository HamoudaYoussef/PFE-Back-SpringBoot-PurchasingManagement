package biz.picosoft.demo.client.kernel.model.enumeration;

import java.util.stream.Stream;

public enum TypeSID {

    ASS("Assistant"), MGR("Manager"), MBR("Membre"), EMP("Employe"), INTR("Interimaire"), GRP("Goupe"), ROLE("Role");

    private final String label;

    TypeSID(String label) {
        this.label = label;
    }

    public static TypeSID of(String label) {
        return Stream.of(TypeSID.values())
                .filter(p -> p.getLabel() == label)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getLabel() {
        return label;
    }
}
