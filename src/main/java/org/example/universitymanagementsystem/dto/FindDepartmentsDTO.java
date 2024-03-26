package org.example.universitymanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.universitymanagementsystem.shared.PageRequest;

@Getter
@Setter
public class FindDepartmentsDTO extends PageRequest {
    private String namePhrase;
    private String codePhrase;
    private Long capacity;
}
