package biz.picosoft.demo.service.criteria;

import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

@ParameterObject
public class ProduitOffertCritereia  implements Serializable, Criteria {
    private static final long serialVersionUID = 1L;

    private LongFilter id;



    public LongFilter getOffreId() {
        return offreId;
    }

    public void setOffreId(LongFilter offreId) {
        this.offreId = offreId;
    }


    public LongFilter getProduitId() {
        return produitId;
    }

    public void setProduitId(LongFilter produitId) {
        this.produitId = produitId;
    }

    private LongFilter produitId;

    private LongFilter offreId;

    public LongFilter getPrix() {
        return prix;
    }

    public void setPrix(LongFilter prix) {
        this.prix = prix;
    }

    private LongFilter prix;


    private StringFilter nom;

    private StringFilter description;
    private LongFilter quantite;


    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getQuantite() {
        return quantite;
    }

    public void setQuantite(LongFilter quantite) {
        this.quantite = quantite;
    }


    private Boolean distinct;

    public ProduitOffertCritereia() {}

    public ProduitOffertCritereia(ProduitOffertCritereia other) {
        this.id = other.id == null ? null : other.id.copy();
        this.produitId = other.produitId == null ? null : other.produitId.copy();
        this.offreId = other.offreId == null ? null : other.offreId.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.quantite = other.quantite == null ? null : other.quantite.copy();
        this.prix = other.prix == null ? null : other.prix.copy();

        this.distinct = other.distinct;
    }

    @Override
    public ProduitOffertCritereia copy() {
        return new ProduitOffertCritereia(this);
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



    public LongFilter offreId() {
        if (offreId == null) {
            offreId = new LongFilter();
        }
        return offreId;
    }
    public LongFilter produitId() {
        if (produitId == null) {
            produitId = new LongFilter();
        }
        return produitId;
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
        final ProduitOffertCritereia that = (ProduitOffertCritereia) o;
        return (
                Objects.equals(id, that.id) &&
                        Objects.equals(produitId, that.produitId) &&
                        Objects.equals(offreId, that.offreId) &&
                        Objects.equals(nom, that.nom) &&
                        Objects.equals(description, that.description) &&
                        Objects.equals(quantite, that.quantite) &&
                        Objects.equals(prix, that.prix) &&

                        Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, produitId,offreId, nom,description,quantite,prix, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitOffertCritereia{" +
                (id != null ? "id=" + id + ", " : "") +
                (offreId != null ? "offreId=" + offreId + ", " : "") +
                (produitId != null ? "fournisseurId=" + produitId + ", " : "") +
                (distinct != null ? "distinct=" + distinct + ", " : "") +
                "}";
    }
}
