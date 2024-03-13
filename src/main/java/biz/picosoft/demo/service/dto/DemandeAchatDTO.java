package biz.picosoft.demo.service.dto;

import biz.picosoft.demo.domain.enumeration.StatutDA;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link biz.picosoft.demo.domain.DemandeAchat} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemandeAchatDTO implements Serializable {

    private Long id;

    private LocalDate datedemande;
    private LocalDate datebesoin;

    private StatutDA statut;


    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public LocalDate getDatedemande() {
        return datedemande;
    }

    public void setDatedemande(LocalDate datedemande) {
        this.datedemande = datedemande;
    }

    public LocalDate getDatebesoin() {
        return datebesoin;
    }

    public void setDatebesoin(LocalDate datebesoin) {
        this.datebesoin = datebesoin;
    }
    public StatutDA getStatut() {
        return statut;
    }

    public void setStatut(StatutDA statut) {
        this.statut = statut;
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
        if (!(o instanceof DemandeAchatDTO)) {
            return false;
        }

        DemandeAchatDTO demandeAchatDTO = (DemandeAchatDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, demandeAchatDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeAchatDTO{" +
            "id=" + getId() +
            ", datedemande='" + getDatedemande() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
