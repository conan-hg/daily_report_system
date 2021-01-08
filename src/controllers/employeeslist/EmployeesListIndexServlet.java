package controllers.employeeslist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.EmployeeFollow;
import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeesListIndexServlet
 */
@WebServlet("/employeeslist/index")
public class EmployeesListIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesListIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @return
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee login_employee = (Employee) (request.getSession().getAttribute("login_employee"));


        int page = 1;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException e){}
        List<Employee> employees = em.createNamedQuery("getAllEmployees", Employee.class)
                                     .setFirstResult(15 * (page - 1))
                                     .setMaxResults(15)
                                     .getResultList();

        List<Follow> follows = em.createNamedQuery("getMyAllFollows",Follow.class)
                                 .setParameter("follow", login_employee)
                                 .getResultList();

        List<EmployeeFollow> employeefollows = new ArrayList<>();

        for(Employee emp :employees)  {

            Integer id = emp.getId();
            Boolean Followed = false;

            for(Follow f :follows) {

                Employee follow = f.getFollower();

                if(id == follow.getId()) {
                    Followed = true;
                    break;
                }

            }

            EmployeeFollow ef = new EmployeeFollow();

            ef.setId(id);
            ef.setCode(emp.getCode());
            ef.setName(emp.getName());
            ef.setFollowed(Followed);

            employeefollows.add(ef);


        }




        long employees_count = (long)em.createNamedQuery("getEmployeesCount", Long.class)
                                        .getSingleResult();
        em.close();

        request.setAttribute("follows", follows);
        request.setAttribute("page", page);
        request.setAttribute("employeefollows", employeefollows);
        request.setAttribute("employees_count", employees_count);



        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employeeslist/index.jsp");
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */

}
