package edu.njit.PollEveryWhere.models.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.njit.PollEveryWhere.models.Authority;

public class AuthorityMapper implements RowMapper<Authority>
{

	@Override
	public Authority mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		Authority authority = new Authority();
		try
		{
			authority.setAuthority(rs.getString("authority"));
			authority.setAuthority_id(rs.getInt("authority_id"));
			authority.setUser_authority_id(rs.getInt("user_authority_id"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return authority;
	}

}
