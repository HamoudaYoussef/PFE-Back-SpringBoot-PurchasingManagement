package biz.picosoft.demo.client.kernel.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


public class RulesDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String srcCode;

    private LocalDate applicationDate;

    private Integer priority;

    private String description;
    private String title;

    private Boolean enabled;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getSrcCode() {
        return srcCode;
    }

    public void setSrcCode(String srcCode) {
        this.srcCode = srcCode;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }



    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RulesDTO)) {
            return false;
        }

        RulesDTO rulesDTO = (RulesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rulesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RulesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", srcCode='" + getSrcCode() + "'" +
            ", applicationDate='" + getApplicationDate() + "'" +
            ", priority=" + getPriority() +
            ", enabled='" + getEnabled() + "'" +
            "}";
    }
}
