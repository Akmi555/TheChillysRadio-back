package theChillys.chillys_radio.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import theChillys.chillys_radio.user.entity.User;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class MailTemplatesUtil {
    private final Configuration freemarkerConfiguration;
    private final ChillysRadioMailSender mailSender;

    public String createEmailTemplate(User newUser, String link) {
        String html;

        try {
            Template template = freemarkerConfiguration.getTemplate("confirm_registration_mail.ftlh");

            Map<String, Object> model = new HashMap<>();
            model.put("name", newUser.getName());
            model.put("link", link);

            return html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void sendMail(User newUser, String html) {
        mailSender.send(newUser.getEmail(), "Registration on Chillys Radio", html); //@Async
    }
}
