package edu.njit.PollEveryWhere.repositories;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.njit.PollEveryWhere.models.Authority;
import edu.njit.PollEveryWhere.models.User;
import edu.njit.PollEveryWhere.models.mappers.AuthorityMapper;
import edu.njit.PollEveryWhere.models.mappers.UserMapper;
import edu.njit.PollEveryWhere.utils.DBUtils;

@Repository
public class UserRepository
{

	@Autowired
	DBUtils dbUtils;

	Logger logger = LoggerFactory.getLogger(UserRepository.class);

	public User findByUserName(Map<String, Object> params)
	{
		// TODO Auto-generated method stub
		//boolean withPassword = Boolean.parseBoolean(params.getOrDefault("withPassword", "false").toString());
		String selectSQL = "select * from user where uemail=:userName";
		User user = dbUtils.getObject(selectSQL, params, new UserMapper());
		params.put("uid", user.getUid());

		if (user != null)
		{
			String authoritiesSQL = "select ua.user_authority_id,ua.authority_id,a.name as authority from user_authorities ua inner join user_authority_master a on a.id=ua.authority_id where user_id=:uid";
			List<Authority> authorities = dbUtils.getList(authoritiesSQL, params, new AuthorityMapper());
			user.setAuthorities(authorities);
		}
		return user;
	}

	/*public JSONObject getUserAsJSON(Map<String, Object> params, boolean withPassword)
	{
	
		String username = params.getOrDefault("userName", "").toString();
		boolean isEmail = Utils.validateEmail(username);
	
		String selectSQL = "";
		if (isEmail)
		{
			selectSQL = "select email as login,password,if(status='0' and isemailVerified='1',true,false) as enabled,id from user where email=:userName";
		}
		else if (params.containsKey("uid"))
		{
			selectSQL = "select email as login,password,if(status='0' and isemailVerified='1',true,false) as enabled,id from user where id=:uid";
		}
		else
		{
			selectSQL = "select phone as login,password,if(status='0' and isphoneVerified='1',true,false) as enabled,id from user where phone=:userName";
		}
	
		JSONObject object = dbUtils.getJSONObject(selectSQL, params);
		return object;
	
	}
	
	public Map<String, Object> signup(Map<String, Object> params)
	{
		String insertSQL = "insert into user set fname=:fname,lname=:lname,country_code=:countryCode,password=:password,status=0,created_on=NOW()";
	
		if (params.containsKey("email") && params.containsKey("phone"))
			insertSQL += ",email=:email,login=:email,phone=:phone";
		else if (params.containsKey("email"))
			insertSQL += ",email=:email,login=:email";
		else if (params.containsKey("phone"))
			insertSQL += ",phone=:phone,login=:phone";
	
		if (Boolean.parseBoolean(params.getOrDefault("phoneVerified", "false").toString()))
			insertSQL += ",isphoneVerified=1";
	
		Map<String, Object> resp = new HashMap<>();
		try
		{
			String response = dbUtils.insert(insertSQL, params);
			int generatedID = Integer.parseInt(response);
			resp.put("generatedID", generatedID);
			if (generatedID > 0)
			{
				dbUtils.insert("INSERT INTO `master`.`user_authorities`(`user_id`,`authority_id`) VALUES("
						+ generatedID + ",1)", null);
				dbUtils.insert("INSERT INTO `master`.`user_authorities`(`user_id`,`authority_id`) VALUES("
						+ generatedID + ",2)", null);
			}
		}
		catch (SQLException e)
		{
			if (e.getMessage().contains("Duplicate entry"))
			{
	
				if (e.getMessage().contains("UK_loginid"))
					resp.put("registrationError",
							"User with same email/Phone Number found. Please try to register with other Email/Phone Number.");
				else
					resp.put("registrationError",
							"User with same data found. Please change data to create your account.");
			}
			else
			{
				resp.put("registrationError", "Unknown Error. Please try again after some time.");
			}
	
			resp.put("errors", true);
		}
		return resp;
	}
	
	public boolean verifyEmail(Map<String, Object> params)
	{
		boolean status=true;
		String updateSQL="UPDATE `master`.`user` SET `isemailverified` = '1' WHERE (`id` = :uid );";
		try
		{
			if(dbUtils.update(updateSQL, params)<1)
				status=false;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			status=false;
		}
		return status;
	}
	
	public boolean updateEmailVerified(Long userId)
	{
		String updateSQL = "update `master`.`user` set isemailverified=1, status = 0, modified_on=NOW() where id=:id ";
	
		Map<String, Object> resp = new HashMap<>();
		Map<String, Object> params = new HashMap<>();
		params.put("id", userId);
		boolean status = true;
		try
		{
			int response = dbUtils.update(updateSQL, params);
		}
		catch (SQLException e)
		{
			logger.debug("Error while updating email verification", e);
			status = false;
		}
		return status;
	}
	
	public User findUserByEmail(Map<String, Object> params)
	{
	
		String email = params.getOrDefault("email", "").toString();
	
		String selectSQL = "";
		selectSQL = "select * from user where email=:email";
	
		JSONObject object = dbUtils.getJSONObject(selectSQL, params);
		User user = null;
		if (object != null)
		{
			try
			{
				user = new User();
				user.setEmail(object.getString("email"));
				user.setFirstName(object.getString("fname"));
				user.setLastName(object.getString("lname"));
				user.setId(object.getLong("id"));
				Object ob = object.get("isphoneverified");
				boolean phoneNumberVerified = false;
				if (ob != null)
				{
					if (ob instanceof Boolean)
					{
						phoneNumberVerified = (Boolean) ob;
					}
					else
					{
						if (ob instanceof Integer)
						{
							phoneNumberVerified = ((Integer) ob) == 1 ? true : false;
						}
					}
					user.setPhoneNumberVerified(phoneNumberVerified);
				}
				ob = object.get("isemailverified");
				boolean emailVerified = false;
				if (ob != null)
				{
					if (ob instanceof Boolean)
					{
						emailVerified = (Boolean) ob;
					}
					else
					{
						if (ob instanceof Integer)
						{
							emailVerified = ((Integer) ob) == 1 ? true : false;
						}
					}
					user.setEmailVerified(emailVerified);
				}
			}
			catch (JSONException ex)
			{
	
				logger.debug("Could not get fields from database", ex);
			}
		}
	
		return user;
	}
	
	public User findUserByUserid(Map<String, Object> params)
	{
	
		String email = params.getOrDefault("uid", "").toString();
	
		String selectSQL = "";
		selectSQL = "select * from user where id=:uid";
	
		JSONObject object = dbUtils.getJSONObject(selectSQL, params);
		User user = null;
		if (object != null)
		{
			try
			{
				user = new User();
				user.setEmail(object.getString("email"));
				user.setFirstName(object.getString("fname"));
				user.setLastName(object.getString("lname"));
				user.setId(object.getLong("id"));
				user.setPassword(object.getString("password"));
	
				Object ob = object.get("isphoneverified");
				boolean phoneNumberVerified = false;
				if (ob != null)
				{
					if (ob instanceof Boolean)
					{
						phoneNumberVerified = (Boolean) ob;
					}
					else
					{
						if (ob instanceof Integer)
						{
							phoneNumberVerified = ((Integer) ob) == 1 ? true : false;
						}
					}
					user.setPhoneNumberVerified(phoneNumberVerified);
				}
				ob = object.get("isemailverified");
				boolean emailVerified = false;
				if (ob != null)
				{
					if (ob instanceof Boolean)
					{
						emailVerified = (Boolean) ob;
					}
					else
					{
						if (ob instanceof Integer)
						{
							emailVerified = ((Integer) ob) == 1 ? true : false;
						}
					}
					user.setEmailVerified(emailVerified);
				}
			}
			catch (JSONException ex)
			{
				//logger.
				logger.debug("Could not get fields from database", ex);
			}
		}
	
		return user;
	}
	
	public boolean updatePassword(User user) throws Exception
	{
		String updateSQL = "update `master`.`user` set password=:password, modified_on=NOW() where id=:id ";
	
		Map<String, Object> params = new HashMap<>();
		params.put("id", user.getId());
		params.put("password", user.getPassword());
		boolean status = true;
		try
		{
			int response = dbUtils.update(updateSQL, params);
		}
		catch (SQLException e)
		{
			logger.debug("Error while updating email verification", e);
			status = false;
			throw e;
	
		}
		return status;
	
	}
	
	public User findUserByMobile(Map<String, Object> params)
	{
		String phone = params.getOrDefault("phone", "").toString();
	
		String selectSQL = "";
		selectSQL = "select * from user where phone=:phone";
		JSONObject object = dbUtils.getJSONObject(selectSQL, params);
		User user = null;
		if (object != null)
		{
			try
			{
				user = new User();
				user.setEmail(object.getString("email"));
				user.setFirstName(object.getString("fname"));
				user.setLastName(object.getString("lname"));
				user.setId(object.getLong("id"));
				Object ob = object.get("isphoneverified");
				boolean phoneNumberVerified = false;
				if (ob != null)
				{
					if (ob instanceof Boolean)
					{
						phoneNumberVerified = (Boolean) ob;
					}
					else
					{
						if (ob instanceof Integer)
						{
							phoneNumberVerified = ((Integer) ob) == 1 ? true : false;
						}
					}
					user.setPhoneNumberVerified(phoneNumberVerified);
				}
				ob = object.get("isemailverified");
				boolean emailVerified = false;
				if (ob != null)
				{
					if (ob instanceof Boolean)
					{
						emailVerified = (Boolean) ob;
					}
					else
					{
						if (ob instanceof Integer)
						{
							emailVerified = ((Integer) ob) == 1 ? true : false;
						}
					}
					user.setEmailVerified(emailVerified);
				}
			}
			catch (JSONException ex)
			{
	
				logger.debug("Could not get fields from database", ex);
			}
		}
	
		return user;
	}*/

}
