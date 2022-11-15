package com.shopgp.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.shopgp.common.entity","com.shopgp.admin.user"})
public class ShopgpBackendApplication {

	public static void main(String[] args){
		SpringApplication.run(ShopgpBackendApplication.class, args);
	}
}
