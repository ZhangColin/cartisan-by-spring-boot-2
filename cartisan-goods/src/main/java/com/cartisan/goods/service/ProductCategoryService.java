package com.cartisan.goods.service;

import com.cartisan.common.dto.PageResult;
import com.cartisan.goods.domain.ProductCategory;
import com.cartisan.goods.dto.ProductCategoryDto;
import com.cartisan.goods.param.ProductCategoryParam;
import com.cartisan.goods.repository.ProductCategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author colin
 */
@Service
public class ProductCategoryService {
    @Autowired
    private ProductCategoryRepository repository;

    @Autowired
    ProductCategoryAttributeRelationService relationService;

    public List<ProductCategoryDto> getProductCategoriesByLevel(Integer level) {
        final List<ProductCategory> productCategories = repository.findAll((Specification<ProductCategory>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            predicateList.add(criteriaBuilder.equal(root.get("level"), level));

            return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
        });
        return productCategories.stream().map(ProductCategoryDto::convertFrom).collect(Collectors.toList());
    }

    public ProductCategoryDto getProductCategory(Long id) {
        return ProductCategoryDto.convertFrom(repository.findById(id).get());
    }

    public PageResult<ProductCategoryDto> searchProductCategories(Long parentId, Integer currentPage, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize,
                new Sort(Sort.Direction.DESC, "sort"));

        final Page<ProductCategory> searchResult = repository.findAll((Specification<ProductCategory>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            predicateList.add(criteriaBuilder.equal(root.get("parentId"), parentId));

            return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
        }, pageRequest);

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                searchResult.map(ProductCategoryDto::convertFrom).getContent());
    }

    @Transactional(rollbackOn = Exception.class)
    public void addProductCategory(ProductCategoryParam productCategoryParam) {
        final ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(productCategoryParam, productCategory);
        setCategoryLevel(productCategory);

        repository.save(productCategory);

        relationService.addRelations(productCategory.getId(), productCategoryParam.getAttributeIds());
    }

    @Transactional(rollbackOn = Exception.class)
    public void editProductCategory(Long id, ProductCategoryParam productCategoryParam) {
        final ProductCategory productCategory = repository.findById(id).get();
        BeanUtils.copyProperties(productCategoryParam, productCategory);
        productCategory.setId(id);

        setCategoryLevel(productCategory);

        repository.save(productCategory);

        relationService.editRelations(productCategory.getId(), productCategoryParam.getAttributeIds());

        //TODO: 更新商品分类时要更新商品中的名称


    }

    private void setCategoryLevel(ProductCategory productCategory) {
        if (!productCategory.getParentId().equals(Long.valueOf(0))) {
            final ProductCategory parentCategory = repository.findById(productCategory.getParentId()).get();
            productCategory.setLevel(parentCategory.getLevel() + 1);
        } else {
            productCategory.setLevel(0);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeProductCategory(long id) {
        repository.deleteById(id);
        relationService.removeRelations(id);
    }
}
