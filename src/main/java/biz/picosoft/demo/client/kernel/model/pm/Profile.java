package biz.picosoft.demo.client.kernel.model.pm;

import biz.picosoft.demo.config.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A Profile.
 */

public class Profile extends Auditable implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;

    private String name;

    private String description;

    private String comment;

    private String mail;

    private int orders;

    @Lob
    private byte[] signature;

    private String signatureContentType;

    private Integer securiteLevel = 0;
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "k_pj_profile_role", schema = "kernel", joinColumns = @JoinColumn(name = "profile", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roles", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @OneToMany(mappedBy = "profile")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    public Profile() {
    }

    public String getSignatureContentType() {
        return signatureContentType;
    }

    public void setSignatureContentType(String signatureContentType) {
        this.signatureContentType = signatureContentType;
    }

    public byte[] getSignature() {
        return signature;
    }

    /*FOR SecuriteLevel*/

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public Integer getSecuriteLevel() {
        return securiteLevel;
    }

    public void setSecuriteLevel(Integer securiteLevel) {
        this.securiteLevel = securiteLevel;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Profile comment(String comment) {
        this.comment = comment;
        return this;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Profile roles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Profile addRoles(Role role) {
        this.roles.add(role);
        role.getProfiles().add(this);
        return this;
    }

    public Profile removeRoles(Role role) {
        this.roles.remove(role);
        role.getProfiles().remove(this);
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profile)) {
            return false;
        }
        return id != null && id.equals(((Profile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + getId() +
                ", profilename='" + getName() + "'" +
                ", profildescription='" + getDescription() + "'" +
                ", comment='" + getComment() + "'" +
                "}";
    }
}
