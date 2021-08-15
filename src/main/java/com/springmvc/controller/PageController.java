package com.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}

	@GetMapping("/leaderboard")
	public String leaderboard() {
		return "leaderboard";
	}

	@GetMapping("/features")
	public String features() {
		return "features";
	}
}
