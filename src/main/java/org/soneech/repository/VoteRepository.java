package org.soneech.repository;

import org.soneech.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    @Query(value = "SELECT * FROM vote WHERE answer_id=?1 AND user_id=?2", nativeQuery = true)
    Optional<Vote> findVoteByAnswerIdAndUserId(Long answerId, Long userId);

    @Query(value = "SELECT vt.id, vt.answer_id, vt.user_id FROM vote vt " +
            "JOIN answer ans ON vt.answer_id = ans.id " +
            "JOIN question q ON ans.question_id = q.id " +
            "JOIN poll pl ON (q.poll_id = pl.id AND pl.id = ?1) WHERE vt.user_id = ?2", nativeQuery = true)
    List<Vote> findUserVotesInThisPoll(Long pollId, Long userId);
}
