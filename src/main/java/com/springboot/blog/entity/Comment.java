package com.springboot.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "comments")
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String name;
  private String email;
  private String body;
  @ManyToOne(fetch = FetchType.LAZY)   // The FetchType.LAZY tells Hibernate to
  // only fetch the related entities
  // from the database when you use the relationship
  @JoinColumn(name = "post_id", nullable = false)  // We use @JoinColumn to specify the foreignKey
  private Post post;
}
