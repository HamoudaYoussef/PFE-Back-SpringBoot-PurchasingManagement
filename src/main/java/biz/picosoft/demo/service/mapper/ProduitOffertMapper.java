package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.BonCommande;
import biz.picosoft.demo.domain.Offre;
import biz.picosoft.demo.domain.Produit;
import biz.picosoft.demo.domain.ProduitOffert;
import biz.picosoft.demo.service.dto.BonCommandeDTO;
import biz.picosoft.demo.service.dto.OffreDTO;
import biz.picosoft.demo.service.dto.ProduitDTO;
import biz.picosoft.demo.service.dto.ProduitOffertDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link ProduitOffert} and its DTO {@link ProduitOffertDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProduitOffertMapper extends EntityMapper<ProduitOffertDTO, ProduitOffert> {
    @Mapping(target = "produit", source = "produit", qualifiedByName = "produitId")
    @Mapping(target = "offre", source = "offre", qualifiedByName = "offreId")
    @Mapping(target = "bonCommande", source = "bonCommande", qualifiedByName = "bonCommandeId")

    ProduitOffertDTO toDto(ProduitOffert s);

    @Named("produitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProduitDTO toDtoProduitId(Produit produit);

    @Named("offreId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OffreDTO toDtoOffreId(Offre offre);

    @Named("bonCommandeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BonCommandeDTO toDtoBonCommandeId(BonCommande bonCommande);
}
