package biz.picosoft.demo.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link biz.picosoft.demo.domain.ProduitDemandee} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitDemandeeDTO implements Serializable {

    private Long id;

    private String description;

    private String nom;

    private Long quantite;

    private String img;

    private ProduitDTO produit;

    private DemandeAchatDTO demandeAchat;

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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public ProduitDTO getProduit() {
        return produit;
    }

    public void setProduit(ProduitDTO produit) {
        this.produit = produit;
    }

    public DemandeAchatDTO getDemandeAchat() {
        return demandeAchat;
    }

    public void setDemandeAchat(DemandeAchatDTO demandeAchat) {
        this.demandeAchat = demandeAchat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProduitDemandeeDTO)) {
            return false;
        }

        ProduitDemandeeDTO produitDemandeeDTO = (ProduitDemandeeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, produitDemandeeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitDemandeeDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", nom='" + getNom() + "'" +
            ", quantite=" + getQuantite() +
            ", img='" + getImg() + "'" +
            ", produit=" + getProduit() +
            ", demandeAchat=" + getDemandeAchat() +
            "}";
    }
}
