package org.fullstack4.projectstudywithme.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.fullstack4.projectstudywithme.dto.MemberDTO;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailService {
    //의존성 주입을 통해서 필요한 객체를 가져온다.
    private final JavaMailSender emailSender;
    // 타임리프를사용하기 위한 객체를 의존성 주입으로 가져온다
    private final SpringTemplateEngine templateEngine;
//    private String authNum; //랜덤 인증 코드

    //메일 양식 작성
    public MimeMessage createEmailForm(MemberDTO memberDTO) throws MessagingException, UnsupportedEncodingException {
        String memberName = memberDTO.getMemberName();
        String toEmail = memberDTO.getEmail(); //받는 사람
        String sendEmail = "geehyun4321@naver.com"; //email-config에 설정한 자신의 이메일 주소(보내는 사람)
        String title = memberName+"님 Study With Me에서 임시 비밀번호를 발급해드립니다."; //제목
        String tempPwd = memberDTO.getPwd();

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail); //보낼 이메일 설정
        message.setSubject(title); //제목 설정
        message.setFrom(sendEmail); //보내는 이메일
        message.setText(setContext(memberName, tempPwd), "utf-8", "html");

        return message;
    }

    //실제 메일 전송
    public void sendEmail(MemberDTO memberDTO) throws MessagingException, UnsupportedEncodingException {
        //메일전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(memberDTO);
        //실제 메일 전송
        emailSender.send(emailForm);
    }

    //타임리프를 이용한 context 설정
    public String setContext(String name, String code) {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("code", code);
        return templateEngine.process("mail", context); //mail.html
    }

}
