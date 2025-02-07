package biz.picosoft.demo.domain;



import javax.persistence.*;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DemandeDevis.
 */
@Entity
@Table(name = "demande_devis", schema = "achat")
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

    @Column(name = "datedemande")
    private LocalDate datedemande;

    @Column(name = "nom")
    private String nom;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Column(name = "reference")
    private String reference;

    @ManyToOne(fetch = FetchType.EAGER)
    private Fournisseur fournisseur;

    @ManyToOne(fetch = FetchType.LAZY)
    private DemandeAchat demandeAchat;

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

    public LocalDate getDatedemande() {
        return this.datedemande;
    }

    public DemandeDevis datedemande(LocalDate datedemande) {
        this.setDatedemande(datedemande);
        return this;
    }

    public void setDatedemande(LocalDate datedemande) {
        this.datedemande = datedemande;
    }

    public String getNom() {
        return this.nom;
    }

    public DemandeDevis nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
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
            ", datedemande='" + getDatedemande() + "'" +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
