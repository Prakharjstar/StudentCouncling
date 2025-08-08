package com.counseling.studentapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarksInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String board10th;
    private String marks10th;

    private String board12th;
    private String marks12th;

    private String entranceExam;
    private String entranceScore;

    private String passingYear;
    private String category;
    private String recommendation;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentInfo student;
}
