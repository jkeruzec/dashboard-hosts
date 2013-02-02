/**
 * 
 */
package fr.dashboard.hosts.mail.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import fr.dashboard.hosts.mail.service.DashboardHostsMailService;

/**
 * Implementation du service de mail pour DashboardHosts
 * 
 * @author ystreibel
 */
@Component("dashboard-hostsMailService")
public class DashboardHostsMailServiceImpl implements DashboardHostsMailService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardHostsMailServiceImpl.class);

    private static final String ACCOUNT_ACCESS_SUBJECT = "[Dashboard Hosts] Accès";

    private static final String HOSTS_REQUEST_SUBJECT = "[Dashboard Hosts] Vous êtes de Petit déjeuner !";

    private static final String HOSTS_CALL_SUBJECT = "[Dashboard Hosts] A table !";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    @Value("${mail.from}")
    private String from;

    @Value("${url}")
    private String url;

    /*
     * (non-Javadoc)
     * @see
     * fr.dashboard.hosts.mail.service.DashboardHostsMailService#sendEmail(java.lang.String)
     */
    @Override
    public void sendEmail(String from, String to, String subject, String text)
    {
        this.sendSimpleEmail(from, to, subject, text);
    }

    @Override
    public void sendTemplatedEmail(String from, String to, String subject, String template, Map<String, String> model) throws MessagingException
    {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);

        String emailText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, "UTF-8", model);
        helper.setText(emailText, true);
        mailSender.send(message);
    }

    private void sendSimpleEmail(String from, String to, String subject, String text)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Override
    public void sendTemplatedEmail(String to, String subject, String template, Map<String, String> model) throws MessagingException
    {
        this.sendTemplatedEmail(this.from, to, subject, template, model);
    }

    @Override
    public void sendTemplatedEmailWithAttachment(String from, String to, String subject, String template, Map<String, String> model, Resource attachment)
            throws MessagingException
    {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);

        String emailText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, "UTF-8", model);
        helper.setText(emailText, true);
        helper.addAttachment(attachment.getFilename(), attachment);
        mailSender.send(message);
    }

    // TODO should be an interface method
    public void sendTemplatedEmail(String from, List<String> tos, String cc, String subject, String template, Map<String, String> model)
            throws MessagingException
    {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(from);

        String[] tosArray = new String[tos.size()];
        helper.setTo((String[]) tos.toArray(tosArray));
        if (cc != null)
        {
            helper.setCc(cc);
        }
        helper.setSubject(subject);

        String emailText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, "UTF-8", model);
        helper.setText(emailText, true);
        mailSender.send(message);
    }

    @Override
    public void sendAccountMail(String to, String login, String password) throws MessagingException
    {
        if (StringUtils.isEmpty(to)) { return; }

        LOGGER.info("Sending information for team {}", login);

        Map<String, String> model = new HashMap<String, String>();
        model.put("login", login);
        model.put("password", password);
        model.put("url", url);

        // TODO i18n mail
        sendTemplatedEmail(to, ACCOUNT_ACCESS_SUBJECT, "mail/account-access.vm", model);
    }

    @Override
    public void sendHostsRequest(List<String> tos, String cc, String teamName, String customMsg) throws MessagingException
    {
        if (CollectionUtils.isEmpty(tos)) { return; }

        LOGGER.info("Sending hosts request for team {}", teamName);

        Map<String, String> model = new HashMap<String, String>();
        model.put("teamName", teamName);
        model.put("url", url);
        model.put("customMsg", customMsg);

        // TODO i18n mail
        sendTemplatedEmail(this.from, tos, cc, HOSTS_REQUEST_SUBJECT, "mail/hosts-request.vm", model);
    }

    @Override
    public void callTeamForHosts(List<String> emails, String teamName, String customMsg) throws MessagingException
    {
        if (CollectionUtils.isEmpty(emails)) { return; }

        LOGGER.info("Sending hosts call for team {}", teamName);

        Map<String, String> model = new HashMap<String, String>();
        model.put("teamName", teamName);
        model.put("customMsg", customMsg);

        // TODO i18n mail
        sendTemplatedEmail(this.from, emails, null, HOSTS_CALL_SUBJECT, "mail/hosts-call.vm", model);
    }
}
