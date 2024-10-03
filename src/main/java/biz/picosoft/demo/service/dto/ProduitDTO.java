package biz.picosoft.demo.service.dto;

import biz.picosoft.demo.domain.ennumeration.Categorie;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.Set;

public class ProduitDTO implements Serializable {

    private Long id;
    private String description;
    private String nom;
    private Long quantite;

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    private Categorie categorie;

    private String couleur;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private String img;







    private Set<FournisseurDTO> fournisseurs;

    // Getters and Setters

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


    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Set<FournisseurDTO> getFournisseurs() {
        return fournisseurs;
    }

    public void setFournisseurs(Set<FournisseurDTO> fournisseurs) {
        this.fournisseurs = fournisseurs;
    }

    @Override
    public String toString() {
        return "ProduitDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", nom='" + nom + '\'' +
                ", quantite=" + quantite +
                ", couleur='" + couleur + '\'' +
                ", fournisseurs=" + fournisseurs +
                '}';
    }
}
