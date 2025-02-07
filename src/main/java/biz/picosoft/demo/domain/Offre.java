package biz.picosoft.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Offre.
 */
@Entity
@Table(name = "offre", schema = "achat")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Offre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prix")
    private Float prix;

    @Column(name = "dateoffre")
    private LocalDate dateoffre;

    @Column(name = "description")
    private String description;

    @Column(name = "referenceoffre")
    private String referenceoffre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "fournisseur", "demandeAchat" }, allowSetters = true)
    private DemandeDevis demandeDevis;

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

    public String getNom() {
        return this.nom;
    }

    public Offre nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    public String getReferenceoffre() {
        return this.referenceoffre;
    }

    public Offre referenceoffre(String referenceoffre) {
        this.setReferenceoffre(referenceoffre);
        return this;
    }

    public void setReferenceoffre(String referenceoffre) {
        this.referenceoffre = referenceoffre;
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

    public DemandeDevis getDemandeDevis() {
        return this.demandeDevis;
    }

    public void setDemandeDevis(DemandeDevis demandeDevis) {
        this.demandeDevis = demandeDevis;
    }

    public Offre demandeDevis(DemandeDevis demandeDevis) {
        this.setDemandeDevis(demandeDevis);
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
            ", nom='" + getNom() + "'" +
            ", prix=" + getPrix() +
            ", dateoffre='" + getDateoffre() + "'" +
            ", description='" + getDescription() + "'" +
            ", referenceoffre='" + getReferenceoffre() + "'" +
            "}";
    }
}
