package com.alura.cursos.LiterAluraChallenge;

import com.alura.cursos.LiterAluraChallenge.principal.Principal;
import com.alura.cursos.LiterAluraChallenge.repository.AutorRepository;
import com.alura.cursos.LiterAluraChallenge.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraChallengeApplication implements CommandLineRunner {
	@Autowired
	private Principal principal;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		principal.muestraElMenu();
	}
}
