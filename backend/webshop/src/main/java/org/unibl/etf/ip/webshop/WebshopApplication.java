package org.unibl.etf.ip.webshop;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.unibl.etf.ip.webshop.security.config.MySecurityConfiguration;


@SpringBootApplication
@EntityScan("org.unibl.etf.ip.webshop.models.entities")

@Import(MySecurityConfiguration.class)
public class WebshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebshopApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		return modelMapper;
	}
	@Bean
	public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
}
