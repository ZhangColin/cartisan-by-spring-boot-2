package com.cartisan.goods.dto;

import com.cartisan.goods.domain.ProductAttributeCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author colin
 */
@Data
@AllArgsConstructor
public class ProductAttributeCategoryDto {
    private Long id;

    private String name;
    private Integer specificationCount;
    private Integer paramCount;

    public static ProductAttributeCategoryDto convertFrom(ProductAttributeCategory category) {
        return new ProductAttributeCategoryDto(category.getId(), category.getName(),
                category.getSpecificationCount(), category.getParamCount());
    }
}