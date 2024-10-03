package biz.picosoft.demo.service.mapper;


import biz.picosoft.demo.domain.ProduitCommandee;
import biz.picosoft.demo.service.dto.ProduitCommandeeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = {DemandeDevisMapper.class})
public interface ProduitCommandeeMapper extends EntityMapper<ProduitCommandeeDTO, ProduitCommandee>{
    @Mapping(source = "produit.id", target = "produitId")
    @Mapping(source = "demandeDevis.id", target = "demandeDevisId")
    ProduitCommandeeDTO toDto(ProduitCommandee produitCommandee);

    @Mapping(source = "produitId", target = "produit.id")
    @Mapping(source = "demandeDevisId", target = "demandeDevis.id")
    ProduitCommandee toEntity(ProduitCommandeeDTO produitCommandeeDTO);

    default ProduitCommandee fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProduitCommandee produitCommandee = new ProduitCommandee();
        produitCommandee.setId(id);
        return produitCommandee;
    }

}
