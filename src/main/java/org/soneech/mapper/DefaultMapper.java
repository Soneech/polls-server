package org.soneech.mapper;

import org.modelmapper.ModelMapper;
import org.soneech.dto.response.*;
import org.soneech.model.Answer;
import org.soneech.model.Poll;
import org.soneech.model.User;
import org.soneech.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public DefaultMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User convertToUser(RegistrationDTO registrationDTO) {
        return modelMapper.map(registrationDTO, User.class);
    }

    public UserInfoDTO convertToUserInfoDTO(User user) {
        UserInfoDTO userInfoDTO = modelMapper.map(user, UserInfoDTO.class);
        userInfoDTO.setCreatedPolls(convertToPollShortDTOSList(user.getCreatedPolls()));
        userInfoDTO.setPollsInWhichVoted(convertToPollShortDTOSList(user.getPollsInWhichVoted()));

        return userInfoDTO;
    }

    public UserPublicInfoDTO convertToUserPublicInfoDTO(User user) {
        UserPublicInfoDTO userPublicInfoDTO = modelMapper.map(user, UserPublicInfoDTO.class);
        userPublicInfoDTO.setCreatedPolls(convertToPollShortDTOSList(user.getCreatedPolls()));
        userPublicInfoDTO.setPollsInWhichVoted(convertToPollShortDTOSList(user.getPollsInWhichVoted()));

        return userPublicInfoDTO;
    }

    public List<PollShortDTO> convertToPollShortDTOSList(List<Poll> polls) {
        List<PollShortDTO> pollShortDTOS = new ArrayList<>();
        for (Poll poll: polls) {
            pollShortDTOS.add(convertToPollShortDTO(poll));
        }
        return pollShortDTOS;
    }

    public UserShortDTO convertToUserShortDTO(User user) {

        return modelMapper.map(user, UserShortDTO.class);
    }

    public VoteDTO convertToVoteDTO(Vote vote) {
        VoteDTO voteDTO = modelMapper.map(vote, VoteDTO.class);
        voteDTO.setUserShortDTO(convertToUserShortDTO(vote.getUser()));
        return voteDTO;
    }

    public AnswerDTO convertToAnswerDTO(Answer answer) {
        AnswerDTO answerDTO = modelMapper.map(answer, AnswerDTO.class);

        List<VoteDTO> voteDTOS = answerDTO.getVoteDTOS();
        for (Vote vote: answer.getVotes()) {
            voteDTOS.add(convertToVoteDTO(vote));
        }
        return answerDTO;
    }

    public PollDTO convertToPollDTO(Poll poll) {
        PollDTO pollDTO = modelMapper.map(poll, PollDTO.class);
        pollDTO.setUserShortDTO(convertToUserShortDTO(poll.getUser()));

        List<AnswerDTO> answerDTOS = pollDTO.getAnswerDTOS();
        for (Answer answer: poll.getAnswers()) {
            answerDTOS.add(convertToAnswerDTO(answer));
        }
        return pollDTO;
    }

    public PollPreviewDTO convertToPollPreviewDTO(Poll poll) {
        PollPreviewDTO pollPreviewDTO = modelMapper.map(poll, PollPreviewDTO.class);
        pollPreviewDTO.setUserShortDTO(convertToUserShortDTO(poll.getUser()));
        return pollPreviewDTO;
    }

    public PollShortDTO convertToPollShortDTO(Poll poll) {
        return modelMapper.map(poll, PollShortDTO.class);
    }
}
