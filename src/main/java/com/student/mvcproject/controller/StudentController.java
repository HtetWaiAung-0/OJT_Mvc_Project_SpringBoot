package com.student.mvcproject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	 @RequestMapping(value = "/updateStu" , method = RequestMethod.POST)
	 public String updateStu(@ModelAttribute("stuBean")StudentBean stuBean,ModelMap model) {
		 List<String> attendArray = stuBean.getStuAttend();
			
			if (stuBean.getStuName().isBlank() ) {
				model.addAttribute("errorFill", "Fill the Blank!!!");
				model.addAttribute("ststuBean", stuBean);
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
				return "redirect:/SearchStudentController";
			}
	 }
	 
	 @RequestMapping(value = "/addStu", method = RequestMethod.POST)
		public String addStu(@ModelAttribute("stuBean") StudentBean stuBean, ModelMap model) {		 
		 List<String> attendArray = stuBean.getStuAttend();
		 if (stuBean.getStuName().isBlank() || stuBean.getStuDob().isBlank() || stuBean.getStuGender().isBlank()||stuBean.getStuPhone().isBlank() ||stuBean.getStuEducation().isBlank()) {
				model.addAttribute("errorFill","Fill the Blank!!!" );
				
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
				
				model.addAttribute("errorFill", "Success Add");			
				return "STU001";
				
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

				if (searchId.isBlank() && searchName.isBlank()) {

					List<String> revList = (List<String>) csdao.selectReverse(searchCourse);
					for(String v : revList) {
						showList = dao.selectId(v);
					}
					for(StudentResponseDTO a : showList) {
						List<String> clist = (List<String>) csdao.selectOne(a.getStuId());
						a.setStuAttend(clist);   
					}
					
				} else if (searchId.isBlank() && searchCourse.isBlank()) {
					showList = dao.selectName(searchName);
					for(StudentResponseDTO a : showList) {
						List<String> clist = (List<String>) csdao.selectOne(a.getStuId());
						a.setStuAttend(clist);   
					}
					
				} else if (searchName.isBlank() && searchCourse.isBlank()) {
					showList = dao.selectId(searchId);
					for(StudentResponseDTO a : showList) {
						List<String> clist = (List<String>) csdao.selec tOne(a.getStuId());
						a.setStuAttend(clist);   
					}
				} else if (searchName.isBlank()) {
					List<String> revList = (List<String>) csdao.selectReverse(searchCourse);
					for(String v : revList) {
						showList = dao.selectId(v);
					}
					showList = dao.selectId(searchId);
					for(StudentResponseDTO a : showList) {
						List<String> clist = (List<String>) csdao.selectOne(a.getStuId());
						a.setStuAttend(clist);   
					}
				} else if (searchId.isBlank()) {
					List<String> revList = (List<String>) csdao.selectReverse(searchCourse);
					for(String v : revList) {
						showList = dao.selectId(v);
					}
					showList = dao.selectName(searchName);
					for(StudentResponseDTO a : showList) {
						List<String> clist = (List<String>) csdao.selectOne(a.getStuId());
						a.setStuAttend(clist);   
					}
					
				} else if (searchCourse.isBlank()) {
					showList = dao.selectIdAndName(searchId, searchName);
					for(StudentResponseDTO a : showList) {
						List<String> clist = (List<String>) csdao.selectOne(a.getStuId());
						a.setStuAttend(clist);   
					}
				} else {
					List<String> revList = (List<String>) csdao.selectReverse(searchCourse);
					for(String v : revList) {
						showList = dao.selectId(v);
					}
					showList = dao.selectIdAndName(searchId, searchName);
					for(StudentResponseDTO a : showList) {
						List<String> clist = (List<String>) csdao.selectOne(a.getStuId());
						a.setStuAttend(clist);   
					}
				}

				model.addAttribute("stuList", showList);
				

				return "STU003";
			}
	 }
}