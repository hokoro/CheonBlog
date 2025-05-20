package com.example.spring.cheonblog.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;



@Entity 
@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
@Builder 
@DynamicUpdate
@Table(name="category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 카테고리 이름 
    @Column(nullable = false , unique = true , name="name")
    private String name;

    // 부가 설명 
    @Column(nullable = true , name="description")
    private String description;
    
}
