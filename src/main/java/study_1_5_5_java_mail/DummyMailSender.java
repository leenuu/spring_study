package study_1_5_5_java_mail;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class DummyMailSender implements MailSender {
    @Override
    public void send(SimpleMailMessage[] simpleMailMessages) throws MailException {

    }

    @Override
    public void send(SimpleMailMessage simpleMailMessage) throws MailException {

    }
}
