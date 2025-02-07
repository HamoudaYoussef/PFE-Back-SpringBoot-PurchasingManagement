package biz.picosoft.demo.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import biz.picosoft.demo.domain.ennumeration.InfoPaiement;
import biz.picosoft.demo.domain.ennumeration.TypeLivraison;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**

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
    private StringFilter nomentreprise;
    private StringFilter adresseentreprise;

    private LocalDateFilter delailivraison;

    public TypeLivraisonFilter getTypeLivraison() {
        return typeLivraison;
    }

    public void setTypeLivraison(TypeLivraisonFilter typeLivraison) {
        this.typeLivraison = typeLivraison;
    }

    public InfoPiamentFilter getInfoPaiement() {
        return infoPaiement;
    }

    public void setInfoPaiement(InfoPiamentFilter infoPaiement) {
        this.infoPaiement = infoPaiement;
    }

    private TypeLivraisonFilter typeLivraison;
    private InfoPiamentFilter infoPaiement;





    private LongFilter fraislivraison;

    private LongFilter taxes;
    private LongFilter montanttotal;

    private StringFilter signature;

    public StringFilter getAdresseentreprise() {
        return adresseentreprise;
    }

    public void setAdresseentreprise(StringFilter adresseentreprise) {
        this.adresseentreprise = adresseentreprise;
    }

    public LocalDateFilter getDelailivraison() {
        return delailivraison;
    }

    public void setDelailivraison(LocalDateFilter delailivraison) {
        this.delailivraison = delailivraison;
    }



    public LongFilter getFraislivraison() {
        return fraislivraison;
    }

    public void setFraislivraison(LongFilter fraislivraison) {
        this.fraislivraison = fraislivraison;
    }

    public LongFilter getTaxes() {
        return taxes;
    }

    public void setTaxes(LongFilter taxes) {
        this.taxes = taxes;
    }

    public LongFilter getMontanttotal() {
        return montanttotal;
    }

    public void setMontanttotal(LongFilter montanttotal) {
        this.montanttotal = montanttotal;
    }

    public StringFilter getSignature() {
        return signature;
    }

    public void setSignature(StringFilter signature) {
        this.signature = signature;
    }


    public StringFilter getNomentreprise() {
        return nomentreprise;
    }

    public void setNomentreprise(StringFilter nomentreprise) {
        this.nomentreprise = nomentreprise;
    }





    public LongFilter getProduitOffertId() {
        return produitOffertId;
    }

    public void setProduitOffertId(LongFilter produitOffertId) {
        this.produitOffertId = produitOffertId;
    }

    private LongFilter produitOffertId;


    private Boolean distinct;

    public BonCommandeCriteria() {}

    public BonCommandeCriteria(BonCommandeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dateboncommande = other.dateboncommande == null ? null : other.dateboncommande.copy();
        this.reference = other.reference == null ? null : other.reference.copy();
        this.nomentreprise = other.nomentreprise == null ? null : other.nomentreprise.copy();
        this.adresseentreprise = other.adresseentreprise == null ? null : other.adresseentreprise.copy();
        this.delailivraison = other.delailivraison == null ? null : other.delailivraison.copy();
        this.fraislivraison = other.fraislivraison == null ? null : other.fraislivraison.copy();
        this.typeLivraison = other.typeLivraison == null ? null : other.typeLivraison.copy();
        this.infoPaiement = other.infoPaiement == null ? null : other.infoPaiement.copy();
        this.taxes = other.taxes == null ? null : other.taxes.copy();
        this.montanttotal = other.montanttotal == null ? null : other.montanttotal.copy();
        this.signature = other.signature == null ? null : other.signature.copy();
        this.produitOffertId = other.produitOffertId == null ? null : other.produitOffertId.copy();
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
            Objects.equals(nomentreprise, that.nomentreprise) &&
            Objects.equals(adresseentreprise, that.adresseentreprise) &&
            Objects.equals(delailivraison, that.delailivraison) &&
            Objects.equals(typeLivraison, that.typeLivraison) &&
            Objects.equals(fraislivraison, that.fraislivraison) &&
                    Objects.equals(taxes, that.taxes) &&
                    Objects.equals(montanttotal, that.montanttotal) && Objects.equals(signature, that.signature) &&
                    Objects.equals(infoPaiement, that.infoPaiement) &&
            Objects.equals(produitOffertId, that.produitOffertId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            dateboncommande,
            reference,
            nomentreprise,
                adresseentreprise,
            delailivraison,
            fraislivraison,
            typeLivraison,
            taxes,
            montanttotal,
            signature,
            infoPaiement,
                produitOffertId,
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
            (produitOffertId != null ? "produitOffertId=" + produitOffertId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }

    public static class TypeLivraisonFilter extends Filter<TypeLivraison> {
        public TypeLivraisonFilter() {};
        public TypeLivraisonFilter(TypeLivraisonFilter filter) {
            super(filter);
        };
        @Override
        public TypeLivraisonFilter copy() {
            return new TypeLivraisonFilter(this);

        }

    }
    public static class InfoPiamentFilter extends Filter<InfoPaiement> {
        public InfoPiamentFilter() {};
        public InfoPiamentFilter(InfoPiamentFilter filter) {
            super(filter);
        };
        @Override
        public InfoPiamentFilter copy() {
            return new InfoPiamentFilter(this);

        }

    }
}
