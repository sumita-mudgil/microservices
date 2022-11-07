package com.intuit.snip.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "url")
public class Url {

  @Id
  private String hash;
  private String originalUrl;
  private Long creationDate;
  private Long expiryDate;
  private Integer userId;


  public Url() {

  }

  public Url(String hash, String originalUrl, Long creationDate, Long expiryDate, Integer userId) {
    this.hash = hash;
    this.originalUrl = originalUrl;
    this.creationDate = creationDate;
    this.expiryDate = expiryDate;
    this.userId = userId;
  }

  public String getHash() {
    return hash;
  }

  public void setHash(String hash) {
    this.hash = hash;
  }

  public String getOriginalUrl() {
    return originalUrl;
  }

  public void setOriginalUrl(String originalUrl) {
    this.originalUrl = originalUrl;
  }

  public Long getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Long creationDate) {
    this.creationDate = creationDate;
  }

  public Long getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(Long expiryDate) {
    this.expiryDate = expiryDate;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }
}