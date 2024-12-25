package com.daud.mvc.sec.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daud.mvc.sec.entities.Users;
import com.daud.mvc.sec.repositories.UserRepository;

@Controller
public class LoginController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userReository;

	@RequestMapping("/signin")
	public String login(Model model) {
		model.addAttribute("title","Sign IN");
		return "signin";
	}
	@RequestMapping("/signup")
	public String signUp(Model model) {
		model.addAttribute("title","Sign UP");
		model.addAttribute("user",new Users());
		return "signup";
	}
	
	@PostMapping("/dosignup")
	public String signUp(@ModelAttribute Users users,Model model,BindingResult result,HttpServletResponse response) {
		if(users.isEnabled()) {
		users.setRole("ROLE_USER");
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		userReository.save(users);
		model.addAttribute("classtype", "alert-success");
		model.addAttribute("message","Sign Up Success Full!!!!");
		model.addAttribute("user",new Users());
		return "signup";
		}
		else
		{
			model.addAttribute("user",users);
			model.addAttribute("message","Sign Up Failed!!!!");
			model.addAttribute("classtype", "alert-danger");
			return "signup";	
		}
	}
	
}
