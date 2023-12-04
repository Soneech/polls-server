package org.soneech.repository;

import org.soneech.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    @Query(value =
            "SELECT DISTINCT pl.id, pl.theme, pl.created_at, pl.user_id FROM poll pl " +
                    "JOIN question q ON q.poll_id = pl.id JOIN answer ans ON q.id = ans.question_id " +
                    "JOIN vote vt ON ans.id = vt.answer_id WHERE vt.user_id = ?1",
    nativeQuery = true)
    List<Poll> findPollsInWhichUserVoted(Long userId);
}
