package biz.picosoft.demo.domain;

import biz.picosoft.demo.domain.enumeration.UnitePoids;
import biz.picosoft.demo.domain.enumeration.UniteSurface;
import biz.picosoft.demo.domain.enumeration.UniteTaille;
import biz.picosoft.demo.domain.enumeration.UniteVolume;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Produit.
 */
@Entity
//@Table(name = "produit", schema = "achat")
@Table(name = "produit")

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")

public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;



    @Column(name = "dateachat")
    private LocalDate dateachat;

    @Column(name = "description")
    private String description;

    @Column(name = "nom")
    private String nom;

    @Column(name = "poids")
    private Long poids;

    @Column(name = "forme")
    private String forme;

    @Column(name = "taille")
    private Long taille;

    @Column(name = "couleur")
    private String couleur;

    @Enumerated(EnumType.STRING)
    @Column(name = "untietaille")
    private UniteTaille untietaille;

    @Enumerated(EnumType.STRING)
    @Column(name = "unitepoids")
    private UnitePoids unitepoids;

    @Column(name = "volume")
    private Long volume;

    @Enumerated(EnumType.STRING)
    @Column(name = "unitevolume")
    private UniteVolume unitevolume;

    @Column(name = "surface")
    private Long surface;

    @Enumerated(EnumType.STRING)
    @Column(name = "unitesurface")
    private UniteSurface unitesurface;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "produit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "boncommandes", "fournisseur", "demandeachat", "produit" }, allowSetters = true)
    private Set<Offre> offres = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "produits", "demandesachats" }, allowSetters = true)
    private DemandeDevis demandeDevis;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Produit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public LocalDate getDateachat() {
        return this.dateachat;
    }

    public Produit dateachat(LocalDate dateachat) {
        this.setDateachat(dateachat);
        return this;
    }

    public void setDateachat(LocalDate dateachat) {
        this.dateachat = dateachat;
    }

    public String getDescription() {
        return this.description;
    }

    public Produit description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom() {
        return this.nom;
    }

    public Produit nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getPoids() {
        return this.poids;
    }

    public Produit poids(Long poids) {
        this.setPoids(poids);
        return this;
    }

    public void setPoids(Long poids) {
        this.poids = poids;
    }

    public String getForme() {
        return this.forme;
    }

    public Produit forme(String forme) {
        this.setForme(forme);
        return this;
    }

    public void setForme(String forme) {
        this.forme = forme;
    }

    public Long getTaille() {
        return this.taille;
    }

    public Produit taille(Long taille) {
        this.setTaille(taille);
        return this;
    }

    public void setTaille(Long taille) {
        this.taille = taille;
    }

    public String getCouleur() {
        return this.couleur;
    }

    public Produit couleur(String couleur) {
        this.setCouleur(couleur);
        return this;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public UniteTaille getUntietaille() {
        return this.untietaille;
    }

    public Produit untietaille(UniteTaille untietaille) {
        this.setUntietaille(untietaille);
        return this;
    }

    public void setUntietaille(UniteTaille untietaille) {
        this.untietaille = untietaille;
    }

    public UnitePoids getUnitepoids() {
        return this.unitepoids;
    }

    public Produit unitepoids(UnitePoids unitepoids) {
        this.setUnitepoids(unitepoids);
        return this;
    }

    public void setUnitepoids(UnitePoids unitepoids) {
        this.unitepoids = unitepoids;
    }

    public Long getVolume() {
        return this.volume;
    }

    public Produit volume(Long volume) {
        this.setVolume(volume);
        return this;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public UniteVolume getUnitevolume() {
        return this.unitevolume;
    }

    public Produit unitevolume(UniteVolume unitevolume) {
        this.setUnitevolume(unitevolume);
        return this;
    }

    public void setUnitevolume(UniteVolume unitevolume) {
        this.unitevolume = unitevolume;
    }

    public Long getSurface() {
        return this.surface;
    }

    public Produit surface(Long surface) {
        this.setSurface(surface);
        return this;
    }

    public void setSurface(Long surface) {
        this.surface = surface;
    }

    public UniteSurface getUnitesurface() {
        return this.unitesurface;
    }

    public Produit unitesurface(UniteSurface unitesurface) {
        this.setUnitesurface(unitesurface);
        return this;
    }

    public void setUnitesurface(UniteSurface unitesurface) {
        this.unitesurface = unitesurface;
    }

    public Set<Offre> getOffres() {
        return this.offres;
    }

    public void setOffres(Set<Offre> offres) {
        if (this.offres != null) {
            this.offres.forEach(i -> i.setProduit(null));
        }
        if (offres != null) {
            offres.forEach(i -> i.setProduit(this));
        }
        this.offres = offres;
    }

    public Produit offres(Set<Offre> offres) {
        this.setOffres(offres);
        return this;
    }

    public Produit addOffres(Offre offre) {
        this.offres.add(offre);
        offre.setProduit(this);
        return this;
    }

    public Produit removeOffres(Offre offre) {
        this.offres.remove(offre);
        offre.setProduit(null);
        return this;
    }

    public DemandeDevis getDemandedevis() {
        return this.demandeDevis;
    }

    public void setDemandedevis(DemandeDevis demandedevis) {
        this.demandeDevis = demandedevis;
    }

    public Produit demandedevis(DemandeDevis demandedevis) {
        this.setDemandedevis(demandedevis);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produit)) {
            return false;
        }
        return getId() != null && getId().equals(((Produit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Produit{" +
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
