package com.student.mvcproject.dto;

public class CourseStudentRequestDTO {
		public CourseStudentRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
		String courseName;
		String stuId;
		public String getCourseName() {
			return courseName;
		}
		public void setCourseName(String courseName) {
			this.courseName = courseName;
		}
		public String getStuId() {
			return stuId;
		}
		public void setStuId(String stuId) {
			this.stuId = stuId;
		}
		public CourseStudentRequestDTO(String courseName, String stuId) {
			super();
			this.courseName = courseName;
			this.stuId = stuId;
		}
		
}
