package org.example;

import org.springframework.boot.SpringApplication;

public class TestBankaccounthexaApplication {

	public static void main(String[] args) {
		SpringApplication.from(BankaccounthexaApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
