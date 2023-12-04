package org.soneech.mapper;

import org.modelmapper.ModelMapper;
import org.soneech.dto.request.*;
import org.soneech.dto.response.*;
import org.soneech.model.*;
import org.soneech.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultMapper {
    private final ModelMapper modelMapper;
    private final AnswerService answerService;

    @Autowired
    public DefaultMapper(ModelMapper modelMapper, AnswerService answerService) {
        this.modelMapper = modelMapper;
        this.answerService = answerService;
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

    public QuestionDTO convertToQuestionDTO(Question question) {
        QuestionDTO questionDTO = modelMapper.map(question, QuestionDTO.class);
        List<AnswerDTO> answerDTOS = questionDTO.getAnswerDTOS();

        for (Answer answer: question.getAnswers()) {
            answerDTOS.add(convertToAnswerDTO(answer));
        }
        return questionDTO;
    }

    public PollDTO convertToPollDTO(Poll poll) {
        PollDTO pollDTO = modelMapper.map(poll, PollDTO.class);
        pollDTO.setUserShortDTO(convertToUserShortDTO(poll.getUser()));

        List<QuestionDTO> questionDTOS = pollDTO.getQuestionDTOS();
        for (Question question: poll.getQuestions()) {
            questionDTOS.add(convertToQuestionDTO(question));
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

    public Vote convertToVote(VoteRequestDTO voteRequestDTO, User user) {
        Vote vote = new Vote();
        vote.setAnswer(answerService.findById(voteRequestDTO.getAnswerId()));
        vote.setUser(user);

        return vote;
    }

    public Answer convertToAnswer(AnswerRequestDTO answerRequestDTO, Question question) {
        Answer answer = modelMapper.map(answerRequestDTO, Answer.class);
        answer.setQuestion(question);
        return answer;
    }

    public Question convertToQuestion(QuestionRequestDTO questionRequestDTO, Poll poll) {
        Question question = modelMapper.map(questionRequestDTO, Question.class);
        question.setPoll(poll);
        List<Answer> answers = question.getAnswers();

        for (AnswerRequestDTO answerDTO: questionRequestDTO.getAnswerRequestDTOS()) {
            answers.add(convertToAnswer(answerDTO, question));
        }
        return question;
    }

    public Poll convertToPoll(PollRequestDTO pollRequestDTO, User user) {
        Poll poll = modelMapper.map(pollRequestDTO, Poll.class);
        poll.setUser(user);
        List<Question> questions = poll.getQuestions();
        for (QuestionRequestDTO questionDTO: pollRequestDTO.getQuestionRequestDTOS()) {
            questions.add(convertToQuestion(questionDTO, poll));
        }

        return poll;
    }
}
