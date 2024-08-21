package biz.picosoft.demo.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;


@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemandeDevisDTO implements Serializable {

    private Long id;

    private String description;

    private Long quantite;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    private String nom;

    public Long getFournisseurId() {
        return fournisseurId;
    }

    public void setFournisseurId(Long fournisseurId) {
        this.fournisseurId = fournisseurId;
    }

    public Long getDemandeAchatId() {
        return demandeAchatId;
    }

    public void setDemandeAchatId(Long demandeAchatId) {
        this.demandeAchatId = demandeAchatId;
    }

    private Long fournisseurId;

    private Long demandeAchatId;


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
        if (!(o instanceof DemandeDevisDTO)) {
            return false;
        }

        DemandeDevisDTO demandeDevisDTO = (DemandeDevisDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, demandeDevisDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeDevisDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", quantite=" + getQuantite() +
            "}";
    }
}
