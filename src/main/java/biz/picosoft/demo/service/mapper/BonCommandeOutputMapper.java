package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.BonCommande;

import biz.picosoft.demo.domain.Offre;
import biz.picosoft.demo.service.dto.BonCommandeOutputDTO;

import biz.picosoft.demo.service.dto.OffreDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = OffreMapper.class)

public abstract class BonCommandeOutputMapper implements EntityMapper<BonCommandeOutputDTO, BonCommande>{
    @Mapping(target = "offre", source = "offre", qualifiedByName = "offreId")
    public abstract BonCommandeOutputDTO toDto(BonCommande bonCommande);
    @Mapping(target = "offre", source = "offre")
    public abstract BonCommande toEntity(BonCommandeOutputDTO bonCommandeOutputDTO);


}
