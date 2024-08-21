package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.ProduitOffert;
import biz.picosoft.demo.service.dto.ProduitOffertDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ProduitOffert} and its DTO {@link ProduitOffertDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProduitOffertMapper extends EntityMapper<ProduitOffertDTO, ProduitOffert> {
    @Mapping(target = "offreId", source = "offre.id")
    @Mapping(target = "fournisseurId", source = "fournisseur.id")
    ProduitOffertDTO toDto(ProduitOffert produitOffert);

    @Mapping(target = "fournisseur",source = "fournisseurId", ignore = true)
    @Mapping(target = "offre",source = "offreId", ignore = true)
    ProduitOffert toEntity(ProduitOffertDTO produitOffertDTO);

    default ProduitOffert fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProduitOffert produitOffert = new ProduitOffert();
        produitOffert.setId(id);
        return produitOffert;
    }
}
