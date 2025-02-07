package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.domain.DemandeDevis;
import biz.picosoft.demo.domain.Fournisseur;
import biz.picosoft.demo.domain.Offre;
import biz.picosoft.demo.service.dto.DemandeAchatDTO;
import biz.picosoft.demo.service.dto.DemandeDevisDTO;
import biz.picosoft.demo.service.dto.FournisseurDTO;
import biz.picosoft.demo.service.dto.OffreDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link DemandeDevis} and its DTO {@link DemandeDevisDTO}.
 */
@Mapper(componentModel = "spring",uses = { FournisseurMapper.class, DemandeAchatMapper.class })
public interface DemandeDevisMapper extends EntityMapper<DemandeDevisDTO, DemandeDevis> {


    @Mapping(target = "fournisseur", source = "fournisseur", qualifiedByName = "fournisseurId")
    @Mapping(target = "demandeAchat", source = "demandeAchat", qualifiedByName = "demandeAchatId")
    DemandeDevisDTO toDto(DemandeDevis s);

    @Named("fournisseurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FournisseurDTO toDtoFournisseurId(Fournisseur fournisseur);

    @Named("demandeAchatId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "datedemande", source = "datedemande")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "nom", source = "nom")
    DemandeAchatDTO toDtoDemandeAchatId(DemandeAchat demandeAchat);


}

