package biz.picosoft.demo.service.dto;

import biz.picosoft.demo.domain.enumeration.UnitePoids;
import biz.picosoft.demo.domain.enumeration.UniteSurface;
import biz.picosoft.demo.domain.enumeration.UniteTaille;
import biz.picosoft.demo.domain.enumeration.UniteVolume;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link biz.picosoft.demo.domain.Produit} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitDTO implements Serializable {

    private Long id;


    private LocalDate dateachat;

    private String description;

    private String nom;

    private Long poids;

    private String forme;

    private Long taille;

    private String couleur;

    private UniteTaille untietaille;

    private UnitePoids unitepoids;

    private Long volume;

    private UniteVolume unitevolume;

    private Long surface;

    private UniteSurface unitesurface;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public LocalDate getDateachat() {
        return dateachat;
    }

    public void setDateachat(LocalDate dateachat) {
        this.dateachat = dateachat;
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

    public Long getPoids() {
        return poids;
    }

    public void setPoids(Long poids) {
        this.poids = poids;
    }

    public String getForme() {
        return forme;
    }

    public void setForme(String forme) {
        this.forme = forme;
    }

    public Long getTaille() {
        return taille;
    }

    public void setTaille(Long taille) {
        this.taille = taille;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public UniteTaille getUntietaille() {
        return untietaille;
    }

    public void setUntietaille(UniteTaille untietaille) {
        this.untietaille = untietaille;
    }

    public UnitePoids getUnitepoids() {
        return unitepoids;
    }

    public void setUnitepoids(UnitePoids unitepoids) {
        this.unitepoids = unitepoids;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public UniteVolume getUnitevolume() {
        return unitevolume;
    }

    public void setUnitevolume(UniteVolume unitevolume) {
        this.unitevolume = unitevolume;
    }

    public Long getSurface() {
        return surface;
    }

    public void setSurface(Long surface) {
        this.surface = surface;
    }

    public UniteSurface getUnitesurface() {
        return unitesurface;
    }

    public void setUnitesurface(UniteSurface unitesurface) {
        this.unitesurface = unitesurface;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProduitDTO)) {
            return false;
        }

        ProduitDTO produitDTO = (ProduitDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, produitDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitDTO{" +
            "id=" + getId() +
            ", dateachat='" + getDateachat() + "'" +
            ", description='" + getDescription() + "'" +
            ", nom='" + getNom() + "'" +
            ", poids=" + getPoids() +
            ", forme='" + getForme() + "'" +
            ", taille=" + getTaille() +
            ", couleur='" + getCouleur() + "'" +
            ", untietaille='" + getUntietaille() + "'" +
            ", unitepoids='" + getUnitepoids() + "'" +
            ", volume=" + getVolume() +
            ", unitevolume='" + getUnitevolume() + "'" +
            ", surface=" + getSurface() +
            ", unitesurface='" + getUnitesurface() + "'" +
            "}";
    }
}
