package com.shopgp.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopgp.common.entity.Role;
import com.shopgp.common.entity.User;

@Controller
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/users")
	public String listAll(Model model) {
		List<User> listUser = service.listAll();
		model.addAttribute("listUser",listUser);
		return "users";
	} 
	
	@GetMapping("/users/new")
	public String newUser(Model model) {
		User user = new User();
		user.setEnabled(true);
		List<Role>listRole = service.listRole();	
		model.addAttribute("user", user);
		model.addAttribute("listRole", listRole);
		model.addAttribute("pageTitle", "Create New User");
		return "user_form";
	}
	
	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes) {
		service.saveUser(user);
		redirectAttributes.addFlashAttribute("message", "User have been add sucessfully.");
		return "redirect:/users";
	}
	
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name="id") Integer id,Model model, RedirectAttributes redirectAttributes) {
		try {
		User user = service.get(id);
		model.addAttribute("user",user);
		List<Role>listRole = service.listRole();
		model.addAttribute("listRole", listRole);
		model.addAttribute("pageTitle", "Edit User (ID: "+id+")");
		return "user_form";
		} catch(NotFoundUserExeption ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/users";
		}
	}
	
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable(name="id") Integer id,RedirectAttributes redirectAttributes) {
		try {
		service.delete(id);
		redirectAttributes.addFlashAttribute("message", "The user ID "+id+" has been deleted successfully");
		} catch(NotFoundUserExeption ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		return "redirect:/users";
	}
}