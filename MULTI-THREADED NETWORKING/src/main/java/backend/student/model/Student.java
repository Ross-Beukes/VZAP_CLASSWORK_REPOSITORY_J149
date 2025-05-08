package backend.student.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private long studentId;
    private String firstName, surname, idNumber, emailAddress;
}
