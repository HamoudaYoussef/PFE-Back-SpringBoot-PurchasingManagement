package biz.picosoft.demo.service.mapper;


import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.domain.Fournisseur;
import biz.picosoft.demo.domain.Offre;
import biz.picosoft.demo.domain.ProduitOffert;
import biz.picosoft.demo.service.dto.DemandeAchatDTO;
import biz.picosoft.demo.service.dto.FournisseurDTO;
import biz.picosoft.demo.service.dto.OffreDTO;
import biz.picosoft.demo.service.dto.ProduitOffertDTO;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface OffreMapper extends EntityMapper<OffreDTO, Offre> {

    @Named("offreId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OffreDTO toDtoOffreId(Offre offre);

    @Mapping(target = "demandeachat", source = "demandeachat", qualifiedByName = "demandeAchatId")
    @Mapping(target = "fournisseur", source = "fournisseur", qualifiedByName = "fournisseurId")
    @Mapping(target = "produitOfferts", source = "produitOfferts")
    OffreDTO toDto(Offre s);

    @Named("demandeAchatId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DemandeAchatDTO toDtoDemandeAchatId(DemandeAchat demandeAchat);

    @Named("fournisseurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FournisseurDTO toDtoFournisseurId(Fournisseur fournisseur);

    @Named("produitOffertId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProduitOffertDTO toDtoProduitOffertId(ProduitOffert produitOffert);


}
