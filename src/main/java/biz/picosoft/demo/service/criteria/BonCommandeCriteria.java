package biz.picosoft.demo.service.criteria;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import biz.picosoft.demo.domain.ennumeration.InfoPaiement;
import biz.picosoft.demo.domain.ennumeration.StatutBonCommande;
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
    private TypeLivraison typelivraison;

    public StatutBonCommande getStatutbc() {
        return statutbc;
    }

    public void setStatutbc(StatutBonCommande statutbc) {
        this.statutbc = statutbc;
    }

    private StatutBonCommande statutbc;


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

    public TypeLivraison getTypelivraison() {
        return typelivraison;
    }

    public void setTypelivraison(TypeLivraison typelivraison) {
        this.typelivraison = typelivraison;
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

    public InfoPaiement getInfopaiement() {
        return infopaiement;
    }

    public void setInfopaiement(InfoPaiement infopaiement) {
        this.infopaiement = infopaiement;
    }

    public StringFilter getNomentreprise() {
        return nomentreprise;
    }

    public void setNomentreprise(StringFilter nomentreprise) {
        this.nomentreprise = nomentreprise;
    }

    private InfoPaiement infopaiement;


    private LongFilter bonlivraisonId;

    private LongFilter factureId;

    private LongFilter offreId;

    private LongFilter demandedevisId;

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
        this.taxes = other.taxes == null ? null : other.taxes.copy();
        this.montanttotal = other.montanttotal == null ? null : other.montanttotal.copy();
        this.signature = other.signature == null ? null : other.signature.copy();
        this.bonlivraisonId = other.bonlivraisonId == null ? null : other.bonlivraisonId.copy();
        this.factureId = other.factureId == null ? null : other.factureId.copy();
        this.offreId = other.offreId == null ? null : other.offreId.copy();
        this.demandedevisId = other.demandedevisId == null ? null : other.demandedevisId.copy();
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

    public LongFilter getBonlivraisonId() {
        return bonlivraisonId;
    }

    public LongFilter bonlivraisonId() {
        if (bonlivraisonId == null) {
            bonlivraisonId = new LongFilter();
        }
        return bonlivraisonId;
    }

    public void setBonlivraisonId(LongFilter bonlivraisonId) {
        this.bonlivraisonId = bonlivraisonId;
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

    public LongFilter getDemandedevisId() {
        return demandedevisId;
    }

    public LongFilter demandedevisId() {
        if (demandedevisId == null) {
            demandedevisId = new LongFilter();
        }
        return demandedevisId;
    }

    public void setDemandedevisId(LongFilter demandedevisId) {
        this.demandedevisId = demandedevisId;
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
            Objects.equals(typelivraison, that.typelivraison) &&
            Objects.equals(fraislivraison, that.fraislivraison) &&
                    Objects.equals(taxes, that.taxes) &&
                    Objects.equals(montanttotal, that.montanttotal) && Objects.equals(signature, that.signature) &&
                    Objects.equals(infopaiement, that.infopaiement) &&
                    Objects.equals(statutbc, that.statutbc) &&
                    Objects.equals(bonlivraisonId, that.bonlivraisonId) &&
            Objects.equals(factureId, that.factureId) &&
            Objects.equals(offreId, that.offreId) &&
            Objects.equals(demandedevisId, that.demandedevisId) &&
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
            typelivraison,
            taxes,
            montanttotal,
            signature,
            statutbc,
            infopaiement,
            bonlivraisonId,
            factureId,
            offreId,
            demandedevisId,
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
            (bonlivraisonId != null ? "bonlivraisonId=" + bonlivraisonId + ", " : "") +
            (factureId != null ? "factureId=" + factureId + ", " : "") +
            (offreId != null ? "offreId=" + offreId + ", " : "") +
            (demandedevisId != null ? "demandedevisId=" + demandedevisId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
