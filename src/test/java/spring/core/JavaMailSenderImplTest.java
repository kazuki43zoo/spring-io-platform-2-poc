package spring.core;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;

/**
 * Created by shimizukazuki on 2015/08/14.
 */
public class JavaMailSenderImplTest {

    @Rule
    public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP);


    @Test
    public void testConnectTestSuccess() throws MessagingException {

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("localhost");
        javaMailSender.setPort(3025);

        javaMailSender.testConnection();

    }

    @Test(expected = MessagingException.class)
    public void testConnectTestFail() throws MessagingException {

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("localhost");
        javaMailSender.setPort(19999);

        javaMailSender.testConnection();

    }


}
