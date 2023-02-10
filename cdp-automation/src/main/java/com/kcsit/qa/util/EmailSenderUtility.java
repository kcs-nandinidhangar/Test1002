package com.kcsit.qa.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kcsit.qa.constant.ApplicationConstant;

/**
 * The Class EmailSenderUtility.
 */
public class EmailSenderUtility {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderUtility.class);

	/** The is email sent triggered. */
	private static boolean isEmailSentTriggered = false;

	/** The Constant TOTEXTFIELD. */
	private static final JTextField TOTEXTFIELD = new JTextField(45);

	/** The Constant CCTEXTFIELD. */
	private static final JTextField CCTEXTFIELD = new JTextField(45);

	/** The Constant SUBJECTTEXTFIELD. */
	private static final JTextField SUBJECTTEXTFIELD = new JTextField(42);

	/** The Constant EMAILCONTENT. */
	private static final JEditorPane EMAILCONTENT = new JEditorPane();

	/**
	 * Instantiates a new email sender utility.
	 */
	private EmailSenderUtility() {
		// DUMMY
	}

	/**
	 * Creates the window.
	 */
	public static void createWindow() {
		JFrame frame = new JFrame("Email Content");
		Image icon = Toolkit.getDefaultToolkit().getImage(ApplicationConstant.EMAIL_SENT_WINDOW_LOGO_IMAGE);
		frame.setIconImage(icon);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createUI(frame);
		frame.setSize(560, 570);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	/**
	 * Creates the UI.
	 *
	 * @param frame the frame
	 */
	private static void createUI(final JFrame frame) {

		LayoutManager layout = new FlowLayout();

		JPanel senderListPanel = new JPanel();
		senderListPanel.setPreferredSize(new Dimension(550, 80));
		senderListPanel.setLayout(layout);
		JLabel toListLabel = new JLabel("TO:");
		senderListPanel.add(toListLabel);
		TOTEXTFIELD.setText(ApplicationConstant.TO_MAIL_DEFAULTLIST);
		senderListPanel.add(TOTEXTFIELD);

		JLabel ccListLabel = new JLabel("CC:");
		senderListPanel.add(ccListLabel);
		CCTEXTFIELD.setText(ApplicationConstant.CC_MAIL_DEFAULTLIST);
		senderListPanel.add(CCTEXTFIELD);

		JLabel subjectListLabel = new JLabel("Subject:");
		senderListPanel.add(subjectListLabel);
		SUBJECTTEXTFIELD.setText(ApplicationConstant.MAIL_SUBJECT);
		senderListPanel.add(SUBJECTTEXTFIELD);

		frame.getContentPane().add(senderListPanel, BorderLayout.NORTH);

		JPanel htmlRenderPanel = new JPanel();
		htmlRenderPanel.setPreferredSize(new Dimension(550, 410));
		htmlRenderPanel.setLayout(layout);
		EMAILCONTENT.setEditable(true);

		String destinationPath = ApplicationConstant.OUTPUT_FOLDER + ApplicationConstant.EMAIL_CONTENT_OUTPUT_FILE
				+ ".htm";
		try {
			EMAILCONTENT.setPage(new File(destinationPath).toURI().toURL());
		} catch (IOException e) {
			EMAILCONTENT.setContentType("text/html");
			EMAILCONTENT.setText("<html>Page not found.</html>");
		}

		JScrollPane jScrollPane = new JScrollPane(EMAILCONTENT);
		jScrollPane.setPreferredSize(new Dimension(510, 400));
		int vericalPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS;
		jScrollPane.setVerticalScrollBarPolicy(vericalPolicy);
		htmlRenderPanel.add(jScrollPane);

		frame.getContentPane().add(htmlRenderPanel, BorderLayout.CENTER);

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(layout);
		JButton sendButton = new JButton("Send Email");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				synchronized (EmailSenderUtility.class) {
					if (!isEmailSentTriggered) {
						isEmailSentTriggered = true;
						sendEmail();
					}
				}
				frame.dispose();
			}
		});
		btnPanel.add(sendButton);
		frame.getContentPane().add(btnPanel, BorderLayout.SOUTH);
	}

	/**
	 * Send email. Ref:
	 * https://mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
	 */
	private static void sendEmail() {

		Properties prop = new Properties();
		prop.put("mail.smtp.host", ApplicationConstant.SMTP_MAIL_HOST);
		prop.put("mail.smtp.port", ApplicationConstant.SMTP_MAIL_SERVER_PORT);
		prop.put("mail.smtp.auth", ApplicationConstant.SMTP_MAIL_AUTH);
		prop.put("mail.smtp.socketFactory.port", ApplicationConstant.SMTP_MAIL_SOCKET_SERVER_PORT);
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(ApplicationConstant.EMAIl_USERNAME,
						ApplicationConstant.EMAIL_PASSWORD);
			}
		});

		try {
			String htmlResponse = EMAILCONTENT.getText();
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(ApplicationConstant.EMAIl_USERNAME));
			message.setRecipients(Message.RecipientType.TO, getMailAddress(TOTEXTFIELD.getText()));
			message.setRecipients(Message.RecipientType.CC, getMailAddress(CCTEXTFIELD.getText()));
			message.setSubject(SUBJECTTEXTFIELD.getText());
			message.setContent(htmlResponse, "text/html;charset=UTF-8");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException exception) {
			LOGGER.error("Failed to send mail. Reason - {}", exception.getLocalizedMessage(), exception);
		}
	}

	/**
	 * Gets the mail address.
	 *
	 * @param mailAddressString the mail address string
	 * @return the mail address
	 * @throws AddressException the address exception
	 */
	private static InternetAddress[] getMailAddress(String mailAddressString) throws AddressException {
		InternetAddress[] internetAddresses = null;
		if (StringUtils.isNotBlank(mailAddressString)) {
			if (mailAddressString.contains(";")) {
				String[] mailAddressList = mailAddressString.split(";");
				internetAddresses = new InternetAddress[mailAddressList.length];
				for (int i = 0; i < mailAddressList.length; i++) {
					internetAddresses[i] = new InternetAddress(mailAddressList[i]);
				}
			} else {
				internetAddresses = new InternetAddress[1];
				internetAddresses[0] = new InternetAddress(mailAddressString);
			}
		}
		return internetAddresses;
	}
}
