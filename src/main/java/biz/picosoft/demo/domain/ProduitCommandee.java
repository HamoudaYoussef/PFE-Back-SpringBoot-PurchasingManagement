package biz.picosoft.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A ProduitCommandee.
 */
@Entity
@Table(name = "produit_commandee", schema = "achat")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitCommandee implements Serializable {

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

    @Column(name = "quantite")
    private Long quantite;


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Column(name = "img")
    private String img;

    @ManyToOne(fetch = FetchType.EAGER)
    private Produit produit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "fournisseur", "demandeAchat" }, allowSetters = true)
    private DemandeDevis demandeDevis;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProduitCommandee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public ProduitCommandee nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public ProduitCommandee description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getQuantite() {
        return this.quantite;
    }

    public ProduitCommandee quantite(Long quantite) {
        this.setQuantite(quantite);
        return this;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public Produit getProduit() {
        return this.produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public ProduitCommandee produit(Produit produit) {
        this.setProduit(produit);
        return this;
    }

    public DemandeDevis getDemandeDevis() {
        return this.demandeDevis;
    }

    public void setDemandeDevis(DemandeDevis demandeDevis) {
        this.demandeDevis = demandeDevis;
    }

    public ProduitCommandee demandeDevis(DemandeDevis demandeDevis) {
        this.setDemandeDevis(demandeDevis);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProduitCommandee)) {
            return false;
        }
        return getId() != null && getId().equals(((ProduitCommandee) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitCommandee{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", quantite=" + getQuantite() +
            "}";
    }
}
