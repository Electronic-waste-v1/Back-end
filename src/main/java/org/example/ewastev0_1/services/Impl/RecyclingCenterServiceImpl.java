package org.example.ewastev0_1.services.Impl;

import org.example.ewastev0_1.dto.request.RecyclingCenterRequest;
import org.example.ewastev0_1.dto.response.RecyclingcenterResponse;
import org.example.ewastev0_1.services.Interface.RecyclingCenterService;

import java.util.List;

public class RecyclingCenterServiceImpl implements RecyclingCenterService {
    @Override
    public RecyclingcenterResponse addRecyclingCenter(RecyclingCenterRequest request) {
        return null;
    }

    @Override
    public RecyclingcenterResponse updateRecyclingCenter(Integer id, RecyclingCenterRequest request) {
        return null;
    }

    @Override
    public void deleteRecyclingCenter(Integer id) {

    }

    @Override
    public RecyclingcenterResponse getRecyclingCenterById(Integer id) {
        return null;
    }

    @Override
    public List<RecyclingcenterResponse> getAllRecyclingCenters() {
        return List.of();
    }

    @Override
    public List<RecyclingcenterResponse> searchRecyclingCentersByLocation(String location) {
        return List.of();
    }

    @Override
    public List<RecyclingcenterResponse> filterRecyclingCentersByAcceptedDevices(String deviceType) {
        return List.of();
    }
}
