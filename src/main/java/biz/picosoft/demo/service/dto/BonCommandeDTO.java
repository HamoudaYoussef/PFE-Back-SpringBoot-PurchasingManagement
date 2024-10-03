package biz.picosoft.demo.service.dto;

import biz.picosoft.demo.domain.ennumeration.InfoPaiement;
import biz.picosoft.demo.domain.ennumeration.StatutBonCommande;
import biz.picosoft.demo.domain.ennumeration.TypeLivraison;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


@Schema(description = "Task entity.\n@author The JHipster team.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BonCommandeDTO implements Serializable {

    private Long id;

    private LocalDate dateboncommande;

    private String reference;

    private String nomentreprise;
    private String adresseentreprise;

    private LocalDate delailivraison;
    private TypeLivraison typelivraison;

    private Long fraislivraison;

    public StatutBonCommande getStatutbc() {
        return statutbc;
    }

    public void setStatutbc(StatutBonCommande statutbc) {
        this.statutbc = statutbc;
    }

    private StatutBonCommande statutbc;


    private Long taxes;

    private Long montanttotal;

    private String signature;

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

    private InfoPaiement infopaiement;

    private OffreDTO offre;

    private DemandeDevisDTO demandedevis;

    private String activityName;
    private String decision;

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public Boolean getEndProcess() {
        return endProcess;
    }

    public void setEndProcess(Boolean endProcess) {
        this.endProcess = endProcess;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getWfProcessID() {
        return wfProcessID;
    }

    public void setWfProcessID(String wfProcessID) {
        this.wfProcessID = wfProcessID;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFileAccessToken() {
        return fileAccessToken;
    }

    public void setFileAccessToken(String fileAccessToken) {
        this.fileAccessToken = fileAccessToken;
    }

    public Integer getSecuriteLevel() {
        return securiteLevel;
    }

    public void setSecuriteLevel(Integer securiteLevel) {
        this.securiteLevel = securiteLevel;
    }

    public Boolean getDraft() {
        return draft;
    }

    public void setDraft(Boolean draft) {
        this.draft = draft;
    }

    public String getWfCurrentComment() {
        return wfCurrentComment;
    }

    public void setWfCurrentComment(String wfCurrentComment) {
        this.wfCurrentComment = wfCurrentComment;
    }

    private Boolean endProcess;
    private String wfProcessID;
    private String assignee;
    private String status;

    private String  fileAccessToken;

    private Integer securiteLevel;

    private Boolean draft;
    private String wfCurrentComment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateboncommande() {
        return dateboncommande;
    }

    public void setDateboncommande(LocalDate dateboncommande) {
        this.dateboncommande = dateboncommande;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public OffreDTO getOffre() {
        return offre;
    }

    public void setOffre(OffreDTO offre) {
        this.offre = offre;
    }

    public DemandeDevisDTO getDemandedevis() {
        return demandedevis;
    }

    public void setDemandedevis(DemandeDevisDTO demandedevis) {
        this.demandedevis = demandedevis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BonCommandeDTO)) {
            return false;
        }

        BonCommandeDTO bonCommandeDTO = (BonCommandeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bonCommandeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BonCommandeDTO{" +
            "id=" + getId() +
            ", dateboncommande='" + getDateboncommande() + "'" +
            ", reference='" + getReference() + "'" +
            ", offre=" + getOffre() +
            ", demandedevis=" + getDemandedevis() +
            "}";
    }
}
