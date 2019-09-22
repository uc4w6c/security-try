package com.example.demo.service;

import com.example.demo.domain.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class SendMailService {
    private final JavaMailSender javaMailSender;

    // ここに書くのは適切ではないと思うが、一旦ここに
    private static final String FROM_MAIL_ADDRESS = "test@localhost.com";

    public SendMailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(MailSender mailSender) {

        javaMailSender.send(new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                        StandardCharsets.UTF_8.name());
                helper.setFrom(FROM_MAIL_ADDRESS);
                helper.setTo(mailSender.getToEmail());
                helper.setSubject(mailSender.getSubject());
                helper.setText(getMailBody(mailSender.getTemplateName(), mailSender.getContext()), true);
            }
        });

    }

    private String getMailBody(String templateName, Context context) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(mailTemplateResolver());
        return templateEngine.process(templateName, context);

    }

    private ClassLoaderTemplateResolver mailTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("mailtemplate/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(true);
        return templateResolver;
    }

}
