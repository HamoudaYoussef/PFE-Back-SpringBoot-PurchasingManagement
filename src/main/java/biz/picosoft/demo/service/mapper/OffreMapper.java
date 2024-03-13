package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.domain.Fournisseur;
import biz.picosoft.demo.domain.Offre;
import biz.picosoft.demo.domain.Produit;
import biz.picosoft.demo.service.dto.DemandeAchatDTO;
import biz.picosoft.demo.service.dto.FournisseurDTO;
import biz.picosoft.demo.service.dto.OffreDTO;
import biz.picosoft.demo.service.dto.ProduitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Offre} and its DTO {@link OffreDTO}.
 */
@Mapper(componentModel = "spring")
public interface OffreMapper extends EntityMapper<OffreDTO, Offre> {
    @Mapping(target = "fournisseur", source = "fournisseur", qualifiedByName = "fournisseurIdfournisseur")
    @Mapping(target = "demandeachat", source = "demandeachat", qualifiedByName = "demandeAchatId")
    @Mapping(target = "produit", source = "produit", qualifiedByName = "produitId")
    OffreDTO toDto(Offre s);

    @Named("fournisseurIdfournisseur")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FournisseurDTO toDtoFournisseurIdfournisseur(Fournisseur fournisseur);

    @Named("demandeAchatId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DemandeAchatDTO toDtoDemandeAchatId(DemandeAchat demandeAchat);

    @Named("produitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProduitDTO toDtoProduitId(Produit produit);
}
