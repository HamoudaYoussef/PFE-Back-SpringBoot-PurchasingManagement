package biz.picosoft.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Fournisseur.
 */
@Entity
@Table(name = "fournisseur", schema = "achat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Fournisseur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "tel")
    private String tel;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fournisseur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "boncommandes", "demandeachat", "fournisseur" }, allowSetters = true)
    private Set<Offre> offres = new HashSet<>();




    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "fournisseurs", "demandeachat" }, allowSetters = true)
    private Produit produit;

    public Set<DemandeDevis> getDemandedevis() {
        return demandedevis;
    }

    public void setDemandedevis(Set<DemandeDevis> demandedevis) {
        this.demandedevis = demandedevis;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fournisseur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fournisseur", "demandeAchat" }, allowSetters = true)
    private Set<DemandeDevis> demandedevis = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fournisseur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fournisseur", "offre" }, allowSetters = true)
    private Set<ProduitOffert>produitOfferts ;

    public Set<ProduitOffert> getProduitOfferts() {
        return produitOfferts;
    }

    public void setProduitOfferts(Set<ProduitOffert> produitOfferts) {
        this.produitOfferts = produitOfferts;
    }
// jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Fournisseur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Fournisseur nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Fournisseur adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return this.tel;
    }

    public Fournisseur tel(String tel) {
        this.setTel(tel);
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Set<Offre> getOffres() {
        return this.offres;
    }

    public void setOffres(Set<Offre> offres) {
        if (this.offres != null) {
            this.offres.forEach(i -> i.setFournisseur(null));
        }
        if (offres != null) {
            offres.forEach(i -> i.setFournisseur(this));
        }
        this.offres = offres;
    }

    public Fournisseur offres(Set<Offre> offres) {
        this.setOffres(offres);
        return this;
    }

    public Fournisseur addOffre(Offre offre) {
        this.offres.add(offre);
        offre.setFournisseur(this);
        return this;
    }

    public Fournisseur removeOffre(Offre offre) {
        this.offres.remove(offre);
        offre.setFournisseur(null);
        return this;
    }

    public Produit getProduit() {
        return this.produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Fournisseur produit(Produit produit) {
        this.setProduit(produit);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fournisseur)) {
            return false;
        }
        return getId() != null && getId().equals(((Fournisseur) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fournisseur{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", tel='" + getTel() + "'" +
            "}";
    }
}
