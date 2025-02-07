package biz.picosoft.demo.service.dto;

import biz.picosoft.demo.client.kernel.model.objects.ObjectsDTO;
import biz.picosoft.demo.domain.ennumeration.InfoPaiement;
import biz.picosoft.demo.domain.ennumeration.TypeLivraison;

import java.io.Serializable;
import java.time.LocalDate;

public class BonCommandeOutputDTO extends ObjectsDTO implements Serializable
{

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

    public InfoPaiement getInfopaiement() {
        return infopaiement;
    }

    public void setInfopaiement(InfoPaiement infopaiement) {
        this.infopaiement = infopaiement;
    }

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


}
