package biz.picosoft.demo.service.mapper;


import biz.picosoft.demo.domain.BonCommande;
import biz.picosoft.demo.domain.BonLivraison;
import biz.picosoft.demo.service.dto.BonCommandeDTO;
import biz.picosoft.demo.service.dto.BonLivraisonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BonLivraison} and its DTO {@link BonLivraisonDTO}.
 */
@Mapper(componentModel = "spring")
public interface BonLivraisonMapper extends EntityMapper<BonLivraisonDTO, BonLivraison> {
    @Mapping(target = "boncommande", source = "boncommande", qualifiedByName = "bonCommandeId")
    BonLivraisonDTO toDto(BonLivraison s);

    @Named("bonCommandeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BonCommandeDTO toDtoBonCommandeId(BonCommande bonCommande);
}
