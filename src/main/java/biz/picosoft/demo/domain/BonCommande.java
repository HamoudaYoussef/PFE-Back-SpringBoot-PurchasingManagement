package biz.picosoft.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

/**
 * Task entity.
 * @author The JHipster team.
 */
@Entity
//@Table(name = "bon_commande", schema = "achat")
@Table(name = "bon_commande")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BonCommande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(name = "dateboncommande")
    private LocalDate dateboncommande;

    @Column(name = "reference")
    private String reference;

    @Column(name = "quantite")
    private Long quantite;

    @Column(name = "prixunitaire")
    private Long prixunitaire;

    @Column(name = "totalht")
    private Long totalht;

    @Column(name = "tva")
    private Long tva;

    @Column(name = "totalttc")
    private Long totalttc;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bonCcmmande")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "paiements", "bonlivraisons", "bonCcmmande" }, allowSetters = true)
    private Set<Facture> factures = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "boncommandes", "fournisseur", "demandeachat", "produit" }, allowSetters = true)
    private Offre offre;

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

    public Long getQuantite() {
        return this.quantite;
    }

    public BonCommande quantite(Long quantite) {
        this.setQuantite(quantite);
        return this;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public Long getPrixunitaire() {
        return this.prixunitaire;
    }

    public BonCommande prixunitaire(Long prixunitaire) {
        this.setPrixunitaire(prixunitaire);
        return this;
    }

    public void setPrixunitaire(Long prixunitaire) {
        this.prixunitaire = prixunitaire;
    }

    public Long getTotalht() {
        return this.totalht;
    }

    public BonCommande totalht(Long totalht) {
        this.setTotalht(totalht);
        return this;
    }

    public void setTotalht(Long totalht) {
        this.totalht = totalht;
    }

    public Long getTva() {
        return this.tva;
    }

    public BonCommande tva(Long tva) {
        this.setTva(tva);
        return this;
    }

    public void setTva(Long tva) {
        this.tva = tva;
    }

    public Long getTotalttc() {
        return this.totalttc;
    }

    public BonCommande totalttc(Long totalttc) {
        this.setTotalttc(totalttc);
        return this;
    }

    public void setTotalttc(Long totalttc) {
        this.totalttc = totalttc;
    }

    public Set<Facture> getFactures() {
        return this.factures;
    }

    public void setFactures(Set<Facture> factures) {
        if (this.factures != null) {
            this.factures.forEach(i -> i.setBonCcmmande(null));
        }
        if (factures != null) {
            factures.forEach(i -> i.setBonCcmmande(this));
        }
        this.factures = factures;
    }

    public BonCommande factures(Set<Facture> factures) {
        this.setFactures(factures);
        return this;
    }

    public BonCommande addFactures(Facture facture) {
        this.factures.add(facture);
        facture.setBonCcmmande(this);
        return this;
    }

    public BonCommande removeFactures(Facture facture) {
        this.factures.remove(facture);
        facture.setBonCcmmande(null);
        return this;
    }

    public Offre getOffre() {
        return this.offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public BonCommande offre(Offre offre) {
        this.setOffre(offre);
        return this;
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
            ", quantite=" + getQuantite() +
            ", prixunitaire=" + getPrixunitaire() +
            ", totalht=" + getTotalht() +
            ", tva=" + getTva() +
            ", totalttc=" + getTotalttc() +
            "}";
    }
}
