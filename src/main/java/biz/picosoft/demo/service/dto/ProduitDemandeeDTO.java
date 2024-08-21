package biz.picosoft.demo.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitDemandeeDTO implements Serializable {

    private Long id;

    public Long getDemandeAchatId() {
        return demandeAchatId;
    }

    public void setDemandeAchatId(Long demandeAchatId) {
        this.demandeAchatId = demandeAchatId;
    }

    private Long demandeAchatId;

    private String nom;
    private String description;
    private Long quantite;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
            "}";
    }
}
