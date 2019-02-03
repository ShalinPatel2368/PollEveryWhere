package edu.njit.PollEveryWhere.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class Utils
{
	private Utils()
	{
	}

	public enum RendmizerType
	{
		STRING_ALPHA_NUMERIC_SPECIAL("ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%&0123456789"), STRING_ALPHA_NUMERIC(
				"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"), STRING_ALPHA(
						"ABCDEFGHIJKLMNOPQRSTUVWXYZ"), STRING_NUMERIC("0123456789");

		private String value;

		RendmizerType(String value)
		{
			this.value = value;
		}

		public String getValue()
		{
			return this.value;
		}
	}

	public static String randomAlphaNumeric(int count, RendmizerType rt)
	{

		String rendmizer_string = rt.getValue();
		StringBuilder builder = new StringBuilder();
		while (count-- != 0)
		{
			int character = (int) (Math.random() * rendmizer_string.length());
			builder.append(rendmizer_string.charAt(character));
		}
		return builder.toString();
	}

	public static long dateDifference(String date)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		long dif = 0;
		Date startDate = new Date();

		try
		{
			startDate = dateFormat.parse(date);
			Date endDate = new Date();
			dif = TimeUnit.MILLISECONDS.toDays(endDate.getTime() - startDate.getTime());
		}
		catch (ParseException e)
		{
			dif = 0;
		}

		return dif;
	}

	public static int toInteger(String str)
	{
		if (str == null || str.trim().equals("") || str.trim().length() == 0)
			return 0;
		else
			return Integer.parseInt(str);
	}

	public static float toFloat(String str)
	{
		if (str == null || str.equals(""))
			return 0f;
		else
			return Float.parseFloat(str);
	}

	public static String tostring(int id)
	{
		return String.valueOf(id);
	}

	public static String base64Decode(String encodedString)
	{
		String response = "";
		if (encodedString != null && encodedString.trim().length() > 0)
			response = new String(Base64.getDecoder().decode(encodedString));
		return response;
	}

	public static String base64Encode(String decodedString)
	{
		String response = "";
		if (decodedString != null && decodedString.trim().length() > 0)
			response = new String(Base64.getEncoder().encode(decodedString.getBytes()));
		return response;
	}

	public static String getString(String value, String ifnull)
	{
		String response = ifnull;
		if (value == null || value.trim().length() == 0)
			return response;
		return response;
	}

	public static String getException(Exception e)
	{
		String response = "";
		if (e.getMessage() != null && e.getMessage().trim().length() > 0
				&& !e.getMessage().trim().equalsIgnoreCase("null"))
			response = e.getMessage();
		return response;
	}

	public static boolean validateEmail(String email)
	{
		String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
		return Pattern.matches(regex, email);
	}

	/*public static JSONObject sendPOST(String URL, byte[] bytesToSend)
	{
		URL obj;
		String message = "";
		boolean status = false;
		try
		{
			obj = new URL(URL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
	
			// For POST only - START
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
	
			os.write(bytesToSend);
			os.flush();
			os.close();
			// For POST only - END
	
			int responseCode = con.getResponseCode();
	
			if (responseCode == HttpURLConnection.HTTP_OK)
			{ //success
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
	
				while ((inputLine = in.readLine()) != null)
				{
					response.append(inputLine);
				}
				in.close();
				message = response.toString();
				status = true;
			}
			else
			{
				message = "URI Responded with " + responseCode + ".";
				status = false;
			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			message = "Problem Requesting the URI.\n" + e.getMessage();
			status = false;
		}
		JSONObject response = new JSONObject();
		response.put("message", message);
		response.put("status", status);
	
		return response;
	}*/

	public static boolean validatePassword(String password)
	{
		return Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.{8,}).+$", password);
	}

	public static void main(String[] args)
	{
	}
}
