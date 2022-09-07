package control;

import model.bean.KickboxingTechniqueBean;
import model.bean.MartialArtistBean;
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

/**
 * Servlet implementation class EsempioServlet
 */
@WebServlet("/SpecificMartialArtist")
public class SpecificMartialArtistServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public SpecificMartialArtistServlet() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uriPlayer = request.getParameter("fighter");
        SkillsFootballOntologyDAO sDao = new SkillsFootballOntologyDAO();

        try {
            if (uriPlayer.contains("http://www.semanticweb.org/gerardodellobuono/ontologies/2022/6/MMATechniquesProject#")) {
                uriPlayer = sDao.doRetrieveURIDBPPlayer(uriPlayer.split("MMATechniquesProject#")[1]);
            }

            uriPlayer = "<" + uriPlayer + ">";
            MartialArtistBean player = sDao.doSpecificMartialArtist(uriPlayer);
            ArrayList<KickboxingTechniqueBean> skills = sDao.doKickboxingSkillMartialArtist(uriPlayer);

            String uriCurrClub = "<" + player.getFootballTeamBean().getUri() + ">";

            request.setAttribute("fighter", player);
            request.setAttribute("skills", skills);

            RequestDispatcher dispatcher = request
                    .getRequestDispatcher(response.encodeRedirectURL("./SpecificPlayer.jsp"));
            dispatcher.forward(request, response);
        } catch (Exception e) {
            // if has exception, then go to home without showing the exception.
            RequestDispatcher dispatcher = request
                    .getRequestDispatcher(response.encodeRedirectURL("./Index.jsp"));
            dispatcher.forward(request, response);
            return;
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
