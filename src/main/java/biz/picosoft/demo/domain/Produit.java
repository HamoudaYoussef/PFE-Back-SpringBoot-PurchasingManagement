package biz.picosoft.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "produit", schema = "achat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "nom")
    private String nom;

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    @Column(name = "quantite")
    private Long quantite;

    public Long getQuantitedemandeur() {
        return quantitedemandeur;
    }

    public void setQuantitedemandeur(Long quantitedemandeur) {
        this.quantitedemandeur = quantitedemandeur;
    }

    @Column(name = "quantitedemanndeur")
    private Long quantitedemandeur;

    @Column(name = "couleur")
    private String couleur;


    public Boolean getStock() {
        return stock;
    }

    public void setStock(Boolean stock) {
        this.stock = stock;
    }

    @Column(name = "stock")
    private Boolean stock;



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "produit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "offres", "demandedevis", "produit" }, allowSetters = true)
    private Set<Fournisseur> fournisseurs = new HashSet<>();



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



    public Set<Fournisseur> getFournisseurs() {
        return this.fournisseurs;
    }

    public void setFournisseurs(Set<Fournisseur> fournisseurs) {
        if (this.fournisseurs != null) {
            this.fournisseurs.forEach(i -> i.setProduit(null));
        }
        if (fournisseurs != null) {
            fournisseurs.forEach(i -> i.setProduit(this));
        }
        this.fournisseurs = fournisseurs;
    }

    public Produit fournisseurs(Set<Fournisseur> fournisseurs) {
        this.setFournisseurs(fournisseurs);
        return this;
    }

    public Produit addFournisseur(Fournisseur fournisseur) {
        this.fournisseurs.add(fournisseur);
        fournisseur.setProduit(this);
        return this;
    }

    public Produit removeFournisseur(Fournisseur fournisseur) {
        this.fournisseurs.remove(fournisseur);
        fournisseur.setProduit(null);
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
            ", description='" + getDescription() + "'" +
            ", nom='" + getNom() + "'" +
            ", couleur='" + getCouleur() + "'" +

            "}";
    }
}
