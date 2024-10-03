package biz.picosoft.demo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.DoubleFilter;

/**
 * Criteria class for filtering ProduitCommandee entities.
 * This class is used in ProduitCommandeeQueryService to filter entities based on specified criteria.
 */
@ParameterObject
public class ProduitCommandeeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private StringFilter nom;
    private LongFilter quantite;
    private StringFilter description;
    private LongFilter produitId;

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    private Boolean distinct;


    public LongFilter getDemandeDevisId() {
        return demandeDevisId;
    }

    public void setDemandeDevisId(LongFilter demandeDevisId) {
        this.demandeDevisId = demandeDevisId;
    }

    private LongFilter demandeDevisId;

    public ProduitCommandeeCriteria() {}

    public ProduitCommandeeCriteria(ProduitCommandeeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.quantite = other.quantite == null ? null : other.quantite.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.produitId = other.produitId == null ? null : other.produitId.copy();
        this.demandeDevisId = other.demandeDevisId == null ? null : other.demandeDevisId.copy();
    }

    @Override
    public ProduitCommandeeCriteria copy() {
        return new ProduitCommandeeCriteria(this);
    }

    // Getters and Setters for filters

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public LongFilter getQuantite() {
        return quantite;
    }

    public void setQuantite(LongFilter quantite) {
        this.quantite = quantite;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getProduitId() {
        return produitId;
    }

    public void setProduitId(LongFilter produitId) {
        this.produitId = produitId;
    }


    // equals, hashCode and toString methods

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProduitCommandeeCriteria)) {
            return false;
        }
        final ProduitCommandeeCriteria that = (ProduitCommandeeCriteria) o;
        return (
                Objects.equals(id, that.id) &&
                        Objects.equals(nom, that.nom) &&
                        Objects.equals(quantite, that.quantite) &&
                        Objects.equals(description, that.description) &&
                        Objects.equals(produitId, that.produitId) &&
                        Objects.equals(demandeDevisId, that.demandeDevisId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, quantite, description, produitId, demandeDevisId);
    }

    @Override
    public String toString() {
        return "ProduitCommandeeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (quantite != null ? "quantite=" + quantite + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (produitId != null ? "produitId=" + produitId + ", " : "") +
                (demandeDevisId != null ? "commandeId=" + demandeDevisId + ", " : "") +
                "}";
    }
}
