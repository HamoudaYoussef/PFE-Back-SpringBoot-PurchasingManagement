package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.BonLivraison;
import biz.picosoft.demo.service.dto.BonLivraisonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BonLivraison} and its DTO {@link BonLivraisonDTO}.
 */
@Mapper(componentModel = "spring")
public interface BonLivraisonMapper extends EntityMapper<BonLivraisonDTO, BonLivraison> {}
