package biz.picosoft.demo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**

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

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    private StringFilter nom;


    private FloatFilter prix;

    private LocalDateFilter dateoffre;


    private StringFilter description;

    public StringFilter getReferenceoffre() {
        return referenceoffre;
    }

    public void setReferenceoffre(StringFilter referenceoffre) {
        this.referenceoffre = referenceoffre;
    }

    private StringFilter referenceoffre;


    private LongFilter boncommandeId;

    private LongFilter demandeachatId;

    private LongFilter fournisseurId;

    private Boolean distinct;

    public OffreCriteria() {}

    public OffreCriteria(OffreCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.prix = other.prix == null ? null : other.prix.copy();
        this.dateoffre = other.dateoffre == null ? null : other.dateoffre.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.referenceoffre = other.referenceoffre == null ? null : other.referenceoffre.copy();
        this.boncommandeId = other.boncommandeId == null ? null : other.boncommandeId.copy();
        this.demandeachatId = other.demandeachatId == null ? null : other.demandeachatId.copy();
        this.fournisseurId = other.fournisseurId == null ? null : other.fournisseurId.copy();
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

    public LongFilter getBoncommandeId() {
        return boncommandeId;
    }

    public LongFilter boncommandeId() {
        if (boncommandeId == null) {
            boncommandeId = new LongFilter();
        }
        return boncommandeId;
    }

    public void setBoncommandeId(LongFilter boncommandeId) {
        this.boncommandeId = boncommandeId;
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
                    Objects.equals(nom, that.nom) &&
                    Objects.equals(prix, that.prix) &&
            Objects.equals(dateoffre, that.dateoffre) &&
            Objects.equals(description, that.description) &&
                    Objects.equals(referenceoffre, that.referenceoffre) &&
                    Objects.equals(boncommandeId, that.boncommandeId) &&
            Objects.equals(demandeachatId, that.demandeachatId) &&
            Objects.equals(fournisseurId, that.fournisseurId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, prix, dateoffre, description, boncommandeId, demandeachatId, fournisseurId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OffreCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (prix != null ? "prix=" + prix + ", " : "") +
            (dateoffre != null ? "dateoffre=" + dateoffre + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (boncommandeId != null ? "boncommandeId=" + boncommandeId + ", " : "") +
            (demandeachatId != null ? "demandeachatId=" + demandeachatId + ", " : "") +
            (fournisseurId != null ? "fournisseurId=" + fournisseurId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
