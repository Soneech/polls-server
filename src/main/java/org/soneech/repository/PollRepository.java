package org.soneech.repository;

import org.soneech.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    @Query(value =
            "SELECT pl.id, pl.theme, pl.created_at, pl.user_id FROM poll pl JOIN answer ans ON pl.id = ans.poll_id JOIN vote vt " +
                    "ON ans.id = vt.answer_id WHERE vt.user_id = ?1",
    nativeQuery = true)
    List<Poll> findPollsInWhichUserVoted(Long userId);
}
