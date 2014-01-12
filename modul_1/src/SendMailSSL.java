
 
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
 
/**
 * Klasa odpowiadajaca za wysylanie wiadomosci e-mail zawierajacej klucze do prawnika.
 *
 */
public class SendMailSSL {

	/**
	 * Metoda odpowiadajaca za wysylanie wiadomosci e-mail zawierajacej klucze do prawnika.
	 * @param recipient
	 * Lancuch znakowy stanowiacy adres e-mail odbiorcy wiadomosci e-mail.
	 * @param subject
	 * Lancuch znakowy stanowiacy temat wiadomosci e-mail.
	 * @param content
	 * Lancuch znakowy stanowiacy tresc wiadomosci e-mail.
	 * @param attachmentName1
	 * Lancuch znakowy stanowiacy nazwe pliku bedacego pierwszym zalacznikiem do wiadomosci e-mail.
	 * @param attachmentPath1
	 * Lancuch znakowy stanowiacy sciezke do pliku bedacego pierwszym zalacznikiem do wiadomosci e-mail.
	 * @param attachmentName2
	 * Lancuch znakowy stanowiacy nazwe pliku bedacego drugim zalacznikiem do wiadomosci e-mail.
	 * @param attachmentPath2
	 * Lancuch znakowy stanowiacy sciezke do pliku bedacego drugim zalacznikiem do wiadomosci e-mail.
	 * @param attachmentName3
	 * Lancuch znakowy stanowiacy nazwe pliku bedacego trzecim zalacznikiem do wiadomosci e-mail.
	 * @param attachmentPath3
	 * Lancuch znakowy stanowiacy sciezke do pliku bedacego trzecim zalacznikiem do wiadomosci e-mail.
	 */
	public void sendEmail(String recipient, String subject, String content, String attachmentName1, String attachmentPath1, String attachmentName2, String attachmentPath2, String attachmentName3, String attachmentPath3) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
 
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("alicjakryptograficzna","Staracyganka");
				}
			});
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("eclipse"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(recipient));
			message.setSubject(subject);
			
			
	        MimeBodyPart messageBodyPart = new MimeBodyPart();
	        MimeBodyPart messageBodyPart1 = new MimeBodyPart();
	        MimeBodyPart messageBodyPart2 = new MimeBodyPart();
	        MimeBodyPart messageBodyPart3 = new MimeBodyPart();
	        
	        Multipart multipart = new MimeMultipart();
	        
	        messageBodyPart.setText(content);
	        
	        DataSource source1 = new FileDataSource(attachmentPath1);
	        messageBodyPart1.setDataHandler(new DataHandler(source1));
	        messageBodyPart1.setFileName(attachmentName1);
	        
	        DataSource source2 = new FileDataSource(attachmentPath2);
	        messageBodyPart2.setDataHandler(new DataHandler(source2));
	        messageBodyPart2.setFileName(attachmentName2);
	        
	        DataSource source3 = new FileDataSource(attachmentPath3);
	        messageBodyPart3.setDataHandler(new DataHandler(source3));
	        messageBodyPart3.setFileName(attachmentName3);
	        
	        multipart.addBodyPart(messageBodyPart);
	        multipart.addBodyPart(messageBodyPart1);
	        multipart.addBodyPart(messageBodyPart2);
	        multipart.addBodyPart(messageBodyPart3);	        
	        
	        
	        
	        message.setContent(multipart);


	        System.out.println("Sending");
	        
			Transport.send(message);
			ClientConfig.sent = true;
			System.out.println("Done");
 
		} catch (MessagingException e) {
			  ClientConfig.sent = false;
		}
	}

}