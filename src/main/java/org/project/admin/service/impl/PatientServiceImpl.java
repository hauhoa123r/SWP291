package org.project.admin.service.impl;

import org.project.admin.dto.request.PatientRequest;
import org.project.admin.dto.request.PatientSearchRequest;
import org.project.admin.dto.response.PatientResponse;
import org.project.admin.entity.Patient;
import org.project.admin.entity.User;
import org.project.admin.enums.AuditAction;
import org.project.admin.mapper.PatientMapper;
import org.project.admin.repository.PatientRepository;
import org.project.admin.repository.UserRepository;
import org.project.admin.service.Log.PatientLogService;
import org.project.admin.service.PatientService;
import org.project.admin.specification.PatientSpecification;
import org.project.admin.util.PageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("adminPatientServiceImpl")
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final PatientMapper patientMapper;

    private final PatientLogService patientLogService;
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();


//    private final EmailService emailService;
//
//    private String generateOtp() {
//        int otp = (int)(Math.random() * 900000) + 100000;
//        return String.valueOf(otp);
//    }


    @Override
    public List<PatientResponse> getAllPatients() {
        return patientMapper.toResponseList(patientRepository.findAll());
    }

    @Override
    public PageResponse<PatientResponse> getPatientPage(Pageable pageable) {
        Page<Patient> page = patientRepository.findAll(pageable);
        Page<PatientResponse> mappedPage = page.map(patientMapper::toResponse);
        return new PageResponse<>(mappedPage);
    }

    @Override
    public PatientResponse getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh nhân"));
        return patientMapper.toResponse(patient);
    }

    @Override
    @Transactional
    public PatientResponse createPatient(PatientRequest req) {
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        Patient patient = patientMapper.toEntity(req, user);
        patient = patientRepository.save(patient);
        patientLogService.logPatientAction(patient, AuditAction.CREATE);
        return patientMapper.toResponse(patient);
    }


    @Override
    @Transactional
    public PatientResponse updatePatient(Long id, PatientRequest req) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh nhân"));

        PatientResponse oldPatient = patientMapper.toResponse(patient);

        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        patient.setUser(user);
        patient.setPhoneNumber(req.getPhoneNumber());
        patient.setEmail(req.getEmail());
        patient.setFullName(req.getFullName());
        patient.setAvatarUrl(req.getAvatarUrl());
        patient.setRelationship(req.getRelationship());
        patient.setAddress(req.getAddress());
//        patient.setGender(req.getGender());
        patient.setBirthdate(req.getBirthdate());
        patient.setBloodType(req.getBloodType());

        patient = patientRepository.save(patient);

        PatientResponse newPatient = patientMapper.toResponse(patient);

        patientLogService.logPatientUpdateAction(oldPatient, newPatient, AuditAction.UPDATE);

        return newPatient;
    }


    @Override
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh nhân"));
        patientLogService.logPatientAction(patient, AuditAction.DELETE);
        patientRepository.deleteById(id);
    }

    @Override
    public PageResponse<PatientResponse> searchPatientPage(PatientSearchRequest req, Pageable pageable) {
        Specification<Patient> spec = PatientSpecification.filter(req);
        Page<Patient> page = patientRepository.findAll(spec, pageable);
        return new PageResponse<>(page.map(patientMapper::toResponse));
    }

    @Override
    public List<PatientResponse> searchPatientsByName(String name) {
        List<Patient> patients = patientRepository.findByFullNameContainingIgnoreCase(name);
        return patientMapper.toResponseList(patients);
    }

}
