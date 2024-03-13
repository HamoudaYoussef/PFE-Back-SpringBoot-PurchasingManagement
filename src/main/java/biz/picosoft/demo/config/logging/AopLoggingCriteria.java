package biz.picosoft.demo.config.logging;

import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;


/**
 * Criteria class for the {@link biz.picosoft.demo.config.logging.domain.AopLogging} entity. This class is used
 * in {@link biz.picosoft.demo.config.logging.AopLoggingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /aop-loggings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AopLoggingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter microserviceName;

    private StringFilter methodName;

    private StringFilter uuid;

    private StringFilter packageFullName;

    private StringFilter packageTitle;

    private LongFilter duration;

    private StringFilter userName;

    private StringFilter uniteName;

    private Boolean distinct;

    public AopLoggingCriteria() {}

    public AopLoggingCriteria(AopLoggingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.microserviceName = other.microserviceName == null ? null : other.microserviceName.copy();
        this.methodName = other.methodName == null ? null : other.methodName.copy();
        this.uuid = other.uuid == null ? null : other.uuid.copy();
        this.packageFullName = other.packageFullName == null ? null : other.packageFullName.copy();
        this.packageTitle = other.packageTitle == null ? null : other.packageTitle.copy();
        this.duration = other.duration == null ? null : other.duration.copy();
        this.userName = other.userName == null ? null : other.userName.copy();
        this.uniteName = other.uniteName == null ? null : other.uniteName.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AopLoggingCriteria copy() {
        return new AopLoggingCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMicroserviceName() {
        return microserviceName;
    }

    public StringFilter microserviceName() {
        if (microserviceName == null) {
            microserviceName = new StringFilter();
        }
        return microserviceName;
    }

    public void setMicroserviceName(StringFilter microserviceName) {
        this.microserviceName = microserviceName;
    }

    public StringFilter getMethodName() {
        return methodName;
    }

    public StringFilter methodName() {
        if (methodName == null) {
            methodName = new StringFilter();
        }
        return methodName;
    }

    public void setMethodName(StringFilter methodName) {
        this.methodName = methodName;
    }

    public StringFilter getUuid() {
        return uuid;
    }

    public StringFilter uuid() {
        if (uuid == null) {
            uuid = new StringFilter();
        }
        return uuid;
    }

    public void setUuid(StringFilter uuid) {
        this.uuid = uuid;
    }

    public StringFilter getPackageFullName() {
        return packageFullName;
    }

    public StringFilter packageFullName() {
        if (packageFullName == null) {
            packageFullName = new StringFilter();
        }
        return packageFullName;
    }

    public void setPackageFullName(StringFilter packageFullName) {
        this.packageFullName = packageFullName;
    }

    public StringFilter getPackageTitle() {
        return packageTitle;
    }

    public StringFilter packageTitle() {
        if (packageTitle == null) {
            packageTitle = new StringFilter();
        }
        return packageTitle;
    }

    public void setPackageTitle(StringFilter packageTitle) {
        this.packageTitle = packageTitle;
    }

    public LongFilter getDuration() {
        return duration;
    }

    public LongFilter duration() {
        if (duration == null) {
            duration = new LongFilter();
        }
        return duration;
    }

    public void setDuration(LongFilter duration) {
        this.duration = duration;
    }

    public StringFilter getUserName() {
        return userName;
    }

    public StringFilter userName() {
        if (userName == null) {
            userName = new StringFilter();
        }
        return userName;
    }

    public void setUserName(StringFilter userName) {
        this.userName = userName;
    }

    public StringFilter getUniteName() {
        return uniteName;
    }

    public StringFilter uniteName() {
        if (uniteName == null) {
            uniteName = new StringFilter();
        }
        return uniteName;
    }

    public void setUniteName(StringFilter uniteName) {
        this.uniteName = uniteName;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AopLoggingCriteria that = (AopLoggingCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(microserviceName, that.microserviceName) &&
            Objects.equals(methodName, that.methodName) &&
            Objects.equals(uuid, that.uuid) &&
            Objects.equals(packageFullName, that.packageFullName) &&
            Objects.equals(packageTitle, that.packageTitle) &&
            Objects.equals(duration, that.duration) &&
            Objects.equals(userName, that.userName) &&
            Objects.equals(uniteName, that.uniteName) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, microserviceName, methodName, uuid, packageFullName, packageTitle, duration, userName, uniteName, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AopLoggingCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (microserviceName != null ? "microserviceName=" + microserviceName + ", " : "") +
            (methodName != null ? "methodName=" + methodName + ", " : "") +
            (uuid != null ? "uuid=" + uuid + ", " : "") +
            (packageFullName != null ? "packageFullName=" + packageFullName + ", " : "") +
            (packageTitle != null ? "packageTitle=" + packageTitle + ", " : "") +
            (duration != null ? "duration=" + duration + ", " : "") +
            (userName != null ? "userName=" + userName + ", " : "") +
            (uniteName != null ? "uniteName=" + uniteName + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
