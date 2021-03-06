package com.student.mvcproject.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.student.mvcproject.dto.StudentRequestDTO;
import com.student.mvcproject.dto.StudentResponseDTO;

@Repository
public class StudentDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	public int insertStudentData(StudentRequestDTO dto) {
        int result=0;
        String sql="insert into student(stuId,stuName,stuDob,stuGender,stuPhone,stuEducation) values(?,?,?,?,?,?)";
        result = jdbcTemplate.update(sql, dto.getStuId(), dto.getStuName(),dto.getStuDob(),dto.getStuGender(),dto.getStuPhone(),dto.getStuEducation());
        return result;
    }
	public int updateStudentData(StudentRequestDTO dto) {
		int result = 0;
		String sql = "update student set stuName=?,stuDob=?,stuGender=?,stuPhone=?,stuEducation=? where stuId=?";
		result = jdbcTemplate.update(sql, dto.getStuName(),dto.getStuDob(),dto.getStuGender(),dto.getStuPhone(),dto.getStuEducation(),dto.getStuId());
		return result;
	}	
	public int deleteStudnetData(StudentRequestDTO dto) {
		int result = 0;
		String sql = "delete from student where stuId=?";
		result = jdbcTemplate.update(sql, dto.getStuId());
		return result;

	}	
	public List<StudentResponseDTO> selectId(String id) {
		
		String sql = "select * from student where stuId=?";
	
		return jdbcTemplate.query(sql, (rs, rowNum) -> new StudentResponseDTO(
				rs.getString("stuId"),
				rs.getString("stuName"),
				rs.getString("stuDob"),
				rs.getString("stuGender"),
				rs.getString("stuPhone"),
				rs.getString("stuEducation")),
				id);
	}
	
public StudentResponseDTO selectIdUpdate(String id) {
		
		String sql = "select * from student where stuId=?";
	
		return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new StudentResponseDTO(
				rs.getString("stuId"),
				rs.getString("stuName"),
				rs.getString("stuDob"),
				rs.getString("stuGender"),
				rs.getString("stuPhone"),
				rs.getString("stuEducation")),
				id);
	}
		
	public List<StudentResponseDTO> selectAll(){
		
		String sql = "select * from student";
		return jdbcTemplate.query(sql, (rs, rowNum) -> new StudentResponseDTO(
				rs.getString("stuId"),
				rs.getString("stuName"),
				rs.getString("stuDob"),
				rs.getString("stuGender"),
				rs.getString("stuPhone"),
				rs.getString("stuEducation")));
	}	
	
public List<StudentResponseDTO> selectSearchInculdeCourse(String id,String name,String course) {
		
	String sql = "SELECT distinct student.stuId,student.stuName FROM mvcproject.student right JOIN mvcproject.course_student ON student.stuId=course_student.stuId where student.stuId=? OR student.stuName Like ? OR course_student.courseName Like ?";
	List<StudentResponseDTO> yo = jdbcTemplate.query(sql, (rs, rowNum) -> new StudentResponseDTO(
			rs.getString("stuId"),
			rs.getString("stuName")),
			id,"%"+name+"%","%"+course+"%");
		return yo;
	}
	
	public int getId() {

		String sql = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'mvcproject' AND TABLE_NAME = 'student';";
		return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getInt(1));

	}
}
