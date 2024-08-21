package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.domain.DemandeDevis;
import biz.picosoft.demo.domain.Fournisseur;
import biz.picosoft.demo.service.dto.DemandeAchatDTO;
import biz.picosoft.demo.service.dto.DemandeDevisDTO;
import biz.picosoft.demo.service.dto.FournisseurDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DemandeDevis} and its DTO {@link DemandeDevisDTO}.
 */
@Mapper(componentModel = "spring", uses = {FournisseurMapper.class, DemandeAchatMapper.class})
public interface DemandeDevisMapper extends EntityMapper<DemandeDevisDTO, DemandeDevis> {

    @Mapping(source = "fournisseurId", target = "fournisseur.id")
    @Mapping(source = "demandeAchatId", target = "demandeAchat.id")
    DemandeDevis toEntity(DemandeDevisDTO demandeDevisDTO);

    @Mapping(source = "fournisseur.id", target = "fournisseurId")
    @Mapping(source = "demandeAchat.id", target = "demandeAchatId")
    DemandeDevisDTO toDto(DemandeDevis demandeDevis);

    default DemandeDevis fromId(Long id) {
        if (id == null) {
            return null;
        }
        DemandeDevis demandeDevis = new DemandeDevis();
        demandeDevis.setId(id);
        return demandeDevis;
    }
}