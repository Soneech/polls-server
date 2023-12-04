package org.soneech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
@NoArgsConstructor
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String text;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "poll_id", referencedColumnName = "id")
    private Poll poll;
}
