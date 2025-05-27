package org.example;

import org.springframework.boot.SpringApplication;

public class TestBankaccountApplication {

	public static void main(String[] args) {
		SpringApplication.from(BankAccountApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
