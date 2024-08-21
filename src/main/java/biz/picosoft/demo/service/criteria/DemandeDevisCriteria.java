package biz.picosoft.demo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**

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

    private LongFilter quantite;

    private StringFilter nom;

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    private LongFilter fournisseurId;

    public LongFilter getDemandeAchatId() {
        return demandeAchatId;
    }

    public void setDemandeAchatId(LongFilter demandeAchatId) {
        this.demandeAchatId = demandeAchatId;
    }

    private LongFilter demandeAchatId;

    private Boolean distinct;

    public DemandeDevisCriteria() {}

    public DemandeDevisCriteria(DemandeDevisCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.quantite = other.quantite == null ? null : other.quantite.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.demandeAchatId = other.demandeAchatId == null ? null : other.demandeAchatId.copy();
        this.fournisseurId = other.fournisseurId == null ? null : other.fournisseurId.copy();
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





    public LongFilter getFournisseurId() {
        return fournisseurId;
    }

    public LongFilter fournisseurId() {
        if (fournisseurId == null) {
            fournisseurId = new LongFilter();
        }
        return fournisseurId;
    }

    public void setFournisseurId(LongFilter fournisseurId) {
        this.fournisseurId = fournisseurId;
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
            Objects.equals(quantite, that.quantite) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(demandeAchatId, that.demandeAchatId) &&
            Objects.equals(fournisseurId, that.fournisseurId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, quantite, nom, demandeAchatId, fournisseurId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeDevisCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (quantite != null ? "quantite=" + quantite + ", " : "") +
            (nom != null ? "nom=" + nom + ", " : "") +
            (demandeAchatId != null ? "demandeAchatId=" + demandeAchatId + ", " : "") +
            (fournisseurId != null ? "fournisseurId=" + fournisseurId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
