package com.springmvc.service;

import java.util.List;

import com.springmvc.entity.Word;
import com.springmvc.exception.ResourceNotFoundException;

public interface WordService {

	public List<Word> getWords();

    public void saveWord(Word word);

    public void saveWords(List<Word> words);

    public Word getWord(long id) throws ResourceNotFoundException;

    public List<Word> getWordsByIdIn(List<Long> ids);

    public void deleteWord(long id) throws ResourceNotFoundException;

    public void deleteWords();
}
