package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.BonCommande;
import biz.picosoft.demo.domain.Offre;
import biz.picosoft.demo.domain.ProduitOffert;
import biz.picosoft.demo.service.dto.BonCommandeOutputDTO;

import biz.picosoft.demo.service.dto.OffreDTO;
import biz.picosoft.demo.service.dto.ProduitOffertDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = ProduitOffertMapper.class)
public interface BonCommandeOutputMapper extends EntityMapper<BonCommandeOutputDTO, BonCommande> {

}
