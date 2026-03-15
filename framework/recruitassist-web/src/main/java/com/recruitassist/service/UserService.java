package com.recruitassist.service;

import com.recruitassist.model.UserProfile;
import com.recruitassist.model.UserRole;
import com.recruitassist.repository.UserRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserProfile> listAllUsers() {
        return userRepository.findAll();
    }

    public List<UserProfile> listUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    public Optional<UserProfile> findById(String userId) {
        return userRepository.findById(userId);
    }

    public Optional<UserProfile> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Map<String, UserProfile> indexById() {
        return userRepository.findAll().stream()
                .collect(Collectors.toMap(
                        UserProfile::getUserId,
                        Function.identity(),
                        (left, right) -> left,
                        LinkedHashMap::new));
    }
}
