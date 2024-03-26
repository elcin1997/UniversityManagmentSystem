package org.example.universitymanagementsystem.repository.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindDepartmentsVo {
    private String namePhrase;
    private String codePhrase;
    private Long capacity;
}
