package com.student.mvcproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.student.mvcproject.bean.CourseBean;
import com.student.mvcproject.dao.CourseDAO;
import com.student.mvcproject.dto.CourseRequestDTO;

@Controller
public class CourseController {
    @Autowired
    private CourseDAO dao;

    @RequestMapping(value = "/courseAddPage", method = RequestMethod.GET)
    public ModelAndView courseAddPage() {
        CourseBean cBean = new CourseBean();
        int i = dao.getId();
        String finalCourseString = "COU" + String.format("%03d", i);
        cBean.setCourseId(finalCourseString);
        return new ModelAndView("BUD003", "cBean", cBean);
    }

    @RequestMapping(value = "/courseAdd", method = RequestMethod.POST)
    public String courseAdd(@ModelAttribute("cBean") CourseBean cBean, ModelMap model) {
        //// if(bs.hasErrors()) {
        //// return "addCourse";
        //// }
        if (cBean.getCourseName().isBlank()) {

            model.addAttribute("errorFill", "Fill the Blank!!!");
            return "BUD003";
        } else {

            CourseRequestDTO dto = new CourseRequestDTO();
            dto.setCourseName(cBean.getCourseName());
            dto.setCourseId(cBean.getCourseId());
            dao.insertCourseData(dto);

            model.addAttribute("errorFill", "Success Add");
            return "BUD003";
        }

    }
}