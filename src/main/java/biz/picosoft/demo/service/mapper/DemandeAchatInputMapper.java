package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.service.dto.DemandeAchatInputDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public abstract class DemandeAchatInputMapper implements EntityMapper<DemandeAchatInputDTO, DemandeAchat>{
    public abstract DemandeAchat toEntity(DemandeAchatInputDTO demandeInputDTO);
    public abstract DemandeAchatInputDTO toDto(DemandeAchat demande);
}
