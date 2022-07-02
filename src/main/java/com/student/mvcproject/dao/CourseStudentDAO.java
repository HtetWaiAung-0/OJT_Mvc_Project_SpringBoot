package com.student.mvcproject.dao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.student.mvcproject.dto.CourseStudentRequestDTO;


@Repository
public class CourseStudentDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public int insertCourseStudnetData(CourseStudentRequestDTO dto) {
        int result=0;      
        String sql="insert into course_student (stuId,courseName) values(?,?)";
        result = jdbcTemplate.update(sql, dto.getStuId(),dto.getCourseName());
        return result;    
    }
	
	public List<String> selectOne(String id) {		
		String sql = "select * from course_student where stuId=?";
		return jdbcTemplate.query(sql,(rs, rowNum) -> rs.getString("courseName"),id);	
	}
	
	public int deleteData(CourseStudentRequestDTO dto) {
		int result = 0;
		String sql = "delete from course_student where stuId=?";
		result = jdbcTemplate.update(sql, dto.getStuId());
		return result;

	}
}