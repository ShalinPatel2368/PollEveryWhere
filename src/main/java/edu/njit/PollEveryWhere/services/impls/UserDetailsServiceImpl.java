package edu.njit.PollEveryWhere.services.impls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.njit.PollEveryWhere.models.User;
import edu.njit.PollEveryWhere.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{

	@Autowired
	private UserRepository userRepository;

	//@Autowired
	//private BCryptPasswordEncoder userPasswordEncoder;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{

		Map<String, Object> params = new HashMap<>();
		params.put("userName", username);
		params.put("withPassword", "true");

		User user = userRepository.findByUserName(params);
		//MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor() ;

		// check if the user has been validated. If the user has not been validated then return that the user has not been validated

		if (user != null)
		{

			if (!user.isEnabled())
			{
			}
			return user;
		}

		throw new UsernameNotFoundException("Could not find user :" + username);
	}

}