import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.util.Arrays;

@WebServlet(name = "/GetUserNumbers")
public class GetUserNumbers extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String filename = (session.getAttribute("password").toString().substring(0, 20)) + ".txt";



        class DecryptedStorage {

            String[] decrypted= new String[(int)session.getAttribute("length")];

            private Cipher cipher= (Cipher) session.getAttribute("cipher");
            private KeyPair pair = (KeyPair) session.getAttribute("pair");

            public DecryptedStorage() {

            }

            public byte[] bytesFileReader(String filename) {
                try {
                    return Files.readAllBytes(Paths.get(filename));
                } catch (IOException e) {
                    e.printStackTrace();

                }
                catch (IllegalArgumentException e){
                    e.printStackTrace();
                }
                return null;
            }

            public String[] decryptData(byte[] data) {
                try {
                    cipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());
                    for(int i=0;i<(int)session.getAttribute("length"); i++){// Splits byte array for everytime the submit button been's pressed.
                        byte[] slice = Arrays.copyOfRange(data, 256 * i, 256+256*i);// Split byte array by 256 byte chunks.
                        byte[] decipheredText = cipher.doFinal(slice);
                        decrypted[i] = (new String(decipheredText, StandardCharsets.UTF_8));

                    }





                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                catch (IllegalArgumentException e){
                    e.printStackTrace();
                }
                /*  (ArrayIndexOutOfBoundsException e ) {
                    e.printStackTrace();
                }

                 */


                return  decrypted ;
            }

        }
        DecryptedStorage ds = new DecryptedStorage();

        String[] decrypted = ds.decryptData(ds.bytesFileReader("encrypted\\"+filename));
        session.setAttribute("draws", decrypted);

        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
            request.setAttribute("draws", decrypted);
            dispatcher.forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }




    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
