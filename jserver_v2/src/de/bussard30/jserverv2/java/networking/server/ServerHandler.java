package networking.server;


import networking.exceptions.BadPacketException;
import networking.logger.Logger;
import networking.server.protocol.NetworkPhase;
import networking.types.Request;
import networking.types.Response;

import javax.crypto.*;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;

/**
 * Handles incoming traffic for one client.<br>
 * Initiated by {@link Server}<br>
 * Run method executed by {@link ServerThread}<br>
 *
 * @author Bussard30
 */
public class ServerHandler {
    private final int current = 0;
    private Socket s;
    private boolean queueEmpty;
    private DataInputStream in;
    private DataOutputStream out;
    private NetworkPhase phase;
    /**
     * TODO conditions
     */
    private HashMap<NetworkPhase, boolean[]> networkphaseprogress;
    private ByteArrayOutputStream bOut;
    private ObjectOutputStream os;


    /**
     * Generates new server handler to handle socket
     *
     * @param s
     */
    public ServerHandler(Socket s) {

    }

    public ServerHandler(Socket s, DataInputStream in, DataOutputStream out) {
        this(s);
        this.out = out;
        this.in = in;
    }

    public static byte[] encrypt(PublicKey publicKey, byte[] msg) throws NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(msg);
    }

    public static byte[] decrypt(PrivateKey privateKey, byte[] encrypted) throws NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encrypted);
    }

    public static byte[] encrypt(SecretKey key, byte[] msg) throws NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(msg);
    }

    public static byte[] decrypt(SecretKey key, byte[] encrypted) throws NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encrypted);
    }

    /**
     * Decodes Base64 string to a RSA public key
     *
     * @param key
     * @return
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public static PublicKey loadPublicKey(String key) throws GeneralSecurityException, UnsupportedEncodingException {
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
    public static String decodePublicKey(PublicKey key) throws UnsupportedEncodingException {
        return new String(Base64.getEncoder().encode(key.getEncoded()), StandardCharsets.UTF_8);
    }

    /**
     * Encodes a RSA private key to a Base64 String
     *
     * @param key
     * @return
     */
    public static String decodePrivateKey(PrivateKey key) {
        return new String(new PKCS8EncodedKeySpec(key.getEncoded()).getEncoded());
    }

    public void run() throws Exception {
        //FETCH PACKET

    }

    private void ping() {
        // try
        // {
        // send(new Request(Requests.REQST_PING.getName(), new
        // ByteArrayWrapper(ping)));
        // } catch (Exception e)
        // {
        // e.printStackTrace();
        // }
    }

    public void advance() {

    }

    public DataInputStream getInputStream() {
        return in;
    }

    public DataOutputStream getOutputStream() {
        return out;
    }

    public void send(Request r) throws Exception {
//		if (phase == NetworkPhase.COM)
//		{
//			String[] sa = getStrings(r.getBuffer());
//			String s = null;
//			if (sa.length == 1)
//			{
//				s = "Req;" + r.getName();
//				s = s + ";" + sa[0];
//			} else if (sa.length > 1)
//			{
//				s = "Req;" + r.getName();
//				for (int i = 0; i < sa.length; i++)
//				{
//					s = s + ";" + sa[i];
//				}
//			}
//			Logger.info("Sending " + s);
//			byte[] b0 = encrypt(key, s.getBytes("UTF8"));
//			out.writeInt(b0.length);
//			out.write(b0);
//
//		} else if (phase == NetworkPhase.PRE2 || phase == NetworkPhase.COM)
//		{
//			String[] sa = getStrings(r.getBuffer());
//			String s = null;
//			if (sa.length == 1)
//			{
//				s = "Req;" + r.getName();
//				s = s + ";" + sa[0];
//			} else if (sa.length > 1)
//			{
//				s = "Req;" + r.getName();
//				for (int i = 0; i < sa.length; i++)
//				{
//					s = s + ";" + sa[i];
//				}
//			}
//			Logger.info("Sending " + s);
//			byte[] b0 = encrypt(key, s.getBytes("UTF8"));
//			out.writeInt(b0.length);
//			out.write(b0);
//
//		} else if (phase == NetworkPhase.PRE1)
//		{
//			if (networkphaseprogress.get(phase)[0])
//			{
//				String[] sa = getStrings(r.getBuffer());
//				String s = null;
//				if (sa.length == 1)
//				{
//					s = "Req;" + r.getName();
//					s = s + ";" + sa[0];
//				} else if (sa.length > 1)
//				{
//					s = "Req;" + r.getName();
//					for (int i = 0; i < sa.length; i++)
//					{
//						s = s + ";" + sa[i];
//					}
//				}
//				Logger.info("Sending " + s);
//				byte[] b0 = encrypt(key, s.getBytes("UTF8"));
//				out.writeInt(b0.length);
//				out.write(b0);
//			} else
//			{
//				String[] sa = getStrings(r.getBuffer());
//				String s = null;
//				if (sa.length == 1)
//				{
//					s = "Req;" + r.getName();
//					s = s + ";" + sa[0];
//				} else if (sa.length > 1)
//				{
//					s = "Req;" + r.getName();
//					for (int i = 0; i < sa.length; i++)
//					{
//						s = s + ";" + sa[i];
//					}
//				}
//				Logger.info("Sending " + s);
//				byte[] b0 = s.getBytes("UTF8");
//				out.writeInt(b0.length);
//				out.write(b0);
//			}
//		} else if (phase == NetworkPhase.PRE0)
//		{
//			String[] sa = getStrings(r.getBuffer());
//			String s = null;
//			if (sa.length == 1)
//			{
//				s = "Req;" + r.getName();
//				s = s + ";" + sa[0];
//			} else if (sa.length > 1)
//			{
//				s = "Req;" + r.getName();
//				for (int i = 0; i < sa.length; i++)
//				{
//					s = s + ";" + sa[i];
//				}
//			}
//			Logger.info("Sending " + s);
//			byte[] b0 = s.getBytes("UTF8");
//			out.writeInt(b0.length);
//			out.write(b0);
//
//		} else
//		{
//			Logger.info("Unknown phase.");
//		}
//		out.flush();
    }

    public void send(Response r) throws Exception {
//		if (phase == NetworkPhase.COM)
//		{
//			String[] sa = getStrings(r.getBuffer());
//			String s = null;
//			if (sa.length == 1)
//			{
//				s = "Res;" + r.getName();
//				s = s + ";" + sa[0];
//			} else if (sa.length > 1)
//			{
//				s = "Res;" + r.getName();
//				for (int i = 0; i < sa.length; i++)
//				{
//					s = s + ";" + sa[i];
//				}
//			}
//			Logger.info("Sending " + s);
//			byte[] b0 = encrypt(key, s.getBytes("UTF8"));
//			out.writeInt(b0.length);
//			out.write(b0);
//
//		} else if (phase == NetworkPhase.PRE2 || phase == NetworkPhase.COM)
//		{
//			String[] sa = getStrings(r.getBuffer());
//			String s = null;
//			if (sa.length == 1)
//			{
//				s = "Res;" + r.getName();
//				s = s + ";" + sa[0];
//			} else if (sa.length > 1)
//			{
//				s = "Res;" + r.getName();
//				for (int i = 0; i < sa.length; i++)
//				{
//					s = s + ";" + sa[i];
//				}
//			}
//			Logger.info("Sending " + s);
//			byte[] b0 = encrypt(key, s.getBytes("UTF8"));
//			out.writeInt(b0.length);
//			out.write(b0);
//
//		} else if (phase == NetworkPhase.PRE1)
//		{
//			if (networkphaseprogress.get(phase)[0])
//			{
//				String[] sa = getStrings(r.getBuffer());
//				String s = null;
//				if (sa.length == 1)
//				{
//					s = "Res;" + r.getName();
//					s = s + ";" + sa[0];
//				} else if (sa.length > 1)
//				{
//					s = "Res;" + r.getName();
//					for (int i = 0; i < sa.length; i++)
//					{
//						s = s + ";" + sa[i];
//					}
//				}
//				Logger.info("Sending " + s);
//				byte[] b0 = encrypt(key, s.getBytes("UTF8"));
//				out.writeInt(b0.length);
//				out.write(b0);
//			} else
//			{
//				String[] sa = getStrings(r.getBuffer());
//				String s = null;
//				if (sa.length == 1)
//				{
//					s = "Res;" + r.getName();
//					s = s + ";" + sa[0];
//				} else if (sa.length > 1)
//				{
//					s = "Res;" + r.getName();
//					for (int i = 0; i < sa.length; i++)
//					{
//						s = s + ";" + sa[i];
//					}
//				}
//				Logger.info("Sending " + s);
//				byte[] b0 = s.getBytes("UTF8");
//				out.writeInt(b0.length);
//				out.write(b0);
//			}
//		} else if (phase == NetworkPhase.PRE0)
//		{
//			String[] sa = getStrings(r.getBuffer());
//			String s = null;
//			if (sa.length == 1)
//			{
//				s = "Res;" + r.getName();
//				s = s + ";" + sa[0];
//			} else if (sa.length > 1)
//			{
//				s = "Res;" + r.getName();
//				for (int i = 0; i < sa.length; i++)
//				{
//					s = s + ";" + sa[i];
//				}
//			}
//			Logger.info("Sending " + s);
//			byte[] b0 = s.getBytes("UTF8");
//			out.writeInt(b0.length);
//			out.write(b0);
//
//		} else
//		{
//			Logger.info("Unknown phase.");
//		}
//		out.flush();
    }

    private String[] getStrings(Object o) throws UnsupportedEncodingException {
        Logger.info(o.getClass().getName());
//		if (o instanceof Wrapper)
//		{
//			String[] s = ((networking.types.Wrapper) o).getStrings();
//			for (int i = 0; i < s.length; i++)
//			{
//				s[i] = s[i].replace(";", "U+003B");
//			}
//			return s;
//		}
        if (o instanceof Key) {
            if (o instanceof PublicKey) {
                return new String[]
                        {decodePublicKey((PublicKey) o)};
            } else if (o instanceof PrivateKey) {
                return new String[]
                        {decodePrivateKey((PrivateKey) o)};
            }
        }
        if (o instanceof String) {
            return new String[]
                    {((String) o).replace(";", "U+003B")};
        }
        if (o instanceof Boolean) {
            return new String[]
                    {((boolean) o) ? "true" : "false"};
        }
        throw new RuntimeException("Could not convert Object to string");
    }

    @SuppressWarnings("unused")
    @Deprecated
    private byte[] serialize(Object o) throws IOException {
        if (bOut != null && os != null) {
            os.writeObject(o);
            os.reset();
            return bOut.toByteArray();
        } else {
            bOut = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bOut);
            os.writeObject(o);
            os.reset();
            return bOut.toByteArray();
        }
    }

    /**
     * Deserializes incoming byte sequence.
     *
     * @param b
     * @return
     * @throws UnsupportedEncodingException
     * @throws BadPacketException
     */
    private Object deserialize(byte[] b) throws UnsupportedEncodingException, BadPacketException {
        String s = new String(b, StandardCharsets.UTF_8);
        Logger.info(s);
        String[] temp = s.split(";");
        String[] info = new String[]
                {temp[0], temp[1]};
        String[] data = new String[temp.length - 2];

        for (int i = 2; i < temp.length; i++) {
            data[i - 2] = temp[i];
        }

        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("null")) {
                data[i] = null;
            } /**
             * else if (data[i].contains("U+003B")) { Logger.info("Replacing
             * ; character"); data[i] = data[i].replace("U+003B", ";"); }
             */
        }

        if (info[0].equals("Req")) {
            // for (Requests r : Requests.values())
            // {
            // if (r.getName().equals(info[1]))
            // {
            // Logger.info(info[1]);
            // if (r.getType().getSuperclass() != null)
            // {
            // if (r.getType().getSuperclass().equals(Wrapper.class))
            // {
            // try
            // {
            // return new Request(info[1],
            // Wrapper.getWrapper((Class<? extends Wrapper>) r.getType(),
            // data));
            // } catch (Throwable t)
            // {
            // Logger.error(t);
            // }
            // }
            // } else if (r.getType().equals(PublicKey.class))
            // {
            // try
            // {
            // return new Request(info[1], loadPublicKey(data[0]));
            // } catch (GeneralSecurityException e)
            // {
            // e.printStackTrace();
            // }
            // } else
            // {
            // String stemp = null;
            // for (String c : data)
            // {
            // stemp += c;
            // }
            // return new Request(info[1], stemp);
            // }
            // }
            // }
            throw new BadPacketException("Package malfunctional.");
        } else if (info[0].equals("Res")) {
            // for (Responses r : Responses.values())
            // {
            // if (r.getName().equals(info[1]))
            // {
            // if (r.getType().getSuperclass() != null)
            // {
            // if (r.getType().getSuperclass().equals(Wrapper.class))
            // {
            // return new Response(info[1],
            // Wrapper.getWrapper((Class<? extends Wrapper>) r.getType(),
            // data));
            // }
            // } else if (r.getType().equals(PublicKey.class))
            // {
            // try
            // {
            // return new Response(info[1], loadPublicKey(data[0]));
            // } catch (GeneralSecurityException e)
            // {
            // e.printStackTrace();
            // }
            // } else
            // {
            // String stemp = null;
            // for (String c : data)
            // {
            // stemp += c;
            // }
            // return new Request(info[1], stemp);
            // }
            // }
            // }
            throw new BadPacketException("Package not properly built");
        } else {
            throw new BadPacketException("Type of package not declared");
        }

    }

    @SuppressWarnings("unused")
    private PrivateKey loadPrivateKey(String key) throws GeneralSecurityException, UnsupportedEncodingException {
        return KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key.getBytes(StandardCharsets.UTF_8))));
    }

    public Socket getSocket() {
        return s;
    }
}
