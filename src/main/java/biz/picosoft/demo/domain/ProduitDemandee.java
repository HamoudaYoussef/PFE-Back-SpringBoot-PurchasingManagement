package biz.picosoft.demo.domain;


import javax.persistence.*;
import java.io.Serializable;

/**
 * A ProduitDemandee.
 */
@Entity
@Table(name = "produit_demandee", schema = "achat")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitDemandee implements Serializable {

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

    @Column(name = "quantite")
    private Long quantite;

    @Column(name = "img")
    private String img;

    @ManyToOne(fetch = FetchType.EAGER)
    private Produit produit;

    @ManyToOne(fetch = FetchType.EAGER)
    private DemandeAchat demandeAchat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProduitDemandee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public ProduitDemandee description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom() {
        return this.nom;
    }

    public ProduitDemandee nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getQuantite() {
        return this.quantite;
    }

    public ProduitDemandee quantite(Long quantite) {
        this.setQuantite(quantite);
        return this;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public String getImg() {
        return this.img;
    }

    public ProduitDemandee img(String img) {
        this.setImg(img);
        return this;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Produit getProduit() {
        return this.produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public ProduitDemandee produit(Produit produit) {
        this.setProduit(produit);
        return this;
    }

    public DemandeAchat getDemandeAchat() {
        return this.demandeAchat;
    }

    public void setDemandeAchat(DemandeAchat demandeAchat) {
        this.demandeAchat = demandeAchat;
    }

    public ProduitDemandee demandeAchat(DemandeAchat demandeAchat) {
        this.setDemandeAchat(demandeAchat);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProduitDemandee)) {
            return false;
        }
        return getId() != null && getId().equals(((ProduitDemandee) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitDemandee{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", nom='" + getNom() + "'" +
            ", quantite=" + getQuantite() +
            ", img='" + getImg() + "'" +
            "}";
    }
}
