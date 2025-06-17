package org.project.api;

import org.project.converter.StaffConverter;
import org.project.entity.StaffEntity;
import org.project.enums.Operation;
import org.project.enums.StaffRole;
import org.project.model.dto.StaffDTO;
import org.project.model.response.StaffResponse;
import org.project.repository.StaffRepository;
import org.project.utils.filter.FilterSpecification;
import org.project.utils.filter.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorAPI {
    private final StaffRole DOCTOR_ROLE = StaffRole.DOCTOR;

    private FilterSpecification<StaffEntity> filterSpecification;
    private StaffRepository staffRepository;
    private StaffConverter staffConverter;

    @Autowired
    public void setFilterSpecification(FilterSpecification<StaffEntity> filterSpecification) {
        this.filterSpecification = filterSpecification;
    }

    @Autowired
    public void setStaffRepository(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Autowired
    public void setStaffConverter(StaffConverter staffConverter) {
        this.staffConverter = staffConverter;
    }

    @GetMapping
    public List<StaffResponse> getDoctors(@RequestBody StaffDTO staffDTO) {
        filterSpecification = filterSpecification.addSearchCriteria(new SearchCriteria("staffRole", Operation.EQUALS, DOCTOR_ROLE), Operation.AND);
        filterSpecification = filterSpecification.addSearchCriteria(new SearchCriteria("fullName", Operation.CONTAINS, staffDTO.getFullName()), Operation.AND);
        filterSpecification = filterSpecification.addSearchCriteria(new SearchCriteria("departmentEntity.name", Operation.EQUALS, staffDTO.getDepartmentEntityName()), Operation.AND);
        filterSpecification = filterSpecification.addSearchCriteria(new SearchCriteria("hospitalEntity.name", Operation.EQUALS, staffDTO.getHospitalEntityName()), Operation.AND);
        filterSpecification = filterSpecification.addSearchCriteria(new SearchCriteria("reviewEntities.rating", Operation.AVG_EQUALS, staffDTO.getAverageRating()), Operation.AND);
        filterSpecification = filterSpecification.addSearchCriteria(new SearchCriteria("reviewEntities.id", Operation.COUNT_GREATER_THAN_OR_EQUAL_TO, staffDTO.getReviewCount()), Operation.AND);
        return null;
      //  return staffRepository.findAll(filterSpecification.getSpecification()).stream().map(staffConverter::toResponse).toList();
    }
}
