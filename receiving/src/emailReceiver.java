import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Properties;

/**
 * Application that downloads e-mail messages from a POP3/IMAP server’s inbox, using JavaMail API
 *
 * @author Anna Nikolskaya
 */
public class emailReceiver {

	private JFrame frame;
	private JTextField textServer;
	private JTextField textUsername;
	private JTextField textPassword;

	/**
	 * Launches the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					emailReceiver window = new emailReceiver();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Connects to server, creates session, pop3 object, folder, prints messages
	 * @param host
	 * @param myAccount
	 * @param password
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	private static String checkMail(String host, String myAccount, String password) throws MessagingException, IOException {
		//changed from pop3 to pop3s to make it work 
		String protocol="pop3s"; //or "Imap"	
	
		Properties properties= new Properties();
		properties.put("mail.pop3.host", host); // "pop.gmail.com--> for gmail"
		properties.put("mail.pop3.port", "995"); 
		properties.put("mail.pop3.starttls.enable", "true");
				
		Session getEmails= Session.getDefaultInstance(properties);
	
		//creates pop3 Store-object
		Store store=getEmails.getStore(protocol);
		store.connect(host,myAccount,password);
		System.out.println ("Connecting to the server");
		
		//crates folder object and sets mode (read only vs read and write)
		Folder emailFolder=store.getFolder("INBOX");
		emailFolder.open(Folder.READ_ONLY);
		
		Message[] messages= emailFolder.getMessages(2, 6); //I want only to print 5 emails
		
		String allMSG="";
		
				for (int i=0, n=messages.length; i<n; i++) {
			Message oneMessage=messages[i];
			
			 allMSG=allMSG+"Message: " + (i + 1)+"\nFrom: " + oneMessage.getFrom()[0]+ "\nSubject: " + oneMessage.getSubject()+"\n\n";
	
		}
		emailFolder.close();
		store.close();
		
		return allMSG;
		
	}

	/**
	 * Create the application.
	 */
	public emailReceiver() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 559, 458);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblServer = new JLabel("Server:");
		lblServer.setBounds(18, 22, 61, 16);
		frame.getContentPane().add(lblServer);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(18, 50, 80, 16);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(18, 78, 80, 16);
		frame.getContentPane().add(lblPassword);
		
		textServer = new JTextField();
		textServer.setBounds(112, 17, 365, 26);
		frame.getContentPane().add(textServer);
		textServer.setColumns(10);
		
		textUsername = new JTextField();
		textUsername.setBounds(110, 45, 367, 26);
		frame.getContentPane().add(textUsername);
		textUsername.setColumns(10);
		
		textPassword = new JTextField();
		textPassword.setBounds(110, 78, 367, 26);
		frame.getContentPane().add(textPassword);
		textPassword.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(18, 154, 523, 259);
		frame.getContentPane().add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JButton btnRecieve = new JButton("Recieve");
		btnRecieve.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String msg=checkMail(textServer.getText(), textUsername.getText(),textPassword.getText());
					textArea.setText(msg);
					textServer.setText(""); textUsername.setText("");textPassword.setText("");
				} catch (Exception e1) {
					e1.printStackTrace();
				} 
			}
		});
		btnRecieve.setBounds(360, 113, 117, 29);
		frame.getContentPane().add(btnRecieve);
	}

}
