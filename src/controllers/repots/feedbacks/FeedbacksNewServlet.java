package controllers.repots.feedbacks;

import java.io.IOException;
import java.sql.Date;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Feedback;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class FeedbacksNewServlet
 */
@WebServlet("/feedbacks/new")
public class FeedbacksNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FeedbacksNewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        request.setAttribute("_token", request.getSession().getId());
        request.getSession().setAttribute("report_id", r);

        Feedback f = new Feedback();
        f.setFeedback_date(new Date(System.currentTimeMillis()));
        request.setAttribute("feedback", f);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/feedbacks/feedback.jsp");
        rd.forward(request, response);


    }

}
