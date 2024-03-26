package org.example.universitymanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.universitymanagementsystem.shared.PageRequest;

@Getter
@Setter
public class FindStudentsDTO extends PageRequest {
    private Long courseId;
    private String namePhrase;
    private String emailPhrase;
    private String addressPhrase;
    private String phoneNumberPhrase;
}
