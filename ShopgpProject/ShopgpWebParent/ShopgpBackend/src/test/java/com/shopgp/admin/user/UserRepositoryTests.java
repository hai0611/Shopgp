package com.shopgp.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import com.shopgp.common.entity.Role;
import com.shopgp.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUserOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userVNH = new User("haivu0352@gmail.com","123456","Vu","Hai");
		userVNH.addRole(roleAdmin);
		User userSave = repo.save(userVNH);
		assertThat(userSave.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateUserTwoRole() {
		User userVNH = new User("AP06112002@gmail.com","123456","Vu Ngoc","Hai");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		userVNH.addRole(roleEditor);
		userVNH.addRole(roleAssistant);
		User userSave = repo.save(userVNH);
		assertThat(userSave.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testFindAllUser() {
		List<User>list =  (List<User>) repo.findAll();
		list.forEach(System.out::println);
	}
	
	@Test 
	public void testGetUserByID() {
		User user = repo.findById(1).orElse(null);
		System.out.println(user);
		assertThat(user).isNotNull();
	}
	
	@Test 
	public void testUpdateUserByID() {
		User user = repo.findById(1).orElse(null);
		user.setEnabled(true);
	}
	
	@Test
	public void testUpdateUserRole() {
		User user = repo.findById(2).orElse(null);
		Role roleEditor = new Role(3);
		Role roleSale = new Role(2);
		user.getRoles().remove(roleEditor);
		user.addRole(roleSale);
		repo.save(user);
	}
	
	@Test
	public void testDeleteUser() {
		repo.deleteById(2);
	}
	
	@Test 
	public void testGetUserByEmail() {
		User user = repo.getUserByEmail("haivu0352@gmail.com");
		System.out.println(user);
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountById() {
		Integer id = 1;
		assertThat(repo.countById(id)).isGreaterThan(0);
	}
	
	@Test 
	public void testDisableUser() {
		Integer id = 1;
		repo.updateEnabledStatus(id, false);
	}
	
	@Test 
	public void testEnableUser() {
		Integer id = 1;
		repo.updateEnabledStatus(id, true);
	}
	
	@Test
	public void testListFirstPage() {
		int pageNumber = 6;
		int pageSize = 4;
		Pageable pageable = PageRequest.of(pageNumber,pageSize);
		Page<User> page = repo.findAll(pageable);
		List<User>listUser = page.getContent();
		System.out.println(page.getTotalPages());
		listUser.forEach(System.out::println);
	}
}
