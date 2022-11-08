package com.ead.authuser.mock;

import com.ead.authuser.models.UserModel;
import com.ead.authuser.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Users implements ApplicationRunner {
    private final UserRepository userRepository;

    public Users(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.userRepository.save(UserModel.builder()
                .email("gabrielmontenegro@gmail.com")
                .username("gabrielmontenegro")
                .password("123456")
                .cpf("53039747070")
                .fullName("Gabriel Monte Negro")
                .build());
        this.userRepository.save(UserModel.builder()
                .email("gabrielffguimaraes@gmail.com")
                .username("gabrielffguimaraes")
                .password("123456")
                .cpf("15224280702")
                .fullName("Gabriel Ferreira Guimarães")
                .build());
        this.userRepository.save(UserModel.builder()
                .email("carlossilva@gmail.com")
                .username("carlos994")
                .password("123456")
                .cpf("64837174078")
                .fullName("Carlos Silva Fernandes")
                .build());
        this.userRepository.save(UserModel.builder()
                .email("ramonsilvano@gmail.com")
                .username("ramon662")
                .password("123456")
                .cpf("62154796087")
                .fullName("Ramon Silvano Charles")
                .build());
        this.userRepository.save(UserModel.builder()
                .email("ernandessuperman@gmail.com")
                .username("ernandes5545")
                .password("123456")
                .cpf("62803889056")
                .fullName("Ernandes de Rico Montosuave")
                .build());
        this.userRepository.save(UserModel.builder()
                .email("craigersonfortes@gmail.com")
                .username("craigdestemido")
                .password("123456")
                .cpf("15604353051")
                .fullName("Crang Wanderson de Neves Prado")
                .build());
    }
}
