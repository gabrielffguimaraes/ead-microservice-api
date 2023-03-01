package com.ead.course.clients;

import com.ead.course.dto.ResponsePagedDto;
import com.ead.course.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.math.BigInteger;

@Component
@Slf4j
public class AuthuserClient {

    @Autowired
    RestTemplate restTemplate;

    @Value("${ead.api.url.authuser}")
    String REQUEST_URI_AUTHUSER;

    @Value("${ead.api.url.user_course}")
    String REQUEST_URI_USER_COURSE;
    public ResponseEntity<ResponsePagedDto<?>> getAllUsersByCourse(BigInteger courseId) {
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

    public ResponseEntity<List<?>> getAllStudentsNotInCourse(BigInteger courseId) {
        String url = REQUEST_URI_AUTHUSER + "/studentsNotIn/course/" +courseId;

        var responseType = new ParameterizedTypeReference<List<?>>() {};

        ResponseEntity<List<?>> response = null;
        try {
            log.info("Url : {}",url);
            response = this.restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public ResponseEntity<UserDto> getOneUserById(BigInteger userId) {
        String url = REQUEST_URI_AUTHUSER + "/" + userId;
        log.info("URL : {}",url);
        return restTemplate.exchange(url,HttpMethod.GET,null, UserDto.class);
    }

    public void saveSubscriptionUserInCourse(BigInteger userID, BigInteger courseId) {
        Map<String , String> body = new LinkedHashMap<String,String>();
        body.put("courseId" , String.valueOf(courseId));
        HttpHeaders requestHeaders = new HttpHeaders();
        String url = REQUEST_URI_USER_COURSE + "/users/"+userID+"/courses/subscription";
        HttpEntity<?> httpEntity = new HttpEntity<Object>(body, requestHeaders);
        log.info("URL REQUEST SUBSCRIPTION AUTHUSER [{}]" , url);
        var response = restTemplate.exchange(url,HttpMethod.POST, httpEntity,String.class);
    }
}
