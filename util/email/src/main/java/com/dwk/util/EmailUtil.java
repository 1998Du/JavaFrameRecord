package com.dwk.util;

import com.dwk.result.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author dwk
 * @date 2024/5/6  14:31
 * @description
 */
@Component
@Slf4j
public class EmailUtil {

    private static EmailUtil emailUtil;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private RedisUtil redisUtil;
    @Value("${spring.mail.username}")
    private String sendEmail;

    @PostConstruct
    public void EmailUtil() {
        emailUtil = this;
        emailUtil.javaMailSender = javaMailSender;
        emailUtil.redisUtil = redisUtil;
        emailUtil.sendEmail = sendEmail;
    }

    /**
     * 发送验证码
     * @param email 接收邮箱
     * @return
     */
    public static CommonResult sendCode(String email) {
        if (emailUtil.redisUtil.hasKey(email)) {
            return CommonResult.failed( "请不要重复获取验证码");
        }
        //生成验证码
        String verificationCode = String.valueOf(Math.round((Math.random() * 9 + 1) * 100000));
        MimeMessage message = emailUtil.javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setSubject("【验证码】");
            helper.setText("你的验证码为:" + verificationCode +
                    "如非本人操作，请忽略此邮件，由此给您带来的不便请谅解!");
            helper.setTo(email);
            helper.setFrom(emailUtil.sendEmail);
            emailUtil.javaMailSender.send(message);
            emailUtil.redisUtil.set(email, verificationCode, 120);
            log.info("发送成功");
            return CommonResult.success("");
        } catch (MessagingException e) {
            e.printStackTrace();
            return CommonResult.failed("发送失败，请检查邮箱地址是否正确");
        }
    }
}
