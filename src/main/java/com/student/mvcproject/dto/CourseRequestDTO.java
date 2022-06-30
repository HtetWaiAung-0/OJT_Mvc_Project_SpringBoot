package com.student.mvcproject.dto;

public class CourseRequestDTO {
	private String courseName;
	private String courseId;
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public CourseRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
}
