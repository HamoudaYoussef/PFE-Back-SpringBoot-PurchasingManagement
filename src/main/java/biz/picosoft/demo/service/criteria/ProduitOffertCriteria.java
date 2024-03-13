package biz.picosoft.demo.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link biz.picosoft.demo.domain.ProduitOffert} entity. This class is used
 * in {@link biz.picosoft.demo.web.rest.ProduitOffertResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /produit-offerts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitOffertCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;


    private LongFilter idproduit;

    private LongFilter idoffre;

    private LongFilter quantiteofferte;

    private LongFilter offreId;

    private LongFilter produitId;

    private Boolean distinct;

    public ProduitOffertCriteria() {}

    public ProduitOffertCriteria(ProduitOffertCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idproduit = other.idproduit == null ? null : other.idproduit.copy();
        this.idoffre = other.idoffre == null ? null : other.idoffre.copy();
        this.quantiteofferte = other.quantiteofferte == null ? null : other.quantiteofferte.copy();
        this.offreId = other.offreId == null ? null : other.offreId.copy();
        this.produitId = other.produitId == null ? null : other.produitId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProduitOffertCriteria copy() {
        return new ProduitOffertCriteria(this);
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



    public LongFilter getIdproduit() {
        return idproduit;
    }

    public LongFilter idproduit() {
        if (idproduit == null) {
            idproduit = new LongFilter();
        }
        return idproduit;
    }

    public void setIdproduit(LongFilter idproduit) {
        this.idproduit = idproduit;
    }

    public LongFilter getIdoffre() {
        return idoffre;
    }

    public LongFilter idoffre() {
        if (idoffre == null) {
            idoffre = new LongFilter();
        }
        return idoffre;
    }

    public void setIdoffre(LongFilter idoffre) {
        this.idoffre = idoffre;
    }

    public LongFilter getQuantiteofferte() {
        return quantiteofferte;
    }

    public LongFilter quantiteofferte() {
        if (quantiteofferte == null) {
            quantiteofferte = new LongFilter();
        }
        return quantiteofferte;
    }

    public void setQuantiteofferte(LongFilter quantiteofferte) {
        this.quantiteofferte = quantiteofferte;
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
        final ProduitOffertCriteria that = (ProduitOffertCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idproduit, that.idproduit) &&
            Objects.equals(idoffre, that.idoffre) &&
            Objects.equals(quantiteofferte, that.quantiteofferte) &&
            Objects.equals(offreId, that.offreId) &&
            Objects.equals(produitId, that.produitId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idproduit, idoffre, quantiteofferte, offreId, produitId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitOffertCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idproduit != null ? "idproduit=" + idproduit + ", " : "") +
            (idoffre != null ? "idoffre=" + idoffre + ", " : "") +
            (quantiteofferte != null ? "quantiteofferte=" + quantiteofferte + ", " : "") +
            (offreId != null ? "offreId=" + offreId + ", " : "") +
            (produitId != null ? "produitId=" + produitId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
