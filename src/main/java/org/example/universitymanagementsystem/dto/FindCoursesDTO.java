package org.example.universitymanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.universitymanagementsystem.shared.PageRequest;

@Getter
@Setter
public class FindCoursesDTO extends PageRequest {
    private Long studentId;
    private String namePhrase;
    private Long capacity;
    private String codePhrase;
    private Integer credit;
}
