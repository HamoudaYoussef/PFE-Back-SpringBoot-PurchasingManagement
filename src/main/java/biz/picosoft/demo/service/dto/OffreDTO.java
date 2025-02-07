package biz.picosoft.demo.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link biz.picosoft.demo.domain.Offre} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OffreDTO implements Serializable {

    private Long id;

    private String nom;

    private Float prix;

    private LocalDate dateoffre;

    private String description;

    private String referenceoffre;

    private FournisseurDTO fournisseur;

    private DemandeDevisDTO demandeDevis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public LocalDate getDateoffre() {
        return dateoffre;
    }

    public void setDateoffre(LocalDate dateoffre) {
        this.dateoffre = dateoffre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReferenceoffre() {
        return referenceoffre;
    }

    public void setReferenceoffre(String referenceoffre) {
        this.referenceoffre = referenceoffre;
    }

    public FournisseurDTO getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(FournisseurDTO fournisseur) {
        this.fournisseur = fournisseur;
    }

    public DemandeDevisDTO getDemandeDevis() {
        return demandeDevis;
    }

    public void setDemandeDevis(DemandeDevisDTO demandeDevis) {
        this.demandeDevis = demandeDevis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OffreDTO)) {
            return false;
        }

        OffreDTO offreDTO = (OffreDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, offreDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OffreDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prix=" + getPrix() +
            ", dateoffre='" + getDateoffre() + "'" +
            ", description='" + getDescription() + "'" +
            ", referenceoffre='" + getReferenceoffre() + "'" +
            ", fournisseur=" + getFournisseur() +
            ", demandeDevis=" + getDemandeDevis() +
            "}";
    }
}
