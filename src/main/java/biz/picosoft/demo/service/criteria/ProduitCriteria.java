package biz.picosoft.demo.service.criteria;

import biz.picosoft.demo.domain.enumeration.UnitePoids;
import biz.picosoft.demo.domain.enumeration.UniteSurface;
import biz.picosoft.demo.domain.enumeration.UniteTaille;
import biz.picosoft.demo.domain.enumeration.UniteVolume;
import java.io.Serializable;
import java.util.Objects;

import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link biz.picosoft.demo.domain.Produit} entity. This class is used
 * in {@link biz.picosoft.demo.web.rest.ProduitResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /produits?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitCriteria implements Serializable, Criteria {

    /**
     * Class for filtering UniteTaille
     */
    public static class UniteTailleFilter extends Filter<UniteTaille> {

        public UniteTailleFilter() {}

        public UniteTailleFilter(UniteTailleFilter filter) {
            super(filter);
        }

        @Override
        public UniteTailleFilter copy() {
            return new UniteTailleFilter(this);
        }
    }

    /**
     * Class for filtering UnitePoids
     */
    public static class UnitePoidsFilter extends Filter<UnitePoids> {

        public UnitePoidsFilter() {}

        public UnitePoidsFilter(UnitePoidsFilter filter) {
            super(filter);
        }

        @Override
        public UnitePoidsFilter copy() {
            return new UnitePoidsFilter(this);
        }
    }

    /**
     * Class for filtering UniteVolume
     */
    public static class UniteVolumeFilter extends Filter<UniteVolume> {

        public UniteVolumeFilter() {}

        public UniteVolumeFilter(UniteVolumeFilter filter) {
            super(filter);
        }

        @Override
        public UniteVolumeFilter copy() {
            return new UniteVolumeFilter(this);
        }
    }

    /**
     * Class for filtering UniteSurface
     */
    public static class UniteSurfaceFilter extends Filter<UniteSurface> {

        public UniteSurfaceFilter() {}

        public UniteSurfaceFilter(UniteSurfaceFilter filter) {
            super(filter);
        }

        @Override
        public UniteSurfaceFilter copy() {
            return new UniteSurfaceFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;


    private LocalDateFilter dateachat;

    private StringFilter description;

    private StringFilter nom;

    private LongFilter poids;

    private StringFilter forme;

    private LongFilter taille;

    private StringFilter couleur;

    private UniteTailleFilter untietaille;

    private UnitePoidsFilter unitepoids;

    private LongFilter volume;

    private UniteVolumeFilter unitevolume;

    private LongFilter surface;

    private UniteSurfaceFilter unitesurface;

    private LongFilter offresId;

    private Boolean distinct;

    public ProduitCriteria() {}

    public ProduitCriteria(ProduitCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dateachat = other.dateachat == null ? null : other.dateachat.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.poids = other.poids == null ? null : other.poids.copy();
        this.forme = other.forme == null ? null : other.forme.copy();
        this.taille = other.taille == null ? null : other.taille.copy();
        this.couleur = other.couleur == null ? null : other.couleur.copy();
        this.untietaille = other.untietaille == null ? null : other.untietaille.copy();
        this.unitepoids = other.unitepoids == null ? null : other.unitepoids.copy();
        this.volume = other.volume == null ? null : other.volume.copy();
        this.unitevolume = other.unitevolume == null ? null : other.unitevolume.copy();
        this.surface = other.surface == null ? null : other.surface.copy();
        this.unitesurface = other.unitesurface == null ? null : other.unitesurface.copy();
        this.offresId = other.offresId == null ? null : other.offresId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProduitCriteria copy() {
        return new ProduitCriteria(this);
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


    public LocalDateFilter getDateachat() {
        return dateachat;
    }

    public LocalDateFilter dateachat() {
        if (dateachat == null) {
            dateachat = new LocalDateFilter();
        }
        return dateachat;
    }

    public void setDateachat(LocalDateFilter dateachat) {
        this.dateachat = dateachat;
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

    public StringFilter getNom() {
        return nom;
    }

    public StringFilter nom() {
        if (nom == null) {
            nom = new StringFilter();
        }
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public LongFilter getPoids() {
        return poids;
    }

    public LongFilter poids() {
        if (poids == null) {
            poids = new LongFilter();
        }
        return poids;
    }

    public void setPoids(LongFilter poids) {
        this.poids = poids;
    }

    public StringFilter getForme() {
        return forme;
    }

    public StringFilter forme() {
        if (forme == null) {
            forme = new StringFilter();
        }
        return forme;
    }

    public void setForme(StringFilter forme) {
        this.forme = forme;
    }

    public LongFilter getTaille() {
        return taille;
    }

    public LongFilter taille() {
        if (taille == null) {
            taille = new LongFilter();
        }
        return taille;
    }

    public void setTaille(LongFilter taille) {
        this.taille = taille;
    }

    public StringFilter getCouleur() {
        return couleur;
    }

    public StringFilter couleur() {
        if (couleur == null) {
            couleur = new StringFilter();
        }
        return couleur;
    }

    public void setCouleur(StringFilter couleur) {
        this.couleur = couleur;
    }

    public UniteTailleFilter getUntietaille() {
        return untietaille;
    }

    public UniteTailleFilter untietaille() {
        if (untietaille == null) {
            untietaille = new UniteTailleFilter();
        }
        return untietaille;
    }

    public void setUntietaille(UniteTailleFilter untietaille) {
        this.untietaille = untietaille;
    }

    public UnitePoidsFilter getUnitepoids() {
        return unitepoids;
    }

    public UnitePoidsFilter unitepoids() {
        if (unitepoids == null) {
            unitepoids = new UnitePoidsFilter();
        }
        return unitepoids;
    }

    public void setUnitepoids(UnitePoidsFilter unitepoids) {
        this.unitepoids = unitepoids;
    }

    public LongFilter getVolume() {
        return volume;
    }

    public LongFilter volume() {
        if (volume == null) {
            volume = new LongFilter();
        }
        return volume;
    }

    public void setVolume(LongFilter volume) {
        this.volume = volume;
    }

    public UniteVolumeFilter getUnitevolume() {
        return unitevolume;
    }

    public UniteVolumeFilter unitevolume() {
        if (unitevolume == null) {
            unitevolume = new UniteVolumeFilter();
        }
        return unitevolume;
    }

    public void setUnitevolume(UniteVolumeFilter unitevolume) {
        this.unitevolume = unitevolume;
    }

    public LongFilter getSurface() {
        return surface;
    }

    public LongFilter surface() {
        if (surface == null) {
            surface = new LongFilter();
        }
        return surface;
    }

    public void setSurface(LongFilter surface) {
        this.surface = surface;
    }

    public UniteSurfaceFilter getUnitesurface() {
        return unitesurface;
    }

    public UniteSurfaceFilter unitesurface() {
        if (unitesurface == null) {
            unitesurface = new UniteSurfaceFilter();
        }
        return unitesurface;
    }

    public void setUnitesurface(UniteSurfaceFilter unitesurface) {
        this.unitesurface = unitesurface;
    }

    public LongFilter getOffresId() {
        return offresId;
    }

    public LongFilter offresId() {
        if (offresId == null) {
            offresId = new LongFilter();
        }
        return offresId;
    }

    public void setOffresId(LongFilter offresId) {
        this.offresId = offresId;
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
        final ProduitCriteria that = (ProduitCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dateachat, that.dateachat) &&
            Objects.equals(description, that.description) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(poids, that.poids) &&
            Objects.equals(forme, that.forme) &&
            Objects.equals(taille, that.taille) &&
            Objects.equals(couleur, that.couleur) &&
            Objects.equals(untietaille, that.untietaille) &&
            Objects.equals(unitepoids, that.unitepoids) &&
            Objects.equals(volume, that.volume) &&
            Objects.equals(unitevolume, that.unitevolume) &&
            Objects.equals(surface, that.surface) &&
            Objects.equals(unitesurface, that.unitesurface) &&
            Objects.equals(offresId, that.offresId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            dateachat,
            description,
            nom,
            poids,
            forme,
            taille,
            couleur,
            untietaille,
            unitepoids,
            volume,
            unitevolume,
            surface,
            unitesurface,
            offresId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dateachat != null ? "dateachat=" + dateachat + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (nom != null ? "nom=" + nom + ", " : "") +
            (poids != null ? "poids=" + poids + ", " : "") +
            (forme != null ? "forme=" + forme + ", " : "") +
            (taille != null ? "taille=" + taille + ", " : "") +
            (couleur != null ? "couleur=" + couleur + ", " : "") +
            (untietaille != null ? "untietaille=" + untietaille + ", " : "") +
            (unitepoids != null ? "unitepoids=" + unitepoids + ", " : "") +
            (volume != null ? "volume=" + volume + ", " : "") +
            (unitevolume != null ? "unitevolume=" + unitevolume + ", " : "") +
            (surface != null ? "surface=" + surface + ", " : "") +
            (unitesurface != null ? "unitesurface=" + unitesurface + ", " : "") +
            (offresId != null ? "offresId=" + offresId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
