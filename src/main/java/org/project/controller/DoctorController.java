package org.project.controller;

import org.project.model.response.DepartmentResponse;
import org.project.model.response.StaffResponse;
import org.project.service.DepartmentService;
import org.project.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private final int PAGE_SIZE = 6;

    private StaffService staffService;
    private DepartmentService departmentService;

    @Autowired
    public void setStaffService(StaffService staffService) {
        this.staffService = staffService;
    }

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @ModelAttribute("departments")
    public List<DepartmentResponse> getDepartmentsByDoctorRole() {
        return departmentService.getAllDepartmentsHaveDoctor();
    }

    @GetMapping("/page/{pageIndex}")
    public String getAllStaffByPage(@PathVariable int pageIndex, Model model) {
        Page<StaffResponse> staffResponsePage = staffService.getDoctorsByPage(pageIndex, PAGE_SIZE);
        if (pageIndex < 0 || pageIndex >= staffResponsePage.getTotalPages()) {
            return "frontend/404";
        }
        model.addAttribute("doctors", staffResponsePage.getContent());
        model.addAttribute("currentPage", pageIndex);
        model.addAttribute("totalPages", staffResponsePage.getTotalPages());
        return "frontend/doctor";
    }

    @GetMapping("/page/{pageIndex}/department/{departmentName}")
    public String getStaffByDepartment(@PathVariable int pageIndex,
                                         @PathVariable String departmentName,
                                         Model model) {
        if (!departmentService.isDepartmentNameExist(departmentName)) {
            return "frontend/404";
        }
        Page<StaffResponse> staffResponsePage = staffService.getDoctorsByDepartmentNameAndPage(departmentName, pageIndex, PAGE_SIZE);
        if (pageIndex < 0 || pageIndex >= staffResponsePage.getTotalPages()) {
            return "frontend/404";
        }
        model.addAttribute("doctors", staffResponsePage.getContent());
        model.addAttribute("currentPage", pageIndex);
        model.addAttribute("totalPages", staffResponsePage.getTotalPages());
        model.addAttribute("departmentName", departmentName);
        return "frontend/doctor";
    }

    @GetMapping("/detail/{staffId}")
    public String getDoctorDetail(@PathVariable Long staffId, Model model) {
        if (!staffService.isDoctorExist(staffId)) {
            return "frontend/404";
        }
        StaffResponse staffResponse = staffService.getDoctorByStaffId(staffId);
        model.addAttribute("doctor", staffResponse);
        List<StaffResponse> medicalStaffResponses = staffService.getColleagueDoctorByStaffId(staffResponse.getDepartmentEntityName(), staffId);
        model.addAttribute("colleagueDoctors", medicalStaffResponses);
        return "frontend/doctor-details";
    }
}
