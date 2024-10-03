package biz.picosoft.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The Employee entity.
 */
@Entity
@Table(name = "facture", schema = "achat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Facture implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The firstname attribute.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datefacture")
    private LocalDate datefacture;

    @Column(name = "designation")
    private String designation;

    @Column(name = "montanttotalfacture")
    private Long montanttotalfacture;

    @Column(name = "nomentrprise")
    private String nomentrprise;

    @Column(name = "adresseentreprise")
    private String adresseentreprise;

    @Column(name = "telentreprise")
    private String telentreprise;

    @Column(name = "emailentreprise")
    private String emailentreprise;

    @Column(name = "nomclient")
    private String nomclient;

    @Column(name = "adresseclient")
    private String adresseclient;

    @Column(name = "telclient")
    private String telclient;

    @Column(name = "emailclien")
    private String emailclien;

    @Column(name = "prixunitaire")
    private Long prixunitaire;

    @Column(name = "totalht")
    private Long totalht;

    @Column(name = "tva")
    private Long tva;

    @Column(name = "totalttc")
    private Long totalttc;

    @Column(name = "reference")
    private String reference;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "facture")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "facture" }, allowSetters = true)
    private Set<Paiement> paiements = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "bonlivraisons", "factures", "offre", "demandedevis" }, allowSetters = true)
    private BonCommande bonCommande;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Facture id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatefacture() {
        return this.datefacture;
    }

    public Facture datefacture(LocalDate datefacture) {
        this.setDatefacture(datefacture);
        return this;
    }

    public void setDatefacture(LocalDate datefacture) {
        this.datefacture = datefacture;
    }

    public String getDesignation() {
        return this.designation;
    }

    public Facture designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Long getMontanttotalfacture() {
        return this.montanttotalfacture;
    }

    public Facture montanttotalfacture(Long montanttotalfacture) {
        this.setMontanttotalfacture(montanttotalfacture);
        return this;
    }

    public void setMontanttotalfacture(Long montanttotalfacture) {
        this.montanttotalfacture = montanttotalfacture;
    }

    public String getNomentrprise() {
        return this.nomentrprise;
    }

    public Facture nomentrprise(String nomentrprise) {
        this.setNomentrprise(nomentrprise);
        return this;
    }

    public void setNomentrprise(String nomentrprise) {
        this.nomentrprise = nomentrprise;
    }

    public String getAdresseentreprise() {
        return this.adresseentreprise;
    }

    public Facture adresseentreprise(String adresseentreprise) {
        this.setAdresseentreprise(adresseentreprise);
        return this;
    }

    public void setAdresseentreprise(String adresseentreprise) {
        this.adresseentreprise = adresseentreprise;
    }

    public String getTelentreprise() {
        return this.telentreprise;
    }

    public Facture telentreprise(String telentreprise) {
        this.setTelentreprise(telentreprise);
        return this;
    }

    public void setTelentreprise(String telentreprise) {
        this.telentreprise = telentreprise;
    }

    public String getEmailentreprise() {
        return this.emailentreprise;
    }

    public Facture emailentreprise(String emailentreprise) {
        this.setEmailentreprise(emailentreprise);
        return this;
    }

    public void setEmailentreprise(String emailentreprise) {
        this.emailentreprise = emailentreprise;
    }

    public String getNomclient() {
        return this.nomclient;
    }

    public Facture nomclient(String nomclient) {
        this.setNomclient(nomclient);
        return this;
    }

    public void setNomclient(String nomclient) {
        this.nomclient = nomclient;
    }

    public String getAdresseclient() {
        return this.adresseclient;
    }

    public Facture adresseclient(String adresseclient) {
        this.setAdresseclient(adresseclient);
        return this;
    }

    public void setAdresseclient(String adresseclient) {
        this.adresseclient = adresseclient;
    }

    public String getTelclient() {
        return this.telclient;
    }

    public Facture telclient(String telclient) {
        this.setTelclient(telclient);
        return this;
    }

    public void setTelclient(String telclient) {
        this.telclient = telclient;
    }

    public String getEmailclien() {
        return this.emailclien;
    }

    public Facture emailclien(String emailclien) {
        this.setEmailclien(emailclien);
        return this;
    }

    public void setEmailclien(String emailclien) {
        this.emailclien = emailclien;
    }

    public Long getPrixunitaire() {
        return this.prixunitaire;
    }

    public Facture prixunitaire(Long prixunitaire) {
        this.setPrixunitaire(prixunitaire);
        return this;
    }

    public void setPrixunitaire(Long prixunitaire) {
        this.prixunitaire = prixunitaire;
    }

    public Long getTotalht() {
        return this.totalht;
    }

    public Facture totalht(Long totalht) {
        this.setTotalht(totalht);
        return this;
    }

    public void setTotalht(Long totalht) {
        this.totalht = totalht;
    }

    public Long getTva() {
        return this.tva;
    }

    public Facture tva(Long tva) {
        this.setTva(tva);
        return this;
    }

    public void setTva(Long tva) {
        this.tva = tva;
    }

    public Long getTotalttc() {
        return this.totalttc;
    }

    public Facture totalttc(Long totalttc) {
        this.setTotalttc(totalttc);
        return this;
    }

    public void setTotalttc(Long totalttc) {
        this.totalttc = totalttc;
    }

    public String getReference() {
        return this.reference;
    }

    public Facture reference(String reference) {
        this.setReference(reference);
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Set<Paiement> getPaiements() {
        return this.paiements;
    }

    public void setPaiements(Set<Paiement> paiements) {
        if (this.paiements != null) {
            this.paiements.forEach(i -> i.setFacture(null));
        }
        if (paiements != null) {
            paiements.forEach(i -> i.setFacture(this));
        }
        this.paiements = paiements;
    }

    public Facture paiements(Set<Paiement> paiements) {
        this.setPaiements(paiements);
        return this;
    }

    public Facture addPaiement(Paiement paiement) {
        this.paiements.add(paiement);
        paiement.setFacture(this);
        return this;
    }

    public Facture removePaiement(Paiement paiement) {
        this.paiements.remove(paiement);
        paiement.setFacture(null);
        return this;
    }

    public BonCommande getBonCommande() {
        return this.bonCommande;
    }

    public void setBonCommande(BonCommande bonCommande) {
        this.bonCommande = bonCommande;
    }

    public Facture bonCommande(BonCommande bonCommande) {
        this.setBonCommande(bonCommande);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Facture)) {
            return false;
        }
        return getId() != null && getId().equals(((Facture) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Facture{" +
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
            "}";
    }
}
