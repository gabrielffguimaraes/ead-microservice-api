package com.ead.course.clients;

import com.ead.course.dto.ResponsePagedDto;
import com.ead.course.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@Slf4j
public class AuthuserClient {

    @Autowired
    RestTemplate restTemplate;

    @Value("${ead.api.url.authuser}")
    String REQUEST_URI_AUTHUSER;
    public ResponseEntity<ResponsePagedDto<?>> getAllUsersByCourse(UUID courseId) {
        String url = REQUEST_URI_AUTHUSER + "?courseId=" +courseId;

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

    public ResponseEntity<UserDto> getOneUserById(UUID userId) {
        String url = REQUEST_URI_AUTHUSER + "/" + userId;
        log.info("URL : {}",url);
        return restTemplate.exchange(url,HttpMethod.GET,null, UserDto.class);
    }
}
