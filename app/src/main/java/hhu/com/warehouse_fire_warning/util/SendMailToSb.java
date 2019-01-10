package hhu.com.warehouse_fire_warning.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailToSb {
    //属性
    //上次发送邮件时间
    private static String lastSend = "2019年1月10日 08:40:20";
    //发送时间间隔常量,单位毫秒
    private static long Interval;
    //发件人的邮箱和密码
    private static String systemEmailAccount = "harleyxiao@foxmail.com";
    //由于系统邮箱为腾讯系邮箱，给定smtp邮件服务器地址
    private static String smtpHost = "smtp.qq.com";
    //收件人邮箱，使用SetReceiveAccount指定，使用GetReceiveAccount获取
    private static String receiveEmailAccount;
    //发件信息，使用SetEmailContent指定，使用GetEmailContent获取
    private static String EmailContent;

    //公有方法
    //该函数用于设定上次发送邮件的时间
    public static void SetLastSend(String _lastSend) {
        lastSend = _lastSend;
    }

    //该函数用于获取上次发送邮件的时间
    public static String GetLastSend() {
        return lastSend;
    }

    //该函数用于设定邮件发送间隔,传入参数单位:分钟
    public static void SetInterval(int _Interval) {
        Interval = (60000 * _Interval);
    }

    //该函数用于返回邮件发送间隔,返回单位:分钟
    public static int GetInterval() {
        return (int) (Interval / 60000);
    }

    //该方法用于设定发送邮件内容
    public static void SetEmailContent(String emailContent) {
        EmailContent = emailContent;
    }

    //该方法用于获取将要发送邮件的内容
    public static String GetEmailContent() {
        return EmailContent;
    }

    //该方法用于设定收件人邮箱
    public static void SetReceiveAccount(String receiveAccount) {
        receiveEmailAccount = receiveAccount;
    }

    //该方法用于获取收件人邮箱
    public static String GetReceiveAccount() {
        return receiveEmailAccount;
    }

    //该方法用于执行发送邮件
    public static boolean sendMail() throws Exception {
        //创建参数配置，用于连接邮件服务器
        Properties props = new Properties();
        //使用的协议
        props.setProperty("mail.transport.protocol", "smtp");
        //发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", smtpHost);
        //需求请求认证
        props.setProperty("mail.smtp.auth", "true");
        //qq邮箱发件端口
        final String smtpPort = "465";
        //设定属性
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        //根据配置创建会话对象，由于和邮件服务器交互
        Session session = Session.getDefaultInstance(props);
        //设置debug模式，允许查看发送日志
        session.setDebug(true);
        //创建一封邮件
        MimeMessage message = createMimeMessage(session, systemEmailAccount, receiveEmailAccount);
        //根据session对象获取邮件
        Transport transport = session.getTransport();
        //连接邮件服务器
        transport.connect("smtp.qq.com", "1150264019@qq.com", "umkglfewfhcxhfda");
        //发送邮件到所有的收件地址
        transport.sendMessage(message, message.getAllRecipients());
        //关闭连接
        transport.close();
        return true;
    }

    //受保护方法
    //创建一封只含文本信息的邮件
    protected static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail) throws Exception {
        //创建一封邮件
        MimeMessage message = new MimeMessage(session);
        //from:发件人
        message.setFrom(new InternetAddress(sendMail, "火灾预警", "UTF-8"));
        //to:收件人
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "仓库管理员", "UTF-8"));
        //邮件主题
        message.setSubject("仓库火灾警报！", "UTF-8");
        //邮件正文
        message.setContent(GetEmailContent(), "text/html;charset=UTF-8");
        //设置发件时间
        message.setSentDate(new Date());
        //保存设置
        message.saveChanges();
        return message;
    }

    //私有方法
    //该函数用于将yyyy年MM月dd日 HH:mm:ss时间转化为unix时间戳
    private static long getTimeStamp(String Time) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = format.parse(Time);
        return date.getTime();
    }

    //该函数用于比较传入的两个long型的时间戳是否相差设定的邮件发送间隔
    private static boolean SendInterval(long timeStamp1, long timeStamp2) {
        long temp;
        //将timeStamp1设置为较大的那个数
        if (timeStamp1 < timeStamp2) {
            temp = timeStamp1;
            timeStamp1 = timeStamp2;
            timeStamp2 = temp;
        }
        //相减
        if (timeStamp1 - timeStamp2 <= Interval) {
            //System.out.println("距离上次发送时间未间隔"+String.valueOf(GetInterval())+"分钟。");
            return false;
        } else {
            //System.out.println("距离上次发送时间已满"+String.valueOf(GetInterval())+"分钟。");
            return true;
        }
    }

    //该函数为发送邮件的主函数
    //传入参数：_date为xxxx年xx月xx日 xx:xx:xx 的字符串
    //_content为邮件正文
    //_interval为发送邮件间隔
    public static void SendMail(String _date, String _content, int _interval) throws Exception {
        SetInterval(_interval);
        if (SendInterval(getTimeStamp(GetLastSend()), getTimeStamp(_date))) {
            //如果已达到发送间隔
            //设定收件人
            SetReceiveAccount("15850673022@163.com");
            //设定发件内容
            SetEmailContent(_content);
            //发送邮件
            sendMail();
            //更新上次邮件发送时间
            SetLastSend(_date);
        } else {
            Log.d("SendMailToSb","未到发送时间");
        }
    }
}
