package com.cartisan.goods.controllers;

import com.cartisan.common.dtos.PageResult;
import com.cartisan.common.responses.GenericResponse;
import com.cartisan.goods.dtos.ProductAttributeDto;
import com.cartisan.goods.params.ProductAttributeParam;
import com.cartisan.goods.services.ProductAttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.cartisan.common.responses.GenericResponse.success;

/**
 * @author colin
 */
@Api(tags = "ProductAttributeController", description = "商品品牌管理")
@RestController
@RequestMapping("/productAttributes")
public class ProductAttributeController {
    @Autowired
    private ProductAttributeService service;

    @ApiOperation(value = "获取商品属性")
    @GetMapping("/{id}")
    public GenericResponse<ProductAttributeDto> getProductAttribute(
            @ApiParam(value = "属性Id", required = true) @PathVariable Long id) {
        return success(service.getProductAttribute(id));
    }

    @ApiOperation(value = "根据分类查询商品属性")
    @GetMapping("/search/{categoryId}/{type}/{currentPage}/{pageSize}")
    public GenericResponse<PageResult<ProductAttributeDto>> searchProductAttributes(
            @ApiParam(value = "属性分类Id", required = true) @PathVariable Long categoryId,
            @ApiParam(value = "类型（规格、参数）", required = true) @PathVariable Integer type,
            @ApiParam(value = "页码", required = true) @PathVariable Integer currentPage,
            @ApiParam(value = "每页记录数", required = true) @PathVariable Integer pageSize) {
        return success(service.searchProductAttributes(categoryId, type, currentPage, pageSize));
    }

    @ApiOperation(value = "添加商品属性")
    @PostMapping
    public GenericResponse addProductAttribute(
            @ApiParam(value = "商品属性信息", required = true) @Validated @RequestBody ProductAttributeParam productAttributeParam) {
        service.addProductAttribute(productAttributeParam);

        return success();
    }

    @ApiOperation(value = "更新商品属性")
    @PutMapping("/{id}")
    public GenericResponse editProductAttribute(
            @ApiParam(value = "商品属性Id", required = true) @PathVariable Long id,
            @ApiParam(value = "商品属性信息", required = true) @Validated @RequestBody ProductAttributeParam productAttributeParam) {
        service.editProductAttribute(id, productAttributeParam);

        return success();
    }

    @ApiOperation(value = "删除商品属性")
    @DeleteMapping("/{id}")
    public GenericResponse removeProductAttribute(
            @ApiParam(value = "商品属性Id", required = true) @PathVariable long id) {
        service.removeProductAttribute(id);

        return success();
    }
}