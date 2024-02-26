package com.example.sudokudbapi.tests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.sudokudbapi.SudokuDbApiApplication;

import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SudokuDbApiApplicationTests {

	@Autowired
	SudokuDbApiApplication context;

	@Test
	void contextLoadsTest() {
		assertThat(context).isNotNull();
	}

}
