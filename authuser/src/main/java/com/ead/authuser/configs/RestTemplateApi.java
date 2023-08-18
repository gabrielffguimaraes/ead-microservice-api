package com.ead.authuser.configs;

import com.ead.authuser.dtos.ContentResponseDto;
import com.ead.authuser.enums.Course;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class RestTemplateApi {

    @Value("${ead.api.url.course}")
    private String urlCourseApi;
    @Autowired
    RestTemplate restTemplate;

    //@Retry(name = "retryInstance",fallbackMethod = "retryfallback")
    public List<Course> getAllCoursesByUser(UUID userId,String token) throws HttpStatusCodeException {

        //String url = "http://localhost:8080/ead-course/api/courseUser/users/"+userId+"/courses";
        String url = urlCourseApi+"/api/courseUser/users/4e339be5-4ea9-4eb0-9faa-f8b8d7a466de/courses";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization",token);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<String>("parameters",httpHeaders);

        ParameterizedTypeReference<ContentResponseDto<Course>> parameterizedType = new ParameterizedTypeReference<>() {};

        ResponseEntity<ContentResponseDto<Course>> responseEntity = null;

        System.out.println("Listando cursos de um determinado usuário");
        try {
            responseEntity = restTemplate.exchange(url,HttpMethod.GET,httpEntity,parameterizedType);
        } catch (HttpStatusCodeException e) {
            System.out.println("Erro esperado .");
            e.printStackTrace();
        }
        if(responseEntity != null) {
            return responseEntity.getBody().content;
        }
        return new ArrayList<>();
    }
    public List<Course> retryfallback(UUID userId,Throwable t) throws HttpStatusCodeException {
        log.info("Inside retry fallback error {}",t.getMessage().toString());
        return new ArrayList<>();
    }
}
