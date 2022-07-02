package com.student.mvcproject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.student.mvcproject.bean.StudentBean;
import com.student.mvcproject.dao.CourseDAO;
import com.student.mvcproject.dao.CourseStudentDAO;
import com.student.mvcproject.dao.StudentDAO;
import com.student.mvcproject.dto.CourseResponseDTO;
import com.student.mvcproject.dto.CourseStudentRequestDTO;

import com.student.mvcproject.dto.StudentRequestDTO;
import com.student.mvcproject.dto.StudentResponseDTO;


@Controller
public class StudentController {
	
	 @Autowired
	    private StudentDAO dao;
	 @Autowired
	    private CourseDAO cdao;
	 @Autowired
	 private CourseStudentDAO csdao;
	 
	   
	 @RequestMapping(value="/stuAddPage", method=RequestMethod.GET)
	    public ModelAndView stuAddPage(ModelMap model) {
	        StudentBean stuBean = new StudentBean();
	        int i = dao.getId();
	        String finalStuString = "STU" + String.format("%03d", i);
	        stuBean.setStuId(finalStuString);
	          
	       model.addAttribute("courseList",cdao.selectAllCourse());
	        return new ModelAndView("STU001","stuBean",stuBean);
	    }
	 
	 @RequestMapping(value="/stuAddNextPage", method=RequestMethod.GET)
	    public ModelAndView stuAddNextPage(ModelMap model) {
	        StudentBean stuBean = new StudentBean();
	        int i = dao.getId();
	        String finalStuString = "STU" + String.format("%03d", i);
	        stuBean.setStuId(finalStuString);
	          
	       model.addAttribute("courseList",cdao.selectAllCourse());
	       model.addAttribute("errorFill", "Success Add");	
	        return new ModelAndView("STU001","stuBean",stuBean);
	    }
	 @RequestMapping(value = "/updateStu" , method = RequestMethod.POST)
	 public String updateStu(@ModelAttribute("stuBean")StudentBean stuBean,ModelMap model) {
		 List<String> attendArray = stuBean.getStuAttend();
			
			if (stuBean.getStuName().isBlank() ) {
				model.addAttribute("errorFill", "Fill the Blank!!!");
				model.addAttribute("courseList",cdao.selectAllCourse());
				return "USR003";
			} else {
				
				StudentRequestDTO dto = new StudentRequestDTO();			
				CourseStudentRequestDTO csdto = new CourseStudentRequestDTO();			
				csdto.setStuId(stuBean.getStuId());
				csdao.deleteData(csdto);	
				for(String a : attendArray ) {
					csdto.setStuId(stuBean.getStuId());
					csdto.setCourseName(a);
					csdao.insertCourseStudnetData(csdto);
				}
				dto.setStuId(stuBean.getStuId());
				dto.setStuName(stuBean.getStuName());
				dto.setStuDob(stuBean.getStuDob());
				dto.setStuGender(stuBean.getStuGender());
				dto.setStuPhone(stuBean.getStuPhone());
				dto.setStuEducation(stuBean.getStuEducation());			
				dao.updateStudentData(dto);			
				return "redirect:/stuSearchPage";
			}
	 }
	 
	 @RequestMapping(value = "/addStu", method = RequestMethod.POST)
		public String addStu(@ModelAttribute("stuBean") StudentBean stuBean, ModelMap model) {		 
		 List<String> attendArray = stuBean.getStuAttend();
		 if (stuBean.getStuName().isBlank() || stuBean.getStuDob().isBlank() || stuBean.getStuGender().isBlank()||stuBean.getStuPhone().isBlank() ||stuBean.getStuEducation().isBlank()) {
				model.addAttribute("errorFill","Fill the Blank!!!" );
				model.addAttribute("courseList",cdao.selectAllCourse());
				return "STU001";
			}else {
				StudentRequestDTO dto = new StudentRequestDTO();	
				CourseStudentRequestDTO csdto = new CourseStudentRequestDTO();		
				for(String a : attendArray ) {
					csdto.setStuId(stuBean.getStuId());
					csdto.setCourseName(a);
					csdao.insertCourseStudnetData(csdto);
				}
				dto.setStuId(stuBean.getStuId());
				dto.setStuName(stuBean.getStuName());
				dto.setStuDob(stuBean.getStuDob());
				dto.setStuGender(stuBean.getStuGender());
				dto.setStuPhone(stuBean.getStuPhone());
				dto.setStuEducation(stuBean.getStuEducation());
				dao.insertStudentData(dto);
				
						
				return "redirect:/stuAddNextPage";
				
			}
		}
	 
	 @RequestMapping(value = "/updateStuPage", method = RequestMethod.GET)
		public ModelAndView updateStuPage(@RequestParam("id") String stuId,ModelMap model) {
		 
		 StudentResponseDTO res = dao.selectIdUpdate(stuId);
		  res.setStuAttend(csdao.selectOne(stuId));
		 
	        
	        List<CourseResponseDTO> courseList = cdao.selectAllCourse();
	       
	       model.addAttribute("courseList",courseList);
		 
			return new ModelAndView("STU002", "stuBean", res );
		}
	 
	 @RequestMapping(value = "/deleteStu", method = RequestMethod.GET)
		public String deleteStu(@RequestParam("id") String stuId, ModelMap model) {
		 
		 StudentRequestDTO dto = new StudentRequestDTO();
			 System.out.println(stuId+"1q3w1weqwe");
			CourseStudentRequestDTO csdto = new CourseStudentRequestDTO();
		 dto.setStuId(stuId);
			csdto.setStuId(stuId);
			dao.deleteStudnetData(dto);
			csdao.deleteData(csdto);
				model.addAttribute("errorFill", "Success delete");
				
				return "redirect:/stuSearchPage";
		}
	 
	 @RequestMapping(value = "/stuSearchPage", method = RequestMethod.GET)
		public ModelAndView stuSearchPage(ModelMap model) {
		 
			
			List<StudentResponseDTO> list = dao.selectAll();
			for(StudentResponseDTO a : list) {
				List<String> clist = csdao.selectOne(a.getStuId());
				a.setStuAttend(clist);   
			}
			model.addAttribute("stuList", list);
			return new ModelAndView("STU003", "stuBean", new StudentBean());
		}
	 
	
	 @RequestMapping(value = "/searchStu", method = RequestMethod.POST)
		public String searchStu(@ModelAttribute("stuBean") StudentBean stuBean, ModelMap model) {	
			String searchId = stuBean.getSearchId();
			String searchName = stuBean.getSearchName();
			String searchCourse = stuBean.getSearchCourse();		
			List<StudentResponseDTO> showList = new ArrayList<>();
			if (searchId.isBlank() && searchName.isBlank() && searchCourse.isBlank()) {
				
				showList = dao.selectAll();
				for(StudentResponseDTO a : showList) {
					List<String> clist = (List<String>) csdao.selectOne(a.getStuId());
					a.setStuAttend(clist);   
				}
				model.addAttribute("stuList", showList);
				return "STU003";
			} else {
				searchId = stuBean.getSearchId().isBlank() ? "#$*@" : stuBean.getSearchId();
				searchName = stuBean.getSearchName().isBlank() ? "#$*@" : stuBean.getSearchName();
				searchCourse = stuBean.getSearchCourse().isBlank() ? "#$*@" : stuBean.getSearchCourse();		
				showList = dao.selectSearchInculdeCourse(searchId, searchName, searchCourse);
				
				for(StudentResponseDTO a : showList) {
					List<String> clist = (List<String>) csdao.selectOne(a.getStuId());
					a.setStuAttend(clist);  
					
				}
				model.addAttribute("stuList", showList);
				return "STU003";
			}
	 }
}