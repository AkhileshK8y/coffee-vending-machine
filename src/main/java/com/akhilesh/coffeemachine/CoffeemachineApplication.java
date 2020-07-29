package com.akhilesh.coffeemachine;

import com.akhilesh.coffeemachine.controller.CoffeeMachineController;
import com.akhilesh.coffeemachine.properties.CoffeeMachineProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
@ComponentScan
public class CoffeemachineApplication implements CommandLineRunner {

	@Autowired
	private CoffeeMachineProperties coffeeMachine;

	@Autowired
	private CoffeeMachineController coffeeMachineController;

	/**
	 * Launches the application
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(CoffeemachineApplication.class, args);
	}

	/**
	 * Overrided run method inorder to make this springboot application
	 * a command line application
	 * @param args Command line arguments
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	@Override
	public void run(String[] args) throws ExecutionException, InterruptedException {
		//initialize machine configuration
		coffeeMachineController.initializeControllerContext(coffeeMachine.getMachine());
		//based on configuration make Beverages
		coffeeMachineController.makeBeverages();
	}

}
