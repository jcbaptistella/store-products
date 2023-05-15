package com.cayena.storeproducts.controller;

import com.cayena.storeproducts.dto.enums.QuantityStockActionEnum;
import com.cayena.storeproducts.dto.product.CreateProductRequestDto;
import com.cayena.storeproducts.dto.product.CreateProductResponseDto;
import com.cayena.storeproducts.dto.product.PatchProductRequestDto;
import com.cayena.storeproducts.dto.product.PatchQuantityStockRequestDto;
import com.cayena.storeproducts.dto.product.ProductResponseDto;
import com.cayena.storeproducts.dto.supplier.SupplierResponseDto;
import com.cayena.storeproducts.service.create_product_service.CreateProductService;
import com.cayena.storeproducts.service.delete_product_service.DeleteProductService;
import com.cayena.storeproducts.service.get_all_product_service.GetAllProductService;
import com.cayena.storeproducts.service.get_product_service.GetProductService;
import com.cayena.storeproducts.service.patch_product_quantity_stock_service.PatchProductQuantityStockService;
import com.cayena.storeproducts.service.patch_product_service.PatchProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static java.time.LocalDateTime.now;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerUTests {
    private MockMvc mockMvc;

    @Mock
    private CreateProductService createProductService;

    @Mock
    private DeleteProductService deleteProductService;

    @Mock
    private GetAllProductService getAllProductService;

    @Mock
    private GetProductService getProductService;

    @Mock
    private PatchProductQuantityStockService patchProductQuantityStockService;

    @Mock
    private PatchProductService patchProductService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void test_get_all_products() throws Exception {
        var supplierResponseDto = new SupplierResponseDto();
        supplierResponseDto.setId(1L);
        supplierResponseDto.setName("Fornecedor A");
        supplierResponseDto.setDateOfCreation(now());
        supplierResponseDto.setDateOfLastUpdate(now());

        var productResponseDto = new ProductResponseDto();
        productResponseDto.setId(1L);
        productResponseDto.setName("Tomate");
        productResponseDto.setQuantityInStock(1);
        productResponseDto.setUnitPrice(1.50F);
        productResponseDto.setSupplierResponseDto(supplierResponseDto);

        when(getAllProductService.getAllProducts()).thenReturn(Collections.singletonList(productResponseDto));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void test_get_product() throws Exception {
        var supplierResponseDto = new SupplierResponseDto();
        supplierResponseDto.setId(1L);
        supplierResponseDto.setName("Fornecedor A");
        supplierResponseDto.setDateOfCreation(now());
        supplierResponseDto.setDateOfLastUpdate(now());

        var productResponseDto = new ProductResponseDto();
        productResponseDto.setId(1L);
        productResponseDto.setName("Tomate");
        productResponseDto.setQuantityInStock(1);
        productResponseDto.setUnitPrice(1.50F);
        productResponseDto.setSupplierResponseDto(supplierResponseDto);

        when(getProductService.getProductById(1L)).thenReturn(productResponseDto);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }


    @Test
    void test_create_product() throws Exception {
        var createProductResponseDto = new CreateProductResponseDto();
        createProductResponseDto.setId(1L);

        var createProductRequestDto = new CreateProductRequestDto();
        createProductRequestDto.setName("Tomate");
        createProductRequestDto.setQuantityInStock(1);
        createProductRequestDto.setUnitPrice(1.50F);
        createProductRequestDto.setSupplierId(1L);

        when(createProductService.createProduct(createProductRequestDto)).thenReturn(createProductResponseDto);

        mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"name\":\"Tomate\",\"quantityStock\":1,\"unitPrice\":1.50,\"supplierId\":1}"))
                .andExpect(status().isCreated());
    }

    @Test
    void test_patch_product() throws Exception {
        var productId = 1L;

        var patchProductRequestDto = new PatchProductRequestDto();
        patchProductRequestDto.setId(1L);
        patchProductRequestDto.setName("Tomate");
        patchProductRequestDto.setUnitPrice(1.50F);
        patchProductRequestDto.setSupplierId(1L);

        var supplierResponseDto = new SupplierResponseDto();
        supplierResponseDto.setId(1L);
        supplierResponseDto.setName("Fornecedor A");
        supplierResponseDto.setDateOfCreation(now());
        supplierResponseDto.setDateOfLastUpdate(now());

        var productResponseDto = new ProductResponseDto();
        productResponseDto.setId(1L);
        productResponseDto.setName("Tomate Seco");
        productResponseDto.setQuantityInStock(2);
        productResponseDto.setUnitPrice(3.50F);
        productResponseDto.setSupplierResponseDto(supplierResponseDto);

        when(patchProductService.patchProduct(productId, patchProductRequestDto)).thenReturn(productResponseDto);

        mockMvc.perform(patch("/products/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"id\":1,\"name\":\"Tomate\",\"unitPrice\":1.50,\"supplierId\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    void test_delete_droduct() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void test_patch_product_quantity_stock() throws Exception {
        var productId = 1L;

        var patchQuantityStockRequestDto = new PatchQuantityStockRequestDto();
        patchQuantityStockRequestDto.setId(1L);
        patchQuantityStockRequestDto.setQuantityInStock(1);
        patchQuantityStockRequestDto.setQuantityStockAction(QuantityStockActionEnum.INCREASE);

        var supplierResponseDto = new SupplierResponseDto();
        supplierResponseDto.setId(1L);
        supplierResponseDto.setName("Fornecedor A");
        supplierResponseDto.setDateOfCreation(now());
        supplierResponseDto.setDateOfLastUpdate(now());

        var productResponseDto = new ProductResponseDto();
        productResponseDto.setId(1L);
        productResponseDto.setName("Tomate Seco");
        productResponseDto.setQuantityInStock(2);
        productResponseDto.setUnitPrice(3.50F);
        productResponseDto.setSupplierResponseDto(supplierResponseDto);

        when(patchProductQuantityStockService.patchProductQuantityStock(productId, patchQuantityStockRequestDto)).thenReturn(productResponseDto);

        mockMvc.perform(patch("/products/1/quantityStock")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"quantityStock\":20}"))
                .andExpect(status().isOk());
    }
}
