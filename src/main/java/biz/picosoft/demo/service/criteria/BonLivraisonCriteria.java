package biz.picosoft.demo.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**

 * For example the following could be a valid request:
 * {@code /bon-livraisons?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BonLivraisonCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter numerobonlivraison;

    private LocalDateFilter datelivraion;

    private LongFilter boncommandeId;

    private Boolean distinct;

    public BonLivraisonCriteria() {}

    public BonLivraisonCriteria(BonLivraisonCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.numerobonlivraison = other.numerobonlivraison == null ? null : other.numerobonlivraison.copy();
        this.datelivraion = other.datelivraion == null ? null : other.datelivraion.copy();
        this.boncommandeId = other.boncommandeId == null ? null : other.boncommandeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BonLivraisonCriteria copy() {
        return new BonLivraisonCriteria(this);
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

    public LongFilter getNumerobonlivraison() {
        return numerobonlivraison;
    }

    public LongFilter numerobonlivraison() {
        if (numerobonlivraison == null) {
            numerobonlivraison = new LongFilter();
        }
        return numerobonlivraison;
    }

    public void setNumerobonlivraison(LongFilter numerobonlivraison) {
        this.numerobonlivraison = numerobonlivraison;
    }

    public LocalDateFilter getDatelivraion() {
        return datelivraion;
    }

    public LocalDateFilter datelivraion() {
        if (datelivraion == null) {
            datelivraion = new LocalDateFilter();
        }
        return datelivraion;
    }

    public void setDatelivraion(LocalDateFilter datelivraion) {
        this.datelivraion = datelivraion;
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
        final BonLivraisonCriteria that = (BonLivraisonCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(numerobonlivraison, that.numerobonlivraison) &&
            Objects.equals(datelivraion, that.datelivraion) &&
            Objects.equals(boncommandeId, that.boncommandeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numerobonlivraison, datelivraion, boncommandeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BonLivraisonCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (numerobonlivraison != null ? "numerobonlivraison=" + numerobonlivraison + ", " : "") +
            (datelivraion != null ? "datelivraion=" + datelivraion + ", " : "") +
            (boncommandeId != null ? "boncommandeId=" + boncommandeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
