package biz.picosoft.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProduitCommandee.
 */
@Entity
//@Table(name = "produit_commandee", schema = "achat")
@Table(name = "produit_commandee")

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")

public class ProduitCommandee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;





    @Column(name = "dateboncommande")
    private LocalDate dateboncommande;

    @Column(name = "quantitecommandee")
    private Long quantitecommandee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "factures", "offre" }, allowSetters = true)
    private BonCommande boncommande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "offres" }, allowSetters = true)
    private Produit produit;

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





    public LocalDate getDateboncommande() {
        return this.dateboncommande;
    }

    public ProduitCommandee dateboncommande(LocalDate dateboncommande) {
        this.setDateboncommande(dateboncommande);
        return this;
    }

    public void setDateboncommande(LocalDate dateboncommande) {
        this.dateboncommande = dateboncommande;
    }

    public Long getQuantitecommandee() {
        return this.quantitecommandee;
    }

    public ProduitCommandee quantitecommandee(Long quantitecommandee) {
        this.setQuantitecommandee(quantitecommandee);
        return this;
    }

    public void setQuantitecommandee(Long quantitecommandee) {
        this.quantitecommandee = quantitecommandee;
    }

    public BonCommande getBoncommande() {
        return this.boncommande;
    }

    public void setBoncommande(BonCommande bonCommande) {
        this.boncommande = bonCommande;
    }

    public ProduitCommandee boncommande(BonCommande bonCommande) {
        this.setBoncommande(bonCommande);
        return this;
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
            ", dateboncommande='" + getDateboncommande() + "'" +
            ", quantitecommandee=" + getQuantitecommandee() +
            "}";
    }
}
