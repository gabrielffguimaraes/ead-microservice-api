package com.ead.authuser.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "viacep" , url="${viacep}")
public interface CepRepository {
    @GetMapping("/{cep}/json/")
    public Optional<Object> cep(@PathVariable("cep") String cep);
}
