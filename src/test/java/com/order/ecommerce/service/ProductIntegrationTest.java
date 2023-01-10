package com.order.ecommerce.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@Transactional
@AutoConfigureEmbeddedDatabase
@AutoConfigureMockMvc
@Sql("/product/insert_prerequisite_records.sql")
public class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetProduct() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/products/106")
                ).andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(
                        content().json("{\n"
                                + "                      \"productId\": \"106\",\n"
                                + "                      \"sku\": \"1006\",\n"
                                + "                      \"title\": \"SoftDrink\",\n"
                                + "                      \"description\": \"Coke\",\n"
                                + "                      \"price\": 5.99\n"
                                + "                    }")
                ).andReturn();
    }

    @Test
    void testGetProduct_NotExists() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/products/107")
                ).andExpect(status().is(404))
                .andExpect(
                        content().string("Cannot find product with id=107")).andReturn();
    }

    @Test
    void testGetAllProducts() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/products")
                ).andExpect(status().is(200))
                .andExpect(content().json("{\"content\":[{\"productId\":\"106\",\"sku\":\"1006\",\"title\":\"SoftDrink\"," +
                        "\"description\":\"Coke\",\"price\":5.99},{\"productId\":\"108\",\"sku\":\"1006\",\"title\":\"Design Patterns\"," +
                        "\"description\":\"Its a book for dev\",\"price\":99.99}]," +
                        "\"pageable\":{\"sort\":{\"empty\":true,\"unsorted\":true,\"sorted\":false},\"offset\":0,\"pageNumber\":0," +
                        "\"pageSize\":20,\"paged\":true,\"unpaged\":false},\"totalElements\":2,\"totalPages\":1,\"last\":true," +
                        "\"size\":20,\"number\":0,\"sort\":{\"empty\":true,\"unsorted\":true,\"sorted\":false},\"numberOfElements\":2," +
                        "\"first\":true,\"empty\":false}"))
                .andReturn();
    }
}
