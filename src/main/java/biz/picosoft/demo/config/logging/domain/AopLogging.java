package biz.picosoft.demo.config.logging.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A AopLogging.
 */
@Entity
@Table(name = "aop_logging")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AopLogging implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "microservice_name")
    private String microserviceName;

    @Column(name = "method_name")
    private String methodName;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "package_full_name")
    private String packageFullName;

    @Column(name = "package_title")
    private String packageTitle;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "unite_name")
    private String uniteName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AopLogging id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMicroserviceName() {
        return this.microserviceName;
    }

    public AopLogging microserviceName(String microserviceName) {
        this.setMicroserviceName(microserviceName);
        return this;
    }

    public void setMicroserviceName(String microserviceName) {
        this.microserviceName = microserviceName;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public AopLogging methodName(String methodName) {
        this.setMethodName(methodName);
        return this;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getUuid() {
        return this.uuid;
    }

    public AopLogging uuid(String uuid) {
        this.setUuid(uuid);
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPackageFullName() {
        return this.packageFullName;
    }

    public AopLogging packageFullName(String packageFullName) {
        this.setPackageFullName(packageFullName);
        return this;
    }

    public void setPackageFullName(String packageFullName) {
        this.packageFullName = packageFullName;
    }

    public String getPackageTitle() {
        return this.packageTitle;
    }

    public AopLogging packageTitle(String packageTitle) {
        this.setPackageTitle(packageTitle);
        return this;
    }

    public void setPackageTitle(String packageTitle) {
        this.packageTitle = packageTitle;
    }


    public AopLogging duration(Long duration) {
        this.setDuration(duration);
        return this;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getUserName() {
        return this.userName;
    }

    public AopLogging userName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUniteName() {
        return this.uniteName;
    }

    public AopLogging uniteName(String uniteName) {
        this.setUniteName(uniteName);
        return this;
    }

    public void setUniteName(String uniteName) {
        this.uniteName = uniteName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AopLogging)) {
            return false;
        }
        return id != null && id.equals(((AopLogging) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AopLogging{" +
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
