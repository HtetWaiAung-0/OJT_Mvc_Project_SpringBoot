package com.student.mvcproject.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.student.mvcproject.dto.UserRequestDTO;
import com.student.mvcproject.dto.UserResponseDTO;

@Repository
public class UserDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;
	public int insertUserData(UserRequestDTO dto) {
        int result=0;
        String sql="insert into user(userId,userMail,userPassword,userRole) values(?,?,?,?)";
        result = jdbcTemplate.update(sql,dto.getUserId(),dto.getUserMail(),dto.getUserPassword(),dto.getUserRole());   
        return result;
    }
	
	
	public int updateUserData(UserRequestDTO dto) {
		int result = 0;
		String sql = "update user set userMail=?,userPassword=?,userRole=? where userId=?";
		result = jdbcTemplate.update(sql,dto.getUserMail(),dto.getUserPassword(),dto.getUserRole(),dto.getUserId());   
        return result;

	}
	
	public int deleteUserData(UserRequestDTO dto) {
		int result = 0;
		String sql = "delete from user where userId=?";
		result = jdbcTemplate.update(sql, dto.getUserId());
		return result;
	}
	
	public UserResponseDTO selectId(String id) {
		String sql = "select * from user where userId=?";
		
		return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new UserResponseDTO(
						rs.getString("userId"),
						rs.getString("userMail"),
						rs.getString("userPassword"),
						rs.getString("userRole")),
						id);

	}
	
	public List<UserResponseDTO> selectIdAndMail(String id,String mail) {
		String sql = "select * from user where userId=? OR userMail=?";
		return jdbcTemplate.query(sql, (rs, rowNum) -> new UserResponseDTO(
				rs.getString("userId"),
				rs.getString("userMail"),
				rs.getString("userPassword"),
				rs.getString("userRole")),
				id,mail);

	}
	
	public Boolean selectMailAndPassword(String mail,String password) {
		String sql = "select * from user where userMail=? AND userpassword=?";
		List<UserResponseDTO> check = jdbcTemplate.query(sql, (rs, rowNum) -> new UserResponseDTO(
				rs.getString("userId"),
				rs.getString("userMail"),
				rs.getString("userPassword"),
				rs.getString("userRole")),
				mail,password);
		boolean c = false;
		if(check != null) {
			c = true;
		}
		return c;
	}
	
	public List<UserResponseDTO> selectMail(String mail) {
		String sql = "select * from user where userMail=?";
		return jdbcTemplate.query(sql, (rs, rowNum) -> new UserResponseDTO(
				rs.getString("userId"),
				rs.getString("userMail"),
				rs.getString("userPassword"),
				rs.getString("userRole")),
				mail);

	}
	
	public List<UserResponseDTO> selectAll(){
		String sql = "select * from user";
		return jdbcTemplate.query(sql, (rs, rowNum) -> new UserResponseDTO(
				rs.getString("userId"),
				rs.getString("userMail"),
				rs.getString("userPassword"),
				rs.getString("userRole")));
	}
	
	public int getId() {

		String sql = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'mvcproject' AND TABLE_NAME = 'user';";
		return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getInt(1));

	}
}
