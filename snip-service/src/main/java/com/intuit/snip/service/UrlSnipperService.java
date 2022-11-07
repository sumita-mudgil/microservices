package com.intuit.snip.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.intuit.snip.data.UrlRepository;
import com.intuit.snip.model.Url;
import com.intuit.snip.util.Constants;
import com.intuit.snip.util.HashGenerator;

@Service
public class UrlSnipperService {

  @Autowired
  UrlRepository urlRepository;

  @Resource(name = "redisTemplate")
  private ListOperations<String, String> redisTemplate;

  protected Logger logger = Logger.getLogger(UrlSnipperService.class.getName());

  public List<Url> findAll() {
    return urlRepository.findAll();
  }

  public String getOriginalUrl(String hash) {
    List<String> urlList = Optional
        .ofNullable(redisTemplate.range(hash, 0L, -1L))
        .orElse(new ArrayList<>());
    logger.info("Getting URL from DB");
    if(!urlList.isEmpty())
      return urlList.get(0);

    return urlRepository.findOriginalUrlByHash(hash);
  }

  public Url add(Url url) {
    return urlRepository.save(url);
  }

  public Url snipUrl(String originalUrl, int userId) {

    // check if user has already created shortened url for this url
    Url url = checkUrlAlreadySnipped(originalUrl, userId);
    if(url != null)
      return url;

    String hash = HashGenerator.getHash(originalUrl+userId);

    // make sure the hash isn't already used
    while (checkHashExists(hash)) {
      hash = HashGenerator.getHash(originalUrl);
    }

    long epochMilli = Instant.now().toEpochMilli();
    url = new Url(hash, originalUrl, epochMilli, epochMilli + Constants.YEAR_IN_MILLIS, userId);
    return urlRepository.save(url);
  }

  private Url  checkUrlAlreadySnipped(String originalUrl, int userId) {
    return urlRepository.findByOriginalUrlAndUserId(originalUrl, userId);
  }

  private boolean checkHashExists(String hash) {
    return urlRepository.findOriginalUrlByHash(hash) == null ? false : true;
  }

}
