package biz.picosoft.demo.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link biz.picosoft.demo.domain.ProduitCommandee} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitCommandeeDTO implements Serializable {

    private Long id;

    private String nom;

    private String description;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private String img;


    private Long quantite;


    public ProduitDTO getProduit() {
        return produit;
    }

    public void setProduit(ProduitDTO produit) {
        this.produit = produit;
    }

    public DemandeDevisDTO getDemandeDevis() {
        return demandeDevis;
    }

    public void setDemandeDevis(DemandeDevisDTO demandeDevis) {
        this.demandeDevis = demandeDevis;
    }

    private ProduitDTO produit;

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



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProduitCommandeeDTO)) {
            return false;
        }

        ProduitCommandeeDTO produitCommandeeDTO = (ProduitCommandeeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, produitCommandeeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitCommandeeDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", quantite=" + getQuantite() +
            "}";
    }
}
