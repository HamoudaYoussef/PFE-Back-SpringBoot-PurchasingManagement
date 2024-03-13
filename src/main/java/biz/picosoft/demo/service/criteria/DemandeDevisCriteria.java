package biz.picosoft.demo.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /demande-devis?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemandeDevisCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private LongFilter produitsId;

    private LongFilter demandesachatId;

    private Boolean distinct;

    public DemandeDevisCriteria() {}

    public DemandeDevisCriteria(DemandeDevisCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.produitsId = other.produitsId == null ? null : other.produitsId.copy();
        this.demandesachatId = other.demandesachatId == null ? null : other.demandesachatId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DemandeDevisCriteria copy() {
        return new DemandeDevisCriteria(this);
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

    public LongFilter getProduitsId() {
        return produitsId;
    }

    public LongFilter produitsId() {
        if (produitsId == null) {
            produitsId = new LongFilter();
        }
        return produitsId;
    }

    public void setProduitsId(LongFilter produitsId) {
        this.produitsId = produitsId;
    }

    public LongFilter getDemandesachatId() {
        return demandesachatId;
    }

    public LongFilter demandesachatId() {
        if (demandesachatId == null) {
            demandesachatId = new LongFilter();
        }
        return demandesachatId;
    }

    public void setDemandesachatId(LongFilter demandesachatId) {
        this.demandesachatId = demandesachatId;
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
        final DemandeDevisCriteria that = (DemandeDevisCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(produitsId, that.produitsId) &&
            Objects.equals(demandesachatId, that.demandesachatId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, produitsId, demandesachatId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeDevisCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (produitsId != null ? "produitsId=" + produitsId + ", " : "") +
            (demandesachatId != null ? "demandesachatId=" + demandesachatId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
