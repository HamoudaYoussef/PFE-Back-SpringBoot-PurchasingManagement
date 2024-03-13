package com.mycompany.demo.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.demo.domain.DemandeDevis} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemandeDevisDTO implements Serializable {

    private Long id;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandeDevisDTO)) {
            return false;
        }

        DemandeDevisDTO demandeDevisDTO = (DemandeDevisDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, demandeDevisDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeDevisDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
