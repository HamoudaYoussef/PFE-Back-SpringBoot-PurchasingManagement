package biz.picosoft.demo.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link biz.picosoft.demo.domain.ProduitOffert} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitOffertDTO implements Serializable {

    private Long id;


    private Long idproduit;

    private Long idoffre;

    private Long quantiteofferte;

    private OffreDTO offre;

    private ProduitDTO produit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Long getIdproduit() {
        return idproduit;
    }

    public void setIdproduit(Long idproduit) {
        this.idproduit = idproduit;
    }

    public Long getIdoffre() {
        return idoffre;
    }

    public void setIdoffre(Long idoffre) {
        this.idoffre = idoffre;
    }

    public Long getQuantiteofferte() {
        return quantiteofferte;
    }

    public void setQuantiteofferte(Long quantiteofferte) {
        this.quantiteofferte = quantiteofferte;
    }

    public OffreDTO getOffre() {
        return offre;
    }

    public void setOffre(OffreDTO offre) {
        this.offre = offre;
    }

    public ProduitDTO getProduit() {
        return produit;
    }

    public void setProduit(ProduitDTO produit) {
        this.produit = produit;
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
            ", idproduit=" + getIdproduit() +
            ", idoffre=" + getIdoffre() +
            ", quantiteofferte=" + getQuantiteofferte() +
            ", offre=" + getOffre() +
            ", produit=" + getProduit() +
            "}";
    }
}
