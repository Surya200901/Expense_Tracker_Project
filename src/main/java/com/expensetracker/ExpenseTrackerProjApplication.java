package com.expensetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.expensetracker.entity")
public class ExpenseTrackerProjApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerProjApplication.class, args);
	}

}