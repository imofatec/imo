package com.imo.backend.models.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document
@Getter
public abstract class Entity {
  @MongoId(FieldType.OBJECT_ID)
  private String id;

  @CreatedDate
  private Date createdAt;

  @LastModifiedDate
  private Date updatedAt;
}
