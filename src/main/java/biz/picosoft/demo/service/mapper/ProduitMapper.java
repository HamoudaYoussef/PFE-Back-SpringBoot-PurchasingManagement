package biz.picosoft.demo.service.mapper;


import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.domain.Produit;
import biz.picosoft.demo.service.dto.DemandeAchatDTO;
import biz.picosoft.demo.service.dto.ProduitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Produit} and its DTO {@link ProduitDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProduitMapper extends EntityMapper<ProduitDTO, Produit> {

    @Mapping(target = "fournisseurs", ignore = true)
    ProduitDTO toDto(Produit produit);

    Produit toEntity(ProduitDTO produitDTO);

}
