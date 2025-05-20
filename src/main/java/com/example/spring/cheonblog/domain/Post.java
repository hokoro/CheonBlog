package com.example.spring.cheonblog.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;


@Getter // Getter , Setter -> 클래스 내의 getter ,setter를 자동으로 생성  
@Setter
@Builder // Post.Builder().title() -> 이런식으로 빌드 객체를 생성 가능 
@AllArgsConstructor   // 모든 필드를 인자로 받는 생성자 생성
@NoArgsConstructor(access=AccessLevel.PROTECTED) // 기본 생성자 생성 + 접근 제어자 설정  , JPA는 기본 생성자 필수  외부에서 호출되면 안되므로 Protect로 제한 
@DynamicUpdate   // 변경된 필드만 SQL에 Update 되도록 표시 
@Entity         // DB 테이블을 생성하겠다는 엔티티 어노테이션 
@Table(name="post") // 테이블 이름 
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // PK 
    
    @Column(nullable = false , unique = false , name="title")
    private String title;   // 게시글의 타이틀 제목 

    @Column(nullable = false, name="viewCount")
    private Long viewCount = 0L;    // 조회수 

    @Column(nullable = false , name="isDeleted")
    private boolean isDeleted = false;  // 소프트 삭제 여부 

    @Column(nullable = false , name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="author_id" , nullable = false)
    private User author;

    // 카테고리 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // 게시글 상태 
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    // 작성일시 
    @CreatedDate
    @Column(updatable = false , nullable = false , name="createdAt")
    private LocalDateTime createdAt;

    // 수정일시 
    @LastModifiedDate
    private LocalDateTime updateAt;
    
}
