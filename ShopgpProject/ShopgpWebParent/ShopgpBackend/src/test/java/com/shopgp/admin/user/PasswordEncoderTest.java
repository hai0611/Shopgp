package com.shopgp.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
	@Test
	public void testEncoderPassword() {
		BCryptPasswordEncoder byBCryptPasswordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "123456789";
		String encodedPassword = byBCryptPasswordEncoder.encode(rawPassword);
		System.out.println(encodedPassword);
		boolean matches = byBCryptPasswordEncoder.matches(rawPassword, encodedPassword);
		assertThat(matches).isTrue();
		}
}
