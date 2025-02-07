package biz.picosoft.demo.service.dto;

import biz.picosoft.demo.domain.ennumeration.InfoPaiement;
import biz.picosoft.demo.domain.ennumeration.TypeLivraison;

import java.time.LocalDate;
import java.util.Objects;

public class BonCommandeInputDTO {

    private Long id;

    private LocalDate dateboncommande;

    private String reference;

    public String getAdresselivraison() {
        return adresselivraison;
    }

    public void setAdresselivraison(String adresselivraison) {
        this.adresselivraison = adresselivraison;
    }

    private String adresselivraison;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    private String numero;


    private LocalDate delailivraison;
    private TypeLivraison typelivraison;

    private Long fraislivraison;



    public LocalDate getDelailivraison() {
        return delailivraison;
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



    public Long getMontanttotal() {
        return montanttotal;
    }

    public void setMontanttotal(Long montanttotal) {
        this.montanttotal = montanttotal;
    }



    public InfoPaiement getInfopaiement() {
        return infopaiement;
    }

    public void setInfopaiement(InfoPaiement infopaiement) {
        this.infopaiement = infopaiement;
    }

    public void setDelailivraison(LocalDate delailivraison) {
        this.delailivraison = delailivraison;
    }


    private Long montanttotal;


    private InfoPaiement infopaiement;



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







    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BonCommandeInputDTO that = (BonCommandeInputDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(dateboncommande, that.dateboncommande) && Objects.equals(reference, that.reference)
&& Objects.equals(delailivraison, that.delailivraison)&& Objects.equals(typelivraison, that.typelivraison)&& Objects.equals(fraislivraison, that.fraislivraison)&&  Objects.equals(montanttotal, that.montanttotal)&& Objects.equals(infopaiement, that.infopaiement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateboncommande, reference,delailivraison,typelivraison,fraislivraison, montanttotal, infopaiement);
    }







}
