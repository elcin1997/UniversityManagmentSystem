package org.example.universitymanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.universitymanagementsystem.repository.entity.UserTypeEnum;

@Getter
@Setter
public class RegisterRequestDTO {
    private String userName;
    private String password;
    private UserTypeEnum userType;
}
