package biz.picosoft.demo.repository;

import java.util.List;
import java.util.Optional;

import biz.picosoft.demo.domain.Facture;
import org.springframework.data.domain.Page;

public interface FactureRepositoryWithBagRelationships {
    Optional<Facture> fetchBagRelationships(Optional<Facture> facture);

    List<Facture> fetchBagRelationships(List<Facture> factures);

    Page<Facture> fetchBagRelationships(Page<Facture> factures);
}
