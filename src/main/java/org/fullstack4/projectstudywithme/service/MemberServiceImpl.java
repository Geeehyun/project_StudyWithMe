package org.fullstack4.projectstudywithme.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.projectstudywithme.Common.CommonUtil;
import org.fullstack4.projectstudywithme.dto.MemberDTO;
import org.fullstack4.projectstudywithme.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.fullstack4.projectstudywithme.domain.MemberEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberServiceIf {
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    @Override
    public MemberDTO login(String memberId, String pwd, HttpSession session) {
        MemberEntity memberEntity = memberRepository.findAllByMemberIdAndStatusEquals(memberId, "Y");
        MemberDTO memberDTO = null;
        if (memberEntity != null) {
            if (pwd.equals(memberEntity.getPwd())) {
                if(memberEntity.getTryCount() >= 5) {
                    int tryCount = memberEntity.getTryCount();
                    memberDTO = MemberDTO.builder()
                            .tryCount(tryCount)
                            .build();
                } else {
                    memberEntity.setTryCount(0);
                    memberRepository.save(memberEntity);
                    memberDTO = modelMapper.map(memberEntity, MemberDTO.class);
                    session.setAttribute("memberDTO", memberDTO);
                }
            } else {
                int tryCount = memberEntity.getTryCount() + 1;
                memberEntity.setTryCount(tryCount);
                memberRepository.save(memberEntity);
                memberDTO = MemberDTO.builder()
                        .tryCount(tryCount)
                        .build();
            }
        }
        return memberDTO;
    }

    @Override
    public MemberDTO autoLogin(String memberId, HttpSession session) {
        MemberEntity memberEntity = memberRepository.findAllByMemberIdAndStatusEquals(memberId, "Y");
        MemberDTO memberDTO = null;
        if (memberEntity != null) {
            memberDTO = modelMapper.map(memberEntity, MemberDTO.class);
            session.setAttribute("memberDTO", memberDTO);
        }
        return memberDTO;
    }

    @Override
    public int join(MemberDTO memberDTO) {
        MemberEntity memberEntity = modelMapper.map(memberDTO, MemberEntity.class);
        log.info("memberEntity : {}",memberEntity);
        int idx = memberRepository.save(memberEntity).getIdx();
        return idx;
    }

    @Override
    public int updatePwdToTemp(String memberId) {
        String tempPwd = CommonUtil.getRandomPassword(11);
        log.info("tempPwd : {}", tempPwd);
        MemberEntity memberEntity = memberRepository.findAllByMemberIdAndStatusEquals(memberId, "Y");
        int idx = 0;
        if (memberEntity != null) {
            memberEntity.setPwd(tempPwd);
            idx = memberRepository.save(memberEntity).getIdx();
        }
        return idx;
    }

    @Override
    public int updatePwdToNew(String memberId, String oldPwd, String newPwd) {
        MemberEntity memberEntity = memberRepository.findAllByMemberIdAndStatusEquals(memberId, "Y");
        int idx = 0;
        if (memberEntity.getPwd().equals(oldPwd)) {
            memberEntity.setPwd(newPwd);
            log.info("SERVICE memberEntity : {}", memberEntity);
            idx = memberRepository.save(memberEntity).getIdx();
        }
        return idx;
    }

    @Transactional
    @Override
    public int updateMember(MemberDTO newMemberDTO) {
        MemberEntity orgMemberEntity = memberRepository.findAllByMemberId(newMemberDTO.getMemberId());
        if(newMemberDTO.getPwd().isEmpty()) {
            newMemberDTO.setPwd(orgMemberEntity.getPwd());
        }
        MemberEntity memberEntity = modelMapper.map(newMemberDTO, MemberEntity.class);
        memberEntity.setIdx(orgMemberEntity.getIdx());
        log.info("SERVICE memberEntity : {}", memberEntity);
        int idx = memberRepository.save(memberEntity).getIdx();
        return idx;
    }

    @Override
    public int leaveMember(String memberId) {
        MemberEntity memberEntity = memberRepository.findAllByMemberId(memberId);
        memberEntity.setStatus("N");
        memberEntity.setLeave_date(LocalDateTime.now());
        int idx = memberRepository.save(memberEntity).getIdx();
        return idx;
    }

    @Override
    public int checkId(String memberId) {
        int result = (int) memberRepository.countAllByMemberId(memberId);
        return result;
    }

    @Override
    public int checkEmail(String email) {
        int result = (int) memberRepository.countAllByEmail(email);
        return result;
    }

    @Override
    public MemberDTO selectMember(String memberId) {
        MemberEntity memberEntity = memberRepository.findAllByMemberId(memberId);
        MemberDTO memberDTO = modelMapper.map(memberEntity, MemberDTO.class);
        return memberDTO;
    }

    @Override
    public List<MemberDTO> memberList(String memberIdOrName) {
        List<MemberEntity> memberEntityList = memberRepository.findMemberEntityByMemberIdLikeOrMemberNameLikeAndStatusEquals("%"+memberIdOrName+"%", "%"+memberIdOrName+"%", "Y");
        List<MemberDTO> memberDTOList = memberEntityList.stream().map(vo -> modelMapper.map(vo, MemberDTO.class)).collect(Collectors.toList());
        return memberDTOList;
    }
}
