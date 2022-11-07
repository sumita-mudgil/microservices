package com.intuit.snip.controller;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.snip.model.Url;
import com.intuit.snip.service.UrlSnipperService;

@RestController
public class SnipController {

  @Autowired
  UrlSnipperService urlSnipperService;

  protected Logger logger = Logger.getLogger(SnipController.class.getName());

  @RequestMapping("/urls")
  public List<Url> findAll() {
    logger.info("Url.findAll()");
    return urlSnipperService.findAll();
  }

  @RequestMapping(method = RequestMethod.POST, value = "/urls")
  public Url add(@RequestBody Url url) {
    logger.info(String.format("Url.add(%s)", url));
    return urlSnipperService.add(url);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/url/snip")
  public Url snipUrl(@RequestBody Url url) {
    logger.info(String.format("Url.snip(%s)", url));
    //Add userid to url
    return urlSnipperService.snipUrl(url.getOriginalUrl(),url.getUserId());
  }

  @RequestMapping(method = RequestMethod.GET, value = "/url/{hash}")
  public String redirectToOriginalUrl(@PathVariable("hash") String hash) {
    String originalUrl = urlSnipperService.getOriginalUrl(hash);
    return "firstValue";
  }

//  @RequestMapping(method = RequestMethod.GET, value = "/url/{hash}")
//  @Cacheable
//  ResponseEntity<Void> redirectToOriginalUrl(@PathVariable("hash") String hash) {
//    String originalUrl = urlSnipperService.getOriginalUrl(hash);
//    return ResponseEntity.status(HttpStatus.FOUND)
//        .location(URI.create(originalUrl))
//        .build();
//  }

}
