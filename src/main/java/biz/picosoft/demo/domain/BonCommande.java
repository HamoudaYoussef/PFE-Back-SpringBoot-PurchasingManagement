package biz.picosoft.demo.domain;

import biz.picosoft.demo.domain.ennumeration.InfoPaiement;
import biz.picosoft.demo.domain.ennumeration.TypeLivraison;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Task entity.
 * @author The JHipster team.
 */
@Entity
@Table(name = "bon_commande", schema = "achat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BonCommande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "dateboncommande")
    private LocalDate dateboncommande;

    @Column(name = "reference")
    private String reference;

    public String getAdresselivraison() {
        return adresselivraison;
    }

    public void setAdresselivraison(String adresselivraison) {
        this.adresselivraison = adresselivraison;
    }

    @Column(name = "adresselivraison")
    private String adresselivraison;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Column(name = "numero")
    private String numero;


    @Column(name = "delailivraison")
    private LocalDate delailivraison;
    @Column(name = "typelivraison")
    @Enumerated(EnumType.STRING)
    private TypeLivraison typelivraison;






    public LocalDate getDelailivraison() {
        return delailivraison;
    }

    public void setDelailivraison(LocalDate delailivraison) {
        this.delailivraison = delailivraison;
    }

    public TypeLivraison getTypelivraison() {
        return typelivraison;
    }

    public void setTypelivraison(TypeLivraison typelivraison) {
        this.typelivraison = typelivraison;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BonCommande id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateboncommande() {
        return this.dateboncommande;
    }

    public BonCommande dateboncommande(LocalDate dateboncommande) {
        this.setDateboncommande(dateboncommande);
        return this;
    }

    public void setDateboncommande(LocalDate dateboncommande) {
        this.dateboncommande = dateboncommande;
    }

    public String getReference() {
        return this.reference;
    }

    public BonCommande reference(String reference) {
        this.setReference(reference);
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }



    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BonCommande)) {
            return false;
        }
        return getId() != null && getId().equals(((BonCommande) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BonCommande{" +
                "id=" + getId() +
                ", dateboncommande='" + getDateboncommande() + "'" +
                ", reference='" + getReference() + "'" +
                "}";
    }
}