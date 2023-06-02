package com.rentas.springboot.backend.apirest.logistica.repository;
 
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import com.rentas.springboot.backend.apirest.logistica.models.TipoPage;
import com.rentas.springboot.backend.apirest.logistica.models.TipoSearchCriteria;
import com.rentas.springboot.backend.apirest.logistica.models.Tipo;

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
public class TipoCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public TipoCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Tipo> findAllWithFilters(TipoPage tipoPage,
                                             TipoSearchCriteria tipoSearchCriteria){
        CriteriaQuery<Tipo> criteriaQuery = criteriaBuilder.createQuery(Tipo.class);
        Root<Tipo> tipoRoot = criteriaQuery.from(Tipo.class);
        Predicate predicate = getPredicate(tipoSearchCriteria, tipoRoot);
        criteriaQuery.where(predicate);
        setOrder(tipoPage, criteriaQuery, tipoRoot);

        TypedQuery<Tipo> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(tipoPage.getPageNumber() * tipoPage.getPageSize());
        typedQuery.setMaxResults(tipoPage.getPageSize());

        Pageable pageable = getPageable(tipoPage);

        long tiposCount = getTiposCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, tiposCount);
    }

    private Predicate getPredicate(TipoSearchCriteria tipoSearchCriteria,
                                   Root<Tipo> tipoRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(tipoSearchCriteria.getTipoproductodescripcion())){
            predicates.add(
                    criteriaBuilder.like(criteriaBuilder.lower(tipoRoot.get("tipoproductodescripcion")),
                            "%" + tipoSearchCriteria.getTipoproductodescripcion().toLowerCase() + "%")
            );
        }
         if(Objects.nonNull(tipoSearchCriteria.getEstado())){
            predicates.add(
                    criteriaBuilder.like(tipoRoot.get("estado"),
                            "%" + tipoSearchCriteria.getEstado() + "%")
            );
        }
         /*
        if(Objects.nonNull(tipoSearchCriteria.getTipotipoid())){
            predicates.add(
                    criteriaBuilder.like(tipoRoot.get("tipotipoid"),
                            "%" + tipoSearchCriteria.getTipotipoid() + "%")
            );
        }
        */
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(TipoPage tipoPage,
                          CriteriaQuery<Tipo> criteriaQuery,
                          Root<Tipo> tipoRoot) {
        if(tipoPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(tipoRoot.get(tipoPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(tipoRoot.get(tipoPage.getSortBy())));
        }
    }

    private Pageable getPageable(TipoPage tipoPage) {
        Sort sort = Sort.by(tipoPage.getSortDirection(), tipoPage.getSortBy());
        return PageRequest.of(tipoPage.getPageNumber(),tipoPage.getPageSize(), sort);
    }

    private long getTiposCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Tipo> countRoot = countQuery.from(Tipo.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
