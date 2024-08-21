package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.service.dto.DemandeAchatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DemandeAchat} and its DTO {@link DemandeAchatDTO}.
 */
@Mapper(componentModel = "spring")
public interface DemandeAchatMapper extends EntityMapper<DemandeAchatDTO, DemandeAchat> {}
