package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.BonCommande;
import biz.picosoft.demo.domain.Offre;
import biz.picosoft.demo.service.dto.BonCommandeDTO;
import biz.picosoft.demo.service.dto.OffreDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BonCommande} and its DTO {@link BonCommandeDTO}.
 */
@Mapper(componentModel = "spring")
public interface BonCommandeMapper extends EntityMapper<BonCommandeDTO, BonCommande> {
    @Mapping(target = "offre", source = "offre", qualifiedByName = "offreId")
    BonCommandeDTO toDto(BonCommande s);

    @Named("offreId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OffreDTO toDtoOffreId(Offre offre);
}
