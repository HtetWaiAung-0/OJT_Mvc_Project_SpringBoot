package com.student.mvcproject.dto;

public class CourseResponseDTO {
	private String courseName;
	private String courseId;
	public String getCourseName() {
		return courseName;
	}
	public CourseResponseDTO(String courseName, String courseId) {
		super();
		this.courseName = courseName;
		this.courseId = courseId;
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
	public CourseResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
}
