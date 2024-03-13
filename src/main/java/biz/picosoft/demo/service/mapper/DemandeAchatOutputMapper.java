package biz.picosoft.demo.service.mapper;


import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.service.dto.DemandeAchatOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Named;


/**
 */
@Named("invoiceOutputMapper")
@Mapper(componentModel = "spring", uses = {})
public abstract class DemandeAchatOutputMapper implements EntityMapper<DemandeAchatOutputDTO, DemandeAchat> {


    public abstract  DemandeAchatOutputDTO toDto(DemandeAchat invoice);

    public abstract  DemandeAchat toEntity(DemandeAchatOutputDTO invoiceOutputDTO);

}
