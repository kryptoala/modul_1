import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Klasa odpowiadajaca za generowanie okien powiadomien skierowanych do uzytkownika programu.
 *
 */
public class NotificationWindow extends JFrame {
	
	final NotificationWindow f_notificationWindow = this;
	
	private JPanel 
	p_panel = new JPanel(),
	p_panel1 = new JPanel();
	
	private JLabel l_label = new JLabel();
	
	private JButton b_exit = new JButton();
	
	public NotificationWindow(){  // 
		
		super("Powiadomienie");
		

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(ClientConfig.NotificationSize[0],ClientConfig.NotificationSize[1]);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		try {
			Image img = ImageIO.read(getClass().getResource("mini_powrot.png"));
				b_exit.setIcon(new ImageIcon(img));
		} catch (IOException ex) {}
		
		/*	sprawdzanie tego co robilismy i jakie info wyswietlic	*/
		if(!ClientConfig.success)
		{
			
			if(ClientConfig.writer)
			{
				try {
					Image img1 = ImageIO.read(getClass().getResource(ClientConfig.WriterNotifications[0]));
					l_label.setIcon(new ImageIcon(img1));
					} catch (IOException ex) {}
			}
			
			if(ClientConfig.signer)
			{
				try {
					Image img1 = ImageIO.read(getClass().getResource(ClientConfig.SignerNotifications[0]));
					l_label.setIcon(new ImageIcon(img1));
					} catch (IOException ex) {}
			}	
			
			if(ClientConfig.cipherer)
			{
				if (ClientConfig.nofile) //plik testament.txt nie istnieje
				{
					try {
						Image img1 = ImageIO.read(getClass().getResource(ClientConfig.CiphererNotifications[3]));
						l_label.setIcon(new ImageIcon(img1));
						} catch (IOException ex) {}
					}
				else
				{
					if(ClientConfig.cipherlength){
					try {
						Image img1 = ImageIO.read(getClass().getResource(ClientConfig.CiphererNotifications[0]));
						l_label.setIcon(new ImageIcon(img1));
						} catch (IOException ex) {}
					}
					else
					{
						try {
							Image img1 = ImageIO.read(getClass().getResource(ClientConfig.CiphererNotifications[2]));
							l_label.setIcon(new ImageIcon(img1));
							} catch (IOException ex) {}
					}
				}
			}	
			
			if(ClientConfig.sender)
			{
				try {
					Image img1 = ImageIO.read(getClass().getResource(ClientConfig.SenderNotifications[0]));
					l_label.setIcon(new ImageIcon(img1));
					} catch (IOException ex) {}
			}	
			
		}
		
		if(ClientConfig.success)
		{
			if(ClientConfig.writer)
			{
				try {
					Image img1 = ImageIO.read(getClass().getResource(ClientConfig.WriterNotifications[1]));
					l_label.setIcon(new ImageIcon(img1));
					} catch (IOException ex) {}
			}
			
			if(ClientConfig.signer)
			{
				try {
					Image img1 = ImageIO.read(getClass().getResource(ClientConfig.SignerNotifications[1]));
					l_label.setIcon(new ImageIcon(img1));
					} catch (IOException ex) {}
			}	
			
			if(ClientConfig.cipherer)
			{
				try {
					Image img1 = ImageIO.read(getClass().getResource(ClientConfig.CiphererNotifications[1]));
					l_label.setIcon(new ImageIcon(img1));
					} catch (IOException ex) {}
			}	
			
			if(ClientConfig.sender)
			{
				try {
					Image img1 = ImageIO.read(getClass().getResource(ClientConfig.SenderNotifications[1]));
					l_label.setIcon(new ImageIcon(img1));
					} catch (IOException ex) {}
			}	
			
		}

		b_exit.setBorder(new EmptyBorder(0,0,0,0));
		l_label.setBorder(new EmptyBorder(0,0,0,0));
		
		p_panel.setLayout(new BorderLayout());
		p_panel.add(l_label, BorderLayout.CENTER);
		
		p_panel1.setLayout(new BorderLayout());
		p_panel1.add(b_exit, BorderLayout.EAST);
		
		this.add(p_panel, BorderLayout.CENTER);
		this.add(p_panel1, BorderLayout.SOUTH);
		
		p_panel.setBackground(Color.black);
		p_panel1.setBackground(Color.black);
		
		/*powrót do ekranu wpisywania albo do menu */
		b_exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(ClientConfig.success)
				{					
					ClientConfig.writer = false;
					ClientConfig.signer = false;
					ClientConfig.cipherer = false;
					ClientConfig.cipherlength = false;
					ClientConfig.sender = false;
					ClientConfig.success = false;
					ClientConfig.nofile=false;
					
					ClientMenu clientMenu = new ClientMenu();
					clientMenu.setLocationRelativeTo(null);
					clientMenu.setResizable(false);
					clientMenu.setVisible(true);
					f_notificationWindow.dispose();
				}
				else
				{
					
					if(ClientConfig.writer)
					{
						
						GeneralWindow.Writer();
						f_notificationWindow.dispose();
					}
					
					if(ClientConfig.signer)
					{
						
						GeneralWindow.Signer();
						f_notificationWindow.dispose();
					}
					
					if(ClientConfig.cipherer)	
					{
						
						GeneralWindow.Cipherer();
						f_notificationWindow.dispose();
						
					}
					if(ClientConfig.sender)
					{
						
						GeneralWindow.Sender();
						f_notificationWindow.dispose();
					}
					
				}
				
			}
			
		});
	}
	
}
