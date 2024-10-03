package biz.picosoft.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * A ProduitDemandee.
 */
@Entity
@Table(name = "produit_demandee", schema = "achat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitDemandee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;
    @Column(name = "description")
    private String description;

    @Column(name = "nom")
    private String nom;
    @Column(name = "quantite")
    private Long quantite;


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Column(name = "img")
    private String img;
    public DemandeDevis getDemandeDevis() {
        return demandeDevis;
    }

    public void setDemandeDevis(DemandeDevis demandeDevis) {
        this.demandeDevis = demandeDevis;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "produitDemandees" }, allowSetters = true)
    private DemandeDevis demandeDevis;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "offres", "demandedevis" }, allowSetters = true)
    private DemandeAchat demandeAchat;



    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProduitDemandee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DemandeAchat getDemandeAchat() {
        return this.demandeAchat;
    }

    public void setDemandeAchat(DemandeAchat demandeAchat) {
        this.demandeAchat = demandeAchat;
    }

    public ProduitDemandee demandeAchat(DemandeAchat demandeAchat) {
        this.setDemandeAchat(demandeAchat);
        return this;
    }


    public ProduitDemandee demandeDevis(DemandeDevis demandeDevis) {
        this.setDemandeDevis(demandeDevis);
        return this;
    }





    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProduitDemandee)) {
            return false;
        }
        return getId() != null && getId().equals(((ProduitDemandee) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitDemandee{" +
            "id=" + getId() +
            "}";
    }
}
