package biz.picosoft.demo.client.kernel.model.pm;


import biz.picosoft.demo.config.audit.Auditable;

import java.io.Serializable;

/**
 * A Variable.
 */

public class Variable extends Auditable implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String value;

    private String description;

    private String comment;

    public Variable() {
    }

    public Variable(String name, String value) {
        this.name = name;
        this.value = value;
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

    public Variable name(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Variable value(String value) {
        this.value = value;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Variable description(String description) {
        this.description = description;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Variable comment(String comment) {
        this.comment = comment;
        return this;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Variable)) {
            return false;
        }
        return id != null && id.equals(((Variable) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", value='" + getValue() + "'" +
                ", description='" + getDescription() + "'" +
                ", comment='" + getComment() + "'" +
                "}";
    }
}
