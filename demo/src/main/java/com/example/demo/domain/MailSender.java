package com.example.demo.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.thymeleaf.context.Context;

import java.util.List;

/**
 * mail送信時の各種設定クラス
 */
@AllArgsConstructor(access= AccessLevel.PRIVATE)
public class MailSender {
    private final String toEmail;
    private final String message;
    private final Context context;
    private final String templateName;

    /*
    lombok使うから消す
    public MailSender(String toEmail, String message, Context context, String templateName) {
        this.toEmail = toEmail;
        this.message = message;
        this.context = context;
        this.templateName = templateName;
    }
    */

    @NoArgsConstructor
    static class Builder {
        private String toEmail;
        private String message;
        private Context context = new Context();
        private String templateName;

        Builder toEmail(String toEmail) {
            this.toEmail = toEmail;
            return this;
        }

        Builder message(String message) {
            this.message = message;
            return this;
        }

        Builder templateName(String templateName) {
            this.templateName = templateName;
            return this;
        }

        Builder context(final String name, final Object value) {
            this.context.setVariable(name, value);
            return this;
        }

        MailSender build() {
            if (toEmail == null
                || message == null
                || templateName == null
                || context.getVariableNames().size() == 0) {

                throw new NullPointerException();
            }
            return new MailSender(toEmail, message, context, templateName);
        }

    }

}
