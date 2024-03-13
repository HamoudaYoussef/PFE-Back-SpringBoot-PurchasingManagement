package biz.picosoft.demo.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link biz.picosoft.demo.domain.Facture} entity. This class is used
 * in {@link biz.picosoft.demo.web.rest.FactureResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /factures?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FactureCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;


    private LocalDateFilter datefacture;

    private StringFilter designation;

    private LongFilter montanttotalfacture;

    private StringFilter nomentrprise;

    private StringFilter adresseentreprise;

    private StringFilter telentreprise;

    private StringFilter emailentreprise;

    private StringFilter nomclient;

    private StringFilter adresseclient;

    private StringFilter telclient;

    private StringFilter emailclien;

    private LongFilter prixunitaire;

    private LongFilter totalht;

    private LongFilter tva;

    private LongFilter totalttc;

    private StringFilter reference;

    private LongFilter paiementsId;

    private LongFilter bonlivraisonsId;

    private LongFilter bonCcmmandeId;

    private Boolean distinct;

    public FactureCriteria() {}

    public FactureCriteria(FactureCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.datefacture = other.datefacture == null ? null : other.datefacture.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
        this.montanttotalfacture = other.montanttotalfacture == null ? null : other.montanttotalfacture.copy();
        this.nomentrprise = other.nomentrprise == null ? null : other.nomentrprise.copy();
        this.adresseentreprise = other.adresseentreprise == null ? null : other.adresseentreprise.copy();
        this.telentreprise = other.telentreprise == null ? null : other.telentreprise.copy();
        this.emailentreprise = other.emailentreprise == null ? null : other.emailentreprise.copy();
        this.nomclient = other.nomclient == null ? null : other.nomclient.copy();
        this.adresseclient = other.adresseclient == null ? null : other.adresseclient.copy();
        this.telclient = other.telclient == null ? null : other.telclient.copy();
        this.emailclien = other.emailclien == null ? null : other.emailclien.copy();
        this.prixunitaire = other.prixunitaire == null ? null : other.prixunitaire.copy();
        this.totalht = other.totalht == null ? null : other.totalht.copy();
        this.tva = other.tva == null ? null : other.tva.copy();
        this.totalttc = other.totalttc == null ? null : other.totalttc.copy();
        this.reference = other.reference == null ? null : other.reference.copy();
        this.paiementsId = other.paiementsId == null ? null : other.paiementsId.copy();
        this.bonlivraisonsId = other.bonlivraisonsId == null ? null : other.bonlivraisonsId.copy();
        this.bonCcmmandeId = other.bonCcmmandeId == null ? null : other.bonCcmmandeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FactureCriteria copy() {
        return new FactureCriteria(this);
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


    public LocalDateFilter getDatefacture() {
        return datefacture;
    }

    public LocalDateFilter datefacture() {
        if (datefacture == null) {
            datefacture = new LocalDateFilter();
        }
        return datefacture;
    }

    public void setDatefacture(LocalDateFilter datefacture) {
        this.datefacture = datefacture;
    }

    public StringFilter getDesignation() {
        return designation;
    }

    public StringFilter designation() {
        if (designation == null) {
            designation = new StringFilter();
        }
        return designation;
    }

    public void setDesignation(StringFilter designation) {
        this.designation = designation;
    }

    public LongFilter getMontanttotalfacture() {
        return montanttotalfacture;
    }

    public LongFilter montanttotalfacture() {
        if (montanttotalfacture == null) {
            montanttotalfacture = new LongFilter();
        }
        return montanttotalfacture;
    }

    public void setMontanttotalfacture(LongFilter montanttotalfacture) {
        this.montanttotalfacture = montanttotalfacture;
    }

    public StringFilter getNomentrprise() {
        return nomentrprise;
    }

    public StringFilter nomentrprise() {
        if (nomentrprise == null) {
            nomentrprise = new StringFilter();
        }
        return nomentrprise;
    }

    public void setNomentrprise(StringFilter nomentrprise) {
        this.nomentrprise = nomentrprise;
    }

    public StringFilter getAdresseentreprise() {
        return adresseentreprise;
    }

    public StringFilter adresseentreprise() {
        if (adresseentreprise == null) {
            adresseentreprise = new StringFilter();
        }
        return adresseentreprise;
    }

    public void setAdresseentreprise(StringFilter adresseentreprise) {
        this.adresseentreprise = adresseentreprise;
    }

    public StringFilter getTelentreprise() {
        return telentreprise;
    }

    public StringFilter telentreprise() {
        if (telentreprise == null) {
            telentreprise = new StringFilter();
        }
        return telentreprise;
    }

    public void setTelentreprise(StringFilter telentreprise) {
        this.telentreprise = telentreprise;
    }

    public StringFilter getEmailentreprise() {
        return emailentreprise;
    }

    public StringFilter emailentreprise() {
        if (emailentreprise == null) {
            emailentreprise = new StringFilter();
        }
        return emailentreprise;
    }

    public void setEmailentreprise(StringFilter emailentreprise) {
        this.emailentreprise = emailentreprise;
    }

    public StringFilter getNomclient() {
        return nomclient;
    }

    public StringFilter nomclient() {
        if (nomclient == null) {
            nomclient = new StringFilter();
        }
        return nomclient;
    }

    public void setNomclient(StringFilter nomclient) {
        this.nomclient = nomclient;
    }

    public StringFilter getAdresseclient() {
        return adresseclient;
    }

    public StringFilter adresseclient() {
        if (adresseclient == null) {
            adresseclient = new StringFilter();
        }
        return adresseclient;
    }

    public void setAdresseclient(StringFilter adresseclient) {
        this.adresseclient = adresseclient;
    }

    public StringFilter getTelclient() {
        return telclient;
    }

    public StringFilter telclient() {
        if (telclient == null) {
            telclient = new StringFilter();
        }
        return telclient;
    }

    public void setTelclient(StringFilter telclient) {
        this.telclient = telclient;
    }

    public StringFilter getEmailclien() {
        return emailclien;
    }

    public StringFilter emailclien() {
        if (emailclien == null) {
            emailclien = new StringFilter();
        }
        return emailclien;
    }

    public void setEmailclien(StringFilter emailclien) {
        this.emailclien = emailclien;
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

    public LongFilter getPaiementsId() {
        return paiementsId;
    }

    public LongFilter paiementsId() {
        if (paiementsId == null) {
            paiementsId = new LongFilter();
        }
        return paiementsId;
    }

    public void setPaiementsId(LongFilter paiementsId) {
        this.paiementsId = paiementsId;
    }

    public LongFilter getBonlivraisonsId() {
        return bonlivraisonsId;
    }

    public LongFilter bonlivraisonsId() {
        if (bonlivraisonsId == null) {
            bonlivraisonsId = new LongFilter();
        }
        return bonlivraisonsId;
    }

    public void setBonlivraisonsId(LongFilter bonlivraisonsId) {
        this.bonlivraisonsId = bonlivraisonsId;
    }

    public LongFilter getBonCcmmandeId() {
        return bonCcmmandeId;
    }

    public LongFilter bonCcmmandeId() {
        if (bonCcmmandeId == null) {
            bonCcmmandeId = new LongFilter();
        }
        return bonCcmmandeId;
    }

    public void setBonCcmmandeId(LongFilter bonCcmmandeId) {
        this.bonCcmmandeId = bonCcmmandeId;
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
        final FactureCriteria that = (FactureCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(datefacture, that.datefacture) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(montanttotalfacture, that.montanttotalfacture) &&
            Objects.equals(nomentrprise, that.nomentrprise) &&
            Objects.equals(adresseentreprise, that.adresseentreprise) &&
            Objects.equals(telentreprise, that.telentreprise) &&
            Objects.equals(emailentreprise, that.emailentreprise) &&
            Objects.equals(nomclient, that.nomclient) &&
            Objects.equals(adresseclient, that.adresseclient) &&
            Objects.equals(telclient, that.telclient) &&
            Objects.equals(emailclien, that.emailclien) &&
            Objects.equals(prixunitaire, that.prixunitaire) &&
            Objects.equals(totalht, that.totalht) &&
            Objects.equals(tva, that.tva) &&
            Objects.equals(totalttc, that.totalttc) &&
            Objects.equals(reference, that.reference) &&
            Objects.equals(paiementsId, that.paiementsId) &&
            Objects.equals(bonlivraisonsId, that.bonlivraisonsId) &&
            Objects.equals(bonCcmmandeId, that.bonCcmmandeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            datefacture,
            designation,
            montanttotalfacture,
            nomentrprise,
            adresseentreprise,
            telentreprise,
            emailentreprise,
            nomclient,
            adresseclient,
            telclient,
            emailclien,
            prixunitaire,
            totalht,
            tva,
            totalttc,
            reference,
            paiementsId,
            bonlivraisonsId,
            bonCcmmandeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FactureCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (datefacture != null ? "datefacture=" + datefacture + ", " : "") +
            (designation != null ? "designation=" + designation + ", " : "") +
            (montanttotalfacture != null ? "montanttotalfacture=" + montanttotalfacture + ", " : "") +
            (nomentrprise != null ? "nomentrprise=" + nomentrprise + ", " : "") +
            (adresseentreprise != null ? "adresseentreprise=" + adresseentreprise + ", " : "") +
            (telentreprise != null ? "telentreprise=" + telentreprise + ", " : "") +
            (emailentreprise != null ? "emailentreprise=" + emailentreprise + ", " : "") +
            (nomclient != null ? "nomclient=" + nomclient + ", " : "") +
            (adresseclient != null ? "adresseclient=" + adresseclient + ", " : "") +
            (telclient != null ? "telclient=" + telclient + ", " : "") +
            (emailclien != null ? "emailclien=" + emailclien + ", " : "") +
            (prixunitaire != null ? "prixunitaire=" + prixunitaire + ", " : "") +
            (totalht != null ? "totalht=" + totalht + ", " : "") +
            (tva != null ? "tva=" + tva + ", " : "") +
            (totalttc != null ? "totalttc=" + totalttc + ", " : "") +
            (reference != null ? "reference=" + reference + ", " : "") +
            (paiementsId != null ? "paiementsId=" + paiementsId + ", " : "") +
            (bonlivraisonsId != null ? "bonlivraisonsId=" + bonlivraisonsId + ", " : "") +
            (bonCcmmandeId != null ? "bonCcmmandeId=" + bonCcmmandeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
