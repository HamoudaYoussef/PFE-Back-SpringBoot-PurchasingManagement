package biz.picosoft.demo.domain;

import biz.picosoft.demo.domain.ennumeration.Region;
import biz.picosoft.demo.domain.ennumeration.StatutDA;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DemandeAchat.
 */
@Entity
@Table(name = "demande_achat", schema = "achat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemandeAchat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;
    @Column(name = "datebesoin")
    private LocalDate datebesoin;
    @Column(name = "datedemande")
    private LocalDate datedemande;

    @Column(name = "statut")
    @Enumerated(EnumType.STRING)
    private StatutDA statut;

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Column(name = "region")
    @Enumerated(EnumType.STRING)
    private Region region;

    @Column(name = "description")
    private String description;

    @Size(min = 0, max = 50)
    @Column(name = "identifiant", length = 50, nullable = true)
    private String identifiant;
    @Column(name = "activity_name")
    private String activityName;
    @Column(name = "demande_number")
    private String demandeNumber;
    @Column(name = "number_of_attachments")
    private Long numberOfattachments = 0L;
    @Column(name = "exclude_from_view")
    private Boolean excludeFromView = true;
    @Column(name = "status")
    private String status;
    @Column(name = "wf_process_id")
    private String wfProcessID;

    @Column(name = "securite_level")
    private Integer securiteLevel;
    @Column(name = "assignee")
    private String assignee;

    @Column(name = "end_process")
    private Boolean endProcess;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "demandeachat")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "boncommandes", "demandeachat", "fournisseur" }, allowSetters = true)
    private Set<Offre> offres = new HashSet<>();

    public Set<DemandeDevis> getDemandeDevis() {
        return demandedevis;
    }

    public void setDemandeDevis(Set<DemandeDevis> demandeDevis) {
        this.demandedevis = demandeDevis;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "demandeAchat")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fournisseur", "demandeAchat" }, allowSetters = true)
    private Set<DemandeDevis> demandedevis = new HashSet<>();

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDatebesoin() {
        return datebesoin;
    }

    public void setDatebesoin(LocalDate datebesoin) {
        this.datebesoin = datebesoin;
    }

    public StatutDA getStatut() {
        return statut;
    }

    public void setStatut(StatutDA statut) {
        this.statut = statut;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Long getNumberOfattachments() {
        return numberOfattachments;
    }

    public void setNumberOfattachments(Long numberOfattachments) {
        this.numberOfattachments = numberOfattachments;
    }

    public Boolean getExcludeFromView() {
        return excludeFromView;
    }

    public void setExcludeFromView(Boolean excludeFromView) {
        this.excludeFromView = excludeFromView;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWfProcessID() {
        return wfProcessID;
    }

    public void setWfProcessID(String wfProcessID) {
        this.wfProcessID = wfProcessID;
    }


    public Integer getSecuriteLevel() {
        return securiteLevel;
    }

    public void setSecuriteLevel(Integer securiteLevel) {
        this.securiteLevel = securiteLevel;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Boolean getEndProcess() {
        return endProcess;
    }

    public void setEndProcess(Boolean endProcess) {
        this.endProcess = endProcess;
    }

    public String getDemandeNumber() {
        return demandeNumber;
    }

    public void setDemandeNumber(String DemandeNumber) {
        this.demandeNumber = DemandeNumber;
    }


    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DemandeAchat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatedemande() {
        return this.datedemande;
    }

    public DemandeAchat datedemande(LocalDate datedemande) {
        this.setDatedemande(datedemande);
        return this;
    }

    public void setDatedemande(LocalDate datedemande) {
        this.datedemande = datedemande;
    }

    public String getDescription() {
        return this.description;
    }

    public DemandeAchat description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Offre> getOffres() {
        return this.offres;
    }

    public void setOffres(Set<Offre> offres) {
        if (this.offres != null) {
            this.offres.forEach(i -> i.setDemandeachat(null));
        }
        if (offres != null) {
            offres.forEach(i -> i.setDemandeachat(this));
        }
        this.offres = offres;
    }

    public DemandeAchat offres(Set<Offre> offres) {
        this.setOffres(offres);
        return this;
    }

    public DemandeAchat addOffre(Offre offre) {
        this.offres.add(offre);
        offre.setDemandeachat(this);
        return this;
    }

    public DemandeAchat removeOffre(Offre offre) {
        this.offres.remove(offre);
        offre.setDemandeachat(null);
        return this;
    }

    public Boolean isExcludeFromView() {
        return excludeFromView;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandeAchat)) {
            return false;
        }
        return getId() != null && getId().equals(((DemandeAchat) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeAchat{" +
            "id=" + getId() +
            ", datedemande='" + getDatedemande() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
