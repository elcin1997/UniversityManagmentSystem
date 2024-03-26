package org.example.universitymanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.universitymanagementsystem.shared.PageRequest;

@Getter
@Setter
public class FindInstructorsDTO extends PageRequest {
    private String namePhrase;
    private String experiencePhrase;
    private String emailPhrase;
    private String phoneNumberPhrase;
    private String addressPhrase;
}
