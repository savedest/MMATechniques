package control;

import model.bean.*;
import model.dao.SkillsFootballOntologyDAO;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EsempioServlet
 */
@WebServlet("/SpecificPlayer")
public class SpecificPlayerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public SpecificPlayerServlet() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uriPlayer = request.getParameter("player");
        String uriWeightClass = request.getParameter("weightClass");
        SkillsFootballOntologyDAO sDao = new SkillsFootballOntologyDAO();

        try {

            uriPlayer = "<" + uriPlayer + ">";
            MartialArtistBean player = sDao.doSpecificMartialArtist(uriPlayer);
            ArrayList<KickboxingTechniqueBean> kickboxingTechniques = sDao.doKickboxingSkillMartialArtist(uriPlayer);
            ArrayList<KarateTechniqueBean> karateTechniques = sDao.doKarateSkillMartialArtist(uriPlayer);
            ArrayList<JudoTechniqueBean> judoTechniques = sDao.doJudoSkillMartialArtist(uriPlayer);
            ArrayList<GrapplingTechniqueBean> grapplingTechniques = sDao.doGrapplingSkillMartialArtist(uriPlayer);
            
            String uriWeightClassBlank = null;
            
            if(uriWeightClass.contains(" ")) {
            	uriWeightClass = "<http://dbpedia.org/resource/" + uriWeightClass + ">";
            	uriWeightClassBlank = uriWeightClass.replaceAll(" ", "_");
            } else {
            	uriWeightClassBlank = "<" + uriWeightClass + ">";
            }
            
            ArrayList<MartialArtistBean> relatedPlayers = sDao.doRetrieveRelatedMartialArtists(uriWeightClassBlank, uriPlayer);
            
            request.setAttribute("player", player);
            request.setAttribute("kickboxingTechniques", kickboxingTechniques);
            request.setAttribute("karateTechniques", karateTechniques);
            request.setAttribute("judoTechniques", judoTechniques);
            request.setAttribute("grapplingTechniques", grapplingTechniques);
            request.setAttribute("relatedPlayers", relatedPlayers);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher(response.encodeRedirectURL("./SpecificPlayer.jsp"));
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
