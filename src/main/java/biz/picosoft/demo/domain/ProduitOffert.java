package biz.picosoft.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A ProduitOffert.
 */
@Entity
@Table(name = "produit_offert", schema = "achat")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitOffert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String description;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Column(name = "img")
    private String img;
    @Column(name = "quantite")
    private Long quantite;


    @Column(name = "prix")
    private Long prix;

    @ManyToOne(fetch = FetchType.EAGER)
    private Produit produit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "demandeDevis" }, allowSetters = true)
    private Offre offre;

    public BonCommande getBonCommande() {
        return bonCommande;
    }

    public void setBonCommande(BonCommande bonCommande) {
        this.bonCommande = bonCommande;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties( allowSetters = true)
    private BonCommande bonCommande;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProduitOffert id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public ProduitOffert nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public ProduitOffert description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getQuantite() {
        return this.quantite;
    }

    public ProduitOffert quantite(Long quantite) {
        this.setQuantite(quantite);
        return this;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public Long getPrix() {
        return this.prix;
    }

    public ProduitOffert prix(Long prix) {
        this.setPrix(prix);
        return this;
    }

    public void setPrix(Long prix) {
        this.prix = prix;
    }

    public Produit getProduit() {
        return this.produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public ProduitOffert produit(Produit produit) {
        this.setProduit(produit);
        return this;
    }

    public Offre getOffre() {
        return this.offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public ProduitOffert offre(Offre offre) {
        this.setOffre(offre);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProduitOffert)) {
            return false;
        }
        return getId() != null && getId().equals(((ProduitOffert) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitOffert{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", quantite=" + getQuantite() +
            ", prix=" + getPrix() +
            "}";
    }
}
