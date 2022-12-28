package com.ead.authuser.clients;

import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.dtos.ResponsePageDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@Log4j2
public class CourseClient {
    @Autowired
    RestTemplate restTemplate;
    
    @Value("${ead.api.url.course}")
    private String REQUEST_URI;
    public Page<CourseDto> getAllCoursesByUser(UUID userId , Pageable pageable) {
        Page<CourseDto> searchResult = null;
        String url = REQUEST_URI + "?userId="+userId+"&page="+pageable.getPageNumber()+"&size="+
                pageable.getPageSize()+"&sort="+pageable.getSort().toString().replaceAll(": ",",");
        log.debug("Request URL: {}",url);
        log.info("Request URL: {}",url);
        try {
            ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType = new ParameterizedTypeReference<>() {};
            ResponseEntity<ResponsePageDto<CourseDto>> result = restTemplate.exchange(url, HttpMethod.GET,null, responseType);
            searchResult = result.getBody();

            log.debug("Response Number of Elements: {}", searchResult.getSize());
        } catch (HttpStatusCodeException e) {
            log.error("Error request userClient : /courses userId {}",userId);
        }
        log.info("Ending request /courses userId {}",userId);
        return searchResult;
    }

    public ResponseEntity<CourseDto> findCourseById(UUID courseId) {
        String url = REQUEST_URI + "/" + courseId;
        return new RestTemplate().exchange(url,HttpMethod.GET,null,CourseDto.class);
    }
}
