import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.Security;

/**
 * Klasa odpowiadajaca za generowanie okien programu.
 *
 */
public class GeneralWindow extends JFrame {
	
	
	final GeneralWindow f_generalWindow = this;
	
	
	private JPanel 
		p_panel1 = new JPanel(), // panel do labeli
		p_panel2 = new JPanel(), // ogolny panel do przyciskow
		p_panel3 = new JPanel(), // panel do przyciskow
		p_panel4 = new JPanel(); // panel do TextFielda
	
	private JLabel 
		l_label = new JLabel(), //spisywanie testamentu
		l_label1 = new JLabel(); // wpisz ponizej tresc
		//l_label2 = new JLabel(),	// klucz prywatny dla podpisywania
		//l_label3 = new JLabel(); // klucz publiczny dla podpisywania
	
	private JButton
		b_save = new JButton(),
		b_back = new JButton(),
		b_exit = new JButton();
	;
	
	private JTextArea
		t_textArea = new JTextArea(),
		t_textArea1 = new JTextArea(); // drugie textArea dla podpisywania

	static SendMailSSL sendMailSSL = new SendMailSSL();
	static AES aes = new AES();
	
	private JFrame f; //do animacji
	
	final Thread animationThread = new Thread(new Runnable() {

		  public void run() {

			  	URL url = getClass().getResource("szyfrowanie.gif");
			  	System.out.println("sciezka: " + url.getPath());
				Icon icon = new ImageIcon(url);
		        JLabel label = new JLabel(icon);

		        f = new JFrame("Czekaj...");
		        f.getContentPane().add(label);
		        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        f.pack();
		        f.setLocationRelativeTo(null);
		        f.setVisible(true);

		  }
		});
	
	final Thread animationThread1 = new Thread(new Runnable() {

		  public void run() {

				
		        URL url = getClass().getResource("wysylanie.gif");
				Icon icon = new ImageIcon(url);
		        JLabel label = new JLabel(icon);

		        f = new JFrame("Czekaj...");
		        f.getContentPane().add(label);
		        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        f.pack();
		        f.setLocationRelativeTo(null);
		        f.setVisible(true);

		  }
		});
	
	final Thread cipheringThread = new Thread(new Runnable() {

		  public void run() {
			  /* dopelnianie tekstu testamentu do krotnosci 16 bitow */
				try {
					File testament = new File("testament.txt");  // czy istnieje plik
					if(testament.exists() && !testament.equals(null)){
						
						if(t_textArea.getText().length() == 16){
							
							/* zapisywanie klucza aes */
							try {
								FileWriter aes_key = new FileWriter ("klucz_aes.txt");
								t_textArea.write(aes_key);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						String plaintext = new Scanner(testament).useDelimiter("\\Z").next();
						
						List<String> blocks = new ArrayList<String>();
						
						/* dzielenie na bloki */
						int index = 0;
						while (index<plaintext.getBytes().length) {
						    blocks.add(plaintext.substring(index,Math.min(index+16,plaintext.length())));
						    index+=16;
						}
					/*	uzupelnianie ostatniego bloku	*/
						if(blocks.get(blocks.size()-1).toString().length()%16 !=0);
						{
							String temp = blocks.get(blocks.size()-1); 
							for(int i = 0; i < 16-(blocks.get(blocks.size()-1).length())%16; i++)
							{
								temp = temp + "\0";
							}
							
							blocks.remove(blocks.size()-1);
							blocks.add(temp);
							
						}
							//System.out.println(plaintext);

							aes.AES(blocks, t_textArea.getText(), "testament_zaszyfrowany.txt");
							
						}
						else
						{
							ClientConfig.cipherlength = false;
							ClientConfig.ciphered = false;
						}
					}
					else
					{
						ClientConfig.ciphered = false;
						ClientConfig.nofile = true;
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ClientConfig.ciphered = false;
				}
				
				if(ClientConfig.ciphered)
				{
					f.setVisible(false);
					f.dispose();
					animationThread.interrupt();
					ClientConfig.success = true;
				}
				else
				{
					animationThread.interrupt();
				}

				
				repaintThread.start();
				
		  }
		});
	
	final Thread sendingThread = new Thread(new Runnable() {

		  public void run() {
			  
			  	sendMailSSL.sendEmail(t_textArea.getText(), "Klucze pozwalajace na odszyfrowanie i weryfikacje tresci testamentu", "Zalaczniki do niniejszej wiadomosci zawieraja dane niezbedne do odszyfrowania tresci testamentu odtworzonej na podstawie czesci sekretu, ktore otrzymali spadkobiercy. Tresc plikow nalezy utrzymywac w tajemnicy w bezpiecznym miejscu az do momentu, w ktorym bedzie dokonywany podzial majatku.", "klucz_aes.txt", "klucz_aes.txt", "klucz_publiczny.txt", "klucz_publiczny.txt", "podpis.txt", "podpis.txt");
				if(ClientConfig.sent)
				{
					f.setVisible(false);
					f.dispose();
					animationThread1.interrupt();
					ClientConfig.success = true;
				}
				
				repaintThread.start();

		  }
		});
	
	final Thread repaintThread = new Thread(new Runnable() {
		 @SuppressWarnings("deprecation")
		public void run() {
			 	f_generalWindow.dispose(); 
			 	NotificationWindow notificationWindow = new NotificationWindow();
			 	notificationWindow.setVisible(false);
			 	notificationWindow.setVisible(true);
			 
	}});
	
	public GeneralWindow(String Tittle, int[] WindowSize, String[] ButtonsPaths){
		
		super(Tittle);
		

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(WindowSize[0], WindowSize[1]);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		try {
			Image img = ImageIO.read(getClass().getResource(ButtonsPaths[0]));
				b_save.setIcon(new ImageIcon(img));
			Image img1 = ImageIO.read(getClass().getResource(ButtonsPaths[1]));
				b_back.setIcon(new ImageIcon(img1));
			Image img2 = ImageIO.read(getClass().getResource(ButtonsPaths[2]));
				b_exit.setIcon(new ImageIcon(img2));
		    Image img3 = ImageIO.read(getClass().getResource(ButtonsPaths[3]));
		    	l_label.setIcon(new ImageIcon(img3));
		    Image img4 = ImageIO.read(getClass().getResource(ButtonsPaths[4]));
		    	l_label1.setIcon(new ImageIcon(img4));
		    	
		  } catch (IOException ex) {
			  
		  }
		
		/*if (ClientConfig.signer)
		{
			
			try {				
			    Image img5 = ImageIO.read(getClass().getResource(ButtonsPaths[5]));
			    	l_label2.setIcon(new ImageIcon(img5));
			    Image img6 = ImageIO.read(getClass().getResource(ButtonsPaths[6]));
			    	l_label3.setIcon(new ImageIcon(img6));
			    	
			  } catch (IOException ex) {
				  
			  }
			
		}*/
		
		b_save.setBorder(new EmptyBorder(0,0,0,0));
		b_back.setBorder(new EmptyBorder(0,0,0,0));
		b_exit.setBorder(new EmptyBorder(0,0,0,0));
		l_label.setBorder(new EmptyBorder(0,0,0,0));
		l_label1.setBorder(new EmptyBorder(0,0,0,0));
		
		/*if(ClientConfig.signer)
		{
			l_label2.setBorder(new EmptyBorder(0,0,0,0));
			l_label3.setBorder(new EmptyBorder(0,0,0,0));
		}*/
		
		p_panel1.setLayout(new GridLayout(2,1));
		p_panel1.add(l_label);
		p_panel1.add(l_label1);
		
		p_panel2.setLayout(new BorderLayout());
		p_panel2.add(p_panel3, BorderLayout.EAST);
		
		p_panel3.setLayout(new BorderLayout());
		p_panel3.add(b_save,BorderLayout.NORTH);
		p_panel3.add(b_back,BorderLayout.CENTER);
		p_panel3.add(b_exit,BorderLayout.SOUTH);
		
		/*		ustalanie wielkosci JTextArea dla writera	*/
		if(ClientConfig.writer)
		{
			t_textArea = new JTextArea(ClientConfig.WriterTextFieldSize[0],ClientConfig.WriterTextFieldSize[1]);
		}
		
	
		
		if(ClientConfig.signer)	
		{
			/*
			p_panel4.setLayout(new GridLayout(4,1));
			p_panel4.add(l_label2);
			p_panel4.add(t_textArea);
			p_panel4.add(l_label3);
			p_panel4.add(t_textArea1);
			t_textArea1.setLineWrap(true);
			*/
		}
		else 
		{
			JPanel temp_panel1 = new JPanel();
            JLabel temp_label1 = new JLabel("xxx");
            JLabel temp_label2 = new JLabel("xxx");
            
            temp_label1.setForeground(Color.black);
            temp_label2.setForeground(Color.black);
            
            //  t_textArea.setPreferredSize(new Dimension(25,20));
 
            temp_panel1.setLayout(new BorderLayout());
            temp_panel1.setBackground(Color.black);
            
            temp_panel1.add(temp_label1, BorderLayout.WEST);
            
            if(!ClientConfig.writer) 
            { 
            	temp_panel1.add(t_textArea, BorderLayout.CENTER);
            } 
            else 
            { 
            	JScrollPane s_scrollPane = new JScrollPane(t_textArea);
            	temp_panel1.add(s_scrollPane, BorderLayout.CENTER); 
            }
            
            temp_panel1.add(temp_label2, BorderLayout.EAST);
            
            
            p_panel4.setLayout(new GridLayout());
            p_panel4.add(temp_panel1); 
		}
		
		t_textArea.setLineWrap(true);
		
		
		
		this.add(p_panel1,BorderLayout.NORTH);
		this.add(p_panel4, BorderLayout.CENTER);
		this.add(p_panel2, BorderLayout.PAGE_END);
		
		p_panel1.setBackground(Color.black);
		p_panel2.setBackground(Color.black);
		
		if(ClientConfig.signer)
		{
			p_panel3.setBackground(Color.black);
			p_panel4.setBackground(Color.black);
		}
		
		
		/*	first button	*/
		b_save.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
				if (ClientConfig.writer) // zapisywanie
				{
					
					Write();
					
					if(ClientConfig.written)
					{
						ClientConfig.success = true;
						ClientConfig.nofile=false;
					}
				}
				
				
				if (ClientConfig.signer)	// podpisywanie
				{
					Sign();
					if(ClientConfig.signed)
					{
						ClientConfig.success = true;
					}
					
				}
				
				if (ClientConfig.cipherer)	//szyfrowanie
				{
					
					f_generalWindow.setVisible(false);
					animationThread.start();
					cipheringThread.start();
					
				}
				
				if (ClientConfig.sender) // wysylanie
				{
					f_generalWindow.setVisible(false);
					animationThread1.start();
					sendingThread.start();

				}
				
				if(!(ClientConfig.cipherer || ClientConfig.sender))
				{
				new NotificationWindow();
				
				f_generalWindow.dispose();
				}
			}
			
		});
		
		
		
		/*	back button */
		b_back.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ClientConfig.writer = false;
				ClientConfig.signer = false;
				ClientConfig.cipherer = false;
				ClientConfig.sender = false;
				ClientMenu clientMenu = new ClientMenu();
				clientMenu.setLocationRelativeTo(null);
				clientMenu.setResizable(false);
				clientMenu.setVisible(true);
				f_generalWindow.dispose();
			}
			
		});
		
		
		
		/*	exit button  */
		b_exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
			
		});
	}
	
	/**
	 * Metoda generujaca okno spisywania testamentu.
	 */
	public static void Writer()
	{
	GeneralWindow generalWindow = new GeneralWindow(
			ClientConfig.WriterTitle,
			ClientConfig.TestamentWriterSize,
			ClientConfig.WriterButtons
			);
	generalWindow.pack();
	generalWindow.setLocationRelativeTo(null);
	generalWindow.setResizable(false);
	generalWindow.setVisible(true);
	};
	
	/**
	 * Metoda generujaca okno podpisywania testamentu.
	 */
	public static void Signer()
	{
		GeneralWindow generalWindow = new GeneralWindow(
				ClientConfig.SignerTitle,
				ClientConfig.TestamentSignerSize,
				ClientConfig.SignerButtons
				);
		generalWindow.pack();
		generalWindow.setLocationRelativeTo(null);
		generalWindow.setResizable(false);
		generalWindow.setVisible(true);
	}
	
	/**
	 * Metoda generujaca okno szyfrowania testamentu.
	 */
	public static void Cipherer()
	{
		GeneralWindow generalWindow = new GeneralWindow(
				ClientConfig.CiphererTitle,
				ClientConfig.TestamentCiphererSize,
				ClientConfig.CiphererButtons
				);
		generalWindow.pack();
		generalWindow.setLocationRelativeTo(null);
		generalWindow.setResizable(false);
		generalWindow.setVisible(true);
	}
	
	/**
	 * Metoda generujaca okno wysylania wiadomosci do prawnika.
	 */
	public static void Sender()
	{
		GeneralWindow generalWindow = new GeneralWindow(
				ClientConfig.SenderTitle,
				ClientConfig.TestamentSenderSize,
				ClientConfig.SenderButtons
				);
		generalWindow.pack();
		generalWindow.setLocationRelativeTo(null);
		generalWindow.setResizable(false);
		generalWindow.setVisible(true);
	}
	
	/**
	 * Metoda generujaca okno spisywania testamentu.
	 */
	public void Write()
	{
		FileWriter testament;
		try {
			testament = new FileWriter ("testament.txt");
			  t_textArea.write(testament);
			   ClientConfig.written = true;
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 ClientConfig.written = false;
		}	
	}
	
	/**
	 * Metoda generujaca okno podpisywania testamentu.
	 * ?!
	 */
	public void Sign1(){

		
		//FileWriter public_key;
	//	FileWriter private_key;
		
		try {

	        /* Generate a key pair */

	        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
	        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

	        keyGen.initialize(512, random);

	        KeyPair pair = generateKeyPair(999);
	        PrivateKey priv = pair.getPrivate();
	        PublicKey pub = pair.getPublic();
	        
	        
	        /*
	         * Create a Signature object and initialize it with the private
	         * key
	         */

	        Signature dsa = Signature.getInstance("SHA1withDSA");

	        

	        /* Update and sign the data */

	        FileInputStream fis = new FileInputStream("testament.txt");
	        BufferedInputStream bufin = new BufferedInputStream(fis);
	        byte[] buffer = new byte[1024];
	        int len=0;
	        while (bufin.available() != 0) 
	        {
	          len = bufin.read(buffer);
	        }
	        
	        dsa.initSign(priv);
	        dsa.update(buffer, 0, len);

	        bufin.close();

	        /*
	         * Now that all the data to be signed has been read in, generate
	         * a signature for it
	         */

	        byte[] realSig = dsa.sign();

	        /* Save the signature in a file */
	        PrintStream sigfos = new PrintStream(new FileOutputStream("testament_podpisany.txt"));
	        BigInteger signed_testament = new BigInteger(realSig);
	        sigfos.print(signed_testament);
	        sigfos.close();

	        /* Save the public key in a file */
	        byte[] key = pub.getEncoded();
	        PrintStream keyfos = new PrintStream(new FileOutputStream("klucz_publiczny.txt"));
	        BigInteger public_key = new BigInteger(key);
	        keyfos.print(public_key);
	        keyfos.close();
	        
	        /* Save the private key in a file */
	        byte[] keypriv = priv.getEncoded();
	        PrintStream keypr = new PrintStream(new FileOutputStream("klucz_prywatny.txt"));
	        BigInteger private_key = new BigInteger(keypriv);
	        keypr.print(private_key);

	        keypr.close();
	        
	       ClientConfig.signed=true;

	      } catch (Exception e) {
	        System.err.println("Caught exception " + e.toString());
	      }

	}

/**
 * Metoda odpowiadajaca za podpisywanie testamentu.
 */
public void Sign(){

		
		//FileWriter public_key;
	//	FileWriter private_key;
		
		try {
			 	Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

			    KeyPair keyPair = generateKeyPair(999);

			    FileInputStream fis = new FileInputStream("testament.txt");
		        BufferedInputStream bufin = new BufferedInputStream(fis);
		        byte[] buffer = new byte[1024];
		        while (bufin.available() != 0) 
		        {
		          bufin.read(buffer);
		        }
		        
			   
			    boolean verified;
			    
			    
		        

		  
			    
			    /* Save the public key in a file */
		        byte[] key = keyPair.getPublic().getEncoded();
		        PrintStream keyfos = new PrintStream(new FileOutputStream("klucz_publiczny.txt"));
		        BigInteger public_key = new BigInteger(key);
		        keyfos.print(public_key);
		        keyfos.close();
		        
		        /* Save the signature in a file */
			    byte[] digitalSignature = signData(buffer, keyPair.getPrivate());
		        PrintStream sigfos = new PrintStream(new FileOutputStream("podpis.txt"));
		        BigInteger signed_testament = new BigInteger(digitalSignature);
		        sigfos.print(signed_testament);
		        sigfos.close();
	/*	        
		        //wczytywanie klucza publicznego
		     	FileInputStream keyfis = new FileInputStream("klucz_publiczny.txt");
		     	StringBuilder builder = new StringBuilder();
		     	int ch;
		     	while((ch = keyfis.read()) != -1){
		     	    builder.append((char)ch);
		     	}
		     	BigInteger public_key1 = new BigInteger(builder.toString());
		        byte[] encKey = public_key1.toByteArray();
		        keyfis.read(encKey);
		        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);

		        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
		        PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
		        

		        
		        //wczytywanie podpisu
		        FileInputStream keyfis1 = new FileInputStream("podpis.txt");
		     	StringBuilder builder1 = new StringBuilder();
		     	int ch1;
		     	while((ch1 = keyfis1.read()) != -1){
		     	    builder1.append((char)ch1);
		     	}
		     	BigInteger sign = new BigInteger(builder1.toString());
		        byte[] encKey1 = sign.toByteArray();
		        keyfis1.read(encKey1);
		        
		        
			 //   BigInteger BI = new BigInteger(t_textArea.getText());
		      //  byte[] encKey1 = BI.toByteArray();

		        //weryfikacja testamentu
		        
			    verified = verifySig(buffer, pubKey, encKey1);
			    System.out.println(verified) ;

			    verified = verifySig(buffer, pubKey, digitalSignature);
			    System.out.println(verified);

	        
	        */
	       ClientConfig.signed=true;

	      } catch (Exception e) {
	        System.err.println("Caught exception " + e.toString());
	      }

	}
	

	/**
	 * Metoda odpowiadajaca za skladanie podpisu.
	 * @param data
	 * Dokument podpisywany (w formacie tablicy danych binarnych).
	 * @param key
	 * Klucz prywatny wykorzystywany do podpisania dokumentu.
	 * @return
	 * Metoda zwraca podpis.
	 * @throws Exception
	 */
	public static byte[] signData(byte[] data, PrivateKey key) throws Exception {
	    Signature signer = Signature.getInstance("SHA1withDSA");
	    signer.initSign(key);
	    signer.update(data);
	    return (signer.sign());
	  }

	  /**
	   * Metoda odpowiadajaca za weryfikacje podpisu.
	   * @param data
	   * Dokument, ktory zostal podpisany (w postaci tablicy danych binarnych).
	   * @param key
	   * Klucz publiczny wykorzystywany do weryfikacji podpisu.
	   * @param sig
	   * Podpis (w postaci tablicy danych binarnych).
	   * @return
	   * Metoda zwraca dane dotyczace tego, czy podpis zostal zweryfikowany pozytywnie.
	   * @throws Exception
	   */
	  public static boolean verifySig(byte[] data, PublicKey key, byte[] sig) throws Exception {
	    Signature signer = Signature.getInstance("SHA1withDSA");
	    signer.initVerify(key);
	    signer.update(data);
	    return (signer.verify(sig));

	  }

	  /**
	   * Metoda generujaca pare kluczy (prywatny i publiczny).
	   * @param seed 
	   * Parametr sluzacy do losowej generacji kluczy.
	   * @return
	   * Metoda zwraca pare kluczy.
	   * @throws Exception
	   */
	  public static KeyPair generateKeyPair(long seed) throws Exception {
	    KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("DSA");
	    SecureRandom rng = SecureRandom.getInstance("SHA1PRNG", "SUN");
	    rng.setSeed(seed);
	    keyGenerator.initialize(1024, rng);

	    return (keyGenerator.generateKeyPair());
	  }
	  
	/**
	 * Metoda odpowiadajaca za zaszyfrowanie testamentu.
	 */
	public void Cipher(){
		
		
		
		/* dopelnianie tekstu testamentu do krotnosci 16 bitow */
		try {
			File testament = new File("testament.txt");  // czy istnieje plik
			if(testament.exists() && !testament.equals(null)){
				
				if(t_textArea.getText().length() == 16){
					
					/* zapisywanie klucza aes */
					try {
						FileWriter aes_key = new FileWriter ("klucz_aes.txt");
						t_textArea.write(aes_key);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				String plaintext = new Scanner(testament).useDelimiter("\\Z").next();
				
				List<String> blocks = new ArrayList<String>();
				
				/* dzielenie na bloki */
				int index = 0;
				while (index<plaintext.getBytes().length) {
				    blocks.add(plaintext.substring(index,Math.min(index+16,plaintext.length())));
				    index+=16;
				}
			/*	uzupelnianie ostatniego bloku	*/
				if(blocks.get(blocks.size()-1).toString().length()%16 !=0);
				{
					String temp = blocks.get(blocks.size()-1); 
					for(int i = 0; i < 16-(blocks.get(blocks.size()-1).length())%16; i++)
					{
						temp = temp + "\0";
					}
					
					blocks.remove(blocks.size()-1);
					blocks.add(temp);
					
				}
					System.out.println(plaintext);

					aes.AES(blocks, t_textArea.getText(), "testament_zaszyfrowany.txt");
					
				}
				else
				{
					ClientConfig.cipherlength = false;
					ClientConfig.ciphered = false;
				}
			}
			else
			{
				ClientConfig.ciphered = false;
				ClientConfig.nofile = true;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ClientConfig.ciphered = false;
		}
		
		

	}
	
	
	/**
	 * Metoda odpowiadajaca za wysylanie wiadomosci zawierajacych czesci sekretu.
	 */
	public void Send()
	{
		sendMailSSL.sendEmail(t_textArea.getText(), "Klucze pozwalajace na odszyfrowanie i weryfikacje tresci testamentu", "Zalaczniki do niniejszej wiadomosci zawieraja dane niezbedne do odszyfrowania tresci testamentu odtworzonej na podstawie czesci sekretu, ktore otrzymali spadkobiercy. Tresc plikow nalezy utrzymywac w tajemnicy w bezpiecznym miejscu az do momentu, w ktorym bedzie dokonywany podzial majatku.", "klucz_aes.txt", "klucz_aes.txt", "klucz_publiczny.txt", "klucz_publiczny.txt", "podpis.txt", "podpis.txt");	
	}
	
	
}
