package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.domain.ProduitDemandee;
import biz.picosoft.demo.service.dto.DemandeAchatDTO;
import biz.picosoft.demo.service.dto.ProduitDemandeeDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 */

@Mapper(componentModel = "spring")
public interface ProduitDemandeeMapper extends EntityMapper<ProduitDemandeeDTO, ProduitDemandee> {

    @Mapping(source = "demandeAchat.id", target = "demandeAchatId")
    ProduitDemandeeDTO toDto(ProduitDemandee produitDemandee);

    @Mapping(source = "demandeAchatId", target = "demandeAchat")
    ProduitDemandee toEntity(ProduitDemandeeDTO produitDemandeeDTO);

    @Named("demandeAchatId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DemandeAchatDTO toDtoDemandeAchatId(DemandeAchat demandeAchat);

    default DemandeAchat fromId(Long id) {
        if (id == null) {
            return null;
        }
        DemandeAchat demandeAchat = new DemandeAchat();
        demandeAchat.setId(id);
        return demandeAchat;
    }
}