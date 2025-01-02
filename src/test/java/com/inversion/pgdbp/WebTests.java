package com.inversion.pgdbp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inversion.pgdbp.user.dto.request.CreateUserRequest;
import com.inversion.pgdbp.user.dto.request.EditUserRequest;
import com.inversion.pgdbp.user.dto.response.UserResponse;
import com.inversion.pgdbp.user.entity.UserEntity;
import com.inversion.pgdbp.user.repository.UserRepository;
import com.inversion.pgdbp.user.routes.UserRoutes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WebTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoad() throws Exception {
        UserEntity user = UserEntity.builder()
                .firstName("TestWeb")
                .lastName("TestWeb")
                .build();
        user = userRepository.save(user);

        mockMvc.perform(get(UserRoutes.BY_ID, user.getId().toString()).contentType(MediaType.APPLICATION_JSON)).
                andDo(print()).
                andExpect(status().isOk());
    }

    @Test
    void createTest() throws Exception {
        CreateUserRequest request = CreateUserRequest.builder()
                .firstName("TestWeb")
                .lastName("TestWeb")
                .build();
        mockMvc.perform(
                post(UserRoutes.CREATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andDo(print()).andExpect(content().string(containsString("TestWeb")));

    }

    @Test
    void findTest() throws Exception {
        UserEntity user = UserEntity.builder()
                .lastName("byIdTest")
                .firstName("byIdTest")
                .build();

        user = userRepository.save(user);
        mockMvc.perform(get(UserRoutes.BY_ID, user.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("byIdTest")));
    }

    @Test
    void findbyId_NotFound() throws Exception {
        mockMvc.perform(get(UserRoutes.BY_ID, 1).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());


    }

    @Test
    void updateTest() throws Exception {
        UserEntity user = UserEntity.builder()
                .lastName("updateTest")
                .firstName("updateTest")
                .build();
        user = userRepository.save(user);
        EditUserRequest request = EditUserRequest.builder()
                .id(user.getId())
                .lastName("updateTestNew")
                .firstName("updateTestNew")
                .build();

        mockMvc.perform(put(UserRoutes.BY_ID, user.getId().toString()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("updateTestNew")));
    }

    @Test
    void deleteTest() throws Exception {
        UserEntity user = UserEntity.builder()
                .lastName("deleteTest")
                .firstName("deleteTest")
                .build();
        user = userRepository.save(user);


        mockMvc.perform(delete(UserRoutes.BY_ID, user.getId().toString()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get(UserRoutes.BY_ID, user.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        assert userRepository.findById(user.getId()).isEmpty();
    }

    @Test
    void searchTest() throws Exception {
        List<UserResponse> result = new ArrayList<>();
        UserEntity user;
        for (int i = 0; i < 1000; i++) {
            user = UserEntity.builder()
                    .lastName("updateTest" + i)
                    .firstName("updateTest" + i)
                    .build();
            user = userRepository.save(user);
            result.add(UserResponse.of(user));
        }

        mockMvc.perform(get(UserRoutes.SEARCH).param("size","1000").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));

    }
}
