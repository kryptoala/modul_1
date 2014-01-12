
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.concurrent.*;

/**
 * Klasa odpowiadajaca za generowanie graficznego interfejsu uzytkownika programu.
 *
 */
public class ClientMenu extends JFrame {
	/**
	 * f_menu = this
	 */
	final ClientMenu f_menu = this;
	final ClientConfig c_config = new ClientConfig();
	final JButton 
			/**
			 * przycisk do spisywania testamentu
			 */
			b_writer = new JButton(),
			/**
			 * przycisk podpisywania testamentu
			*/
			b_sign = new JButton(),
			/**
			* szyfrowanie testamentu
			 */
			b_cipher = new JButton(),
			/**
			 * przycisk do wysylania
			 */
			b_send = new JButton(),
			/**
			 * przycisk do wyjscia
			 */
			b_exit = new JButton();
	final JPanel p_panel1 = new JPanel(); //panel na przyciski
	final JPanel p_panel2 = new JPanel(); // panel na exit
	final JPanel p_panel3 = new JPanel(); // panel na na label
	final JLabel l_label = new JLabel(); // label na nazwe

	public ClientMenu(){
		super("Zarz¹dzanie testamentem");
		

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(ClientConfig.MenuSize[0], ClientConfig.MenuSize[1]);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		
		/* obrazki  na buttonach */
		try {
			Image img = ImageIO.read(getClass().getResource(ClientConfig.MenuButtons[0]));
				b_writer.setIcon(new ImageIcon(img));
			Image img1 = ImageIO.read(getClass().getResource(ClientConfig.MenuButtons[1]));
				b_sign.setIcon(new ImageIcon(img1));
			Image img2 = ImageIO.read(getClass().getResource(ClientConfig.MenuButtons[2]));
				b_cipher.setIcon(new ImageIcon(img2));
		    Image img3 = ImageIO.read(getClass().getResource(ClientConfig.MenuButtons[3]));
		    	b_send.setIcon(new ImageIcon(img3));
		    Image img4 = ImageIO.read(getClass().getResource(ClientConfig.MenuButtons[4]));
		    	b_exit.setIcon(new ImageIcon(img4));
		    Image img5 = ImageIO.read(getClass().getResource(ClientConfig.MenuButtons[5]));
		    	l_label.setIcon(new ImageIcon(img5));
		  } catch (IOException ex) {
			  
		  }
		
		
		b_writer.setBorder(new EmptyBorder(0,0,0,0));
		b_sign.setBorder(new EmptyBorder(0,0,0,0));
		b_cipher.setBorder(new EmptyBorder(0,0,0,0));
		b_send.setBorder(new EmptyBorder(0,0,0,0));
		b_exit.setBorder(new EmptyBorder(0,0,0,0));
		l_label.setBorder(new EmptyBorder(0,0,0,0));
		
		p_panel1.setLayout(new FlowLayout());
		p_panel1.add(b_writer);
		p_panel1.add(b_sign);
		p_panel1.add(b_cipher);
		p_panel1.add(b_send);
		p_panel2.setLayout(new BorderLayout());
		p_panel2.add(b_exit, BorderLayout.EAST);
		p_panel3.setLayout(new FlowLayout());
		p_panel3.add(l_label);
		
		this.add(p_panel1,BorderLayout.CENTER);
		this.add(p_panel2,BorderLayout.SOUTH);
		this.add(p_panel3, BorderLayout.NORTH);
		
		p_panel1.setBackground(Color.black);
		p_panel2.setBackground(Color.black);
		p_panel3.setBackground(Color.black);
		
		
		/*		Spisywanie testamentu		*/
		b_writer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ClientConfig.writer=true;
				GeneralWindow.Writer();
				f_menu.dispose();
                
				
			}
		});
		
		/*	Podpisywanie testamentu */		
		
		b_sign.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ClientConfig.signer = true;
				GeneralWindow.Signer();
				f_menu.dispose();
                
	         
			} 
		}
		);
		
		/*	Szyfrowanie testamentu */		
		
		b_cipher.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ClientConfig.cipherer = true;
				GeneralWindow.Cipherer();
				f_menu.dispose();
                
	         
			} 
		}
		);
		
		/*	Wysy³anie */		
		
		b_send.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ClientConfig.sender = true;
				GeneralWindow.Sender();
				f_menu.dispose();
                
	         
			}
			
	        
		}
		);
		
		
		/*		EXIT	*/	
		
		b_exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
			
		});
	
	
		
		
	}
	
		/**
		 * Glowna funkcja programu.
		 * @param args
		 * @throws Exception
		 */
		public static void main(String[] args) throws Exception {	
			SwingUtilities.invokeLater(new Runnable() {
				public void run(){
					new ClientMenu();
				}
			});
			TimeUnit.SECONDS.sleep(1);
		SwingUtilities.invokeLater(new Runnable() {
		public void run(){
				
			}
		
				
		});
		}
		
}
