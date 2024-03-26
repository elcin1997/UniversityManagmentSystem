package org.example.universitymanagementsystem.repository.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindStudentsVo {
    private Long courseId;
    private String namePhrase;
    private String emailPhrase;
    private String addressPhrase;
    private String phoneNumberPhrase;
}
