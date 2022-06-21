package com.liquibase.graphql.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "books")
public class Book implements Serializable {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name",nullable = false,unique = true)
    private String name;

    @OneToOne(cascade =CascadeType.ALL )
    @JoinColumn(name = "author")
    private Author author;
}
