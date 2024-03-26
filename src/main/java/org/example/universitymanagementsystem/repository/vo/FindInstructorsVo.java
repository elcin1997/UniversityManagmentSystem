package org.example.universitymanagementsystem.repository.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindInstructorsVo {
    private String namePhrase;
    private String experiencePhrase;
    private String emailPhrase;
    private String phoneNumberPhrase;
    private String addressPhrase;
}
