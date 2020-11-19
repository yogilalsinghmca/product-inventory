# product-inventory
Basic REST service to manage product and category

#### PostgreSQL/MYSQL CTE. for H2 database RECURSIVE keyword is optional

```
WITH RECURSIVE category_tree(id, parent_category) AS (
   SELECT id, parent_category
   FROM category
   WHERE id = 7  -- this defines the start of the recursion
   UNION ALL
   SELECT child.id,  child.parent_category
   FROM category   child
     JOIN category_tree  parent ON parent.id = child.parent_category -- the self join to the CTE builds up the recursion
)
SELECT * FROM category_tree;
```
#### ORACLE CTE( you have to remove the RECURSIVE keyword)

``` 
WITH category_tree(id, parent_category) AS (
   SELECT id, parent_category
   FROM category
   WHERE id = 7  -- this defines the start of the recursion
   UNION ALL
   SELECT child.id,  child.parent_category
   FROM category   child
     JOIN category_tree  parent ON parent.id = child.parent_category -- the self join to the CTE builds up the recursion
)
SELECT * FROM category_tree;
