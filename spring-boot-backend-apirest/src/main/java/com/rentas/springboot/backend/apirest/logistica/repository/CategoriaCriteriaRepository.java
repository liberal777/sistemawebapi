package com.rentas.springboot.backend.apirest.logistica.repository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import com.rentas.springboot.backend.apirest.logistica.models.CategoriaPage;
import com.rentas.springboot.backend.apirest.logistica.models.CategoriaSearchCriteria;
import com.rentas.springboot.backend.apirest.logistica.models.Categoria;

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
public class CategoriaCriteriaRepository {

	private final EntityManager entityManager;
	private final CriteriaBuilder criteriaBuilder;

	public CategoriaCriteriaRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.criteriaBuilder = entityManager.getCriteriaBuilder();
	}

	public Page<Categoria> findAllWithFilters(CategoriaPage categoriaPage,
			CategoriaSearchCriteria categoriaSearchCriteria) {
		CriteriaQuery<Categoria> criteriaQuery = criteriaBuilder.createQuery(Categoria.class);
		Root<Categoria> categoriaRoot = criteriaQuery.from(Categoria.class);
		Predicate predicate = getPredicate(categoriaSearchCriteria, categoriaRoot);
		criteriaQuery.where(predicate);
		setOrder(categoriaPage, criteriaQuery, categoriaRoot);

		TypedQuery<Categoria> typedQuery = entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult(categoriaPage.getPageNumber() * categoriaPage.getPageSize());
		typedQuery.setMaxResults(categoriaPage.getPageSize());

		Pageable pageable = getPageable(categoriaPage);

		long categoriasCount = getCategoriasCount(predicate);

		return new PageImpl<>(typedQuery.getResultList(), pageable, categoriasCount);
	}

	private Predicate getPredicate(CategoriaSearchCriteria categoriaSearchCriteria, Root<Categoria> categoriaRoot) {
		List<Predicate> predicates = new ArrayList<>();
		if (Objects.nonNull(categoriaSearchCriteria.getCategoriacodigo())) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(categoriaRoot.get("categoriacodigo")),
					"%" + categoriaSearchCriteria.getCategoriacodigo().toLowerCase() + "%"));
		}
		if (Objects.nonNull(categoriaSearchCriteria.getCategoriadescripcion())) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(categoriaRoot.get("categoriadescripcion")),
					"%" + categoriaSearchCriteria.getCategoriadescripcion().toLowerCase() + "%"));
		}
		if (Objects.nonNull(categoriaSearchCriteria.getEstado())) {
			predicates.add(
					criteriaBuilder.like(categoriaRoot.get("estado"), "%" + categoriaSearchCriteria.getEstado() + "%"));
		}
		if (Objects.nonNull(categoriaSearchCriteria.getLineaid())) {
			predicates
					.add(criteriaBuilder.in(categoriaRoot.get("lineaid")).value(categoriaSearchCriteria.getLineaid()));
		}
	    
	        

		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}

	private void setOrder(CategoriaPage categoriaPage, CriteriaQuery<Categoria> criteriaQuery,
			Root<Categoria> categoriaRoot) {
		if (categoriaPage.getSortDirection().equals(Sort.Direction.ASC)) {
			criteriaQuery.orderBy(criteriaBuilder.asc(categoriaRoot.get(categoriaPage.getSortBy())));
		} else {
			criteriaQuery.orderBy(criteriaBuilder.desc(categoriaRoot.get(categoriaPage.getSortBy())));
		}
	}

	private Pageable getPageable(CategoriaPage categoriaPage) {
		Sort sort = Sort.by(categoriaPage.getSortDirection(), categoriaPage.getSortBy());
		return PageRequest.of(categoriaPage.getPageNumber(), categoriaPage.getPageSize(), sort);
	}

	private long getCategoriasCount(Predicate predicate) {
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<Categoria> countRoot = countQuery.from(Categoria.class);
		countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
		return entityManager.createQuery(countQuery).getSingleResult();
	}
}
