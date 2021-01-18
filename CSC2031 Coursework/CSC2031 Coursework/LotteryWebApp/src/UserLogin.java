import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Base64;



@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet {

    private Connection conn;
    private Statement stmt;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("firstname");
        session.removeAttribute("lastname");
        session.removeAttribute("username");
        session.removeAttribute("password");
        session.removeAttribute("email");
        session.removeAttribute("admin_login");
        session.removeAttribute("public_login");
        session.removeAttribute("draws");
        session.removeAttribute("LuckyNumbers");
        if(session.getAttribute("length")==null){ // Variable used for creating new cipher
            session.removeAttribute("length");
            session.removeAttribute("pair");
            session.removeAttribute("cipher");
        }



        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String USER = "user";
        String PASS = "password";

        // URLs to connect to database depending on your development approach
        // (NOTE: please change to option 1 when submitting)

        // 1. use this when running everything in Docker using docker-compose
        //String DB_URL = "jdbc:mysql://db:3306/lottery";

        // 2. use this when running tomcat server locally on your machine and mysql database server in Docker
        String DB_URL = "jdbc:mysql://localhost:33333/lottery";

        // 3. use this when running tomcat and mysql database servers on your machine
        //String DB_URL = "jdbc:mysql://localhost:3306/lottery";

        try {
            // create database connection and statement
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            // query database and get results
            ResultSet rs1=stmt.executeQuery("SELECT * FROM Luckydraw");
            while(rs1.next()) {//Retrieves lucky numbers from the database
                String lucky = rs1.getString("Numbers");
                session.setAttribute("LuckyNumbers", lucky);
            }

            ResultSet rs = stmt.executeQuery("SELECT * FROM userAccounts");
            String content = "<table border='1' cellspacing='2' cellpadding='2' width='100%' align='left'>" +
                    "<tr><th>First name</th><th>Last name</th><th>Email</th><th>Phone number</th><th>Username</th><th>User  Role</th></tr>";
            Hashing hash=new Hashing();
            boolean Userflag= false;
            while(rs.next()){
                content += "<tr><td>"+ rs.getString("Firstname") + "</td>" +
                        "<td>" + rs.getString("Lastname") + "</td>" +
                        "<td>" + rs.getString("Email") + "</td>" +
                        "<td>" + rs.getString("Phone") + "</td>" +
                        "<td>" + rs.getString("Username") + "</td>" +
                        "<td>" + rs.getString("Userrole") + "</td></tr>";

                if(request.getParameter("username").equals(rs.getString("Username"))){
                    byte[] salt=Base64.getDecoder().decode(rs.getString("Salt"));// function for hashing plaintext provided password and removing salt from encrypted password.
                    String Password =hash.hashpassword(request.getParameter("password"), salt);

                    if(Password.equals(rs.getString("Pwd"))&& request.getParameter("txt_role").equals(rs.getString("Userrole"))) {
                        session.setAttribute("firstname", rs.getString("Firstname"));
                        session.setAttribute("lastname", rs.getString("Lastname"));
                        session.setAttribute("username", rs.getString("Username"));
                        session.setAttribute("email", rs.getString("Email"));
                        session.setAttribute("password", Password);
                        session.setAttribute("tries", 3);
                        if(session.getAttribute("length")==null) {
                            int i = 0;
                            session.setAttribute("length", i);
                        }
                        Userflag= true;

                        if (rs.getString("Userrole").equals("admin")) {//If admin  user exists, they are redirected to admin home page
                            session.setAttribute("admin_login", rs.getString("Username"));
                        }
                        else if(rs.getString("Userrole").equals("public")) {//If public user exists, they are redirected to public home page
                            session.setAttribute("public_login", rs.getString("Username"));
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
                            request.setAttribute("message", "login success");
                            dispatcher.forward(request, response);
                        }

                    }
                }
            }
            content += "</table>";


            if(Userflag==false){

                try{ //if user doesn't exist, they are forwarded to error page.
                    int j= (int) session.getAttribute("tries");
                    j-=1;
                    session.setAttribute("tries", j);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                    request.setAttribute("message", "login unsuccessful, " +j +" tries left.");
                    dispatcher.forward(request, response);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
            else{
                try {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/admin_home.jsp");//Database info parsed
                    request.setAttribute("data", content);
                    dispatcher.forward(request, response);
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }

            // close connection
            conn.close();

        } catch (Exception se) {
            se.printStackTrace();
            // display error.jsp page with given message if successful
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            request.setAttribute("message", "Database Error, Please try again");
            dispatcher.forward(request, response);
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
