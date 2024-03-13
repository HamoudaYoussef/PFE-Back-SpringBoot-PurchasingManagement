package biz.picosoft.demo.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link biz.picosoft.demo.domain.ProduitCommandee} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitCommandeeDTO implements Serializable {

    private Long id;


    private Long idproduit;

    private Long idboncommande;

    private LocalDate dateboncommande;

    private Long quantitecommandee;

    private BonCommandeDTO boncommande;

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

    public Long getIdboncommande() {
        return idboncommande;
    }

    public void setIdboncommande(Long idboncommande) {
        this.idboncommande = idboncommande;
    }

    public LocalDate getDateboncommande() {
        return dateboncommande;
    }

    public void setDateboncommande(LocalDate dateboncommande) {
        this.dateboncommande = dateboncommande;
    }

    public Long getQuantitecommandee() {
        return quantitecommandee;
    }

    public void setQuantitecommandee(Long quantitecommandee) {
        this.quantitecommandee = quantitecommandee;
    }

    public BonCommandeDTO getBoncommande() {
        return boncommande;
    }

    public void setBoncommande(BonCommandeDTO boncommande) {
        this.boncommande = boncommande;
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
            ", idproduit=" + getIdproduit() +
            ", idboncommande=" + getIdboncommande() +
            ", dateboncommande='" + getDateboncommande() + "'" +
            ", quantitecommandee=" + getQuantitecommandee() +
            ", boncommande=" + getBoncommande() +
            ", produit=" + getProduit() +
            "}";
    }
}
