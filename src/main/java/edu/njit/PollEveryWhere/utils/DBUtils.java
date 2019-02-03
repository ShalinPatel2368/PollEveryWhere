package edu.njit.PollEveryWhere.utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository("dbUtils")
public class DBUtils implements AutoCloseable
{
	private final String FOUND_ROWS = "SELECT FOUND_ROWS();";
	private Integer sql_found_rows = 0;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public String insert(String insertSQL, Map<String, Object> params) throws SQLException
	{
		try
		{
			KeyHolder holder = new GeneratedKeyHolder();
			namedParameterJdbcTemplate.update(insertSQL, new MapSqlParameterSource(params), holder);
			holder.getKeyList();
			return String.valueOf(holder.getKey().intValue());
		}
		catch (Exception e)
		{
			throw new SQLException(e.getMessage());
		}
	}

	public Integer getTotalRows()
	{
		return sql_found_rows;
	}

	public List<Map<String, Object>> getList(String selectSQL, Map<String, Object> params)
	{
		List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(selectSQL, params);
		sql_found_rows = namedParameterJdbcTemplate.queryForObject(FOUND_ROWS, params, Integer.class);
		if (list != null)
			return list;
		else
			return new ArrayList<Map<String, Object>>();
	}
	
	public <T> List<T> getList(String selectSQL, Map<String, Object> params,RowMapper<T> mapper)
	{
		List<T> list = namedParameterJdbcTemplate.query(selectSQL, params,mapper);
		sql_found_rows = namedParameterJdbcTemplate.queryForObject(FOUND_ROWS, params, Integer.class);
		if (list != null)
			return list;
		else
			return new ArrayList<T>();
	}

	public <T> T getObject(String selectSQL, Map<String, Object> params,RowMapper<T> mapper)
	{
		return namedParameterJdbcTemplate.queryForObject(selectSQL,new MapSqlParameterSource(params),mapper);
	}


	public Integer getInt(String selectSQL, Map<String, Object> params)
	{
		return namedParameterJdbcTemplate.queryForObject(selectSQL, params, Integer.class);
	}

	public String getString(String selectSQL, Map<String, Object> params)
	{
		return namedParameterJdbcTemplate.queryForObject(selectSQL, params, String.class);
	}

	@Override
	public void close() throws Exception
	{
	}
}