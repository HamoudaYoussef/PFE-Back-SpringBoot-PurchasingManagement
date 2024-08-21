package biz.picosoft.demo.service.mapper;


import biz.picosoft.demo.domain.Fournisseur;
import biz.picosoft.demo.domain.Produit;
import biz.picosoft.demo.domain.ProduitOffert;
import biz.picosoft.demo.service.dto.FournisseurDTO;
import biz.picosoft.demo.service.dto.ProduitDTO;
import biz.picosoft.demo.service.dto.ProduitOffertDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fournisseur} and its DTO {@link FournisseurDTO}.
 */
@Mapper(componentModel = "spring")
public interface FournisseurMapper extends EntityMapper<FournisseurDTO, Fournisseur> {
    @Mapping(target = "produit", ignore = true)
    @Mapping(target = "produitOfferts", source = "produitOfferts")
    FournisseurDTO toDto(Fournisseur fournisseur);
    @Mapping(target = "produitOfferts", source = "produitOfferts")
    Fournisseur toEntity(FournisseurDTO fournisseurDTO);

    @Named("produitOffertId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProduitOffertDTO toDtoProduitOffertId(ProduitOffert produitOffert);
}
