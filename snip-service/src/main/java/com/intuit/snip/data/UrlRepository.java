package com.intuit.snip.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.intuit.snip.model.Url;

public interface UrlRepository extends MongoRepository<Url, String> {

  public String findOriginalUrlByHash(String hash);

  public Url findByHashAndUserId(String hash, Long userId);

  public Url findByOriginalUrlAndUserId(String url, Integer userId);

  public List<Url> findByOriginalUrl(String url);

  public List<Url> findByUserId(Long userId);

}