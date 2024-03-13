package biz.picosoft.demo.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link biz.picosoft.demo.domain.Offre} entity. This class is used
 * in {@link biz.picosoft.demo.web.rest.OffreResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /offres?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OffreCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;


    private FloatFilter prix;

    private LocalDateFilter dateoffre;

    private StringFilter description;

    private LongFilter boncommandesId;

    private LongFilter fournisseurId;

    private LongFilter demandeachatId;

    private LongFilter produitId;

    private Boolean distinct;

    public OffreCriteria() {}

    public OffreCriteria(OffreCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.prix = other.prix == null ? null : other.prix.copy();
        this.dateoffre = other.dateoffre == null ? null : other.dateoffre.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.boncommandesId = other.boncommandesId == null ? null : other.boncommandesId.copy();
        this.fournisseurId = other.fournisseurId == null ? null : other.fournisseurId.copy();
        this.demandeachatId = other.demandeachatId == null ? null : other.demandeachatId.copy();
        this.produitId = other.produitId == null ? null : other.produitId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public OffreCriteria copy() {
        return new OffreCriteria(this);
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



    public FloatFilter getPrix() {
        return prix;
    }

    public FloatFilter prix() {
        if (prix == null) {
            prix = new FloatFilter();
        }
        return prix;
    }

    public void setPrix(FloatFilter prix) {
        this.prix = prix;
    }

    public LocalDateFilter getDateoffre() {
        return dateoffre;
    }

    public LocalDateFilter dateoffre() {
        if (dateoffre == null) {
            dateoffre = new LocalDateFilter();
        }
        return dateoffre;
    }

    public void setDateoffre(LocalDateFilter dateoffre) {
        this.dateoffre = dateoffre;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getBoncommandesId() {
        return boncommandesId;
    }

    public LongFilter boncommandesId() {
        if (boncommandesId == null) {
            boncommandesId = new LongFilter();
        }
        return boncommandesId;
    }

    public void setBoncommandesId(LongFilter boncommandesId) {
        this.boncommandesId = boncommandesId;
    }

    public LongFilter getFournisseurId() {
        return fournisseurId;
    }

    public LongFilter fournisseurId() {
        if (fournisseurId == null) {
            fournisseurId = new LongFilter();
        }
        return fournisseurId;
    }

    public void setFournisseurId(LongFilter fournisseurId) {
        this.fournisseurId = fournisseurId;
    }

    public LongFilter getDemandeachatId() {
        return demandeachatId;
    }

    public LongFilter demandeachatId() {
        if (demandeachatId == null) {
            demandeachatId = new LongFilter();
        }
        return demandeachatId;
    }

    public void setDemandeachatId(LongFilter demandeachatId) {
        this.demandeachatId = demandeachatId;
    }

    public LongFilter getProduitId() {
        return produitId;
    }

    public LongFilter produitId() {
        if (produitId == null) {
            produitId = new LongFilter();
        }
        return produitId;
    }

    public void setProduitId(LongFilter produitId) {
        this.produitId = produitId;
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
        final OffreCriteria that = (OffreCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(prix, that.prix) &&
            Objects.equals(dateoffre, that.dateoffre) &&
            Objects.equals(description, that.description) &&
            Objects.equals(boncommandesId, that.boncommandesId) &&
            Objects.equals(fournisseurId, that.fournisseurId) &&
            Objects.equals(demandeachatId, that.demandeachatId) &&
            Objects.equals(produitId, that.produitId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, prix, dateoffre, description, boncommandesId, fournisseurId, demandeachatId, produitId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OffreCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (prix != null ? "prix=" + prix + ", " : "") +
            (dateoffre != null ? "dateoffre=" + dateoffre + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (boncommandesId != null ? "boncommandesId=" + boncommandesId + ", " : "") +
            (fournisseurId != null ? "fournisseurId=" + fournisseurId + ", " : "") +
            (demandeachatId != null ? "demandeachatId=" + demandeachatId + ", " : "") +
            (produitId != null ? "produitId=" + produitId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
