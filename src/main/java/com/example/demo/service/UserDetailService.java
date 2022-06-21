package com.example.demo.service;

import com.example.demo.converter.Converter;
import com.example.demo.error.UserNotFoundException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.persistence.entity.UserEntity;
import com.example.demo.persistence.repo.RoleRepository;
import com.example.demo.persistence.repo.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailService implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Converter<UserEntity, User> converter;

    @Override
    @Transactional(readOnly = true)
    public User loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(converter::convert)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException(String.format("User [%s] not found", username));
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll()
                .stream().map(converter::convert)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveUser(@NonNull User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        log.debug("Pass: {}", user.getPassword());

        userRepository.saveAndFlush(converter.convert(user));
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(long id) {
        return userRepository.findById(id)
                .map(converter::convert)
                .orElseThrow(() -> new UserNotFoundException(String.format("User bu id [%s] not found", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByUsername(@NonNull String username) {
        return userRepository.findByUsername(username)
                .map(converter::convert)
                .orElseThrow(() -> new UserNotFoundException(String.format("User [%s] not found", username)));
    }

    @Override
    @Transactional
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRoleById(long id) {
        return roleRepository.getRoleById(id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userRepository.saveAndFlush(converter.convert(user));
    }
}
