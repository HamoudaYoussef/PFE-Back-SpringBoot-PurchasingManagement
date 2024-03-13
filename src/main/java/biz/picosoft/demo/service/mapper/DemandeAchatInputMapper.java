package biz.picosoft.demo.service.mapper;


import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.service.dto.DemandeAchatInputDTO;
import biz.picosoft.demo.service.dto.DemandeAchatOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Named;


/**
 */
@Named("invoiceInputMapper")
@Mapper(componentModel = "spring", uses = {})
public abstract class DemandeAchatInputMapper implements EntityMapper<DemandeAchatInputDTO, DemandeAchat> {


    public abstract  DemandeAchatInputDTO toDto(DemandeAchat demandeAchat);

    public abstract  DemandeAchat toEntity(DemandeAchatInputDTO invoiceInputDTO);


}
