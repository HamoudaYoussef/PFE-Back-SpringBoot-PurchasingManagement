package biz.picosoft.demo.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link biz.picosoft.demo.domain.ProduitOffert} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitOffertDTO implements Serializable {

    private Long id;

    private String nom;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private String img;


    private String description;

    private Long quantite;

    private Long prix;

    private ProduitDTO produit;

    private OffreDTO offre;

    public BonCommandeDTO getBonCommande() {
        return bonCommande;
    }

    public void setBonCommande(BonCommandeDTO bonCommande) {
        this.bonCommande = bonCommande;
    }

    private BonCommandeDTO bonCommande;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public Long getPrix() {
        return prix;
    }

    public void setPrix(Long prix) {
        this.prix = prix;
    }

    public ProduitDTO getProduit() {
        return produit;
    }

    public void setProduit(ProduitDTO produit) {
        this.produit = produit;
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
        if (!(o instanceof ProduitOffertDTO)) {
            return false;
        }

        ProduitOffertDTO produitOffertDTO = (ProduitOffertDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, produitOffertDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitOffertDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", quantite=" + getQuantite() +
            ", prix=" + getPrix() +
            ", produit=" + getProduit() +
            ", offre=" + getOffre() +
            "}";
    }
}
