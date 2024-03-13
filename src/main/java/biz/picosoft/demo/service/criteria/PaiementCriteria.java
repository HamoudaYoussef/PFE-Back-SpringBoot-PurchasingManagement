package biz.picosoft.demo.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link biz.picosoft.demo.domain.Paiement} entity. This class is used
 * in {@link biz.picosoft.demo.web.rest.PaiementResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /paiements?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaiementCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;


    private LongFilter montanttotal;

    private LocalDateFilter datepaiement;

    private StringFilter methodepaiement;

    private StringFilter statuts;

    private LongFilter factureId;

    private Boolean distinct;

    public PaiementCriteria() {}

    public PaiementCriteria(PaiementCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.montanttotal = other.montanttotal == null ? null : other.montanttotal.copy();
        this.datepaiement = other.datepaiement == null ? null : other.datepaiement.copy();
        this.methodepaiement = other.methodepaiement == null ? null : other.methodepaiement.copy();
        this.statuts = other.statuts == null ? null : other.statuts.copy();
        this.factureId = other.factureId == null ? null : other.factureId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PaiementCriteria copy() {
        return new PaiementCriteria(this);
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



    public LongFilter getMontanttotal() {
        return montanttotal;
    }

    public LongFilter montanttotal() {
        if (montanttotal == null) {
            montanttotal = new LongFilter();
        }
        return montanttotal;
    }

    public void setMontanttotal(LongFilter montanttotal) {
        this.montanttotal = montanttotal;
    }

    public LocalDateFilter getDatepaiement() {
        return datepaiement;
    }

    public LocalDateFilter datepaiement() {
        if (datepaiement == null) {
            datepaiement = new LocalDateFilter();
        }
        return datepaiement;
    }

    public void setDatepaiement(LocalDateFilter datepaiement) {
        this.datepaiement = datepaiement;
    }

    public StringFilter getMethodepaiement() {
        return methodepaiement;
    }

    public StringFilter methodepaiement() {
        if (methodepaiement == null) {
            methodepaiement = new StringFilter();
        }
        return methodepaiement;
    }

    public void setMethodepaiement(StringFilter methodepaiement) {
        this.methodepaiement = methodepaiement;
    }

    public StringFilter getStatuts() {
        return statuts;
    }

    public StringFilter statuts() {
        if (statuts == null) {
            statuts = new StringFilter();
        }
        return statuts;
    }

    public void setStatuts(StringFilter statuts) {
        this.statuts = statuts;
    }

    public LongFilter getFactureId() {
        return factureId;
    }

    public LongFilter factureId() {
        if (factureId == null) {
            factureId = new LongFilter();
        }
        return factureId;
    }

    public void setFactureId(LongFilter factureId) {
        this.factureId = factureId;
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
        final PaiementCriteria that = (PaiementCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(montanttotal, that.montanttotal) &&
            Objects.equals(datepaiement, that.datepaiement) &&
            Objects.equals(methodepaiement, that.methodepaiement) &&
            Objects.equals(statuts, that.statuts) &&
            Objects.equals(factureId, that.factureId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, montanttotal, datepaiement, methodepaiement, statuts, factureId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaiementCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (montanttotal != null ? "montanttotal=" + montanttotal + ", " : "") +
            (datepaiement != null ? "datepaiement=" + datepaiement + ", " : "") +
            (methodepaiement != null ? "methodepaiement=" + methodepaiement + ", " : "") +
            (statuts != null ? "statuts=" + statuts + ", " : "") +
            (factureId != null ? "factureId=" + factureId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
