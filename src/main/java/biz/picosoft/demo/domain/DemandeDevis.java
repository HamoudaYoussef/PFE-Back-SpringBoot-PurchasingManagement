package biz.picosoft.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DemandeDevis.
 */
@Entity
@Table(name = "demande_devis", schema = "achat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemandeDevis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    public LocalDate getDatedemande() {
        return datedemande;
    }

    public void setDatedemande(LocalDate datedemande) {
        this.datedemande = datedemande;
    }

    @Column(name = "datedemande")
    private LocalDate datedemande;

    @Column(name = "quantite")
    private Long quantite;

    public String getNom() {
            return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Column(name = "nom")
    private String nom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "offres", "demandedevis", "produit" }, allowSetters = true)
    private Fournisseur fournisseur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "offres", "demandedevis" }, allowSetters = true)
    private DemandeAchat demandeAchat;

    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    @OneToOne(mappedBy = "demandeDevis", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    @JsonIgnoreProperties(value = { "demandeDevis" }, allowSetters = true)
    private Offre offre;

    public Set<ProduitDemandee> getProduitsDemandes() {
        return produitDemandees;
    }

    public void setProduitsDemandes(Set<ProduitDemandee> produitDemandees) {
        this.produitDemandees = produitDemandees;
    }

    @OneToMany(mappedBy = "demandeDevis", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = { "demandeDevis" }, allowSetters = true)
    private Set<ProduitDemandee> produitDemandees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DemandeDevis id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public DemandeDevis description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getQuantite() {
        return this.quantite;
    }

    public DemandeDevis quantite(Long quantite) {
        this.setQuantite(quantite);
        return this;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }



    public Fournisseur getFournisseur() {
        return this.fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public DemandeDevis fournisseur(Fournisseur fournisseur) {
        this.setFournisseur(fournisseur);
        return this;
    }

    public DemandeAchat getDemandeAchat() {
        return this.demandeAchat;
    }

    public void setDemandeAchat(DemandeAchat demandeAchat) {
        this.demandeAchat = demandeAchat;
    }

    public DemandeDevis demandeAchat(DemandeAchat demandeAchat) {
        this.setDemandeAchat(demandeAchat);
        return this;
    }
    public DemandeDevis produitsDemandees(Set<ProduitDemandee> produitDemandees) {
        this.setProduitsDemandes(produitDemandees);
        return this;
    }

    public DemandeDevis addProduitDemandee(ProduitDemandee produitDemandee) {
        this.produitDemandees.add(produitDemandee);
        produitDemandee.setDemandeDevis(this);
        return this;
    }

    public DemandeDevis removeProduitDemandee(ProduitDemandee produitDemandee) {
        this.produitDemandees.remove(produitDemandee);
        produitDemandee.setDemandeDevis(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandeDevis)) {
            return false;
        }
        return getId() != null && getId().equals(((DemandeDevis) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeDevis{" +
                "id=" + getId() +
                ", description='" + getDescription() + "'" +
                ", quantite=" + getQuantite() +
                "}";
    }
}
