package biz.picosoft.demo.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link biz.picosoft.demo.domain.DemandeDevis} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemandeDevisDTO implements Serializable {

    private Long id;

    private String description;

    private LocalDate datedemande;

    private String nom;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    private String reference;


    private FournisseurDTO fournisseur;

    public FournisseurDTO getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(FournisseurDTO fournisseur) {
        this.fournisseur = fournisseur;
    }


    public DemandeAchatDTO getDemandeAchat() {
        return demandeAchat;
    }

    public void setDemandeAchat(DemandeAchatDTO demandeAchat) {
        this.demandeAchat = demandeAchat;
    }

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

    public LocalDate getDatedemande() {
        return datedemande;
    }

    public void setDatedemande(LocalDate datedemande) {
        this.datedemande = datedemande;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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
            ", datedemande='" + getDatedemande() + "'" +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
