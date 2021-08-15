package com.springmvc.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.entity.Word;
import com.springmvc.exception.ResourceNotFoundException;
import com.springmvc.repository.WordRepository;

@Service
public class WordServiceImpl implements WordService {

	@Autowired
    private WordRepository wordRepository;

	@Override
	@Transactional
	public List<Word> getWords() {
		return wordRepository.findAll();

	}

	@Override
	@Transactional
	public void saveWord(Word word) {
		wordRepository.save(word);

	}

	@Override
	@Transactional
	public void saveWords(List<Word> words) {
		wordRepository.saveAll(words);

	}

	@Override
	@Transactional
	public Word getWord(long id) throws ResourceNotFoundException {
		return wordRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

	}

	@Override
	public List<Word> getWordsByIdIn(List<Long> ids) {
		return wordRepository.findByIdIn(ids);
	}

	@Override
	@Transactional
	public void deleteWord(long id) throws ResourceNotFoundException {
		wordRepository.deleteById(id);

	}

	@Override
	public void deleteWords() {
		wordRepository.deleteAll();

	}
}
