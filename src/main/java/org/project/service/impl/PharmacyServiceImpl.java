package org.project.service.impl;

import org.project.converter.ConverterPharmacyProduct;
import org.project.entity.PharmacistEntity;
import org.project.model.response.PharmacyListResponse;
import org.project.repository.PharmacyRepository;
import org.project.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PharmacyServiceImpl implements PharmacyService {
    @Autowired
    private PharmacyRepository pharmacyRepositoryImpl;

    @Autowired
    private ConverterPharmacyProduct toConverterPharmacy;

    @Override
    public List<PharmacyListResponse> getAllPharmacies() {
        List<PharmacistEntity> pharmacyProductEntity = pharmacyRepositoryImpl.findAll();
        List<PharmacyListResponse> pharmacyListResponse = toConverterPharmacy
                .toConverterPharmacyProductList(pharmacyProductEntity);
        return pharmacyListResponse;
    }
}
