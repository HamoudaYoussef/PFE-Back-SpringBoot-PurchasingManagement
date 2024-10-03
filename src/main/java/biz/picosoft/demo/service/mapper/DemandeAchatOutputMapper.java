package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.service.dto.DemandeAchatOutputDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})

public abstract class DemandeAchatOutputMapper implements EntityMapper<DemandeAchatOutputDTO, DemandeAchat> {
    public abstract DemandeAchat toEntity(DemandeAchatOutputDTO demandeOutputDTO);

    public abstract DemandeAchatOutputDTO toDto(DemandeAchat demande);

    DemandeAchat fromId(Long id) {
        if (id == null) {
            return null;
        }
        DemandeAchat requestCase = new DemandeAchat();
        requestCase.setId(id);
        return requestCase;
    }
}