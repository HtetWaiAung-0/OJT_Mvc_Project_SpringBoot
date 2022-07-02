package com.student.mvcproject.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.student.mvcproject.bean.UserBean;
import com.student.mvcproject.dao.UserDAO;
import com.student.mvcproject.dto.UserRequestDTO;
import com.student.mvcproject.dto.UserResponseDTO;

@Controller
public class UserController {
	@Autowired
	private UserDAO dao;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPost(@RequestParam("loginMail") String loginMail,
			@RequestParam("loginPassword") String loginPassword, HttpSession session, ModelMap model) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
		session.setAttribute("date", LocalDate.now().format(formatter));

		boolean check = false;
		check = dao.selectMailAndPassword(loginMail, loginPassword);
		if (check == true || (loginMail.equals("admin@gmail.com") && loginPassword.equals("123"))) {

			session.setAttribute("loginName", loginMail);
			session.setAttribute("loginPassword", loginPassword);
			return "MNU001";

		} else {
			model.addAttribute("error", " Please check your data again.");
			return "LGN001";
		}
	}

	@RequestMapping(value = "/logOut", method = RequestMethod.GET)
	public String login(HttpSession session) {
		session.removeAttribute("loginName");
		session.removeAttribute("loginPassword");
		session.removeAttribute("date");
		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping(value = "/welcomePage", method = RequestMethod.GET)
	public String welcomePage() {
		return "MNU001";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView login() {
		return new ModelAndView("LGN001", "userBean", new UserBean());
	}

	@RequestMapping(value = "/addUserPage", method = RequestMethod.GET)
	public ModelAndView addUserPage() {
		return new ModelAndView("USR001", "userBean", new UserBean());
	}

	@RequestMapping(value = "/addUserNextPage", method = RequestMethod.GET)
	public ModelAndView addUserNextPage(ModelMap model) {
		model.addAttribute("errorFill", "Success Register!");
		return new ModelAndView("USR001", "userBean", new UserBean());
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("userBean") UserBean userBean, ModelMap model) {
		// if(bs.hasErrors()) {
		// return "addUser";
		// }
		if (userBean.getUserMail().isBlank() || userBean.getUserPassword().isBlank()
				|| userBean.getUserConPassword().isBlank() || userBean.getUserRole().isBlank()) {
			model.addAttribute("errorFill", "Fill the Blank!!!");
			return "USR001";
		} else {
			int i = dao.getId();
			String stuIdString = String.format("%03d", i);
			String finalString = "USR" + stuIdString;
			UserRequestDTO dto = new UserRequestDTO();
			dto.setUserId(finalString);
			dto.setUserMail(userBean.getUserMail());
			dto.setUserPassword(userBean.getUserPassword());
			dto.setUserRole(userBean.getUserRole());
			dao.insertUserData(dto);
			return "redirect:/addUserNextPage";
		}
	}

	@RequestMapping(value = "/updateUserPage", method = RequestMethod.GET)
	public ModelAndView updateUserPage(@RequestParam("id") String userId, ModelMap model) {

		return new ModelAndView("USR002", "userBean", dao.selectId(userId));
	}

	@RequestMapping(value = "/searchUserPage", method = RequestMethod.GET)
	public ModelAndView searchUserPage(ModelMap model) {
		List<UserResponseDTO> list = dao.selectAll();
		model.addAttribute("userList", list);
		return new ModelAndView("USR003", "userBean", new UserBean());
	}

	@RequestMapping(value = "/searchUser", method = RequestMethod.POST)
	public ModelAndView searchUser(@ModelAttribute("userBean") UserBean userBean, ModelMap model) {
		List<UserResponseDTO> showList = new ArrayList<>();
		if (userBean.getSearchUserId().isBlank() && userBean.getSearchUserMail().isBlank()) {
			showList = dao.selectAll();
			model.addAttribute("userList", showList);
			return new ModelAndView("USR003", "userBean", new UserBean());
		} else {
			showList = dao.selectIdAndMail(userBean.getSearchUserId(), userBean.getSearchUserMail());
			model.addAttribute("userList", showList);
			return new ModelAndView("USR003", "userBean", new UserBean());
		}
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("userBean") UserBean userBean, ModelMap model) {
		// if(bs.hasErrors()) {
		// return "addUser";
		// }
		if (userBean.getUserMail().isBlank() || userBean.getUserPassword().isBlank()
				|| userBean.getUserConPassword().isBlank() || userBean.getUserRole().isBlank()) {
			model.addAttribute("errorFill", "Fill the Blank!!!");
			return "USR002";
		} else {
			UserRequestDTO dto = new UserRequestDTO();
			dto.setUserId(userBean.getUserId());
			dto.setUserMail(userBean.getUserMail());
			dto.setUserPassword(userBean.getUserPassword());
			dto.setUserRole(userBean.getUserRole());
			dao.updateUserData(dto);
			model.addAttribute("errorFill", "Success Register!");
			return "redirect:/searchUserPage";
		}
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public String deleteUser(@RequestParam("id") String userId, ModelMap model) {
		UserRequestDTO dto = new UserRequestDTO();
		dto.setUserId(userId);
		dao.deleteUserData(dto);
		return "redirect:/searchUserPage";
	}
}