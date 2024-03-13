package biz.picosoft.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * not an ignored comment
 */
@Entity
//@Table(name = "offre", schema = "achat")
@Table(name = "offre")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

@SuppressWarnings("common-java:DuplicatedBlocks")
public class Offre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "prix")
    private Float prix;

    @Column(name = "dateoffre")
    private LocalDate dateoffre;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "offre")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "factures", "offre" }, allowSetters = true)
    private Set<BonCommande> boncommandes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Fournisseur fournisseur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "offres" }, allowSetters = true)
    private DemandeAchat demandeachat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "offres" }, allowSetters = true)
    private Produit produit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Offre id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Float getPrix() {
        return this.prix;
    }

    public Offre prix(Float prix) {
        this.setPrix(prix);
        return this;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public LocalDate getDateoffre() {
        return this.dateoffre;
    }

    public Offre dateoffre(LocalDate dateoffre) {
        this.setDateoffre(dateoffre);
        return this;
    }

    public void setDateoffre(LocalDate dateoffre) {
        this.dateoffre = dateoffre;
    }

    public String getDescription() {
        return this.description;
    }

    public Offre description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<BonCommande> getBoncommandes() {
        return this.boncommandes;
    }

    public void setBoncommandes(Set<BonCommande> bonCommandes) {
        if (this.boncommandes != null) {
            this.boncommandes.forEach(i -> i.setOffre(null));
        }
        if (bonCommandes != null) {
            bonCommandes.forEach(i -> i.setOffre(this));
        }
        this.boncommandes = bonCommandes;
    }

    public Offre boncommandes(Set<BonCommande> bonCommandes) {
        this.setBoncommandes(bonCommandes);
        return this;
    }

    public Offre addBoncommandes(BonCommande bonCommande) {
        this.boncommandes.add(bonCommande);
        bonCommande.setOffre(this);
        return this;
    }

    public Offre removeBoncommandes(BonCommande bonCommande) {
        this.boncommandes.remove(bonCommande);
        bonCommande.setOffre(null);
        return this;
    }

    public Fournisseur getFournisseur() {
        return this.fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Offre fournisseur(Fournisseur fournisseur) {
        this.setFournisseur(fournisseur);
        return this;
    }

    public DemandeAchat getDemandeachat() {
        return this.demandeachat;
    }

    public void setDemandeachat(DemandeAchat demandeAchat) {
        this.demandeachat = demandeAchat;
    }

    public Offre demandeachat(DemandeAchat demandeAchat) {
        this.setDemandeachat(demandeAchat);
        return this;
    }

    public Produit getProduit() {
        return this.produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Offre produit(Produit produit) {
        this.setProduit(produit);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Offre)) {
            return false;
        }
        return getId() != null && getId().equals(((Offre) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Offre{" +
            "id=" + getId() +
            ", prix=" + getPrix() +
            ", dateoffre='" + getDateoffre() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
