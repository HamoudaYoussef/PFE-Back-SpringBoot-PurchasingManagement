package biz.picosoft.demo.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link biz.picosoft.demo.domain.ProduitCommandee} entity. This class is used
 * in {@link biz.picosoft.demo.web.rest.ProduitCommandeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /produit-commandees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitCommandeeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;


    private LongFilter idproduit;

    private LongFilter idboncommande;

    private LocalDateFilter dateboncommande;

    private LongFilter quantitecommandee;

    private LongFilter boncommandeId;

    private LongFilter produitId;

    private Boolean distinct;

    public ProduitCommandeeCriteria() {}

    public ProduitCommandeeCriteria(ProduitCommandeeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idproduit = other.idproduit == null ? null : other.idproduit.copy();
        this.idboncommande = other.idboncommande == null ? null : other.idboncommande.copy();
        this.dateboncommande = other.dateboncommande == null ? null : other.dateboncommande.copy();
        this.quantitecommandee = other.quantitecommandee == null ? null : other.quantitecommandee.copy();
        this.boncommandeId = other.boncommandeId == null ? null : other.boncommandeId.copy();
        this.produitId = other.produitId == null ? null : other.produitId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProduitCommandeeCriteria copy() {
        return new ProduitCommandeeCriteria(this);
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

    public LongFilter getIdboncommande() {
        return idboncommande;
    }

    public LongFilter idboncommande() {
        if (idboncommande == null) {
            idboncommande = new LongFilter();
        }
        return idboncommande;
    }

    public void setIdboncommande(LongFilter idboncommande) {
        this.idboncommande = idboncommande;
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

    public LongFilter getQuantitecommandee() {
        return quantitecommandee;
    }

    public LongFilter quantitecommandee() {
        if (quantitecommandee == null) {
            quantitecommandee = new LongFilter();
        }
        return quantitecommandee;
    }

    public void setQuantitecommandee(LongFilter quantitecommandee) {
        this.quantitecommandee = quantitecommandee;
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
        final ProduitCommandeeCriteria that = (ProduitCommandeeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idproduit, that.idproduit) &&
            Objects.equals(idboncommande, that.idboncommande) &&
            Objects.equals(dateboncommande, that.dateboncommande) &&
            Objects.equals(quantitecommandee, that.quantitecommandee) &&
            Objects.equals(boncommandeId, that.boncommandeId) &&
            Objects.equals(produitId, that.produitId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            idproduit,
            idboncommande,
            dateboncommande,
            quantitecommandee,
            boncommandeId,
            produitId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitCommandeeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idproduit != null ? "idproduit=" + idproduit + ", " : "") +
            (idboncommande != null ? "idboncommande=" + idboncommande + ", " : "") +
            (dateboncommande != null ? "dateboncommande=" + dateboncommande + ", " : "") +
            (quantitecommandee != null ? "quantitecommandee=" + quantitecommandee + ", " : "") +
            (boncommandeId != null ? "boncommandeId=" + boncommandeId + ", " : "") +
            (produitId != null ? "produitId=" + produitId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
