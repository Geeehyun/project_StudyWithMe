package org.fullstack4.projectstudywithme;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.projectstudywithme.domain.MemberEntity;
import org.fullstack4.projectstudywithme.dto.MemberDTO;
import org.fullstack4.projectstudywithme.service.MemberServiceIf;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Log4j2
@SpringBootTest
public class MemberServiceTests {
    @Autowired
    private MemberServiceIf memberServiceIf;
//    @Test
//    public void testLogin() {
//        log.info("===========================");
////        MemberDTO memberDTO = memberServiceIf.login("test", "1234");
////        log.info("memberDTO : {}",memberDTO);
//    }
//    @Test
//    public void testJoin() {
//        log.info("===========================");
//        MemberDTO memberDTO = MemberDTO.builder()
//                .memberId("test")
//                .memberName("테스트")
//                .pwd("1234")
//                .email("wkdwl578@google.com")
//                .phone("01012123434")
//                .status("Y")
//                .build();
//        log.info("memberDTO : {}",memberDTO);
//        int result = memberServiceIf.join(memberDTO);
//        log.info("result : {}", result);
//    }
//
//    @Test
//    public void testUpdatePwdToTemp() {
//        log.info("===========================");
//        int result = memberServiceIf.updatePwdToTemp("test");
//        log.info("result : {}", result);
//    }
//
//    @Test
//    public void testUpdatePwdToNew() {
//        log.info("===========================");
////        int result = memberServiceIf.updatePwdToNew("test", "4321");
////        log.info("result : {}", result);
//    }
//
//    @Test
//    public void testUpdateMember() {
//        log.info("===========================");
//        MemberDTO memberDTO = MemberDTO.builder()
//                .memberId("test")
//                .memberName("수정")
//                .email("test@test.test")
//                .phone("123456789")
//                .build();
//        int result = memberServiceIf.updateMember(memberDTO);
//        log.info("result : {}", result);
//    }
//
//    @Test
//    public void testLeaveMember() {
//        log.info("===========================");
//        int result = memberServiceIf.leaveMember("wkdwl578");
//        log.info("result : {}", result);
//    }

    @Test
    public void testMemberList() {
        log.info("======================== testMemberList 시작");
        List<MemberDTO> memberDTOList = memberServiceIf.memberList("%dddwt%");
        for (MemberDTO dto : memberDTOList) {
            log.info("dto : {}", dto);
        }
        log.info("======================== testMemberList 종료");
    }
}
