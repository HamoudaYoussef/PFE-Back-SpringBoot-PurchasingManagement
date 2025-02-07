package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.DemandeDevis;
import biz.picosoft.demo.domain.Produit;
import biz.picosoft.demo.domain.ProduitCommandee;
import biz.picosoft.demo.service.dto.DemandeDevisDTO;
import biz.picosoft.demo.service.dto.ProduitCommandeeDTO;
import biz.picosoft.demo.service.dto.ProduitDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link ProduitCommandee} and its DTO {@link ProduitCommandeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProduitCommandeeMapper extends EntityMapper<ProduitCommandeeDTO, ProduitCommandee> {
    @Mapping(target = "produit", source = "produit", qualifiedByName = "produitId")
    @Mapping(target = "demandeDevis", source = "demandeDevis", qualifiedByName = "demandeDevisId")
    ProduitCommandeeDTO toDto(ProduitCommandee s);

    @Named("produitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProduitDTO toDtoProduitId(Produit produit);

    @Named("demandeDevisId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DemandeDevisDTO toDtoDemandeDevisId(DemandeDevis demandeDevis);
}
