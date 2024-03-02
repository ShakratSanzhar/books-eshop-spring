package com.shakratsanzhar.service;

import com.shakratsanzhar.domain.dto.UserDetailCreateEditDto;
import com.shakratsanzhar.domain.dto.UserDetailReadDto;
import com.shakratsanzhar.mapper.UserDetailCreateEditMapper;
import com.shakratsanzhar.mapper.UserDetailReadMapper;
import com.shakratsanzhar.repository.UserDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDetailService {

    private final UserDetailRepository userDetailRepository;
    private final UserDetailReadMapper userDetailReadMapper;
    private final UserDetailCreateEditMapper userDetailCreateEditMapper;

    public List<UserDetailReadDto> findAll() {
        return userDetailRepository.findAll().stream()
                .map(userDetailReadMapper::map)
                .toList();
    }

    public Optional<UserDetailReadDto> findById(Long id) {
        return userDetailRepository.findById(id)
                .map(userDetailReadMapper::map);
    }

    @Transactional
    public UserDetailReadDto create(UserDetailCreateEditDto userDetailDto) {
        userDetailDto.setRegistrationDate(LocalDateTime.now());
        return Optional.of(userDetailDto)
                .map(userDetailCreateEditMapper::map)
                .map(userDetailRepository::save)
                .map(userDetailReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserDetailReadDto> update(Long id, UserDetailCreateEditDto userDetailDto) {
        return userDetailRepository.findById(id)
                .map(entity -> userDetailCreateEditMapper.map(userDetailDto, entity))
                .map(userDetailRepository::saveAndFlush)
                .map(userDetailReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return userDetailRepository.findById(id)
                .map(entity -> {
                    userDetailRepository.delete(entity);
                    userDetailRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
