package biz.picosoft.demo.service.criteria;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

import biz.picosoft.demo.domain.LocalDateTypeAdapter;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**

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

    private StringFilter description;

    private LongFilter offreId;

    private LongFilter produitId;

    public LongFilter getDemandedevisId() {
        return demandedevisId;
    }

    public void setDemandedevisId(LongFilter demandedevisId) {
        this.demandedevisId = demandedevisId;
    }

    private LongFilter demandedevisId;


    private Boolean distinct;

    public DemandeAchatCriteria() {}

    public DemandeAchatCriteria(DemandeAchatCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
   //     this.datedemande = other.datedemande == null ? null : other.datedemande.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.offreId = other.offreId == null ? null : other.offreId.copy();
        this.produitId = other.produitId == null ? null : other.produitId.copy();
        this.demandedevisId = other.demandedevisId == null ? null : other.demandedevisId.copy();
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

    public LongFilter getOffreId() {
        return offreId;
    }

    public LongFilter offreId() {
        if (offreId == null) {
            offreId = new LongFilter();
        }
        return offreId;
    }

    public void setOffreId(LongFilter offreId) {
        this.offreId = offreId;
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
        final DemandeAchatCriteria that = (DemandeAchatCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(datedemande, that.datedemande) &&
            Objects.equals(description, that.description) &&
            Objects.equals(offreId, that.offreId) &&
            Objects.equals(produitId, that.produitId) &&
                    Objects.equals(demandedevisId, that.demandedevisId) &&
                    Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, datedemande, description, offreId, produitId,demandedevisId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeAchatCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (datedemande != null ? "datedemande=" + datedemande + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (offreId != null ? "offreId=" + offreId + ", " : "") +
            (produitId != null ? "produitId=" + produitId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
