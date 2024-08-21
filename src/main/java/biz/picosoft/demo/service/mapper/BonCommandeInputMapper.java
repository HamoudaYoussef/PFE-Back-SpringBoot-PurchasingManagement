package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.BonCommande;
import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.domain.Offre;
import biz.picosoft.demo.service.dto.BonCommandeDTO;
import biz.picosoft.demo.service.dto.BonCommandeInputDTO;
import biz.picosoft.demo.service.dto.DemandeAchatInputDTO;
import biz.picosoft.demo.service.dto.OffreDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = OffreMapper.class)
public abstract class BonCommandeInputMapper implements EntityMapper<BonCommandeInputDTO, BonCommande>{

    @Mapping(target = "offre", source = "offre", qualifiedByName = "offreId")
    public abstract BonCommandeInputDTO toDto(BonCommande bonCommande);

    @Mapping(target = "offre", source = "offre")
    public abstract BonCommande toEntity(BonCommandeInputDTO bonCommandeInputDTO);

}
