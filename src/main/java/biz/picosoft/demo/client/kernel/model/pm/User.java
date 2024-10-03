package biz.picosoft.demo.client.kernel.model.pm;


import biz.picosoft.demo.config.audit.Auditable;

import java.io.Serializable;


public class User extends Auditable implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    private String samaccountname;

    private String mail;

    private String displayname;

    private String distinguishedname;

    private String password;

    private String comment;

    private String apitoken;

    private String adObjectGuid;


    public User(String displayname) {
        this.displayname = displayname;
    }

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSamaccountname() {
        return samaccountname;
    }

    public void setSamaccountname(String samaccountname) {
        this.samaccountname = samaccountname;
    }

    public User samaccountname(String samaccountname) {
        this.samaccountname = samaccountname;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public User mail(String mail) {
        this.mail = mail;
        return this;
    }

    public String getDistinguishedname() {
        return distinguishedname;
    }

    public void setDistinguishedname(String distinguishedname) {
        this.distinguishedname = distinguishedname;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public User displayname(String displayname) {
        this.displayname = displayname;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User password(String password) {
        this.password = password;
        return this;
    }

    public String getAdObjectGuid() {
        return adObjectGuid;
    }

    public void setAdObjectGuid(String adObjectGuid) {
        this.adObjectGuid = adObjectGuid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User comment(String comment) {
        this.comment = comment;
        return this;
    }


// jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public String getApitoken() {
        return apitoken;
    }

    public void setApitoken(String apitoken) {
        this.apitoken = apitoken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", samaccountname='" + samaccountname + '\'' +
//                ", mail='" + mail + '\'' +
//                ", displayname='" + displayname + '\'' +
//                ", distinguishedname='" + distinguishedname + '\'' +
//                ", password='" + password + '\'' +
//                ", comment='" + comment + '\'' +
//                ", apitoken='" + apitoken + '\'' +
//                ", profile=" + profile +
//                ", groups=" + groups +
//                '}';
//    }
}
