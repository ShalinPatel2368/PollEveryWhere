package edu.njit.PollEveryWhere.models;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5631216956257333984L;
	private int user_authority_id;
	private int authority_id;
	private String authority;

	public int getUser_authority_id()
	{
		return user_authority_id;
	}

	public void setUser_authority_id(int user_authority_id)
	{
		this.user_authority_id = user_authority_id;
	}

	public int getAuthority_id()
	{
		return authority_id;
	}

	public void setAuthority_id(int authority_id)
	{
		this.authority_id = authority_id;
	}

	public void setAuthority(String authority)
	{
		this.authority = authority;
	}

	@Override
	public String getAuthority()
	{
		return this.authority;
	}

}
