package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.Facture;
import biz.picosoft.demo.domain.Paiement;
import biz.picosoft.demo.service.dto.FactureDTO;
import biz.picosoft.demo.service.dto.PaiementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Paiement} and its DTO {@link PaiementDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaiementMapper extends EntityMapper<PaiementDTO, Paiement> {
    @Mapping(target = "facture", source = "facture", qualifiedByName = "factureId")
    PaiementDTO toDto(Paiement s);

    @Named("factureId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FactureDTO toDtoFactureId(Facture facture);
}
