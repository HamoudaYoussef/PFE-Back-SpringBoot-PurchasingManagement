package biz.picosoft.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Paiement.
 */
@Entity
//@Table(name = "paiement", schema = "achat")
@Table(name = "paiement")

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")

public class Paiement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "montanttotal")
    private Long montanttotal;

    @Column(name = "datepaiement")
    private LocalDate datepaiement;

    @Column(name = "methodepaiement")
    private String methodepaiement;

    @Column(name = "statuts")
    private String statuts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "paiements", "bonlivraisons", "bonCcmmande" }, allowSetters = true)
    private Facture facture;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Paiement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Long getMontanttotal() {
        return this.montanttotal;
    }

    public Paiement montanttotal(Long montanttotal) {
        this.setMontanttotal(montanttotal);
        return this;
    }

    public void setMontanttotal(Long montanttotal) {
        this.montanttotal = montanttotal;
    }

    public LocalDate getDatepaiement() {
        return this.datepaiement;
    }

    public Paiement datepaiement(LocalDate datepaiement) {
        this.setDatepaiement(datepaiement);
        return this;
    }

    public void setDatepaiement(LocalDate datepaiement) {
        this.datepaiement = datepaiement;
    }

    public String getMethodepaiement() {
        return this.methodepaiement;
    }

    public Paiement methodepaiement(String methodepaiement) {
        this.setMethodepaiement(methodepaiement);
        return this;
    }

    public void setMethodepaiement(String methodepaiement) {
        this.methodepaiement = methodepaiement;
    }

    public String getStatuts() {
        return this.statuts;
    }

    public Paiement statuts(String statuts) {
        this.setStatuts(statuts);
        return this;
    }

    public void setStatuts(String statuts) {
        this.statuts = statuts;
    }

    public Facture getFacture() {
        return this.facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public Paiement facture(Facture facture) {
        this.setFacture(facture);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paiement)) {
            return false;
        }
        return getId() != null && getId().equals(((Paiement) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paiement{" +
            "id=" + getId() +
            ", montanttotal=" + getMontanttotal() +
            ", datepaiement='" + getDatepaiement() + "'" +
            ", methodepaiement='" + getMethodepaiement() + "'" +
            ", statuts='" + getStatuts() + "'" +
            "}";
    }
}
