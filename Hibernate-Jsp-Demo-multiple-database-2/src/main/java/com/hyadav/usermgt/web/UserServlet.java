package com.hyadav.usermgt.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hyadav.usermgt.dao.UserDao;
import com.hyadav.usermgt.model.User;


@WebServlet("/")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao;
    private int domainDB = -1;
    private String domain = null;
    private Hashtable<String, Integer> domainDatabase = new Hashtable<String, Integer>();
    private HttpSession session = null;
    

    public void init() {
        userDao = new UserDao();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getServletPath();
        String reqUrl = request.getServerName();
        this.domain = reqUrl;
        request.setAttribute("domain", domain);
        this.session = request.getSession(false);
        if(this.session == null) {
        	RequestDispatcher dispatcher = request.getRequestDispatcher("database-configure.jsp");
            dispatcher.forward(request, response);
        } else {
        	if(action.equals("/insertDatabase"))
        		insertDatabase(request, response);
        	if(this.domainDatabase.get(reqUrl) == null) {
        		RequestDispatcher dispatcher = request.getRequestDispatcher("database-configure.jsp");
                dispatcher.forward(request, response);
        	}
        	this.domainDB = this.domainDatabase.get(reqUrl);
        }
        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertUser(request, response);
                    break;
                case "/delete":
                    deleteUser(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateUser(request, response);
                    break;
                case "/insertDatabase":
                	insertDatabase(request, response);
                	break;
                default:
                    listUser(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listUser(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        List < User > listUser = userDao.getAllUser(this.domainDB);
        request.setAttribute("listUser", listUser);
        request.setAttribute("domainDB", this.domainDB);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userDao.getUser(id, this.domainDB);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        request.setAttribute("user", existingUser);
        dispatcher.forward(request, response);

    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        User newUser = new User(name, email, country);
        userDao.saveUser(newUser, this.domainDB);
        response.sendRedirect("list");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        User user = new User(id, name, email, country);
        userDao.updateUser(user, this.domainDB);
        response.sendRedirect("list");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userDao.deleteUser(id, this.domainDB);
        response.sendRedirect("list");
    }
    
//    private void configureDatabase(HttpServletRequest request, HttpServletResponse response)
//    throws SQLException, IOException, ServletException {
//    	RequestDispatcher dispatcher = request.getRequestDispatcher("database-configure.jsp");
//        dispatcher.forward(request, response);
//    }
    
    private void insertDatabase(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    	String databaseType = request.getParameter("database");
        String databaseUrl = request.getParameter("url");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int newDatabaseIndex = this.userDao.generateSessionFactory(databaseType, databaseUrl, username, password);
        this.domainDatabase.put(this.domain, newDatabaseIndex);
        System.out.println(this.domain + this.domainDatabase.get(this.domain));
        this.domainDB = newDatabaseIndex;
        listUser(request, response);
    }
}
