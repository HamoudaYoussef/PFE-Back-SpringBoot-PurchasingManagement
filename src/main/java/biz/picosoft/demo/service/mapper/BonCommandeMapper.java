package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.*;
import biz.picosoft.demo.service.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BonCommandeMapper extends EntityMapper<BonCommandeDTO, BonCommande> {
    @Mapping(target = "offre", source = "offre", qualifiedByName = "offreId")
    BonCommandeDTO toDto(BonCommande bonCommande);

    @Mapping(target = "offre", source = "offre")
    BonCommande toEntity(BonCommandeDTO bonCommandeDTO);

    @Named("offreId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OffreDTO toDtoOffreId(Offre offre);
}