package biz.picosoft.demo.client.kernel.model.global;

import java.io.Serializable;


public class StringResourceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String key;

    private String arabic;

    private String french;

    private String english;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StringResourceDTO id(Long id) {
        this.setId(id);
        return this;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public StringResourceDTO key(String key) {
        this.setKey(key);
        return this;
    }

    public String getArabic() {
        return this.arabic;
    }

    public void setArabic(String arabic) {
        this.arabic = arabic;
    }

    public StringResourceDTO arabic(String arabic) {
        this.setArabic(arabic);
        return this;
    }

    public String getFrench() {
        return this.french;
    }

    public void setFrench(String french) {
        this.french = french;
    }

    public StringResourceDTO french(String french) {
        this.setFrench(french);
        return this;
    }

    public String getEnglish() {
        return this.english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public StringResourceDTO english(String english) {
        this.setEnglish(english);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StringResourceDTO)) {
            return false;
        }
        return id != null && id.equals(((StringResourceDTO) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StringResource{" +
                "id=" + getId() +
                ", key='" + getKey() + "'" +
                ", arabic='" + getArabic() + "'" +
                ", french='" + getFrench() + "'" +
                ", english='" + getEnglish() + "'" +
                "}";
    }
}
