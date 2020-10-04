import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.SystemColor;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Sends e-mails with help of SMTP (Simple Mail Transfer Protocol is used for sending e-mails)
 * 
 * @author Anna Nikolskaya
 */
public class Epost {

	private JFrame frame;
	private JTextField textServer;
	private JTextField textUsername;
	private JTextField textPassword;
	private JTextField textFrom;
	private JTextField textTo;
	private JTextField textSubject;
	private JTextArea textAreaMessage;

	/**
	 * Launch the application.
	 * @throws MessagingException 
	 */
	public static void main(String[] args) {
	//runs the GUI	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Epost window = new Epost();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

	private static Message prepareMessage(Session session, String myAccount, String from, String to, String subject, String messageText) {
		try {
			Message message =new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(messageText);
			return message;
			
		}catch(Exception e){
			Logger.getLogger(Epost.class.getName()).log(Level.SEVERE,null,e);
		}
		return null;
	}
	
	/**
	 * Sends the message
	 * 
	 * @param host
	 * @param myAccount
	 * @param password
	 * @param from
	 * @param to
	 * @param subject
	 * @param messageText
	 * @throws MessagingException
 */
	private static void sendMessage(String host,String myAccount, String password, String from, String to, String subject, String messageText) throws MessagingException {
		
		System.out.println("Prepeiring to send an email");
		
		Properties properties= new Properties();
	
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host); //"smtp.gmail.com--> for gmail"
		properties.put("mail.smtp.port","587"); //or 465
		
		
		Session session =Session.getInstance(properties, new Authenticator() {
		
			@Override
		protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccount,password);	
			}	
		});
		

		Message message= prepareMessage(session, myAccount, from, to, subject, messageText);
		Transport.send(message);
		System.out.println("Message is send");
	}
	/**
	 * Clears all text fields after the message is send
	 */
	private void cleartFields() {
		textServer.setText("");
		textUsername.setText("");
		textPassword.setText("");
		textFrom.setText("");
		textTo.setText("");
		textSubject.setText("");
		textAreaMessage.setText("");
	}
	
	/**
	 * Create the application.
	 */
	public Epost() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.textHighlight);
		frame.setBounds(100, 100, 465, 517);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblServer = new JLabel("Server:");
		lblServer.setBounds(17, 6, 61, 16);
		frame.getContentPane().add(lblServer);
		
		textServer = new JTextField();
		textServer.setBounds(130, 3, 295, 21);
		frame.getContentPane().add(textServer);
		textServer.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(17, 34, 85, 16);
		frame.getContentPane().add(lblUsername);
		
		textUsername = new JTextField();
		textUsername.setBounds(130, 27, 295, 26);
		frame.getContentPane().add(textUsername);
		textUsername.setColumns(10);
		
		textPassword = new JTextField();
		textPassword.setBounds(130, 58, 295, 26);
		frame.getContentPane().add(textPassword);
		textPassword.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(17, 63, 85, 16);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblFrom = new JLabel("From:");
		lblFrom.setBounds(17, 93, 61, 16);
		frame.getContentPane().add(lblFrom);
		
		textFrom = new JTextField();
		textFrom.setBounds(130, 88, 295, 26);
		frame.getContentPane().add(textFrom);
		textFrom.setColumns(10);
		
		JLabel lblTo = new JLabel("To:");
		lblTo.setBounds(17, 121, 61, 16);
		frame.getContentPane().add(lblTo);
		
		textTo = new JTextField();
		textTo.setBounds(130, 116, 295, 26);
		frame.getContentPane().add(textTo);
		textTo.setColumns(10);
		
		JLabel lblSubject = new JLabel("Subject:");
		lblSubject.setBounds(17, 149, 61, 16);
		frame.getContentPane().add(lblSubject);
		
		textSubject = new JTextField();
		textSubject.setBounds(130, 144, 295, 26);
		frame.getContentPane().add(textSubject);
		textSubject.setColumns(10);
		
		textAreaMessage = new JTextArea();
		textAreaMessage.setBounds(16, 184, 409, 255);
		frame.getContentPane().add(textAreaMessage);
		
		JButton btnSend = new JButton("SEND");
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					sendMessage(textServer.getText(),textUsername.getText(),textPassword.getText(),textFrom.getText(),textTo.getText(),textSubject.getText(),textAreaMessage.getText());
					cleartFields();
				} catch (MessagingException e1) {

					e1.printStackTrace();
				} 
			}
		});
		btnSend.setBounds(305, 451, 117, 29);
		frame.getContentPane().add(btnSend);
		
	}
}
