package biz.picosoft.demo.service.mapper;

import biz.picosoft.demo.domain.BonCommande;
import biz.picosoft.demo.domain.Fournisseur;
import biz.picosoft.demo.domain.Offre;
import biz.picosoft.demo.domain.ProduitOffert;
import biz.picosoft.demo.service.dto.*;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BonCommandeInputMapper extends EntityMapper<BonCommandeInputDTO, BonCommande> {

}