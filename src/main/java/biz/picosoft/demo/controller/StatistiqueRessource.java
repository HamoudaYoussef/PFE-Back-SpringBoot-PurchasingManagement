package biz.picosoft.demo.controller;

import biz.picosoft.demo.domain.ennumeration.Region;
import biz.picosoft.demo.service.StatistiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/statistiques")
public class StatistiqueRessource {
    @Autowired
    private StatistiqueService statistiqueService;

    @GetMapping("/getStat")
    public Map<String, Long> getStatistiques() {
        return statistiqueService.getStatistiques();
    }
    @GetMapping("/demande-achat-par-mois")
    public Map<String, Long> getDemandesAchatParMois() {
        return statistiqueService.getDemandesAchatParMois();
    }

    @GetMapping("/bon-commande-par-mois")
    public Map<String, Long> getBonsCommandeParMois() {
        return statistiqueService.getBonsCommandeParMois();
    }

    @GetMapping("/offres-par-mois")
    public Map<String, Long> getOffresParMois() {
        return statistiqueService.getOffresParMois();
    }

    @GetMapping("/demande-achat-par-region")
    public Map<String, Long> getDemandesAchatParRegion() {
        return statistiqueService.getDemandesAchatParRegion();
    }

    @GetMapping("/demande-achat-par-statut")
    public Map<String, Long> getDemandesAchatParStatut() {
        return statistiqueService.getDemandesAchatParStatut();
    }

}
