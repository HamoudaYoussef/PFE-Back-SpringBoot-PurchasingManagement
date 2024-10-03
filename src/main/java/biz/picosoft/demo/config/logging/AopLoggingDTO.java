package biz.picosoft.demo.config.logging;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link biz.picosoft.demo.config.logging.domain.AopLogging} entity.
 */
public class AopLoggingDTO implements Serializable {

    private Long id;

    private String microserviceName;

    private String methodName;

    private String uuid;

    private String packageFullName;

    private String packageTitle;

    private Long duration;

    private String userName;

    private String uniteName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMicroserviceName() {
        return microserviceName;
    }

    public void setMicroserviceName(String microserviceName) {
        this.microserviceName = microserviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPackageFullName() {
        return packageFullName;
    }

    public void setPackageFullName(String packageFullName) {
        this.packageFullName = packageFullName;
    }

    public String getPackageTitle() {
        return packageTitle;
    }

    public void setPackageTitle(String packageTitle) {
        this.packageTitle = packageTitle;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUniteName() {
        return uniteName;
    }

    public void setUniteName(String uniteName) {
        this.uniteName = uniteName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AopLoggingDTO)) {
            return false;
        }

        AopLoggingDTO aopLoggingDTO = (AopLoggingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, aopLoggingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AopLoggingDTO{" +
            "id=" + getId() +
            ", microserviceName='" + getMicroserviceName() + "'" +
            ", methodName='" + getMethodName() + "'" +
            ", uuid='" + getUuid() + "'" +
            ", packageFullName='" + getPackageFullName() + "'" +
            ", packageTitle='" + getPackageTitle() + "'" +
            ", duration='" + getDuration() + "'" +
            ", userName='" + getUserName() + "'" +
            ", uniteName='" + getUniteName() + "'" +
            "}";
    }
}
