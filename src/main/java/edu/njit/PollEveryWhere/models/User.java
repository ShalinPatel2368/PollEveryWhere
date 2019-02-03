package edu.njit.PollEveryWhere.models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5011142412612697666L;
	/**
	 * 
	 */
	private int uid;
	private String uemail = "";
	private String ufname = "";
	private String ulname = "";
	private String password = "";
	private int utype;
	private int status;
	private List<Authority> authorities = null;

	public int getUid()
	{
		return uid;
	}

	public void setUid(int uid)
	{
		this.uid = uid;
	}

	public String getUfname()
	{
		return ufname;
	}

	public void setUfname(String ufname)
	{
		this.ufname = ufname;
	}

	public String getUlname()
	{
		return ulname;
	}

	public void setUlname(String ulname)
	{
		this.ulname = ulname;
	}

	public void setUemail(String uemail)
	{
		this.uemail = uemail;
	}

	public void setAuthorities(List<Authority> authorities)
	{
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword()
	{
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername()
	{
		// TODO Auto-generated method stub
		return uemail;
	}

	@Override
	public boolean isAccountNonExpired()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		// TODO Auto-generated method stub
		return true;
	}

	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public int getUtype()
	{
		return utype;
	}

	public void setUtype(int utype)
	{
		this.utype = utype;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

}
