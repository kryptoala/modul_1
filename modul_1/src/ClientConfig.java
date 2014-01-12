/**
 * Klasa opisujaca parametry graficznego interfejsu uzytkownika programu.
 */
public class ClientConfig {
	
	/**
	 * Tablica lancuchow znakowych zawierajacych nazwy plikow graficznych wyswietlanych w menu glownym.
	 */
	public static String[] MenuButtons = {"spisz_testament.png", "podpisz_testament.png", "szyfruj_testament.png", "wyslij_klucze.png", "zamknij.png", "zarzadzanie_testamentem.png"};
	
	/**
	 * Lancuch znakowy stanowiacy tytul menu.
	 */
	public static String MenuTitle = "Menu";
	
	/**
	 * Tablica zmiennych typu int stanowiacych wymiary okna menu glownego.
	 */
	public static int[] MenuSize = {520, 285};
	
	/**
	 * Lancuch znakowy stanowiacy tytul okna sluzacego do spisywania testamenmtu.
	 */
	public static String WriterTitle = "Spisywanie testamentu";
	
	/**
	 * Tablica zmiennych typu int stanowiacych wymiary okna sluzacego do spisywania testamentu.
	 */
	public static int[] TestamentWriterSize = {520, 500};
	
	/**
	 * Tablica lancuchow znakowych zawierajacych nazwy plikow graficznych wyswietlanych w oknie spisywania testamentu.
	 */
	public static String[] WriterButtons = {"zapisz.png", "wroc_do_menu.png", "zamknij.png", "spisywanie_testamentu.png", "wpisz_tresc_testamentu.png"};
	
	/**
	 * Tablica zmiennych typu int stanowiacych wymiary pola tekstowego sluzacego do wprowadzania testamentu.
	 */
	public static int[] WriterTextFieldSize = {10, 10};
	
	/**
	 * Tablica lancuchow znakowych zawierajacych nazwy plikow graficznych wyswietlanych w okienku powiadomien pojawiajacym sie przy procedurze spisywania testamentu.
	 */
	public static String[] WriterNotifications = {"zapisywanie_testamentu_blad.png", "zapisywanie_testamentu_ok.png"};
	
	/**
	 * Zmienna boolowska opisujaca, czy uzytkownik korzysta z modulu spisywania testamentu (true), czy nie (false).
	 */
	public static boolean writer = false;
	
	/**
	 * Zmienna boolowska opisujaca, czy testament zostal napisany (true), czy nie (false).
	 */
	public static boolean written = false;
	
	/**
	 * Lancuch znakowy stanowiacy tytul okna sluzacego do podpisywania testamenmtu.
	 */	
	public static String SignerTitle = "Podpisywanie testamentu";
	
	/**
	 * Tablica zmiennych typu int stanowiacych wymiary okna sluzacego do podpisywania testamentu.
	 */
	public static int[] TestamentSignerSize = {520, 500};
	
	/**
	 * Tablica lancuchow znakowych zawierajacych nazwy plikow graficznych wyswietlanych w oknie podpisywania testamentu.
	 */
	public static String[] SignerButtons = {"zloz_podpis.png", "wroc_do_menu.png", "zamknij.png", "podpisywanie_testamentu.png", "skladanie_podpisu.png", "wprowadz_klucz_prywatny.png", "wprowadz_klucz_publiczny.png"};
	
	/**
	 * Tablica lancuchow znakowych zawierajacych nazwy plikow graficznych wyswietlanych w okienku powiadomien pojawiajacym sie przy procedurze podpisywania testamentu.
	 */
	public static String[] SignerNotifications = {"podpisywanie_testamentu_blad.png", "podpisywanie_testamentu_ok.png"};
	
	/**
	 * Zmienna boolowska opisujaca, czy uzytkownik korzysta z modulu podpisywania (true), czy nie (false).
	 */
	public static boolean signer = false;
	
	/**
	 * Zmienna boolowska opisujaca, czy testament zostal podpisany (true), czy nie (false).
	 */
	public static boolean signed = false;
	
	/**
	 * Lancuch znakowy stanowiacy tytul okna sluzacego do szyfrowania testamenmtu.
	 */
	public static String CiphererTitle = "Szyfrowanie testamentu";
	
	/**
	 * Tablica zmiennych typu int stanowiacych wymiary okna sluzacego do szyfrowania testamentu.
	 */
	public static int[] TestamentCiphererSize = {520, 500};
	
	/**
	 * Tablica lancuchow znakowych zawierajacych nazwy plikow graficznych wyswietlanych w oknie szyfrowania testamentu.
	 */
	public static String[] CiphererButtons = {"zaszyfruj.png", "wroc_do_menu.png", "zamknij.png", "szyfrowanie_testamentu.png", "szyfrowanie.png"};
	
	/**
	 * Tablica lancuchow znakowych zawierajacych nazwy plikow graficznych wyswietlanych w okienku powiadomien pojawiajacym sie przy procedurze szyfrowania testamentu.
	 */
	public static String[] CiphererNotifications = {"szyfrowanie_testamentu_blad.png", "szyfrowanie_testamentu_ok.png", "klucz_AES_blad.png", "plik_nie_istnieje_blad.png" };
	
	/**
	 * Zmienna boolowska opisujaca, czy dlugosc klucza AES jest odpowiednia (true), czy nie (false).
	 */
	public static boolean cipherlength = false;  
	
	/**
	 * Zmienna boolowska opisujaca, czy plik z testamentem istnieje (true), czy nie (false).
	 */
	public static boolean nofile = false; 
	
	/**
	 * Zmienna boolowska opisujaca, czy uzytkownik korzysta z modulu szyfrowania (true), czy nie (false).
	 */
	public static boolean cipherer = false;		
	
	/**
	 * Zmienna boolowska opisujaca, czy testament zostal zaszyfrowany (true), czy nie (false).
	 */
	public static boolean ciphered = false;	
	
	
	/**
	 * Lancuch znakowy stanowiacy tytul okna sluzacego do wysylania kluczy do prawnika.
	 */
	public static String SenderTitle = "Wysy³anie kluczy";
	
	/**
	 * Tablica zmiennych typu int stanowiacych wymiary okna sluzacego do wysylania kluczy do prawnika.
	 */
	public static int[] TestamentSenderSize = {520, 500};
	
	/**
	 * Tablica lancuchow znakowych zawierajacych nazwy plikow graficznych wyswietlanych w oknie wysylania kluczy.
	 */
	public static String[] SenderButtons = {"wyslij_klucze1.png", "wroc_do_menu.png", "zamknij.png", "wysylanie_kluczy.png", "wyslanie_kluczy.png"};
	
	/**
	 * Tablica lancuchow znakowych zawierajacych nazwy plikow graficznych wyswietlanych w okienku powiadomien pojawiajacym sie przy procedurze wysylania kluczy.
	 */
	public static String[] SenderNotifications = {"wysylanie_kluczy_blad.png", "wysylanie_kluczy_ok.png"};
	
	/**
	 * Zmienna boolowska opisujaca, czy uzytkownik korzysta z modulu wysylania kluczy (true), czy nie (false).
	 */
	public static boolean sender= false;
	
	/**
	 * Zmienna boolowska opisujaca, czy wiadomosc zawierajaca klucze zostala wyslana (true), czy nie (false).
	 */
	public static boolean sent= false;
	
	/**
	 * Tablica zmiennych typu int stanowiacych wymiary okna sluzacego do wysylania kluczy do prawnika.
	 */
	public static int[] NotificationSize = {610,200};
	
	/**
	 * Zmienna boolowska opisujaca, czy aktualnie wykonywana procedura powiodla sie (true), czy nie (false).
	 */
	public static boolean success = false;

}
