    import javax.servlet.RequestDispatcher;
    import javax.servlet.ServletException;
    import javax.servlet.annotation.WebServlet;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import javax.servlet.http.HttpSession;
    import java.io.File;
    import java.io.IOException;
    import java.util.Arrays;

@WebServlet(name = "/CheckNumbers")
public class CheckNumbers extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String filename = (session.getAttribute("password").toString().substring(0, 20)) + ".txt";
        String[] decrypted= (String[]) session.getAttribute("draws");
        String lucky= (String) session.getAttribute("LuckyNumbers");

        boolean flag=false;

        if(Arrays.asList(decrypted).contains(lucky)){//Checks if user numbers contains winning lottery numbers
            flag=true;
        }



        if(flag){
            try {
                File encrypted=new File("\\apache-tomcat-9.0.36\\bin\\encrypted");//Directory for the file we store user numbers
                String[] entries=encrypted.list();
                for(String s:entries){//deletes every file inside the folder
                    File currentFile= new File(encrypted.getPath(),s);
                    currentFile.delete();
                }
                encrypted.delete();//deletes the folder

                RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
                request.setAttribute("message", "Winner winner chicken dinner!");
                dispatcher.forward(request, response);

            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
        else{
            try {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
                request.setAttribute("message", "Better luck next time");
                dispatcher.forward(request, response);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }

        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
