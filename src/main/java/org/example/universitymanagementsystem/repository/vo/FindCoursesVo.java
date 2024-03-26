package org.example.universitymanagementsystem.repository.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindCoursesVo {
    private Long studentId;
    private String namePhrase;
    private Long capacity;
    private String codePhrase;
    private Integer credit;
}
