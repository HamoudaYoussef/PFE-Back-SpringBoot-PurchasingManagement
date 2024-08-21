package biz.picosoft.demo.service.dto;

import biz.picosoft.demo.domain.Fournisseur;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@SuppressWarnings("common-java:DuplicatedBlocks")
public class FournisseurDTO implements Serializable {

    private Long id;

    private String nom;

    private String adresse;

    private String tel;
    private ProduitDTO produit;

    public Set<ProduitOffertDTO> getProduitOfferts() {
        return produitOfferts;
    }

    public void setProduitOfferts(Set<ProduitOffertDTO> produitOfferts) {
        this.produitOfferts = produitOfferts;
    }

    private Set<ProduitOffertDTO> produitOfferts;




    public Set<DemandeDevisDTO> getDemandesDevis() {
        return demandesDevis;
    }

    public void setDemandesDevis(Set<DemandeDevisDTO> demandesDevis) {
        this.demandesDevis = demandesDevis;
    }

    private Set<DemandeDevisDTO> demandesDevis = new HashSet<>();

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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public ProduitDTO getProduit() {
        return produit;
    }

    public void setProduit(ProduitDTO produit) {
        this.produit = produit;
    }

    public FournisseurDTO(Long id, String nom, String adresse, String tel) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.tel = tel;
    }
    public static FournisseurDTO fromFournisseur(Fournisseur fournisseur) {
        return new FournisseurDTO(
                fournisseur.getId(),
                fournisseur.getNom(),
                fournisseur.getAdresse(),
                fournisseur.getTel()
        );
    }

        @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FournisseurDTO)) {
            return false;
        }

        FournisseurDTO fournisseurDTO = (FournisseurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fournisseurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FournisseurDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", tel='" + getTel() + "'" +
            ", produit=" + getProduit() +
            "}";
    }
}
