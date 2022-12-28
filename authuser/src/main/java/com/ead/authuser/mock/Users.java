package com.ead.authuser.mock;

import com.ead.authuser.models.UserCourse;
import com.ead.authuser.models.User;
import com.ead.authuser.repository.UserCourseRepository;
import com.ead.authuser.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Users implements ApplicationRunner {
    private final UserRepository userRepository;
    private final UserCourseRepository userCourseRepository;

    public Users(UserRepository userRepository, UserCourseRepository userCourseRepository) {
        this.userRepository = userRepository;
        this.userCourseRepository = userCourseRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var user1 = this.userRepository.save(User.builder()
                .email("gabrielmontenegro@gmail.com")
                .username("gabrielmontenegro")
                .password("123456")
                .cpf("53039747070")
                .fullName("Gabriel Monte Negro")
                .build());
        var user2 =this.userRepository.save(User.builder()
                .email("gabrielffguimaraes@gmail.com")
                .username("gabrielffguimaraes")
                .password("123456")
                .cpf("15224280702")
                .fullName("Gabriel Ferreira Guimarães")
                .build());
        var user3 =this.userRepository.save(User.builder()
                .email("carlossilva@gmail.com")
                .username("carlos994")
                .password("123456")
                .cpf("64837174078")
                .fullName("Carlos Silva Fernandes")
                .build());
        var user4 =this.userRepository.save(User.builder()
                .email("ramonsilvano@gmail.com")
                .username("ramon662")
                .password("123456")
                .cpf("62154796087")
                .fullName("Ramon Silvano Charles")
                .build());
        var user5 =this.userRepository.save(User.builder()
                .email("ernandessuperman@gmail.com")
                .username("ernandes5545")
                .password("123456")
                .cpf("62803889056")
                .fullName("Ernandes de Rico Montosuave")
                .build());
        var user6 =this.userRepository.save(User.builder()
                .email("craigersonfortes@gmail.com")
                .username("craigdestemido")
                .password("123456")
                .cpf("15604353051")
                .fullName("Crang Wanderson de Neves Prado")
                .build());
        var userCourse1 = UserCourse
                .builder()
                .courseId(UUID.fromString("090e97ad-e33c-486d-a58d-fcdd3bee7522"))
                .userModel(user1)
                .build();
        var userCourse2 = UserCourse
                .builder()
                .courseId(UUID.fromString("090e97ad-e33c-486d-a58d-fcdd3bee7522"))
                .userModel(user2)
                .build();

        var userCourse3 = UserCourse
                .builder()
                .courseId(UUID.fromString("ed72bbbe-c0c1-4dce-868b-72625322c1ff"))
                .userModel(user3)
                .build();
        var userCourse4 = UserCourse
                .builder()
                .courseId(UUID.fromString("ed72bbbe-c0c1-4dce-868b-72625322c1ff"))
                .userModel(user4)
                .build();
        var userCourse5 = UserCourse
                .builder()
                .courseId(UUID.fromString("ed72bbbe-c0c1-4dce-868b-72625322c1ff"))
                .userModel(user5)
                .build();

        this.userCourseRepository.save(userCourse1);
        this.userCourseRepository.save(userCourse2);
        this.userCourseRepository.save(userCourse3);
        this.userCourseRepository.save(userCourse4);
        this.userCourseRepository.save(userCourse5);
    }
}
