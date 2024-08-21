package biz.picosoft.demo.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


@Schema(description = "The Employee entity.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FactureDTO implements Serializable {

    /**
     * The firstname attribute.
     */
    @Schema(description = "The firstname attribute.")
    private Long id;

    private LocalDate datefacture;

    private String designation;

    private Long montanttotalfacture;

    private String nomentrprise;

    private String adresseentreprise;

    private String telentreprise;

    private String emailentreprise;

    private String nomclient;

    private String adresseclient;

    private String telclient;

    private String emailclien;

    private Long prixunitaire;

    private Long totalht;

    private Long tva;

    private Long totalttc;

    private String reference;

    private BonCommandeDTO bonCommande;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatefacture() {
        return datefacture;
    }

    public void setDatefacture(LocalDate datefacture) {
        this.datefacture = datefacture;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Long getMontanttotalfacture() {
        return montanttotalfacture;
    }

    public void setMontanttotalfacture(Long montanttotalfacture) {
        this.montanttotalfacture = montanttotalfacture;
    }

    public String getNomentrprise() {
        return nomentrprise;
    }

    public void setNomentrprise(String nomentrprise) {
        this.nomentrprise = nomentrprise;
    }

    public String getAdresseentreprise() {
        return adresseentreprise;
    }

    public void setAdresseentreprise(String adresseentreprise) {
        this.adresseentreprise = adresseentreprise;
    }

    public String getTelentreprise() {
        return telentreprise;
    }

    public void setTelentreprise(String telentreprise) {
        this.telentreprise = telentreprise;
    }

    public String getEmailentreprise() {
        return emailentreprise;
    }

    public void setEmailentreprise(String emailentreprise) {
        this.emailentreprise = emailentreprise;
    }

    public String getNomclient() {
        return nomclient;
    }

    public void setNomclient(String nomclient) {
        this.nomclient = nomclient;
    }

    public String getAdresseclient() {
        return adresseclient;
    }

    public void setAdresseclient(String adresseclient) {
        this.adresseclient = adresseclient;
    }

    public String getTelclient() {
        return telclient;
    }

    public void setTelclient(String telclient) {
        this.telclient = telclient;
    }

    public String getEmailclien() {
        return emailclien;
    }

    public void setEmailclien(String emailclien) {
        this.emailclien = emailclien;
    }

    public Long getPrixunitaire() {
        return prixunitaire;
    }

    public void setPrixunitaire(Long prixunitaire) {
        this.prixunitaire = prixunitaire;
    }

    public Long getTotalht() {
        return totalht;
    }

    public void setTotalht(Long totalht) {
        this.totalht = totalht;
    }

    public Long getTva() {
        return tva;
    }

    public void setTva(Long tva) {
        this.tva = tva;
    }

    public Long getTotalttc() {
        return totalttc;
    }

    public void setTotalttc(Long totalttc) {
        this.totalttc = totalttc;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BonCommandeDTO getBonCommande() {
        return bonCommande;
    }

    public void setBonCommande(BonCommandeDTO bonCommande) {
        this.bonCommande = bonCommande;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FactureDTO)) {
            return false;
        }

        FactureDTO factureDTO = (FactureDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, factureDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FactureDTO{" +
            "id=" + getId() +
            ", datefacture='" + getDatefacture() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", montanttotalfacture=" + getMontanttotalfacture() +
            ", nomentrprise='" + getNomentrprise() + "'" +
            ", adresseentreprise='" + getAdresseentreprise() + "'" +
            ", telentreprise='" + getTelentreprise() + "'" +
            ", emailentreprise='" + getEmailentreprise() + "'" +
            ", nomclient='" + getNomclient() + "'" +
            ", adresseclient='" + getAdresseclient() + "'" +
            ", telclient='" + getTelclient() + "'" +
            ", emailclien='" + getEmailclien() + "'" +
            ", prixunitaire=" + getPrixunitaire() +
            ", totalht=" + getTotalht() +
            ", tva=" + getTva() +
            ", totalttc=" + getTotalttc() +
            ", reference='" + getReference() + "'" +
            ", bonCommande=" + getBonCommande() +
            "}";
    }
}
