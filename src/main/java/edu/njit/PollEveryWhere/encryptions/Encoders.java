package edu.njit.PollEveryWhere.encryptions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class Encoders
{

	@Bean(name = "oauthClientPasswordEncoder")
	public BCryptPasswordEncoder oauthClientPasswordEncoder()
	{
		return new BCryptPasswordEncoder(4);
	}

	@Bean(name = "userPasswordEncoder")
	public BCryptPasswordEncoder userPasswordEncoder()
	{
		return new BCryptPasswordEncoder(8);
	}

	public static void main(String[] args)
	{
		System.out.println(new Encoders().oauthClientPasswordEncoder().encode("NJIT_Poll_Password"));
		System.out.println(new Encoders().userPasswordEncoder().encode("shalin1610"));
	}
}
