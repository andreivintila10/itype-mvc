package com.springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.springmvc.entity.TypingResult;
import com.springmvc.entity.User;
import com.springmvc.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/register")
	public String register(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if (session != null)
			return "redirect:/";

		model.addAttribute("registerForm", new User());
		return "register";
	}

	@PostMapping("/register")
	public String processRegistration(@ModelAttribute("registerForm") User user, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if (session != null)
			return "redirect:/";

		String invalidString = "is-invalid";
		String nameValidation = "";
		String emailValidation = "";
		String usernameValidation = "";
		String passwordValidation = "";
		String loginAlert = "";
		String loginError = "";
		boolean hasErrors = false;
		if (user.getName().isEmpty()) {
			nameValidation = invalidString;
			hasErrors = true;
		}

		if (user.getEmail().isEmpty()) {
			emailValidation = invalidString;
			hasErrors = true;
		}

		if (user.getUsername().isEmpty()) {
			usernameValidation = invalidString;
			hasErrors = true;
		}

		if (user.getPassword().isEmpty()) {
			passwordValidation = invalidString;
			hasErrors = true;
		}

		if (!hasErrors) {
			userService.saveUser(user);
			return "redirect:/login";
		}

		model.addAttribute("nameValidation", nameValidation);
		model.addAttribute("emailValidation", emailValidation);
		model.addAttribute("usernameValidation", usernameValidation);
		model.addAttribute("passwordValidation", passwordValidation);
		model.addAttribute("loginAlert", loginAlert);
		model.addAttribute("loginError", loginError);

		return "register";
	}

	@GetMapping("/login")
	public String login(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if (session != null)
			return "redirect:/";

		model.addAttribute("loginForm", new User());
		return "login";
	}

	@PostMapping("/login")
	public String loginProcess(@ModelAttribute("loginForm") User user, HttpServletRequest request, Model model) {
		String invalidString = "is-invalid";
		String usernameValidation = "";
		String passwordValidation = "";
		String loginAlert = "";
		String loginError = "";
		boolean hasErrors = false;
		if (user.getUsername().isEmpty()) {
			usernameValidation = invalidString;
			hasErrors = true;
		}

		if (user.getPassword().isEmpty()) {
			passwordValidation = invalidString;
			hasErrors = true;
		}

		if (!hasErrors) {
			User existingUser = userService.validateUser(user);
			if (existingUser != null) {
				HttpSession session = request.getSession();
				session.setAttribute("userId", existingUser.getId());
				session.setAttribute("name", existingUser.getName());
				session.setAttribute("username", existingUser.getUsername());
				session.setAttribute("isLoggedIn", true);
				return "redirect:/";
			}

			loginAlert = "alert alert-danger";
			loginError = "Incorrect Username or Password!";
		}

		model.addAttribute("usernameValidation", usernameValidation);
		model.addAttribute("passwordValidation", passwordValidation);
		model.addAttribute("loginAlert", loginAlert);
		model.addAttribute("loginError", loginError);

		return "login";
    }

	@GetMapping("/profile")
	public String profile(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if (session == null)
			return "redirect:/";

		long userId = (long) session.getAttribute("userId");
		User user = userService.getUser(userId);
		model.addAttribute("userInfo", user);
		return "profile";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null)
			session.invalidate();

		return "redirect:/";
	}

	@GetMapping("/profile-edit")
	public String editProfile(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if (session == null)
			return "redirect:/";

		long userId = (long) session.getAttribute("userId");
		User user = userService.getUser(userId);
		model.addAttribute("userForm", user);

		return "profile-edit";
	}

	@PostMapping("/profile-edit")
	public String editProfileProcess(@ModelAttribute("userForm") User user, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if (session == null)
			return "redirect:/";

		session.setAttribute("name", user.getName());

		long userId = (long) session.getAttribute("userId");
		User userNew = userService.getUser(userId);
		userNew.setName(user.getName());
		userNew.setEmail(user.getEmail());
		userNew.setGender(user.getGender());
		userNew.setPhone(user.getPhone());
		userNew.setAddress(user.getAddress());

		userService.saveUser(userNew);

		return "redirect:/profile";
	}

	@PostMapping("/practice")
	public String saveResults(@ModelAttribute("typeSessionData") TypingResult result, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if (session == null)
			return "redirect:/";
		
		double wpm = result.getWpm();

		long userId = (long) session.getAttribute("userId");
		User userNew = userService.getUser(userId);
		userNew.setTotalSessions(userNew.getTotalSessions() + 1);
		if (userNew.getHighScore() < wpm)
			userNew.setHighScore(wpm);

		userService.saveUser(userNew);

		return "practice";
	}
}
