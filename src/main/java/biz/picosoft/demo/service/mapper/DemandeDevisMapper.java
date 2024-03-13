package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.DemandeDevis;
import com.mycompany.demo.service.dto.DemandeDevisDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DemandeDevis} and its DTO {@link DemandeDevisDTO}.
 */
@Mapper(componentModel = "spring")
public interface DemandeDevisMapper extends EntityMapper<DemandeDevisDTO, DemandeDevis> {}
