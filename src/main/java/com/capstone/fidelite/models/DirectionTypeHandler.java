package com.capstone.fidelite.models;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

 

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

 

public class DirectionTypeHandler extends BaseTypeHandler<Direction> {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Direction parameter, JdbcType jdbcType) throws SQLException {
	    logger.info(parameter.getCode() + "hgsadufhiasdfo*******");
		ps.setString(i, parameter.name());

	}

 

	@Override
	public Direction getNullableResult(ResultSet rs, String columnName) throws SQLException {
		logger.info(rs.getString(columnName) + "hgsadufhiasdfozzzzzzzzz@@@@@@@@@@@@@@@@*******");
		return Direction.of(rs.getString(columnName));
	}

 

	@Override
	public Direction getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		logger.info(rs.getString(columnIndex) + "hgsadufhiasdfo*************************************");
		// TODO Auto-generated method stub

		return null;
	}

 

	@Override
	public Direction getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {

		// TODO Auto-generated method stub
		return null;
	}

 

}