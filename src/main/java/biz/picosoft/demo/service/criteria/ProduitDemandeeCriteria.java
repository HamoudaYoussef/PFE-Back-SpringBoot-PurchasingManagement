package biz.picosoft.demo.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * e all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /produit-demandees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitDemandeeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter demandeAchatId;
    private StringFilter nom;

    private StringFilter description;

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getQuantite() {
        return quantite;
    }

    public void setQuantite(LongFilter quantite) {
        this.quantite = quantite;
    }

    private LongFilter quantite;

    private Boolean distinct;

    public ProduitDemandeeCriteria() {}

    public ProduitDemandeeCriteria(ProduitDemandeeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.demandeAchatId = other.demandeAchatId == null ? null : other.demandeAchatId.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.quantite = other.quantite == null ? null : other.quantite.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProduitDemandeeCriteria copy() {
        return new ProduitDemandeeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getDemandeAchatId() {
        return demandeAchatId;
    }

    public LongFilter demandeAchatId() {
        if (demandeAchatId == null) {
            demandeAchatId = new LongFilter();
        }
        return demandeAchatId;
    }

    public void setDemandeAchatId(LongFilter demandeAchatId) {
        this.demandeAchatId = demandeAchatId;
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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProduitDemandeeCriteria that = (ProduitDemandeeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(demandeAchatId, that.demandeAchatId) &&
            Objects.equals(nom, that.nom) &&
                    Objects.equals(description, that.description) &&
                    Objects.equals(quantite, that.quantite) &&
                    Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, demandeAchatId, nom,description,quantite, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitDemandeeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (demandeAchatId != null ? "demandeAchatId=" + demandeAchatId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
