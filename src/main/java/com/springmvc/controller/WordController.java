package com.springmvc.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.math3.random.MersenneTwister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.springmvc.entity.TypingResult;
import com.springmvc.entity.Word;
import com.springmvc.service.WordService;

@Controller
public class WordController {

	@Autowired
	private WordService wordService;

	@Autowired
	private ServletContext servletContext;

	@GetMapping("/populate")
	@ResponseStatus(value = HttpStatus.OK)
	public void populateWords() {
		Scanner scanner = null;

		try {
			System.out.println(new File(servletContext.getRealPath("/resources/files/words.txt")).getAbsolutePath());
			scanner = new Scanner(new File(servletContext.getRealPath("/resources/files/words.txt")));
			wordService.deleteWords();

			Word word;
			List<Word> words = new ArrayList<Word>();
			while (scanner.hasNext()) {
				word = new Word((String) scanner.next());
				words.add(word);
			}

			wordService.saveWords(words);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/delete-words")
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteWords() {
		wordService.deleteWords();
	}

	@GetMapping("/practice")
	public String practice(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if (session == null)
			return "redirect:/login";

		MersenneTwister mt = new MersenneTwister();

		final int noOfWords = 49118;
		final int maxWords = 300;
		final int rangeValue = noOfWords + 1;

		List<Long> ids = new ArrayList<Long>();
		for (int index = 0; index < maxWords; index++)
			ids.add((long) mt.nextInt(rangeValue));

		List<Word> words = wordService.getWordsByIdIn(ids);

		model.addAttribute("words", words);
		model.addAttribute("typeSessionData", new TypingResult());

		return "practice";
	}

}
