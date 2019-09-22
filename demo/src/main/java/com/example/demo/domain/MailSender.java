package com.example.demo.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.thymeleaf.context.Context;

import java.util.List;

/**
 * mail送信時の各種設定クラス
 */
@AllArgsConstructor(access= AccessLevel.PRIVATE)
@Getter
public class MailSender {
    private final String toEmail;
    private final String subject;
    private final Context context;
    private final String templateName;

    @NoArgsConstructor
    public static class Builder {
        private String toEmail;
        private String subject;
        private Context context = new Context();
        private String templateName;

        public Builder toEmail(String toEmail) {
            this.toEmail = toEmail;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder templateName(String templateName) {
            this.templateName = templateName;
            return this;
        }

        public Builder context(final String name, final Object value) {
            this.context.setVariable(name, value);
            return this;
        }

        public MailSender build() {
            if (toEmail == null
                || subject == null
                || templateName == null
                || context.getVariableNames().size() == 0) {

                throw new NullPointerException();
            }
            return new MailSender(toEmail, subject, context, templateName);
        }

    }

}
