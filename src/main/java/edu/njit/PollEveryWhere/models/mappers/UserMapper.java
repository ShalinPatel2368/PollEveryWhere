package edu.njit.PollEveryWhere.models.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.njit.PollEveryWhere.models.User;

public class UserMapper implements RowMapper<User>
{
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		User user = new User();
		try
		{
			user.setUid(rs.getInt("uid"));
			user.setUemail(rs.getString("uemail"));
			user.setUfname(rs.getString("ufname"));
			user.setUlname(rs.getString("ulname"));
			user.setPassword(rs.getString("password"));
			user.setUtype(rs.getInt("utype"));
			user.setStatus(rs.getInt("status"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return user;
	}

}
