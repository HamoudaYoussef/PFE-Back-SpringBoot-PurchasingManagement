package biz.picosoft.demo.domain;

import biz.picosoft.demo.domain.enumeration.StatutDA;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DemandeAchat.
 */
@Entity
//@Table(name = "demande_achat", schema = "achat")
@Table(name = "demande_achat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemandeAchat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(name = "datedemande", columnDefinition = "TIMESTAMP")
    private LocalDate datedemande = LocalDate.now();

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private StatutDA statut;

    @Column(name = "datebesoin")
    private LocalDate datebesoin;

    @Column(name = "wf_process_id")
    private String wfProcessID;

    @Column(name = "activity_name")
    private String activityName;

    @Size(min = 0, max = 50)
    @Column(name = "identifiant", length = 50, nullable = true)
    private String identifiant;

    @Column(name = "draft")
    private Boolean draft;

    @Column(name = "securite_level")
    private Integer securiteLevel;

    @Column(name = "number_of_attachments")
    private Long numberOfattachments;
    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "produits", "demandesachats" }, allowSetters = true)
    private DemandeDevis demandeDevis;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "demandeachat")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "boncommandes", "fournisseur", "demandeachat", "produit" }, allowSetters = true)
    private Set<Offre> offres = new HashSet<>();

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

    public StatutDA getStatut() {
        return this.statut;
    }

    public DemandeAchat statut(StatutDA statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(StatutDA statut) {
        this.statut = statut;
    }


    public LocalDate getDatebesoin() {
        return this.datebesoin;
    }

    public DemandeAchat datebesoin(LocalDate datebesoin) {
        this.setDatebesoin(datebesoin);
        return this;
    }

    public void setDatebesoin(LocalDate datebesoin) {
        this.datebesoin = datebesoin;
    }


    public DemandeDevis getDemandedevis() {
        return this.demandeDevis;
    }

    public void setDemandedevis(DemandeDevis demandedevis) {
        this.demandeDevis = demandedevis;
    }

    public DemandeAchat demandedevis(DemandeDevis demandedevis) {
        this.setDemandedevis(demandedevis);
        return this;
    }
    public String getWfProcessID() {
        return wfProcessID;
    }

    public void setWfProcessID(String wfProcessID) {
        this.wfProcessID = wfProcessID;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }
    public Boolean getDraft() {
        return draft;
    }

    public void setDraft(Boolean draft) {
        this.draft = draft;
    }

    public Integer getSecuriteLevel() {
        return securiteLevel;
    }

    public void setSecuriteLevel(Integer securiteLevel) {
        this.securiteLevel = securiteLevel;
    }

    public Long getNumberOfattachments() {
        return numberOfattachments;
    }

    public void setNumberOfattachments(Long numberOfattachments) {
        this.numberOfattachments = numberOfattachments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public DemandeAchat addOffres(Offre offre) {
        this.offres.add(offre);
        offre.setDemandeachat(this);
        return this;
    }

    public DemandeAchat removeOffres(Offre offre) {
        this.offres.remove(offre);
        offre.setDemandeachat(null);
        return this;
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
                ", datebesoin='" + getDatebesoin() + "'" +
                ", statut='" + getStatut() + "'" +
                ", description='" + getDescription() + "'" +
            "}";
    }
}
