package utils.emailUtil;

import org.apache.commons.mail.*;
import utils.constants.Constants;

import javax.activation.DataSource;

public class EmailTrigger {

    public static void main(String arg[]) throws EmailException {
        sendResultToMail("smtp.gmail.com",465,"email","pswd","receiver_email",2,0,2);
    }

    public static void sendResultToMail(String host,int port,String username,String password,String receivers,int passCount,int failCount,int totalCount) throws EmailException {

        System.out.println("started");
        HtmlEmail email = new HtmlEmail();
        email.setHostName(host);
        email.setSmtpPort(port);
        email.setAuthenticator(new DefaultAuthenticator(username, password));
        email.setSSLOnConnect(true);
        email.setFrom("automation@gmail.com");
        email.setSubject("RAFT Automation Report");
        email.setMsg("This is a test mail ... :-)");
        email.addTo(receivers);
        email.addHeader("Content-transfer-encoding", "quoted-printable");

        String msg = "<!DOCTYPE html><html><title>OnlineHTMLEditor</title><head></head><body><div>Hi Team,</div><br><div>Please find the attached automation report for the latest exeuction.</div><br><div>Status :</div><br><div>Total Count : "+totalCount+"</div><div>Pass Count  : "+passCount+"</div><div>Fail Count  : "+failCount+"</div><br><div>Regards,</div><div>RAFT Automation</div></body></html>";

        email.setHtmlMsg(msg);

        // Create the attachment
        EmailAttachment attachment = new EmailAttachment();
        System.out.println(System.getProperty("user.dir")+"/extentreports/"+System.getProperty("BUILD_NUMBER", Constants.DEFAULT_BUILD_NUMBER)+".html");
        attachment.setPath(System.getProperty("user.dir")+"/extentreports/"+System.getProperty("BUILD_NUMBER", Constants.DEFAULT_BUILD_NUMBER)+".html");
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("Extent Report");
        attachment.setName("RAFT_REPORT_"+System.getProperty("BUILD_NUMBER", Constants.DEFAULT_BUILD_NUMBER));


        // add the attachment
        email.attach(attachment);


        email.send();
        System.out.println("sent");
    }
}
