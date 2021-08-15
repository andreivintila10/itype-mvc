package com.springmvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springmvc.entity.Word;

@Repository("wordRepository")
public interface WordRepository extends JpaRepository<Word, Long> {

	List<Word> findByIdIn(List<Long> ids);

}
