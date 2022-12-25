package com.ead.course.clients;

import com.ead.course.dto.ResponsePagedDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@Slf4j
public class CourseClient  {

    @Autowired
    RestTemplate restTemplate;

    String REQUEST_URI = "http://localhost:8080/users";
    public ResponseEntity<ResponsePagedDto<?>> getAllUsersByCourse(UUID courseId) {
        String url = REQUEST_URI + "?courseId=" +courseId;

        var responseType = new ParameterizedTypeReference<ResponsePagedDto<?>>() {};

        ResponseEntity<ResponsePagedDto<?>> response = null;
        try {
            log.info("Url : {}",url);
            response = this.restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
