package biz.picosoft.demo.client.kernel.model.pm;

import java.util.stream.Stream;

public enum ActivityType {
    CONNECT("connect"), DECONNECT("deconnect"), REST_PW("reset password"), TENTATIVE("tentative de connexion"), READ("read"), READ_ATT("download file");

    private final String label;

    ActivityType(String label) {
        this.label = label;
    }

    public static ActivityType of(String label) {
        return Stream.of(ActivityType.values())
                .filter(p -> p.getLabel() == label)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getLabel() {
        return label;
    }

}
