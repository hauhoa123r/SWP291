package org.project.admin.service.impl;

import org.project.admin.dto.request.StaffScheduleRequest;
import org.project.admin.dto.request.StaffScheduleSearchRequest;
import org.project.admin.dto.response.StaffScheduleResponse;
import org.project.admin.entity.Staff;
import org.project.admin.entity.StaffSchedule;
import org.project.admin.enums.AuditAction;
import org.project.admin.mapper.StaffScheduleMapper;
import org.project.admin.repository.StaffRepository;
import org.project.admin.repository.StaffScheduleRepository;
import org.project.admin.service.Log.StaffScheduleLogService;
import org.project.admin.service.StaffScheduleService;
import org.project.admin.specification.StaffScheduleSpecification;
import org.project.admin.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("adminStaffScheduleServiceImpl")
@RequiredArgsConstructor
public class StaffScheduleServiceImpl implements StaffScheduleService {

    private final StaffScheduleRepository staffScheduleRepository;
    private final StaffRepository staffRepository;
    private final StaffScheduleMapper staffScheduleMapper;
    private final StaffScheduleLogService staffScheduleLogService;

    @Override
    @Transactional
    public StaffScheduleResponse create(StaffScheduleRequest request) {
        Staff staff = staffRepository.findById(request.getStaffId()).orElse(null);
        StaffSchedule entity = staffScheduleMapper.toEntity(request);
        entity.setStaff(staff);
        StaffSchedule saved = staffScheduleRepository.save(entity);
        staffScheduleLogService.logStaffScheduleAction(saved, AuditAction.CREATE);
        return staffScheduleMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public StaffScheduleResponse update(Long id, StaffScheduleRequest request) {
        // 1. Lấy entity trước update (old)
        StaffSchedule entity = staffScheduleRepository.findById(id).orElse(null);
        StaffScheduleResponse old = staffScheduleMapper.toResponse(entity);

        // 2. Update dữ liệu
        staffScheduleMapper.updateEntityFromRequest(request, entity);

        if (request.getStaffId() != null) {
            Staff staff = staffRepository.findById(request.getStaffId()).orElse(null);
            entity.setStaff(staff);
        }

        // 3. Lưu entity mới
        StaffSchedule saved = staffScheduleRepository.save(entity);

        // 4. Lấy entity sau update (new)
        StaffScheduleResponse updated = staffScheduleMapper.toResponse(saved);

        // 5. Ghi log chi tiết thay đổi
        staffScheduleLogService.logStaffScheduleUpdateAction(old, updated, AuditAction.UPDATE);

        return updated;
    }



    @Override
    @Transactional
    public void delete(Long id) {
        StaffSchedule entity = staffScheduleRepository.findById(id).orElse(null);
        if (entity != null) {
            staffScheduleLogService.logStaffScheduleAction(entity, AuditAction.DELETE);
            staffScheduleRepository.deleteById(id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public StaffScheduleResponse getById(Long id) {
        StaffSchedule entity = staffScheduleRepository.findById(id).orElse(null);
        return staffScheduleMapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StaffScheduleResponse> getByStaffId(Long staffId) {
        List<StaffSchedule> schedules = staffScheduleRepository.findByStaff_StaffId(staffId);
        return staffScheduleMapper.toResponseList(schedules);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<StaffScheduleResponse> getByStaffId(Long staffId, Pageable pageable) {
        Page<StaffSchedule> page = staffScheduleRepository.findByStaff_StaffId(staffId, pageable);
        Page<StaffScheduleResponse> responsePage = page.map(staffScheduleMapper::toResponse);
        return new PageResponse<>(responsePage);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<StaffScheduleResponse> getAll(Pageable pageable) {
        Page<StaffSchedule> page = staffScheduleRepository.findAll(pageable);
        Page<StaffScheduleResponse> responsePage = page.map(staffScheduleMapper::toResponse);
        return new PageResponse<>(responsePage);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<StaffScheduleResponse> search(StaffScheduleSearchRequest request, Pageable pageable) {
        Page<StaffSchedule> page = staffScheduleRepository.findAll(
                StaffScheduleSpecification.filter(request), pageable
        );
        Page<StaffScheduleResponse> responsePage = page.map(staffScheduleMapper::toResponse);
        return new PageResponse<>(responsePage);
    }

}
