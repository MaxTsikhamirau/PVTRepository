package by.academy.alekhno.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bundle.Bundle;
import by.academy.alekhno.dao.connection.ConnectionPool;
import by.academy.alekhno.dao.interf.AbstractDao;
import by.academy.alekhno.dao.interf.CustomUserRoleDao;
import by.academy.alekhno.dao.interf.SqlMethode;
import by.academy.alekhno.exception.DaoException;
import by.academy.alekhno.vo.Role;
import by.academy.alekhno.vo.User;
import by.academy.alekhno.vo.UserRole;

public class UserRoleImpl extends AbstractDao<UserRole> implements CustomUserRoleDao {

	@Override
	protected String getSql(SqlMethode sqlMethode) {
		// TODO Auto-generated method stub
		switch (sqlMethode){
		case ADD:
			return Bundle.getQueryResource("query.add.user_role");
		case DELETE:
			return Bundle.getQueryResource("query.delete.user_role");
		case UPDATE:
			return Bundle.getQueryResource("query.update.user_role");
		case GET_ALL:
			return Bundle.getQueryResource("query.get.all.user_role");
		case GET_BY_ID:
			return Bundle.getQueryResource("query.get.by.id.user_role");
		default:
			
		}
			
		return null;
	}

	@Override
	protected UserRole getVO(ResultSet resultSet) throws DaoException {
		// TODO Auto-generated method stub
		UserRole userRole = new UserRole();
		User user = new User();
		Role role = new Role();
		try {
			userRole.setId(resultSet.getInt("id"));
			
			user.setId(resultSet.getInt("user_id"));
			user.setLogin(resultSet.getString("login"));
			//May be return password NULL
			user.setPassword(resultSet.getString("password"));
			user.setFirstName(resultSet.getString("first_name"));
			user.setSecondName(resultSet.getString("second_name"));
			user.setTelephone(resultSet.getLong("telephone"));
			userRole.setUser(user);
			
			role.setId(resultSet.getInt("role_id"));
			role.setName(resultSet.getString("name"));
			userRole.setRole(role);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DaoException("Get VO UserRole exception");
		}
		return userRole;
	}

	@Override
	protected void setParam(PreparedStatement preparedStatement, UserRole userRole,
			SqlMethode sqlMethode) throws DaoException {
		// TODO Auto-generated method stub
		switch (sqlMethode){
		case ADD:
			try {
				preparedStatement.setInt(1, userRole.getId());
				User user = userRole.getUser();
				preparedStatement.setInt(2, user.getId());
				Role role = userRole.getRole();
				preparedStatement.setInt(3, role.getId());
				break;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new DaoException("Set UserRole preparesStatement for ADD exception.");
			}
		case DELETE:
			try {
				preparedStatement.setInt(1, userRole.getId());
				break;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new DaoException("Set UserRole preparesStatement for DELETE exception.");
			}
		case UPDATE:
			try {
				User user = userRole.getUser();
				preparedStatement.setInt(1, user.getId());
				Role role = userRole.getRole();
				preparedStatement.setInt(2, role.getId());
				preparedStatement.setInt(3, userRole.getId());
				break;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new DaoException("Set UserRole preparesStatement for UPDATE exception.");
			}
		case GET_ALL:
			break;
		case GET_BY_ID:
			try {
				preparedStatement.setInt(1, userRole.getId());
				break;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new DaoException("Set UserRole preparesStatement for GET_BY_ID exception.");
			}
		default:
			
		}
		
	}

	public List<UserRole> getByIdUser(int user_id) throws DaoException {
		// TODO Auto-generated method stub
		List<UserRole> roles = new ArrayList<UserRole>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try{
			Connection connection = ConnectionPool.getInstance().getConnection();
			preparedStatement = connection.prepareStatement(Bundle.getQueryResource("query.get.by.user_id.user_role"));
			preparedStatement.setInt(1, user_id);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()){
				roles.add(getVO(resultSet));
			}			
		} catch (SQLException e) {
			throw new DaoException("Get List UserRole by user_id");
		} finally {
			close(resultSet, preparedStatement);
		}
		return roles;
	}

}
