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
 * A DemandeDevis.
 */
@Entity
//@Table(name = "demande_devis", schema = "achat")
@Table(name = "demande_devis")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DemandeDevis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "demandeDevis")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "offres", "demandeDevis" }, allowSetters = true)
    private Set<Produit> produits = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "demandeDevis")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "offres", "demandeDevis" }, allowSetters = true)
    private Set<DemandeAchat> demandesachats = new HashSet<>();

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

    public Set<Produit> getProduits() {
        return this.produits;
    }

    public void setProduits(Set<Produit> produits) {
        if (this.produits != null) {
            this.produits.forEach(i -> i.setDemandedevis(null));
        }
        if (produits != null) {
            produits.forEach(i -> i.setDemandedevis(this));
        }
        this.produits = produits;
    }

    public DemandeDevis produits(Set<Produit> produits) {
        this.setProduits(produits);
        return this;
    }

    public DemandeDevis addProduits(Produit produit) {
        this.produits.add(produit);
        produit.setDemandedevis(this);
        return this;
    }

    public DemandeDevis removeProduits(Produit produit) {
        this.produits.remove(produit);
        produit.setDemandedevis(null);
        return this;
    }

    public Set<DemandeAchat> getDemandesachats() {
        return this.demandesachats;
    }

    public void setDemandesachats(Set<DemandeAchat> demandeAchats) {
        if (this.demandesachats != null) {
            this.demandesachats.forEach(i -> i.setDemandedevis(null));
        }
        if (demandeAchats != null) {
            demandeAchats.forEach(i -> i.setDemandedevis(this));
        }
        this.demandesachats = demandeAchats;
    }

    public DemandeDevis demandesachats(Set<DemandeAchat> demandeAchats) {
        this.setDemandesachats(demandeAchats);
        return this;
    }

    public DemandeDevis addDemandesachat(DemandeAchat demandeAchat) {
        this.demandesachats.add(demandeAchat);
        demandeAchat.setDemandedevis(this);
        return this;
    }

    public DemandeDevis removeDemandesachat(DemandeAchat demandeAchat) {
        this.demandesachats.remove(demandeAchat);
        demandeAchat.setDemandedevis(null);
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
            "}";
    }
}
