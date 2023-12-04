package org.soneech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "answer")
@NoArgsConstructor
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String text;

    @OneToMany(mappedBy = "answer")
    private List<Vote> votes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private Question question;
}
