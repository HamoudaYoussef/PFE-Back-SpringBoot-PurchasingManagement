package biz.picosoft.demo.client.kernel.model.global;


import biz.picosoft.demo.client.kernel.model.acl.AclClass;
import biz.picosoft.demo.config.audit.Auditable;

import java.io.Serializable;

public class StateWorkflow extends Auditable implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String description;

    private String label;

    private String color;

    private Boolean isEndState;

    private AclClass classes;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Boolean getEndState() {
        return isEndState;
    }

    public void setEndState(Boolean endState) {
        isEndState = endState;
    }

    public AclClass getClasses() {
        return classes;
    }

    public void setClasses(AclClass classes) {
        this.classes = classes;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
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

    public StateWorkflow name(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StateWorkflow description(String description) {
        this.description = description;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public StateWorkflow label(String label) {
        this.label = label;
        return this;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public StateWorkflow color(String color) {
        this.color = color;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StateWorkflow)) {
            return false;
        }
        return id != null && id.equals(((StateWorkflow) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore


    @Override
    public String toString() {
        return "StateWorkflow{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", label='" + label + '\'' +
                ", color='" + color + '\'' +
                ", classes=" + classes +
                '}';
    }
}
