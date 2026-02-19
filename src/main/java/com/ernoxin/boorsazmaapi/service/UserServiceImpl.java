package com.ernoxin.boorsazmaapi.service;

import com.ernoxin.boorsazmaapi.dto.UserCreateRequest;
import com.ernoxin.boorsazmaapi.dto.UserResponse;
import com.ernoxin.boorsazmaapi.dto.UserUpdateRequest;
import com.ernoxin.boorsazmaapi.exception.DuplicateResourceException;
import com.ernoxin.boorsazmaapi.exception.ResourceNotFoundException;
import com.ernoxin.boorsazmaapi.mapper.UserMapper;
import com.ernoxin.boorsazmaapi.model.User;
import com.ernoxin.boorsazmaapi.model.UserRole;
import com.ernoxin.boorsazmaapi.repository.UserRepository;
import com.ernoxin.boorsazmaapi.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse create(UserCreateRequest request) {
        validateUniqueFieldsForCreate(request);
        User user = userMapper.toEntity(request);
        user.setRole(UserRole.USER);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponse getById(Long id) {
        validateOwnerOrAdmin(id);
        return userMapper.toDto(findById(id));
    }

    @Override
    public List<UserResponse> getAll() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    @Override
    public UserResponse update(UserUpdateRequest request) {
        validateOwnerOrAdmin(request.getId());
        User user = findById(request.getId());
        validateUniqueFieldsForUpdate(request);
        userMapper.updateEntity(request, user);
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        validateOwnerOrAdmin(id);
        User user = findById(id);
        userRepository.delete(user);
    }

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("کاربر مورد نظر یافت نشد."));
    }

    private void validateUniqueFieldsForCreate(UserCreateRequest request) {
        if (userRepository.existsByNationalCode(request.getNationalCode())) {
            throw new DuplicateResourceException("کد ملی واردشده قبلا ثبت شده است.");
        }
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new DuplicateResourceException("شماره موبایل واردشده قبلا ثبت شده است.");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("ایمیل واردشده قبلا ثبت شده است.");
        }
    }

    private void validateUniqueFieldsForUpdate(UserUpdateRequest request) {
        if (userRepository.existsByNationalCodeAndIdNot(request.getNationalCode(), request.getId())) {
            throw new DuplicateResourceException("کد ملی واردشده قبلا ثبت شده است.");
        }
        if (userRepository.existsByPhoneNumberAndIdNot(request.getPhoneNumber(), request.getId())) {
            throw new DuplicateResourceException("شماره موبایل واردشده قبلا ثبت شده است.");
        }
        if (userRepository.existsByEmailAndIdNot(request.getEmail(), request.getId())) {
            throw new DuplicateResourceException("ایمیل واردشده قبلا ثبت شده است.");
        }
    }

    private void validateOwnerOrAdmin(Long targetUserId) {
        Long currentUserId = SecurityUtils.currentUserId();
        UserRole currentUserRole = SecurityUtils.currentUserRole();
        if (currentUserRole != UserRole.ADMIN && !currentUserId.equals(targetUserId)) {
            throw new AccessDeniedException("عدم دسترسی");
        }
    }
}
