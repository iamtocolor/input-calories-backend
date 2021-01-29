package com.toptal.project.inputcaloriesapis.filter;

import com.toptal.project.inputcaloriesapis.dto.request.ComplexSearchRequest;
import com.toptal.project.inputcaloriesapis.dto.request.SearchField;
import com.toptal.project.inputcaloriesapis.dto.request.SearchRequest;
import com.toptal.project.inputcaloriesapis.dto.request.SimpleSearchRequest;
import com.toptal.project.inputcaloriesapis.entity.FoodEntity;
import com.toptal.project.inputcaloriesapis.entity.UserEntity;
import com.toptal.project.inputcaloriesapis.util.TypeConverterRegistry;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class FoodSearchSpecification implements Specification<FoodEntity> {

    private SearchRequest searchRequest;
    private TypeConverterRegistry typeConverterRegistry;

    public FoodSearchSpecification(SearchRequest searchRequest, TypeConverterRegistry typeConverterRegistry) {
        this.searchRequest = searchRequest;
        this.typeConverterRegistry = typeConverterRegistry;
    }


    @Override
    public Predicate toPredicate(Root<FoodEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        query.distinct(true);
        return recurToPredicate(root, query, criteriaBuilder, searchRequest);
    }


    public Predicate recurToPredicate(Root<FoodEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, SearchRequest searchRequest) {
        try {
            if (searchRequest instanceof SimpleSearchRequest) {
                switch (searchRequest.getOperator()) {
                    case EQ: {
                        Object v = typeConverterRegistry.get(root.get(((SimpleSearchRequest) searchRequest).getOperand1().getTableKey()).getJavaType()).convert(((SimpleSearchRequest) searchRequest).getOperand2());
                        return (criteriaBuilder.equal(root.<String>get(((SimpleSearchRequest) searchRequest).getOperand1().getTableKey()), v));
                    }
                    case GT: {
                        Object v = typeConverterRegistry.get(root.get(((SimpleSearchRequest) searchRequest).getOperand1().getTableKey()).getJavaType()).convert(((SimpleSearchRequest) searchRequest).getOperand2());
                        return (criteriaBuilder.greaterThan(root.get(((SimpleSearchRequest) searchRequest).getOperand1().getTableKey()), v.toString()));
                    }
                    case LT: {
                        Object v = typeConverterRegistry.get(root.get(((SimpleSearchRequest) searchRequest).getOperand1().getTableKey()).getJavaType()).convert(((SimpleSearchRequest) searchRequest).getOperand2());
                        return (criteriaBuilder.lessThan(root.get(((SimpleSearchRequest) searchRequest).getOperand1().getTableKey()), v.toString()));
                    }
                    case NE: {
                        Object v = typeConverterRegistry.get(root.get(((SimpleSearchRequest) searchRequest).getOperand1().getTableKey()).getJavaType()).convert(((SimpleSearchRequest) searchRequest).getOperand2());
                        return (criteriaBuilder.notEqual(root.<String>get(((SimpleSearchRequest) searchRequest).getOperand1().getTableKey()), v));
                    }
                }

            } else {
                switch (searchRequest.getOperator()) {
                    case AND: {
                        return criteriaBuilder.and(
                                recurToPredicate(root, query, criteriaBuilder, ((ComplexSearchRequest)searchRequest).getOperand1()),
                                recurToPredicate(root, query, criteriaBuilder, ((ComplexSearchRequest)searchRequest).getOperand2())
                        );
                    }
                    case OR: {
                        return criteriaBuilder.or(
                                recurToPredicate(root, query, criteriaBuilder, ((ComplexSearchRequest)searchRequest).getOperand1()),
                                recurToPredicate(root, query, criteriaBuilder, ((ComplexSearchRequest)searchRequest).getOperand2())
                        );
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
