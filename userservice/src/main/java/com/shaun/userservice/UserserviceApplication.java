package com.shaun.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

/*	@Bean
	CommandLineRunner commandLineRunner(UserRepository users, UserRelationRepository userRelationRepository, PasswordEncoder encoder)
	{
		return args ->{
			Calendar calendar = Calendar.getInstance();

			User shaun = User.builder()
					.username("root")
					.email("shaun@gmail.com")
					.password(encoder.encode("root"))
					.fullname("Shaun Lewis")
					.isCelebrity(true)
					.phone("+9176136637")
					.createdAt(new Date(calendar.getTime().getTime()))
					.build();

			User chanchal = new User();
			chanchal.setUsername("chanchal");
			chanchal.setEmail("chcanchal@gmail.com");
			chanchal.setPassword(encoder.encode("user"));
			chanchal.setFullname("Chanchal Chanchal");
			chanchal.setPhone("+9176136637");

			users.save(shaun);
			users.save(chanchal);
			User dummy = User.builder()
					.username("dummy")
					.email("dummy@gmail.com")
					.password(encoder.encode("root"))
					.fullname("Humpy Dumpy")
					.phone("+9176136637")
					.createdAt(new Date(calendar.getTime().getTime()))
					.build();




			users.save(dummy);
			User service = User.builder()
					.username("service")
					.email("service@gmail.com")
					.password(encoder.encode("service"))
					.fullname("Services")
					.phone("+91376136637")
					.createdAt(new Date(calendar.getTime().getTime()))
					.build();




			users.save(service);

		};*/
	//}
}
