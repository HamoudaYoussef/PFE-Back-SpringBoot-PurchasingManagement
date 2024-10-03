package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.ennumeration.StatutDA;
import biz.picosoft.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatistiqueService {
    @Autowired
    private DemandeAchatRepository demandeAchatRepository;

    @Autowired
    private BonCommandeRepository bonCommandeRepository;

    @Autowired
    private DemandeDevisRepository demandeDevisRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private OffreRepository offreRepository;

    public Map<String, Long> getStatistiques() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("DemandesAchat", demandeAchatRepository.count());
        stats.put("BonsCommande", bonCommandeRepository.count());
        stats.put("DemandesDevis", demandeDevisRepository.count());
        stats.put("Produits", produitRepository.count());
        stats.put("Offres", offreRepository.count());
        return stats;
    }
    public Map<String, Long> getDemandesAchatParMois() {
        Map<String, Long> stats = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            stats.put(getMois(i), demandeAchatRepository.countByDateCreationMois(i));
        }
        return stats;
    }

    // Nombre de bons de commande par mois
    public Map<String, Long> getBonsCommandeParMois() {
        Map<String, Long> stats = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            stats.put(getMois(i), bonCommandeRepository.countByDateCreationMois(i));
        }
        return stats;
    }

    // Nombre de demandes de devis par mois
    /*public Map<String, Long> getDemandesDevisParMois() {
        Map<String, Long> stats = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            stats.put(getMois(i), demandeDevisRepository.countByDateCreationMois(i));
        }
        return stats;
    }*/

    // Nombre de produits par catégorie
 /*   public Map<String, Long> getProduitsParCategorie() {
        Map<String, Long> stats = new HashMap<>();
        for (CategorieProduit categorie : CategorieProduit.values()) {
            stats.put(categorie.name(), produitRepository.countByCategorie(categorie));
        }
        return stats;
    }
*/
    // Nombre d'offres par mois
    public Map<String, Long> getOffresParMois() {
        Map<String, Long> stats = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            stats.put(getMois(i), offreRepository.countByDateCreationMois(i));
        }
        return stats;
    }



    // Nombre de demandes d'achat par région

    public Map<String, Long> getDemandesAchatParStatut() {
        Map<String, Long> stats = new HashMap<>();
        stats.put(StatutDA.en_attente.name(), demandeAchatRepository.countByStatut(StatutDA.en_attente));
        stats.put(StatutDA.termine.name(), demandeAchatRepository.countByStatut(StatutDA.termine));
        return stats;
    }


    // Nombre d'offres par région
   /* public Map<String, Long> getOffresParRegion() {
        Map<String, Long> stats = new HashMap<>();
        for (Region region : Region.values()) {
            stats.put(region.name(), offreRepository.countByRegion(region));
        }
        return stats;
    }*/

    private String getMois(int mois) {
        switch (mois) {
            case 1:
                return "Janvier";
            case 2:
                return "Février";
            case 3:
                return "Mars";
            case 4:
                return "Avril";
            case 5:
                return "Mai";
            case 6:
                return "Juin";
            case 7:
                return "Juillet";
            case 8:
                return "Août";
            case 9:
                return "Septembre";
            case 10:
                return "Octobre";
            case 11:
                return "Novembre";
            case 12:
                return "Décembre";
            default:
                return "";
        }
    }
}
