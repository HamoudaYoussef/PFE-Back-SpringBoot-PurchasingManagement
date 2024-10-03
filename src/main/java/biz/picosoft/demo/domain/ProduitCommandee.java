package biz.picosoft.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "produit_commandee", schema = "achat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProduitCommandee implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "description")
    private String description;
    @Column(name = "quantite")
    private Long quantite;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public DemandeDevis getDemandeDevis() {
        return demandeDevis;
    }

    public void setDemandeDevis(DemandeDevis demandeDevis) {
        this.demandeDevis = demandeDevis;
    }




    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JsonIgnoreProperties(value = { "produitCommandees" }, allowSetters = true)
    private Produit produit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "produitCommandees" }, allowSetters = true)
    private DemandeDevis demandeDevis;
}
