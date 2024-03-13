package biz.picosoft.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProduitOffert.
 */
@Entity
//@Table(name = "produit_offert", schema = "achat")
@Table(name = "produit_offert")

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")

public class ProduitOffert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "quantiteofferte")
    private Long quantiteofferte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "boncommandes", "fournisseur", "demandeachat", "produit" }, allowSetters = true)
    private Offre offre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "offres" }, allowSetters = true)
    private Produit produit;

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


    public Long getQuantiteofferte() {
        return this.quantiteofferte;
    }

    public ProduitOffert quantiteofferte(Long quantiteofferte) {
        this.setQuantiteofferte(quantiteofferte);
        return this;
    }

    public void setQuantiteofferte(Long quantiteofferte) {
        this.quantiteofferte = quantiteofferte;
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
            ", quantiteofferte=" + getQuantiteofferte() +
            "}";
    }
}
