package org.soneech.repository;

import org.soneech.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = "SELECT * FROM question q WHERE q.poll_id=?1", nativeQuery = true)
    List<Question> findByPollId(Long pollId);

    @Query(value = "SELECT q.id, q.text, q.poll_id FROM question q " +
            "JOIN answer ans ON ans.question_id = q.id WHERE ans.id=?1", nativeQuery = true)
    Question findByAnswerId(Long answerId);
}
