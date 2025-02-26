package org.example.ewastev0_1.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ewastev0_1.domain.entites.Ewaste;
import org.example.ewastev0_1.dto.request.EwasteRequest;
import org.example.ewastev0_1.dto.response.EwasteResponse;
import org.example.ewastev0_1.mapper.EwasteMapper;
import org.example.ewastev0_1.repository.EwasteRepository;
import org.example.ewastev0_1.services.Interface.EwasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Transactional
public class EwasteServiceImpl implements EwasteService {

    private final EwasteMapper ewasteMapper;
    private final EwasteRepository ewasteRepository;

    @Override
    public EwasteResponse createEwaste(EwasteRequest request) {
        Ewaste ewaste = ewasteMapper.toEntity(request);
        Ewaste savedEwaste = ewasteRepository.save(ewaste);
        return ewasteMapper.toResponse(savedEwaste);
    }

    @Override
    public EwasteResponse getEwasteById(Long id) {
        return null;
    }

    @Override
    public EwasteResponse updateEwaste(Long id, EwasteRequest request) {
        return null;
    }

    @Override
    public void deleteEwaste(Long id) {

    }

    @Override
    public List<EwasteResponse> getAllEwastes() {
        return List.of();
    }

    @Override
    public List<EwasteResponse> getEwastesByUser(Long userId) {
        return List.of();
    }

    @Override
    public List<EwasteResponse> searchEwasteByCategory(String category) {
        return List.of();
    }

    @Override
    public EwasteResponse updateEwasteStatus(Long id, EwasteRequest etat) {
        return null;
    }
}
