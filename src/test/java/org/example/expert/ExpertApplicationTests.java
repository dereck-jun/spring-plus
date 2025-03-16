package org.example.expert;

import java.util.ArrayList;
import java.util.List;

import org.example.expert.config.JwtAuthenticationFilter;
import org.example.expert.config.JwtUtil;
import org.example.expert.config.PropertyConfig;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Import({PropertyConfig.class, JwtUtil.class, JwtAuthenticationFilter.class})
@SpringBootTest
class ExpertApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@PersistenceContext
	private EntityManager em;

	@Test
	void contextLoads() {
	}

	// @Transactional
	// @Rollback(value = false)
	// @Test
	// public void 더미_데이터_저장() {
	// 	int total = 1000000;
	// 	int batchSize = 1000;
	// 	List<User> users = new ArrayList<>(batchSize);
	//
	// 	for (int i = 0; i < total; i++) {
	// 		String email = "test@tester" + i + ".com";
	// 		String nickname = "nickname" + i;
	// 		User user = new User(email, "password", nickname, UserRole.USER);
	// 		users.add(user);
	// 		System.out.println(i);
	// 		if (users.size() >= batchSize) {
	// 			userRepository.saveAll(users);
	// 			userRepository.flush();
	// 			em.clear();
	// 			users.clear();
	// 		}
	// 	}
	//
	// 	if (!users.isEmpty()) {
	// 		userRepository.saveAll(users);
	// 		userRepository.flush();
	// 	}
	// }

}
