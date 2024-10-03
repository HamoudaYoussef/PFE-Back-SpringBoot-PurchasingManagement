package biz.picosoft.demo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link biz.picosoft.demo.domain.Produit} entity. This class is used
 * the Http GET request parameters.
 * For example, a valid request could be:
 * {@code /produits?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 */
@ParameterObject
public class ProduitCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private StringFilter nom;

    private LongFilter quantite;


    private StringFilter couleur;

    private Boolean distinct;


    public ProduitCriteria() {}

    public ProduitCriteria(ProduitCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.quantite = other.quantite == null ? null : other.quantite.copy();
        this.couleur = other.couleur == null ? null : other.couleur.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProduitCriteria copy() {
        return new ProduitCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
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


    public StringFilter getCouleur() {
        return couleur;
    }

    public void setCouleur(StringFilter couleur) {
        this.couleur = couleur;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProduitCriteria)) {
            return false;
        }
        final ProduitCriteria that = (ProduitCriteria) o;
        return (
                Objects.equals(id, that.id) &&
                        Objects.equals(description, that.description) &&
                        Objects.equals(nom, that.nom) &&
                        Objects.equals(quantite, that.quantite) &&
                        Objects.equals(couleur, that.couleur) &&
                        Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                description,
                nom,
                quantite,
                couleur,
                distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (quantite != null ? "quantite=" + quantite + ", " : "") +
                (couleur != null ? "couleur=" + couleur + ", " : "") +
                (distinct != null ? "distinct=" + distinct + ", " : "") +
                "}";
    }
}
