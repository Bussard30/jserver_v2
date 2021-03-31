package de.bussard30.jserverv2.networking.server;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import de.bussard30.jserverv2.networking.exceptions.BadPacketException;
import de.bussard30.jserverv2.networking.server.protocol.NetworkPhase;

/**
 * Handles incoming traffic for one client.<br>
 * Initiated by {@link Server}<br>
 * Run method executed by {@link ServerThread}<br>
 * 
 * @author Bussard30
 *
 */
public class ServerHandler
{
	private Socket s;

	private boolean queueEmpty;


	private DataInputStream in;
	private DataOutputStream out;

	private NetworkPhase phase;

	private final int current = 0;

	/**
	 * TODO conditions
	 */
	private HashMap<NetworkPhase, boolean[]> networkphaseprogress;


	/**
	 * Generates new server handler to handle socket
	 * 
	 * @param s
	 */
	public ServerHandler(Socket s)
	{

	}

	public ServerHandler(Socket s, DataInputStream in, DataOutputStream out)
	{
		this(s);
		this.out = out;
		this.in = in;
	}


	public void run() throws Exception
	{
		//FETCH PACKET
		
	}

	private void ping()
	{
		// try
		// {
		// send(new Request(Requests.REQST_PING.getName(), new
		// ByteArrayWrapper(ping)));
		// } catch (Exception e)
		// {
		// e.printStackTrace();
		// }
	}

	public void advance()
	{
		try
		{
			in.read();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DataInputStream getInputStream()
	{
		return in;
	}

	public DataOutputStream getOutputStream()
	{
		return out;
	}

	public void send(Package p) throws Exception
	{

	}

	private ByteArrayOutputStream bOut;
	private ObjectOutputStream os;

	public static byte[] encrypt(PublicKey publicKey, byte[] msg) throws NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException
	{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(msg);
	}

	public static byte[] decrypt(PrivateKey privateKey, byte[] encrypted) throws NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException
	{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(encrypted);
	}

	public static byte[] encrypt(SecretKey key, byte[] msg) throws NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, InvalidKeyException
	{
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(msg);
	}

	public static byte[] decrypt(SecretKey key, byte[] encrypted) throws NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException
	{
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(encrypted);
	}

	/**
	 * Deserializes incoming byte sequence.
	 * 
	 * @param b
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws BadPacketException
	 */
	private Object deserialize(byte[] b) throws UnsupportedEncodingException, BadPacketException
	{
		String s = new String(b, StandardCharsets.UTF_8);
		String[] temp = s.split(";");
		String[] info = new String[]
		{ temp[0], temp[1] };
		String[] data = new String[temp.length - 2];

		for (int i = 2; i < temp.length; i++)
		{
			data[i - 2] = temp[i];
		}

		for (int i = 0; i < data.length; i++)
		{
			if (data[i].equals("null"))
			{
				data[i] = null;
			}
		}
		return null;
	}

	@SuppressWarnings("unused")
	private PrivateKey loadPrivateKey(String key) throws GeneralSecurityException, UnsupportedEncodingException
	{
		return KeyFactory.getInstance("RSA")
				.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key.getBytes(StandardCharsets.UTF_8))));
	}

	/**
	 * Decodes Base64 string to a RSA public key
	 * 
	 * @param key
	 * @return
	 * @throws GeneralSecurityException
	 * @throws UnsupportedEncodingException
	 */
	public static PublicKey loadPublicKey(String key) throws GeneralSecurityException, UnsupportedEncodingException
	{
		return KeyFactory.getInstance("RSA")
				.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(key.getBytes(StandardCharsets.UTF_8))));
	}

	/**
	 * Encodes a RSA public key to a Base64 string
	 * 
	 * @param key
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decodePublicKey(PublicKey key) throws UnsupportedEncodingException
	{
		return new String(Base64.getEncoder().encode(key.getEncoded()), StandardCharsets.UTF_8);
	}

	/**
	 * Encodes a RSA private key to a Base64 String
	 * 
	 * @param key
	 * @return
	 */
	public static String decodePrivateKey(PrivateKey key)
	{
		return new String(new PKCS8EncodedKeySpec(key.getEncoded()).getEncoded());
	}

	public Socket getSocket()
	{
		return s;
	}
}
