package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.BonCommande;
import biz.picosoft.demo.domain.Produit;
import biz.picosoft.demo.domain.ProduitCommandee;
import biz.picosoft.demo.service.dto.BonCommandeDTO;
import biz.picosoft.demo.service.dto.ProduitCommandeeDTO;
import biz.picosoft.demo.service.dto.ProduitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProduitCommandee} and its DTO {@link ProduitCommandeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProduitCommandeeMapper extends EntityMapper<ProduitCommandeeDTO, ProduitCommandee> {
    @Mapping(target = "boncommande", source = "boncommande", qualifiedByName = "bonCommandeIdboncommande")
    @Mapping(target = "produit", source = "produit", qualifiedByName = "produitIdproduit")
    ProduitCommandeeDTO toDto(ProduitCommandee s);

    @Named("bonCommandeIdboncommande")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BonCommandeDTO toDtoBonCommandeIdboncommande(BonCommande bonCommande);

    @Named("produitIdproduit")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProduitDTO toDtoProduitIdproduit(Produit produit);
}
