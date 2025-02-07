package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.domain.Produit;
import biz.picosoft.demo.domain.ProduitDemandee;
import biz.picosoft.demo.service.dto.DemandeAchatDTO;
import biz.picosoft.demo.service.dto.ProduitDTO;
import biz.picosoft.demo.service.dto.ProduitDemandeeDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link ProduitDemandee} and its DTO {@link ProduitDemandeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProduitDemandeeMapper extends EntityMapper<ProduitDemandeeDTO, ProduitDemandee> {
    @Mapping(target = "produit", source = "produit", qualifiedByName = "produitId")
    @Mapping(target = "demandeAchat", source = "demandeAchat", qualifiedByName = "demandeAchatId")
    ProduitDemandeeDTO toDto(ProduitDemandee s);

    @Named("produitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProduitDTO toDtoProduitId(Produit produit);

    @Named("demandeAchatId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DemandeAchatDTO toDtoDemandeAchatId(DemandeAchat demandeAchat);
}
