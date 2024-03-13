package biz.picosoft.demo.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link biz.picosoft.demo.domain.DemandeAchat} entity. This class is used
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /demande-achats?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemandeAchatCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;


    private LocalDateFilter datedemande;
    private LocalDateFilter datebesoin;

    private StringFilter statut;


    private StringFilter description;

    private LongFilter offresId;

    private Boolean distinct;

    public DemandeAchatCriteria() {}

    public DemandeAchatCriteria(DemandeAchatCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.datedemande = other.datedemande == null ? null : other.datedemande.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.offresId = other.offresId == null ? null : other.offresId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DemandeAchatCriteria copy() {
        return new DemandeAchatCriteria(this);
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



    public LocalDateFilter getDatedemande() {
        return datedemande;
    }

    public LocalDateFilter datedemande() {
        if (datedemande == null) {
            datedemande = new LocalDateFilter();
        }
        return datedemande;
    }

    public void setDatedemande(LocalDateFilter datedemande) {
        this.datedemande = datedemande;
    }

    public LocalDateFilter getDatebesoin() {
        return datebesoin;
    }

    public LocalDateFilter datebesoin() {
        if (datebesoin == null) {
            datebesoin = new LocalDateFilter();
        }
        return datebesoin;
    }

    public void setDatebesoin(LocalDateFilter datebesoin) {
        this.datebesoin = datebesoin;
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

    public StringFilter getStatut() {
        return statut;
    }

    public StringFilter statut() {
        if (statut == null) {
            statut = new StringFilter();
        }
        return statut;
    }

    public void setStatut(StringFilter statut) {
        this.statut = statut;
    }

    public LongFilter getOffresId() {
        return offresId;
    }

    public LongFilter offresId() {
        if (offresId == null) {
            offresId = new LongFilter();
        }
        return offresId;
    }

    public void setOffresId(LongFilter offresId) {
        this.offresId = offresId;
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
        final DemandeAchatCriteria that = (DemandeAchatCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(datedemande, that.datedemande) &&
            Objects.equals(description, that.description) &&
            Objects.equals(offresId, that.offresId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, datedemande, description, offresId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeAchatCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (datedemande != null ? "datedemande=" + datedemande + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (offresId != null ? "offresId=" + offresId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
