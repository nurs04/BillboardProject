package com.example.billboardproject.service;

import com.example.billboardproject.model.Billboard;

import java.util.List;

public interface BillboardService {
    List<Billboard> getAllBillboards();
    Billboard addBillboard(Billboard billboard);
    Billboard updateBillboard(Billboard billboard);
    void deleteBillboard(Long id);
}
