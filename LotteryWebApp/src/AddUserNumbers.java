import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.sql.Connection;
import java.sql.Statement;

@WebServlet(name = "/AddUserNumbers")
public class AddUserNumbers extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String nums="";
        nums+=request.getParameter("number1").toString()+", ";
        nums+=request.getParameter("number2").toString()+", ";
        nums+=request.getParameter("number3").toString()+", ";
        nums+=request.getParameter("number4").toString()+", ";
        nums+=request.getParameter("number5").toString()+", ";
        nums+=request.getParameter("number6").toString();// String created and 6 numbers get added.
        HttpSession session = request.getSession();
        byte[] data = nums.getBytes();
        String filename = (session.getAttribute("password").toString().substring(0, 20))+".txt";// Hashed password is called and 20 first symbols are used as text file name.

        class EncryptedStorage {


            private Cipher cipher;
            private KeyPair pair;

            public EncryptedStorage(String data){}

            public EncryptedStorage() {


            }

            public byte[] encryptData(byte[] data) {
                try{
                    int i= (int) session.getAttribute("length");
                    if(i<=0) {// if pair and cipher already exist, they are not generated.
                        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
                        pair = keyPairGen.generateKeyPair();
                        session.setAttribute("pair", pair);
                        cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                        session.setAttribute("cipher", cipher);
                    }
                    else if(i>0){
                        pair = (KeyPair) session.getAttribute("pair");
                        cipher= (Cipher) session.getAttribute("cipher");

                    }

                    PublicKey publicKey = pair.getPublic();
                    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                    cipher.update(data);
                    i+=1;
                    session.setAttribute("length", i);
                    return cipher.doFinal();
                }
                catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex){
                    ex.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
                catch (NullPointerException e){
                    e.printStackTrace();
                }
                return null;
            }
            public void bytesFileWriter(String filename , byte[] data){
                try {
                    File encrypted = new File("\\apache-tomcat-9.0.36\\bin\\encrypted");//creates a new folder
                    encrypted.mkdir();
                    OutputStream os = new FileOutputStream("encrypted\\"+filename,true);//txt file is created in the new folder.
                    os.write(data);
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        EncryptedStorage es = new EncryptedStorage();
        es.bytesFileWriter(filename,es.encryptData(data));



        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
            request.setAttribute("message", "Draw added successfully");
            dispatcher.forward(request, response);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        class Destroy extends Thread {

            public void run() { // Application to remove any user files from tomcat when application is closed .
                try {
                    File encrypted = new File("\\apache-tomcat-9.0.36\\bin\\encrypted");
                    String[] entries = encrypted.list();
                    for (String s : entries) {
                        File currentFile = new File(encrypted.getPath(), s);
                        currentFile.delete();
                    }
                    encrypted.delete();
                }

                catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        }
        try {
            // register shutdown hook
            Runtime.getRuntime().addShutdownHook(new Destroy());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }





    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

