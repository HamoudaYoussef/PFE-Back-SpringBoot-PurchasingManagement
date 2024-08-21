package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.BonCommande;
import biz.picosoft.demo.domain.Facture;
import biz.picosoft.demo.service.dto.BonCommandeDTO;
import biz.picosoft.demo.service.dto.FactureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Facture} and its DTO {@link FactureDTO}.
 */
@Mapper(componentModel = "spring")
public interface FactureMapper extends EntityMapper<FactureDTO, Facture> {
    @Mapping(target = "bonCommande", source = "bonCommande", qualifiedByName = "bonCommandeId")
    FactureDTO toDto(Facture s);

    @Named("bonCommandeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BonCommandeDTO toDtoBonCommandeId(BonCommande bonCommande);
}
