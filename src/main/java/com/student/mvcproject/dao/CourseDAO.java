package com.student.mvcproject.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.student.mvcproject.dto.CourseRequestDTO;
import com.student.mvcproject.dto.CourseResponseDTO;


@Repository
public class CourseDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public int insertCourseData(CourseRequestDTO dto) {
		int result = 0;
		String sql = "insert into course(courseId,courseName) values(?,?)";
		result = jdbcTemplate.update(sql, dto.getCourseId(), dto.getCourseName());
		return result;
	}

	public int updateCourseData(CourseRequestDTO dto) {
		int result = 0;
		String sql = "update course set courseName=?" + "where courseId=?";
		result = jdbcTemplate.update(sql, dto.getCourseName(), dto.getCourseId());

		return result;

	}

	public int deleteCourseData(CourseRequestDTO dto) {
		int result = 0;
		String sql = "delete from course where courseId=?";

		result = jdbcTemplate.update(sql, dto.getCourseId());
		return result;

	}

	public CourseResponseDTO selectOne(CourseRequestDTO dto) {

		String sql = "select * from course where courseId=?";
		return jdbcTemplate.queryForObject(sql,
				(rs, rowNum) -> new CourseResponseDTO(rs.getString("courseId"), rs.getString("courseName")),
				dto.getCourseId());

	}

	public List<CourseResponseDTO> selectAllCourse() {

		String sql = "select * from course";
		return jdbcTemplate.query(sql, (rs,rowNum)-> new CourseResponseDTO(rs.getString("courseId"),rs.getString("courseName")));
	
		
	}
	
	
	public int getId() {

		String sql = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'mvcproject' AND TABLE_NAME = 'course';";
		return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getInt(1));

	}
}