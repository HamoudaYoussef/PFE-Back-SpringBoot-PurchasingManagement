package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.Offre;
import biz.picosoft.demo.domain.Produit;
import biz.picosoft.demo.domain.ProduitOffert;
import biz.picosoft.demo.service.dto.OffreDTO;
import biz.picosoft.demo.service.dto.ProduitDTO;
import biz.picosoft.demo.service.dto.ProduitOffertDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProduitOffert} and its DTO {@link ProduitOffertDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProduitOffertMapper extends EntityMapper<ProduitOffertDTO, ProduitOffert> {
    @Mapping(target = "offre", source = "offre", qualifiedByName = "offreId")
    @Mapping(target = "produit", source = "produit", qualifiedByName = "produitId")
    ProduitOffertDTO toDto(ProduitOffert s);

    @Named("offreId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OffreDTO toDtoOffreId(Offre offre);

    @Named("produitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProduitDTO toDtoProduitId(Produit produit);
}
