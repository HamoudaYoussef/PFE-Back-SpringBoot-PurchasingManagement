package biz.picosoft.demo.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link biz.picosoft.demo.domain.BonCommande} entity. This class is used
 * in {@link biz.picosoft.demo.web.rest.BonCommandeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bon-commandes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BonCommandeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;


    private LocalDateFilter dateboncommande;

    private StringFilter reference;

    private LongFilter quantite;

    private LongFilter prixunitaire;

    private LongFilter totalht;

    private LongFilter tva;

    private LongFilter totalttc;

    private LongFilter facturesId;

    private LongFilter offreId;

    private Boolean distinct;

    public BonCommandeCriteria() {}

    public BonCommandeCriteria(BonCommandeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dateboncommande = other.dateboncommande == null ? null : other.dateboncommande.copy();
        this.reference = other.reference == null ? null : other.reference.copy();
        this.quantite = other.quantite == null ? null : other.quantite.copy();
        this.prixunitaire = other.prixunitaire == null ? null : other.prixunitaire.copy();
        this.totalht = other.totalht == null ? null : other.totalht.copy();
        this.tva = other.tva == null ? null : other.tva.copy();
        this.totalttc = other.totalttc == null ? null : other.totalttc.copy();
        this.facturesId = other.facturesId == null ? null : other.facturesId.copy();
        this.offreId = other.offreId == null ? null : other.offreId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BonCommandeCriteria copy() {
        return new BonCommandeCriteria(this);
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



    public LocalDateFilter getDateboncommande() {
        return dateboncommande;
    }

    public LocalDateFilter dateboncommande() {
        if (dateboncommande == null) {
            dateboncommande = new LocalDateFilter();
        }
        return dateboncommande;
    }

    public void setDateboncommande(LocalDateFilter dateboncommande) {
        this.dateboncommande = dateboncommande;
    }

    public StringFilter getReference() {
        return reference;
    }

    public StringFilter reference() {
        if (reference == null) {
            reference = new StringFilter();
        }
        return reference;
    }

    public void setReference(StringFilter reference) {
        this.reference = reference;
    }

    public LongFilter getQuantite() {
        return quantite;
    }

    public LongFilter quantite() {
        if (quantite == null) {
            quantite = new LongFilter();
        }
        return quantite;
    }

    public void setQuantite(LongFilter quantite) {
        this.quantite = quantite;
    }

    public LongFilter getPrixunitaire() {
        return prixunitaire;
    }

    public LongFilter prixunitaire() {
        if (prixunitaire == null) {
            prixunitaire = new LongFilter();
        }
        return prixunitaire;
    }

    public void setPrixunitaire(LongFilter prixunitaire) {
        this.prixunitaire = prixunitaire;
    }

    public LongFilter getTotalht() {
        return totalht;
    }

    public LongFilter totalht() {
        if (totalht == null) {
            totalht = new LongFilter();
        }
        return totalht;
    }

    public void setTotalht(LongFilter totalht) {
        this.totalht = totalht;
    }

    public LongFilter getTva() {
        return tva;
    }

    public LongFilter tva() {
        if (tva == null) {
            tva = new LongFilter();
        }
        return tva;
    }

    public void setTva(LongFilter tva) {
        this.tva = tva;
    }

    public LongFilter getTotalttc() {
        return totalttc;
    }

    public LongFilter totalttc() {
        if (totalttc == null) {
            totalttc = new LongFilter();
        }
        return totalttc;
    }

    public void setTotalttc(LongFilter totalttc) {
        this.totalttc = totalttc;
    }

    public LongFilter getFacturesId() {
        return facturesId;
    }

    public LongFilter facturesId() {
        if (facturesId == null) {
            facturesId = new LongFilter();
        }
        return facturesId;
    }

    public void setFacturesId(LongFilter facturesId) {
        this.facturesId = facturesId;
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
        final BonCommandeCriteria that = (BonCommandeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dateboncommande, that.dateboncommande) &&
            Objects.equals(reference, that.reference) &&
            Objects.equals(quantite, that.quantite) &&
            Objects.equals(prixunitaire, that.prixunitaire) &&
            Objects.equals(totalht, that.totalht) &&
            Objects.equals(tva, that.tva) &&
            Objects.equals(totalttc, that.totalttc) &&
            Objects.equals(facturesId, that.facturesId) &&
            Objects.equals(offreId, that.offreId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            dateboncommande,
            reference,
            quantite,
            prixunitaire,
            totalht,
            tva,
            totalttc,
            facturesId,
            offreId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BonCommandeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dateboncommande != null ? "dateboncommande=" + dateboncommande + ", " : "") +
            (reference != null ? "reference=" + reference + ", " : "") +
            (quantite != null ? "quantite=" + quantite + ", " : "") +
            (prixunitaire != null ? "prixunitaire=" + prixunitaire + ", " : "") +
            (totalht != null ? "totalht=" + totalht + ", " : "") +
            (tva != null ? "tva=" + tva + ", " : "") +
            (totalttc != null ? "totalttc=" + totalttc + ", " : "") +
            (facturesId != null ? "facturesId=" + facturesId + ", " : "") +
            (offreId != null ? "offreId=" + offreId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
