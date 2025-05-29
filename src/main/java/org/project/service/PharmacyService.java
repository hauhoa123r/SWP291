package org.project.service;


import org.project.model.response.PharmacyListResponse;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PharmacyService {
    List<PharmacyListResponse> getAllPharmacies();
}
