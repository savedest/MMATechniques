package control;

import model.bean.SkillBean;
import model.bean.SoccerPlayerBean;
import model.dao.SkillsFootballOntologyDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
@WebServlet("/ServletBallonDOR")
public class ServletBallonDOR extends HttpServlet{

        private static final long serialVersionUID = 1L;

        /**
         * Default constructor.
         */
        public ServletBallonDOR() {
            // TODO Auto-generated constructor stub
        }

        /**
         * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
         */
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            //@todo edit mock uriPlayer
            SkillsFootballOntologyDAO dao = new SkillsFootballOntologyDAO();
            request.setAttribute("players", dao.doRetrieveTheUltimateFighter());
            RequestDispatcher dispatcher = request
                    .getRequestDispatcher(response.encodeRedirectURL("./BallonDOR.jsp"));
            dispatcher.forward(request, response);
        }

        /**
         * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
         */
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doGet(request, response);
        }
    }

