package biz.picosoft.demo.domain;

import biz.picosoft.demo.domain.ennumeration.InfoPaiement;
import biz.picosoft.demo.domain.ennumeration.StatutBonCommande;
import biz.picosoft.demo.domain.ennumeration.TypeLivraison;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Task entity.
 * @author The JHipster team.
 */
@Entity
@Table(name = "bon_commande", schema = "achat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BonCommande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "dateboncommande")
    private LocalDate dateboncommande;

    @Column(name = "reference")
    private String reference;
    @Column(name = "nomentreprise")
    private String nomentreprise;
    @Column(name = "adresseentreprise")
    private String adresseentreprise;
    @Column(name = "statutbc")
    @Enumerated(EnumType.STRING)
    private StatutBonCommande statutbc;

    public StatutBonCommande getStatutbc() {
        return statutbc;
    }

    public void setStatutbc(StatutBonCommande statutbc) {
        this.statutbc = statutbc;
    }

    @Column(name = "delailivraison")
    private LocalDate delailivraison;
    @Column(name = "typelivraison")
    @Enumerated(EnumType.STRING)
    private TypeLivraison typelivraison;

    @Column(name = "fraislivraison")
    private Long fraislivraison;

    @Column(name = "taxes")
    private Long taxes;

    @Column(name = "montanttotal")
    private Long montanttotal;

    @Column(name = "signature")
    private String signature;

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

    @Column(name = "infopaiement")
    @Enumerated(EnumType.STRING)
    private InfoPaiement infopaiement;


    public Boolean getExcludeFromView() {
        return excludeFromView;
    }
    public Boolean isExcludeFromView() {
        return excludeFromView;
    }

    public void setExcludeFromView(Boolean excludeFromView) {
        this.excludeFromView = excludeFromView;
    }

    @Column(name = "status")
    private String status;

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

    public @Size(min = 0, max = 50) String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(@Size(min = 0, max = 50) String identifiant) {
        this.identifiant = identifiant;
    }

    public String getDemandeNumber() {
        return demandeNumber;
    }

    public void setDemandeNumber(String demandeNumber) {
        this.demandeNumber = demandeNumber;
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

    public Integer getSecuriteLevel() {
        return securiteLevel;
    }

    public void setSecuriteLevel(Integer securiteLevel) {
        this.securiteLevel = securiteLevel;
    }

    @Column(name = "wf_process_id")
    private String wfProcessID;

    @Column(name = "securite_level")
    private Integer securiteLevel;
    @Column(name = "assignee")
    private String assignee;

    public String getNomentreprise() {
        return nomentreprise;
    }

    public void setNomentreprise(String nomentreprise) {
        this.nomentreprise = nomentreprise;
    }

    public String getAdresseentreprise() {
        return adresseentreprise;
    }

    public void setAdresseentreprise(String adresseentreprise) {
        this.adresseentreprise = adresseentreprise;
    }

    public LocalDate getDelailivraison() {
        return delailivraison;
    }

    public void setDelailivraison(LocalDate delailivraison) {
        this.delailivraison = delailivraison;
    }

    public TypeLivraison getTypelivraison() {
        return typelivraison;
    }

    public void setTypelivraison(TypeLivraison typelivraison) {
        this.typelivraison = typelivraison;
    }

    public Long getFraislivraison() {
        return fraislivraison;
    }

    public void setFraislivraison(Long fraislivraison) {
        this.fraislivraison = fraislivraison;
    }

    public Long getTaxes() {
        return taxes;
    }

    public void setTaxes(Long taxes) {
        this.taxes = taxes;
    }

    public Long getMontanttotal() {
        return montanttotal;
    }

    public void setMontanttotal(Long montanttotal) {
        this.montanttotal = montanttotal;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public InfoPaiement getInfopaiement() {
        return infopaiement;
    }

    public void setInfopaiement(InfoPaiement infopaiement) {
        this.infopaiement = infopaiement;
    }

    @Column(name = "end_process")
    private Boolean endProcess;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "boncommande")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "boncommande" }, allowSetters = true)
    private Set<BonLivraison> bonlivraisons = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bonCommande")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "paiements", "bonCommande" }, allowSetters = true)
    private Set<Facture> factures = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "boncommandes", "demandeachat", "fournisseur" }, allowSetters = true)
    private Offre offre;


    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BonCommande id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateboncommande() {
        return this.dateboncommande;
    }

    public BonCommande dateboncommande(LocalDate dateboncommande) {
        this.setDateboncommande(dateboncommande);
        return this;
    }

    public void setDateboncommande(LocalDate dateboncommande) {
        this.dateboncommande = dateboncommande;
    }

    public String getReference() {
        return this.reference;
    }

    public BonCommande reference(String reference) {
        this.setReference(reference);
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Set<BonLivraison> getBonlivraisons() {
        return this.bonlivraisons;
    }

    public void setBonlivraisons(Set<BonLivraison> bonLivraisons) {
        if (this.bonlivraisons != null) {
            this.bonlivraisons.forEach(i -> i.setBoncommande(null));
        }
        if (bonLivraisons != null) {
            bonLivraisons.forEach(i -> i.setBoncommande(this));
        }
        this.bonlivraisons = bonLivraisons;
    }

    public BonCommande bonlivraisons(Set<BonLivraison> bonLivraisons) {
        this.setBonlivraisons(bonLivraisons);
        return this;
    }

    public BonCommande addBonlivraison(BonLivraison bonLivraison) {
        this.bonlivraisons.add(bonLivraison);
        bonLivraison.setBoncommande(this);
        return this;
    }

    public BonCommande removeBonlivraison(BonLivraison bonLivraison) {
        this.bonlivraisons.remove(bonLivraison);
        bonLivraison.setBoncommande(null);
        return this;
    }

    public Set<Facture> getFactures() {
        return this.factures;
    }

    public void setFactures(Set<Facture> factures) {
        if (this.factures != null) {
            this.factures.forEach(i -> i.setBonCommande(null));
        }
        if (factures != null) {
            factures.forEach(i -> i.setBonCommande(this));
        }
        this.factures = factures;
    }

    public BonCommande factures(Set<Facture> factures) {
        this.setFactures(factures);
        return this;
    }

    public BonCommande addFacture(Facture facture) {
        this.factures.add(facture);
        facture.setBonCommande(this);
        return this;
    }

    public BonCommande removeFacture(Facture facture) {
        this.factures.remove(facture);
        facture.setBonCommande(null);
        return this;
    }

    public Offre getOffre() {
        return this.offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public BonCommande offre(Offre offre) {
        this.setOffre(offre);
        return this;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BonCommande)) {
            return false;
        }
        return getId() != null && getId().equals(((BonCommande) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BonCommande{" +
            "id=" + getId() +
            ", dateboncommande='" + getDateboncommande() + "'" +
            ", reference='" + getReference() + "'" +
            "}";
    }
}
