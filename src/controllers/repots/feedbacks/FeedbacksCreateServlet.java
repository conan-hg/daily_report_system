package controllers.repots.feedbacks;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Feedback;
import models.Report;
import models.validators.FeedbackValidator;
import utils.DBUtil;

/**
 * Servlet implementation class FeedbacksCreateServlet
 */
@WebServlet("/feedbacks/create")
public class FeedbacksCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FeedbacksCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Feedback f = new Feedback();

            f.setEmployee((Employee)request.getSession().getAttribute("login_employee"));

            f.setReport((Report)request.getSession().getAttribute("report_id"));

            Date feedback_date = new Date(System.currentTimeMillis());
            String rd_str = request.getParameter("feedback_date");
            if(rd_str != null && !rd_str.equals("")) {
                feedback_date = Date.valueOf(request.getParameter("feedback_date"));
            }
            f.setFeedback_date(feedback_date);

            f.setContent(request.getParameter("content"));

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            f.setCreated_at(currentTime);

            List<String> errors = FeedbackValidator.validate(f);
            if(errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("feedback", f);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/feedbacks/feedback.jsp");
                rd.forward(request, response);
            } else {
                em.getTransaction().begin();
                em.persist(f);
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "登録が完了しました。");

                response.sendRedirect(request.getContextPath() + "/reports/index");
            }



        }
    }

}
