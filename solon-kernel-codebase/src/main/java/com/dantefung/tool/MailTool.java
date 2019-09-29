package com.dantefung.tool;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

public class MailTool {
    public static void main(String[] args) throws MessagingException, GeneralSecurityException {
        Properties props = new Properties();

        // 开启debug调试
        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", "smtp.qq.com");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);

        Session session = Session.getInstance(props);

        Message msg = new MimeMessage(session);
        msg.setSubject("这是Dante的测试邮件");
        StringBuilder builder = new StringBuilder();
        builder.append("url = " + "http://www.cnblogs.com/dantefung/");
        builder.append("\nDante的博客");
        builder.append("\n当前的时间为： " + new Date().getTime());
        msg.setText(builder.toString());
        msg.setFrom(new InternetAddress("476400902@qq.com"));

        Transport transport = session.getTransport();
        transport.connect("smtp.qq.com", "476400902@qq.com", "zzpcujbgtquccagd");

        transport.sendMessage(msg, new Address[] { new InternetAddress("476400902@qq.com") });
        transport.close();
    }
}