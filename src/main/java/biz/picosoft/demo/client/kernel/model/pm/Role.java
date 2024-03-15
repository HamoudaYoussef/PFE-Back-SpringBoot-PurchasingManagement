package biz.picosoft.demo.client.kernel.model.pm;


import biz.picosoft.demo.client.kernel.model.acl.AclSid;
import biz.picosoft.demo.config.audit.Auditable;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Role.
 */

public class Role extends Auditable implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String description;

    private String comment;

    private Set<Profile> profiles = new HashSet<>();

    private AclSid aclSid;

    private String anomalieDescription;
    private ZonedDateTime anomalieDate;

    public Role() {
    }

    public String getAnomalieDescription() {
        return anomalieDescription;
    }

    public void setAnomalieDescription(String anomalieDescription) {
        this.anomalieDescription = anomalieDescription;
    }

    public ZonedDateTime getAnomalieDate() {
        return anomalieDate;
    }

    public void setAnomalieDate(ZonedDateTime anomalieDate) {
        this.anomalieDate = anomalieDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AclSid getAclSid() {
        return aclSid;
    }

    public void setAclSid(AclSid aclSid) {
        this.aclSid = aclSid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Role comment(String comment) {
        this.comment = comment;
        return this;
    }


    public Set<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
    }

    public Role profiles(Set<Profile> profiles) {
        this.profiles = profiles;
        return this;
    }

    public Role addProfile(Profile profile) {
        this.profiles.add(profile);
        profile.getRoles().add(this);
        return this;
    }

    public Role removeProfile(Profile profile) {
        this.profiles.remove(profile);
        profile.getRoles().remove(this);
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        return id != null && id.equals(((Role) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", description='" + getDescription() + "'" +
                ", comment='" + getComment() + "'" +
                "}";
    }
}
