package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.BonCommande;
import biz.picosoft.demo.domain.BonLivraison;
import biz.picosoft.demo.domain.Facture;
import biz.picosoft.demo.service.dto.BonCommandeDTO;
import biz.picosoft.demo.service.dto.BonLivraisonDTO;
import biz.picosoft.demo.service.dto.FactureDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Facture} and its DTO {@link FactureDTO}.
 */
@Mapper(componentModel = "spring")
public interface FactureMapper extends EntityMapper<FactureDTO, Facture> {
    @Mapping(target = "bonlivraisons", source = "bonlivraisons", qualifiedByName = "bonLivraisonIdSet")
    @Mapping(target = "bonCcmmande", source = "bonCcmmande", qualifiedByName = "bonCommandeId")
    FactureDTO toDto(Facture s);

    @Mapping(target = "removeBonlivraisons", ignore = true)
    Facture toEntity(FactureDTO factureDTO);

    @Named("bonLivraisonId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BonLivraisonDTO toDtoBonLivraisonId(BonLivraison bonLivraison);

    @Named("bonLivraisonIdSet")
    default Set<BonLivraisonDTO> toDtoBonLivraisonIdSet(Set<BonLivraison> bonLivraison) {
        return bonLivraison.stream().map(this::toDtoBonLivraisonId).collect(Collectors.toSet());
    }

    @Named("bonCommandeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BonCommandeDTO toDtoBonCommandeId(BonCommande bonCommande);
}
