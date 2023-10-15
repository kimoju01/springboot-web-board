package com.study.springbootwebboard.service;

import com.study.springbootwebboard.domain.Reply;
import com.study.springbootwebboard.dto.PageRequestDTO;
import com.study.springbootwebboard.dto.PageResponseDTO;
import com.study.springbootwebboard.dto.ReplyDTO;
import com.study.springbootwebboard.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long register(ReplyDTO replyDTO) {
        Reply reply = modelMapper.map(replyDTO, Reply.class);
        Long rno = replyRepository.save(reply).getRno();

        return rno;
    }

    @Override
    public ReplyDTO read(Long rno) {
        Optional<Reply> result = replyRepository.findById(rno);
        // 아래 코드 추가해주지 않으면 테스트 코드에서 없는 번호 넣어도 예외를 안 띄워서 테스트 성공 띄워버림 !!
        // 추가해주어야 NoSuchElementException 예외 발생해서 잘못된걸 알 수 있다.
        Reply reply = result.orElseThrow();

        ReplyDTO replyDTO = modelMapper.map(result, ReplyDTO.class);

        return replyDTO;
    }

    @Override
    public void modify(ReplyDTO replyDTO) {
        Optional<Reply> result = replyRepository.findById(replyDTO.getRno());
        Reply reply = result.orElseThrow();

        reply.changeText(replyDTO.getReplyText());  // 댓글 내용 수정
        replyRepository.save(reply);
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }

    @Override
    public PageResponseDTO<ReplyDTO> listOfBoard(Long bno, PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <= 0 ? 0 : pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("rno").ascending());

        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);

        List<ReplyDTO> dtoList = result.getContent().stream()
                .map(reply -> modelMapper.map(reply, ReplyDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<ReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }
}
