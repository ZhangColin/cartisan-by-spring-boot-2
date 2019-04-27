package com.cartisan.goods.controllers;

import com.cartisan.common.dtos.PageResult;
import com.cartisan.common.responses.GenericResponse;
import com.cartisan.goods.dtos.BrandDto;
import com.cartisan.goods.params.BrandParam;
import com.cartisan.goods.services.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cartisan.common.responses.GenericResponse.success;

/**
 * @author colin
 */
@Api(tags = "BrandController", description = "商品品牌管理")
@RestController
@RequestMapping("/brands")
public class BrandController {
    @Autowired
    private BrandService service;

    @ApiOperation(value = "获取所有品牌")
    @GetMapping
    public GenericResponse<List<BrandDto>> getAllBrands() {
        return success(service.getAllBrands());
    }

    @ApiOperation(value = "获取品牌")
    @GetMapping("/{id}")
    public GenericResponse<BrandDto> getBrand(
            @ApiParam(value = "品牌Id", required = true) @PathVariable Long id) {
        return success(service.getBrand(id));
    }

    @ApiOperation(value = "搜索品牌")
    @GetMapping("/search/{currentPage}/{pageSize}")
    public GenericResponse<PageResult<BrandDto>> searchBrands(
            @ApiParam(value = "查询品牌名") @RequestParam(required = false) String name,
            @ApiParam(value = "页码", required = true) @PathVariable Integer currentPage,
            @ApiParam(value = "每页记录数", required = true) @PathVariable Integer pageSize) {
        return success(service.searchBrands(name, currentPage, pageSize));
    }

    @ApiOperation(value = "添加品牌")
    @PostMapping
    public GenericResponse addBrand(
            @ApiParam(value = "品牌信息", required = true) @Validated @RequestBody BrandParam brandParam) {
        service.addBrand(brandParam);

        return success();
    }

    @ApiOperation(value = "更新品牌")
    @PutMapping("/{id}")
    public GenericResponse editBrand(
            @ApiParam(value = "品牌Id", required = true) @PathVariable Long id,
            @ApiParam(value = "品牌信息", required = true) @Validated @RequestBody BrandParam brandParam) {
        service.editBrand(id, brandParam);

        return success();
    }

    @ApiOperation(value = "删除品牌")
    @DeleteMapping("/{id}")
    public GenericResponse removeBrand(
            @ApiParam(value = "品牌Id", required = true) @PathVariable long id) {
        service.removeBrand(id);

        return success();
    }
}