package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.DemandeDevis;
import biz.picosoft.demo.domain.Fournisseur;
import biz.picosoft.demo.domain.Offre;
import biz.picosoft.demo.service.dto.DemandeDevisDTO;
import biz.picosoft.demo.service.dto.FournisseurDTO;
import biz.picosoft.demo.service.dto.OffreDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Offre} and its DTO {@link OffreDTO}.
 */
@Mapper(componentModel = "spring")
public interface OffreMapper extends EntityMapper<OffreDTO, Offre> {
    @Mapping(target = "fournisseur", source = "fournisseur", qualifiedByName = "fournisseurId")
    @Mapping(target = "demandeDevis", source = "demandeDevis", qualifiedByName = "demandeDevisId")
    OffreDTO toDto(Offre s);

    @Named("fournisseurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FournisseurDTO toDtoFournisseurId(Fournisseur fournisseur);

    @Named("demandeDevisId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DemandeDevisDTO toDtoDemandeDevisId(DemandeDevis demandeDevis);
}
