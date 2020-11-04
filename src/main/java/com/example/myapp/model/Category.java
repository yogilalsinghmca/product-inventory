package com.example.myapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parentid")
    @Nullable
    private Category parent;

    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    private Set<Category> childCategories;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    @JsonIgnore
    private Set<Product> products;
}
