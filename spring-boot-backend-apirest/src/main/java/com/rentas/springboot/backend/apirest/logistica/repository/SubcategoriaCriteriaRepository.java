package com.rentas.springboot.backend.apirest.logistica.repository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import com.rentas.springboot.backend.apirest.logistica.models.SubcategoriaPage;
import com.rentas.springboot.backend.apirest.logistica.models.SubcategoriaSearchCriteria;
import com.rentas.springboot.backend.apirest.logistica.models.Subcategoria;

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
public class SubcategoriaCriteriaRepository {

	private final EntityManager entityManager;
	private final CriteriaBuilder criteriaBuilder;

	public SubcategoriaCriteriaRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.criteriaBuilder = entityManager.getCriteriaBuilder();
	}

	public Page<Subcategoria> findAllWithFilters(SubcategoriaPage subcategoriaPage,
			SubcategoriaSearchCriteria subcategoriaSearchCriteria) {
		CriteriaQuery<Subcategoria> criteriaQuery = criteriaBuilder.createQuery(Subcategoria.class);
		Root<Subcategoria> subcategoriaRoot = criteriaQuery.from(Subcategoria.class);
		Predicate predicate = getPredicate(subcategoriaSearchCriteria, subcategoriaRoot);
		criteriaQuery.where(predicate);
		setOrder(subcategoriaPage, criteriaQuery, subcategoriaRoot);

		TypedQuery<Subcategoria> typedQuery = entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult(subcategoriaPage.getPageNumber() * subcategoriaPage.getPageSize());
		typedQuery.setMaxResults(subcategoriaPage.getPageSize());

		Pageable pageable = getPageable(subcategoriaPage);

		long categoriaCount = getSubcategoriasCount(predicate);

		return new PageImpl<>(typedQuery.getResultList(), pageable, categoriaCount);
	}

	private Predicate getPredicate(SubcategoriaSearchCriteria subcategoriaSearchCriteria,
			Root<Subcategoria> subcategoriaRoot) {
		List<Predicate> predicates = new ArrayList<>();

		if (Objects.nonNull(subcategoriaSearchCriteria.getSubcategoriacodigo())) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(subcategoriaRoot.get("subcategoriacodigo")),
					"%" + subcategoriaSearchCriteria.getSubcategoriacodigo().toLowerCase() + "%"));
		}
		if (Objects.nonNull(subcategoriaSearchCriteria.getSubcategoriadescripcion())) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(subcategoriaRoot.get("subcategoriadescripcion")),
					"%" + subcategoriaSearchCriteria.getSubcategoriadescripcion().toLowerCase() + "%"));
		}
		if (Objects.nonNull(subcategoriaSearchCriteria.getEstado())) {
			predicates.add(criteriaBuilder.like(subcategoriaRoot.get("estado"),
					"%" + subcategoriaSearchCriteria.getEstado() + "%"));
		}
		if (Objects.nonNull(subcategoriaSearchCriteria.getCategoriaid())) {
			predicates.add(criteriaBuilder.in(subcategoriaRoot.get("categoriaid"))
					.value(subcategoriaSearchCriteria.getCategoriaid()));
		}

		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}

	private void setOrder(SubcategoriaPage subcategoriaPage, CriteriaQuery<Subcategoria> criteriaQuery,
			Root<Subcategoria> subcategoriaRoot) {
		if (subcategoriaPage.getSortDirection().equals(Sort.Direction.ASC)) {
			criteriaQuery.orderBy(criteriaBuilder.asc(subcategoriaRoot.get(subcategoriaPage.getSortBy())));
		} else {
			criteriaQuery.orderBy(criteriaBuilder.desc(subcategoriaRoot.get(subcategoriaPage.getSortBy())));
		}
	}

	private Pageable getPageable(SubcategoriaPage subcategoriaPage) {
		Sort sort = Sort.by(subcategoriaPage.getSortDirection(), subcategoriaPage.getSortBy());
		return PageRequest.of(subcategoriaPage.getPageNumber(), subcategoriaPage.getPageSize(), sort);
	}

	private long getSubcategoriasCount(Predicate predicate) {
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<Subcategoria> countRoot = countQuery.from(Subcategoria.class);
		countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
		return entityManager.createQuery(countQuery).getSingleResult();
	}
}
