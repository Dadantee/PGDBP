package com.inversion.pgdbp;

import com.inversion.pgdbp.user.entity.UserEntity;
import com.inversion.pgdbp.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PgdbpApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
	}
	@Test
	void repositoryTest() {
		UserEntity user = UserEntity.builder()
				.firstName("Test")
				.lastName("Test")
				.build();
		user = userRepository.save(user);

		UserEntity check = userRepository.findById((user.getId())).get();

		assert  check.getId().equals(user.getId());
	}

}
