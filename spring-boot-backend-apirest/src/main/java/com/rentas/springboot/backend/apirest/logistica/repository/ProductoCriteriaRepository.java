package com.rentas.springboot.backend.apirest.logistica.repository;
 
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import com.rentas.springboot.backend.apirest.logistica.models.ProductoPage;
import com.rentas.springboot.backend.apirest.logistica.models.ProductoSearchCriteria;
import com.rentas.springboot.backend.apirest.logistica.models.Productox;

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
public class ProductoCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public ProductoCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

	public Page<Productox> findAllWithFilters(ProductoPage productoPage,
                                             ProductoSearchCriteria productoSearchCriteria){
        CriteriaQuery<Productox> criteriaQuery = criteriaBuilder.createQuery(Productox.class);
        Root<Productox> productoRoot = criteriaQuery.from(Productox.class);
        Predicate predicate = getPredicate(productoSearchCriteria, productoRoot);
        criteriaQuery.where(predicate);
        setOrder(productoPage, criteriaQuery, productoRoot);

        TypedQuery<Productox> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(productoPage.getPageNumber() * productoPage.getPageSize());
        typedQuery.setMaxResults(productoPage.getPageSize());

        Pageable pageable = getPageable(productoPage);

        long productosCount = getProductoxsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, productosCount);
    }

    private Predicate getPredicate(ProductoSearchCriteria productoSearchCriteria,
                                   Root<Productox> productoRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(productoSearchCriteria.getProductocodigo())){
            predicates.add(
                    criteriaBuilder.like(criteriaBuilder.lower(productoRoot.get("productocodigo")),
                            "%" + productoSearchCriteria.getProductocodigo().toLowerCase() + "%")
            );
        }  
        if(Objects.nonNull(productoSearchCriteria.getDescripcion())){
            predicates.add(
                    criteriaBuilder.like(criteriaBuilder.lower(productoRoot.get("descripcion")),
                            "%" + productoSearchCriteria.getDescripcion().toLowerCase() + "%")
            );
        }      
        if(Objects.nonNull(productoSearchCriteria.getEstado())){
            predicates.add(
                    criteriaBuilder.like(productoRoot.get("estado"),"%" + productoSearchCriteria.getEstado() + "%")
            );
        }   
        if(Objects.nonNull(productoSearchCriteria.getMarcaid())){
            predicates.add(
            		criteriaBuilder.in(productoRoot.get("marcaid")).value(productoSearchCriteria.getMarcaid())
            );
        }        
        if(Objects.nonNull(productoSearchCriteria.getTipoproductoid())){
            predicates.add(
            		criteriaBuilder.in(productoRoot.get("tipoproductoid")).value(productoSearchCriteria.getTipoproductoid())                    
            );
        }
        
        if(Objects.nonNull(productoSearchCriteria.getLineaid())){
            predicates.add(
            		criteriaBuilder.in(productoRoot.get("subcategoriaid").get("categoriaid").get("lineaid")).value(productoSearchCriteria.getLineaid())                    
            );
        } 
        
        if(Objects.nonNull(productoSearchCriteria.getCategoriaid())){
            predicates.add(
            		criteriaBuilder.in(productoRoot.get("subcategoriaid").get("categoriaid")).value(productoSearchCriteria.getCategoriaid())                    
            );
        }
        if(Objects.nonNull(productoSearchCriteria.getSubcategoriaid())){
            predicates.add(
            		criteriaBuilder.in(productoRoot.get("subcategoriaid")).value(productoSearchCriteria.getSubcategoriaid())                    
            );
        }
        
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(ProductoPage productoPage,
                          CriteriaQuery<Productox> criteriaQuery,
                          Root<Productox> productoRoot) {
        if(productoPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(productoRoot.get(productoPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(productoRoot.get(productoPage.getSortBy())));
        }
    }

    private Pageable getPageable(ProductoPage productoPage) {
        Sort sort = Sort.by(productoPage.getSortDirection(), productoPage.getSortBy());
        return PageRequest.of(productoPage.getPageNumber(),productoPage.getPageSize(), sort);
    }

    private long getProductoxsCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Productox> countRoot = countQuery.from(Productox.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
