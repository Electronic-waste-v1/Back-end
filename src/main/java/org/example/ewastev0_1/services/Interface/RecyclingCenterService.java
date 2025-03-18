package org.example.ewastev0_1.services.Interface;

import org.example.ewastev0_1.dto.request.RecyclingCenterRequest;
import org.example.ewastev0_1.dto.response.RecyclingcenterResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecyclingCenterService {
    RecyclingcenterResponse addRecyclingCenter(RecyclingCenterRequest request);
    RecyclingcenterResponse updateRecyclingCenter(Integer id, RecyclingCenterRequest request);
    void deleteRecyclingCenter(Integer id);
    RecyclingcenterResponse getRecyclingCenterById(Integer id);
    Page<RecyclingcenterResponse> getAllRecyclingCenters(int page, int size);
    List<RecyclingcenterResponse> searchRecyclingCentersByLocation(String location);
    List<RecyclingcenterResponse> filterRecyclingCentersByAcceptedDevices(String deviceType);
}