package com.example.billboardproject.service.impl;

import com.example.billboardproject.model.Billboard;
import com.example.billboardproject.repository.BillboardRepository;
import com.example.billboardproject.service.BillboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillboardServiceImpl implements BillboardService {

    @Autowired
    private BillboardRepository billboardRepository;

    @Override
    public List<Billboard> getAllBillboards() {
        return billboardRepository.findAll();
    }

    @Override
    public Billboard addBillboard(Billboard billboard) {
        return billboardRepository.save(billboard);
    }

    @Override
    public Billboard updateBillboard(Billboard billboard) {
        return billboardRepository.save(billboard);
    }

    @Override
    public void deleteBillboard(Long id) {
        billboardRepository.deleteById(id);
    }
}
