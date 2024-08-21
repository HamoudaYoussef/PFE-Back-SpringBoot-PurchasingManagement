package biz.picosoft.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BonLivraison.
 */
@Entity
@Table(name = "bon_livraison", schema = "achat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BonLivraison implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "numerobonlivraison")
    private Long numerobonlivraison;

    @Column(name = "datelivraion")
    private LocalDate datelivraion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "bonlivraisons", "factures", "offre", "demandedevis" }, allowSetters = true)
    private BonCommande boncommande;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BonLivraison id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumerobonlivraison() {
        return this.numerobonlivraison;
    }

    public BonLivraison numerobonlivraison(Long numerobonlivraison) {
        this.setNumerobonlivraison(numerobonlivraison);
        return this;
    }

    public void setNumerobonlivraison(Long numerobonlivraison) {
        this.numerobonlivraison = numerobonlivraison;
    }

    public LocalDate getDatelivraion() {
        return this.datelivraion;
    }

    public BonLivraison datelivraion(LocalDate datelivraion) {
        this.setDatelivraion(datelivraion);
        return this;
    }

    public void setDatelivraion(LocalDate datelivraion) {
        this.datelivraion = datelivraion;
    }

    public BonCommande getBoncommande() {
        return this.boncommande;
    }

    public void setBoncommande(BonCommande bonCommande) {
        this.boncommande = bonCommande;
    }

    public BonLivraison boncommande(BonCommande bonCommande) {
        this.setBoncommande(bonCommande);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BonLivraison)) {
            return false;
        }
        return getId() != null && getId().equals(((BonLivraison) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BonLivraison{" +
            "id=" + getId() +
            ", numerobonlivraison=" + getNumerobonlivraison() +
            ", datelivraion='" + getDatelivraion() + "'" +
            "}";
    }
}
