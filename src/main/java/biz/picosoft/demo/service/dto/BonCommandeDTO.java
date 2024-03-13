package biz.picosoft.demo.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link biz.picosoft.demo.domain.BonCommande} entity.
 */
@Schema(description = "Task entity.\n@author The JHipster team.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BonCommandeDTO implements Serializable {

    private Long id;


    private LocalDate dateboncommande;

    private String reference;

    private Long quantite;

    private Long prixunitaire;

    private Long totalht;

    private Long tva;

    private Long totalttc;

    private OffreDTO offre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public LocalDate getDateboncommande() {
        return dateboncommande;
    }

    public void setDateboncommande(LocalDate dateboncommande) {
        this.dateboncommande = dateboncommande;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public Long getPrixunitaire() {
        return prixunitaire;
    }

    public void setPrixunitaire(Long prixunitaire) {
        this.prixunitaire = prixunitaire;
    }

    public Long getTotalht() {
        return totalht;
    }

    public void setTotalht(Long totalht) {
        this.totalht = totalht;
    }

    public Long getTva() {
        return tva;
    }

    public void setTva(Long tva) {
        this.tva = tva;
    }

    public Long getTotalttc() {
        return totalttc;
    }

    public void setTotalttc(Long totalttc) {
        this.totalttc = totalttc;
    }

    public OffreDTO getOffre() {
        return offre;
    }

    public void setOffre(OffreDTO offre) {
        this.offre = offre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BonCommandeDTO)) {
            return false;
        }

        BonCommandeDTO bonCommandeDTO = (BonCommandeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bonCommandeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BonCommandeDTO{" +
            "id=" + getId() +
            ", dateboncommande='" + getDateboncommande() + "'" +
            ", reference='" + getReference() + "'" +
            ", quantite=" + getQuantite() +
            ", prixunitaire=" + getPrixunitaire() +
            ", totalht=" + getTotalht() +
            ", tva=" + getTva() +
            ", totalttc=" + getTotalttc() +
            ", offre=" + getOffre() +
            "}";
    }
}
