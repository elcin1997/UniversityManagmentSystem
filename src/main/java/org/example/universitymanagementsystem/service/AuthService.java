package org.example.universitymanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.universitymanagementsystem.auth.JwtService;
import org.example.universitymanagementsystem.dto.AuthenticationRequestDTO;
import org.example.universitymanagementsystem.dto.AuthenticationResponseDTO;
import org.example.universitymanagementsystem.dto.RegisterRequestDTO;
import org.example.universitymanagementsystem.repository.DepartmentRepository;
import org.example.universitymanagementsystem.repository.InstructorRepository;
import org.example.universitymanagementsystem.repository.StudentRepository;
import org.example.universitymanagementsystem.repository.UserRepository;
import org.example.universitymanagementsystem.repository.entity.DepartmentEntity;
import org.example.universitymanagementsystem.repository.entity.InstructorEntity;
import org.example.universitymanagementsystem.repository.entity.StudentEntity;
import org.example.universitymanagementsystem.repository.entity.UserEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;
    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDTO register(RegisterRequestDTO request) {
        var userOptional = userRepository.findByUsername(request.getUserName());

        if (userOptional.isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        var user = createUserByUserType(request);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDTO.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        var user = userRepository.findByUsername(request.getUserName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDTO.builder()
                .token(jwtToken)
                .build();
    }

    private UserEntity createUserByUserType(RegisterRequestDTO registrationDto) {
        switch (registrationDto.getUserType()) {
            case DEPARTMENT -> {
                DepartmentEntity department = new DepartmentEntity();
                department.setUsername(registrationDto.getUserName());
                department.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
                return departmentRepository.save(department);
            }
            case INSTRUCTOR -> {
                InstructorEntity instructor = new InstructorEntity();
                instructor.setUsername(registrationDto.getUserName());
                instructor.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
                return instructorRepository.save(instructor);
            }
            case STUDENT -> {
                StudentEntity student = new StudentEntity();
                student.setUsername(registrationDto.getUserName());
                student.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
                return studentRepository.save(student);
            }
            default -> throw new IllegalArgumentException("Unsupported user type");
        }
    }
}
