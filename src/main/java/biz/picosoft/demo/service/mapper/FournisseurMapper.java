package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.Fournisseur;
import biz.picosoft.demo.service.dto.FournisseurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fournisseur} and its DTO {@link FournisseurDTO}.
 */
@Mapper(componentModel = "spring")
public interface FournisseurMapper extends EntityMapper<FournisseurDTO, Fournisseur> {}
