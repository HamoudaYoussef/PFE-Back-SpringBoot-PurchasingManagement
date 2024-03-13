package biz.picosoft.demo.repository;

import biz.picosoft.demo.domain.Facture;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class FactureRepositoryWithBagRelationshipsImpl implements FactureRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Facture> fetchBagRelationships(Optional<Facture> facture) {
        return facture.map(this::fetchBonlivraisons);
    }

    @Override
    public Page<Facture> fetchBagRelationships(Page<Facture> factures) {
        return new PageImpl<>(fetchBagRelationships(factures.getContent()), factures.getPageable(), factures.getTotalElements());
    }

    @Override
    public List<Facture> fetchBagRelationships(List<Facture> factures) {
        return Optional.of(factures).map(this::fetchBonlivraisons).orElse(Collections.emptyList());
    }

    Facture fetchBonlivraisons(Facture result) {
        return entityManager
            .createQuery("select facture from Facture facture left join fetch facture.bonlivraisons where facture.id = :id", Facture.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Facture> fetchBonlivraisons(List<Facture> factures) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, factures.size()).forEach(index -> order.put(factures.get(index).getId(), index));
        List<Facture> result = entityManager
            .createQuery(
                "select facture from Facture facture left join fetch facture.bonlivraisons where facture in :factures",
                Facture.class
            )
            .setParameter("factures", factures)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
