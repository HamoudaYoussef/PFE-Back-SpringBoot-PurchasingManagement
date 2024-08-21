package biz.picosoft.demo.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;


@Schema(description = "not an ignored comment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OffreDTO implements Serializable {

    private Long id;
    private String nom;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    private Float prix;

    private LocalDate dateoffre;

    private String description;
    private String referenceoffre;

    public String getReferenceoffre() {
        return referenceoffre;
    }

    public void setReferenceoffre(String referenceoffre) {
        this.referenceoffre = referenceoffre;
    }

    private DemandeAchatDTO demandeachat;

    private FournisseurDTO fournisseur;

    public Set<ProduitOffertDTO> getProduitOfferts() {
        return produitOfferts;
    }

    public void setProduitOfferts(Set<ProduitOffertDTO> produitOfferts) {
        this.produitOfferts = produitOfferts;
    }

    private Set<ProduitOffertDTO> produitOfferts;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public DemandeAchatDTO getDemandeachat() {
        return demandeachat;
    }

    public void setDemandeachat(DemandeAchatDTO demandeachat) {
        this.demandeachat = demandeachat;
    }

    public FournisseurDTO getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(FournisseurDTO fournisseur) {
        this.fournisseur = fournisseur;
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
            ", prix=" + getPrix() +
            ", dateoffre='" + getDateoffre() + "'" +
            ", description='" + getDescription() + "'" +
            ", demandeachat=" + getDemandeachat() +
            ", fournisseur=" + getFournisseur() +
            "}";
    }
}
