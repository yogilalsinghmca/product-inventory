package com.example.myapp.api;


import com.example.myapp.model.Category;
import com.example.myapp.service.CategoryService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CategoryController.class)
public class CategoryControllerTest {
    private static final String URL = "/categories";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void testRetrieveAllCategories() throws Exception {
        // categories
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("C1");
        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("C2");
        Category category3 = new Category();
        category3.setId(3L);
        category3.setName("C3");
        Category category4 = new Category();
        category4.setId(4L);
        category4.setName("C4");
        Category category5 = new Category();
        category5.setId(5L);
        category5.setName("C5");
        // parent-child associations
        category1.setChildCategories(Collections.singleton(category2));
        category2.setParent(category1);
        category3.setChildCategories(Collections.singleton(category4));
        category4.setParent(category3);

        Mockito.when(categoryService.getAllCategories()).thenReturn(Arrays.asList(category1, category2, category3, category4, category5));

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name", Matchers.is("C1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name", Matchers.is("C2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].name", Matchers.is("C3")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[3].name", Matchers.is("C4")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[4].name", Matchers.is("C5")));
    }

    @Test
    public void testRetrieveAllCategoriesEmpty() throws Exception {
        Mockito.when(categoryService.getAllCategories()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.empty()));
    }

    @Test
    public void testRetrieveCategory() throws Exception {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("C1");
        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("C2");

        // parent-child associations
        category1.setChildCategories(Collections.singleton(category2));
        category2.setParent(category1);

        Mockito.when(categoryService.getCategoryById(Mockito.eq(2L))).thenReturn(Optional.of(category2));

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{categoryId}", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("C2")));
    }

    @Test
    public void testCreateCategory() throws Exception {
        Category category = new Category();
        category.setId(1L);
        category.setName("C1");

        Mockito.when(categoryService.createCategory(Mockito.eq("C1"))).thenReturn(category);

        String request = "{ \"name\": \"C1\" }";
        mockMvc.perform(MockMvcRequestBuilders.post(URL).content(request).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("C1")))
        ;

        Mockito.verify(categoryService).createCategory(Mockito.eq("C1"));
    }

    @Test
    public void testUpdateCategory() throws Exception {
        Category category = new Category();
        category.setId(1L);
        category.setName("C1");

        Mockito.when(categoryService.getCategoryById(Mockito.eq(1L))).thenReturn(Optional.of(category));
        Mockito.doNothing().when(categoryService).updateCategory(Mockito.eq(category), Mockito.eq("C1"));

        String request = "{ \"name\": \"C1\" }";
        mockMvc.perform(MockMvcRequestBuilders.put(URL + "/{categoryId}", "1").content(request).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("C1")))
        ;

        Mockito.verify(categoryService).updateCategory(Mockito.eq(category), Mockito.eq("C1"));
    }

    @Test
    public void testDeleteCategory() throws Exception {
        Category category = new Category();
        category.setId(1L);
        category.setName("C1");

        Mockito.when(categoryService.getCategoryById(Mockito.eq(1L))).thenReturn(Optional.of(category));
        Mockito.doNothing().when(categoryService).deleteCategory(Mockito.eq(category));

        mockMvc.perform(delete(URL + "/{categoryId}", "1"))
                .andDo(print())
                .andExpect(status().isOk());

        Mockito.verify(categoryService).deleteCategory(Mockito.eq(category));
    }

    @Test
    public void testResourceNotFound() throws Exception {
        mockMvc.perform(get(URL + "/{categoryId}", "-99999"))
                .andDo(print())
                .andExpect(status().isNotFound());

        mockMvc.perform(put(URL + "/{categoryId}", "-99999").content("{ \"name\": \"name\" }").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        mockMvc.perform(delete(URL + "/{categoryId}", "-99999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
