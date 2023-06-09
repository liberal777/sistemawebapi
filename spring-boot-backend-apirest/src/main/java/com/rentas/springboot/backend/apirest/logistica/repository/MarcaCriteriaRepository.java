package com.rentas.springboot.backend.apirest.logistica.repository;
 
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import com.rentas.springboot.backend.apirest.logistica.models.MarcaPage;
import com.rentas.springboot.backend.apirest.logistica.models.MarcaSearchCriteria;
import com.rentas.springboot.backend.apirest.logistica.models.Marca;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class MarcaCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public MarcaCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Marca> findAllWithFilters(MarcaPage tipoPage,
                                             MarcaSearchCriteria tipoSearchCriteria){
        CriteriaQuery<Marca> criteriaQuery = criteriaBuilder.createQuery(Marca.class);
        Root<Marca> tipoRoot = criteriaQuery.from(Marca.class);
        Predicate predicate = getPredicate(tipoSearchCriteria, tipoRoot);
        criteriaQuery.where(predicate);
        setOrder(tipoPage, criteriaQuery, tipoRoot);

        TypedQuery<Marca> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(tipoPage.getPageNumber() * tipoPage.getPageSize());
        typedQuery.setMaxResults(tipoPage.getPageSize());

        Pageable pageable = getPageable(tipoPage);

        long tiposCount = getMarcasCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, tiposCount);
    }

    private Predicate getPredicate(MarcaSearchCriteria tipoSearchCriteria,
                                   Root<Marca> tipoRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(tipoSearchCriteria.getMarcadescripcion())){
            predicates.add(
                    criteriaBuilder.like(criteriaBuilder.lower(tipoRoot.get("marcadescripcion")),
                            "%" + tipoSearchCriteria.getMarcadescripcion().toLowerCase() + "%")
            );
        }
         if(Objects.nonNull(tipoSearchCriteria.getEstado())){
            predicates.add(
                    criteriaBuilder.like(tipoRoot.get("estado"),
                            "%" + tipoSearchCriteria.getEstado() + "%")
            );
        }
      
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(MarcaPage tipoPage,
                          CriteriaQuery<Marca> criteriaQuery,
                          Root<Marca> tipoRoot) {
        if(tipoPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(tipoRoot.get(tipoPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(tipoRoot.get(tipoPage.getSortBy())));
        }
    }

    private Pageable getPageable(MarcaPage tipoPage) {
        Sort sort = Sort.by(tipoPage.getSortDirection(), tipoPage.getSortBy());
        return PageRequest.of(tipoPage.getPageNumber(),tipoPage.getPageSize(), sort);
    }

    private long getMarcasCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Marca> countRoot = countQuery.from(Marca.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
