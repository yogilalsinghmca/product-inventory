package com.example.myapp.dao;

import com.example.myapp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // recursive Common table expression(ETE) query
     String CTE = "WITH category_tree(id, parentid) AS (\n" +
            "   SELECT id, parentid\n" +
            "   FROM category\n" +
            "   WHERE id = ?1 \n" +
            "   UNION ALL\n" +
            "   SELECT child.id,  child.parentid\n" +
            "   FROM category   child\n" +
            "     JOIN category_tree  parent ON parent.id = child.parentid\n" +
            ")\n" +
            "SELECT id FROM category_tree";

    String FIND_ALL_PRODUCTS_ASSOCIATED_WITH_GIVEN_CATEGORY = "SELECT p.* FROM product p INNER JOIN product_category pc on p.id = pc.productid WHERE pc.categoryid in (" + CTE + ")";
    String COUNT_ALL_PRODUCTS_ASSOCIATED_WITH_GIVEN_CATEGORY = "SELECT COUNT(1) FROM (" + FIND_ALL_PRODUCTS_ASSOCIATED_WITH_GIVEN_CATEGORY + ")";


    @Query(value = FIND_ALL_PRODUCTS_ASSOCIATED_WITH_GIVEN_CATEGORY, nativeQuery = true ,countQuery = COUNT_ALL_PRODUCTS_ASSOCIATED_WITH_GIVEN_CATEGORY)
    Page<Product> getAllProductsFor(Long categoryId, Pageable pageable);

}
