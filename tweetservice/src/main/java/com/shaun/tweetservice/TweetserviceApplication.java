package com.shaun.tweetservice;

import com.shaun.tweetservice.repo.TweetsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
@EnableDiscoveryClient
public class TweetserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TweetserviceApplication.class, args);
	}

	/*CommandLineRunner commandLineRunner(TweetsRepository tweetsRepository)
	{
		return args ->{
			System.out.println(tweetsRepository.findTweetById(1));

		};
	}*/

}
