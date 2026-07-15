package com.gautam.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WalletServiceApplication {

    public static void main(String[] args) {
        System.out.println("Application Started...");
        SpringApplication.run(WalletServiceApplication.class, args);
    }
}