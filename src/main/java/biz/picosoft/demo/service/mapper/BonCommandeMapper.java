package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.BonCommande;
import biz.picosoft.demo.domain.Offre;
import biz.picosoft.demo.domain.ProduitOffert;
import biz.picosoft.demo.service.dto.BonCommandeDTO;
import biz.picosoft.demo.service.dto.OffreDTO;
import biz.picosoft.demo.service.dto.ProduitOffertDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link BonCommande} and its DTO {@link BonCommandeDTO}.
 */
@Mapper(componentModel = "spring")
public interface BonCommandeMapper extends EntityMapper<BonCommandeDTO, BonCommande> {

}
