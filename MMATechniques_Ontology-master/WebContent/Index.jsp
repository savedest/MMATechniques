<%@ page import="model.bean.SoccerPlayerBean" %>
<%@ page import="model.bean.MartialArtistBean" %>
<%@ page import="model.dao.SkillsFootballOntologyDAO" %>
<%@ page import="model.bean.FootBallTeamBean" %>
<%@ page import="model.bean.SkillBean" %>
<%@ page import="model.bean.KickboxingTechniqueBean" %>
<%@ page import="model.bean.KarateTechniqueBean" %>
<%@ page import="model.bean.JudoTechniqueBean" %>
<%@ page import="model.bean.GrapplingTechniqueBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
	
<!DOCTYPE html>
<html lang="it">

<head>
  <jsp:include page="./parts/head.jsp" />
  <link rel="stylesheet" href="./styles/home.css">
</head>

<body>

<jsp:include page="./parts/header.jsp" />

<div class="container">

  <div class="accordion" id="accordion-sfo">
    <div class="accordion-item">
          <h2 class="accordion-header" id="headingZero">
            <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseZero" aria-expanded="true" aria-controls="collapseZero">
              <h3>I migliori calciatori</h3>
            </button>
          </h2>
          <div id="collapseZero" class="accordion-collapse collapse show" aria-labelledby="headingZero">
            <div class="accordion-body">
              <div class="row">
                <%
                  SkillsFootballOntologyDAO dao2 = new SkillsFootballOntologyDAO();

                  ArrayList<SoccerPlayerBean> playerss = dao2.doRetrieveBest30SoccerPlayerInTheWorld();
                  if (playerss != null) {
                    for (SoccerPlayerBean sp : playerss)
                    {
                      if (!sp.getName().equals("\"playing-style\"")) {
                %>
                <div class="col-lg-4">
                  <div data-uri="<%= sp.getUri() %>" class="card card_player" style="width: 15rem; height: 32rem; margin: 5rem;" data-thumb="<%= sp.getThumbnail() %>">
                    <img class="card-img-top" src="<%=sp.getThumbnail()%>" alt="Card image cap">
                    <img src="./img/sample-soccer-player.png" class="card-img-top img_sample" style="display: none;">
                    <div class="card-body">
                      <h5 class="card-title"><%=sp.getName()%></h5>
                      <p class="card-text"><%=sp.getFootballTeamBean().getName()%></p>
                    </div>
                    <ul class="list-group list-group-flush">
                      <li class="list-group-item">Overall : <%=sp.getOverall()%></li>
                      <li class="list-group-item">Ruolo : <%=sp.getPosition()%></li>

                    </ul>
                    <div class="card-body">
                      <%if(dao2.hasPlayerWonBallonDor("<" + sp.getUri() + ">")){%><p style="color: darkgoldenrod">Questo giocatore ha vinto almeno un <a href="ServletBallonDOR">pallone d'oro</a></p>
                      <%}%>
                    </div>
                  </div>
                </div>
                <%}}}%>
              </div>
            </div>
          </div>
        </div>
    <div class="accordion-item">
      <h2 class="accordion-header" id="headingOne">
        <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
          <h3>I migliori fighter</h3>
        </button>
      </h2>
      <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne">
        <div class="accordion-body">
          <div class="row">
            <%
              SkillsFootballOntologyDAO dao = new SkillsFootballOntologyDAO();

              ArrayList<MartialArtistBean> players = dao.doRetrieve30RandomMartialArtist();
              if (players != null) {
                for (MartialArtistBean sp : players)
                {
                  if (!sp.getName().equals("\"playing-style\"")) {
            %>
            <div class="col-lg-4">
              <div data-uri="<%= sp.getUri() %>" data-uri2="<%= sp.getCurrWeightClassUri() %>" class="card card_player" style="width: 15rem; height: 32rem; margin: 5rem;" data-thumb="<%= sp.getThumbnail() %>">
                <img class="card-img-top" src="<%=sp.getThumbnail()%>" alt="Card image cap">
                <img src="./img/sample-soccer-player.png" class="card-img-top img_sample" style="display: none;">
                <div class="card-body">
                  <h5 class="card-title"><%=sp.getName()%></h5>
                  <p class="card-text"><%=sp.getCurrWeightClass()%></p>
                </div>
                <ul class="list-group list-group-flush">
                  <li class="list-group-item">Peso : <%=sp.getWeight()%> Kg</li>
                  <li class="list-group-item">Altezza : <%=sp.getHeight()%> Cm</li>

                </ul>

              </div>
            </div>
            <%}}}%>
          </div>
        </div>
      </div>
    </div>
    <div class="accordion-item">
      <h2 class="accordion-header" id="headingTwo">
        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
          <h3>Le migliori squadre di calcio</h3>
        </button>
      </h2>
      <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo">
        <div class="accordion-body">
          <%
            ArrayList<FootBallTeamBean> teams = dao.doRetrieveStatsFootballTeamWithMaxAvgAndMinimunOverall();
            if (teams != null) { %>
          <div class="row">
            <%
              for (FootBallTeamBean ft : teams)
              {%>
            <div class="col-lg-4">
              <div class="card card_team" style="width: 15rem; height: 28rem; margin: 5rem;" data-thumb="<%= ft.getThumbnail() %>">
                <img class="card-img-top" src="<%= ft.getThumbnail() %>" alt="Card image cap">
                <img src="./img/sample-logo-team.png" class="card-img-top img_sample" style="display: none;">
                <div class="card-body">
                  <h5 class="card-title">
                    <%=ft.getName()%>
                    <a href="<%= ft.getUri() %>" target="_blank">
                      <i class="fa-solid fa-arrow-up-right-from-square"></i>
                    </a>
                  </h5>
                  <p class="card-text">Media overall : <%=ft.getAvg_overall()%></p>
                </div>
                <ul class="list-group list-group-flush">
                  <li class="list-group-item"> Overall max : <%=ft.getMax_overall()%></li>
                  <li class="list-group-item"> Overall min  : <%=ft.getMin_overall()%></li>
                </ul>
              </div>
            </div>
            <%}}%>
          </div>
        </div>
      </div>
    </div>
    <div class="accordion-item">
          <h2 class="accordion-header" id="headingThree">
            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
              <h3>Info Tecniche di Kickboxing</h3>
            </button>
          </h2>
          <div id="collapseThree" class="accordion-collapse collapse" aria-labelledby="headingThree">
            <div class="accordion-body">
              <table class="table table-striped table-hover">
                      <%
                        ArrayList<KickboxingTechniqueBean> kickboxingTechniques = dao.doRetrieveKickboxingTechniquesWithNameAndMartialArtist();
                        if (kickboxingTechniques != null) { %>

                      <tr>
                        <th>Nome Tecnica</th>
                        <th>Tipo di Tecnica</th>
                        <th>Descrizione Tecnica</th>
                        <th >Questi Fighter praticano questa Tecnica di Kickboxing</th>
                      </tr>

                      <%
                        for (KickboxingTechniqueBean skill : kickboxingTechniques)
                        {
                          ArrayList<String> calciatori = new ArrayList<>();
                          ArrayList<String> uri = new ArrayList<>();
                          for(MartialArtistBean sc : skill.getPlayers()){
                            calciatori.add(sc.getName());
                            uri.add(sc.getUri());
                          }
                      %>
                      <tr id="<%= skill.getNome() %>">
                        <td><%=skill.getNome()%></td>
                        <td><%=skill.getTipo()%></td>
                        <td><%=skill.getDescrizione()%></td>
                        <td>
                          <%for(int index = 0; index< calciatori.size();index ++) {%>
                          <a href="SpecificMartialArtist?fighter=<%=uri.get(index)%>"><%=calciatori.get(index)%></a>,
                          <%}%>
                        </td>
                      </tr>
                      <%}}%>

                    </table>
            </div>
          </div>
    </div>
    <div class="accordion-item">
              <h2 class="accordion-header" id="headingFour">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                  <h3>Info Tecniche di Karate</h3>
                </button>
              </h2>
              <div id="collapseFour" class="accordion-collapse collapse" aria-labelledby="headingFour">
                <div class="accordion-body">
                <table class="table table-striped table-hover">
                      <%
                        ArrayList<KarateTechniqueBean> karateTechniques = dao.doRetrieveKarateTechniquesWithNameAndMartialArtist();
                        if (karateTechniques != null) { %>

                      <tr>
                        <th>Nome Tecnica</th>
                        <th>Tipo di Tecnica</th>
                        <th>Descrizione Tecnica</th>
                        <th >Questi Fighter praticano questa Tecnica di Karate</th>
                      </tr>

                      <%
                        for (KarateTechniqueBean skill : karateTechniques)
                        {
                          ArrayList<String> calciatori = new ArrayList<>();
                          ArrayList<String> uri = new ArrayList<>();
                          for(MartialArtistBean sc : skill.getPlayers()){
                            calciatori.add(sc.getName());
                            uri.add(sc.getUri());
                          }
                      %>
                      <tr id="<%= skill.getNome() %>">
                        <td><%=skill.getNome()%></td>
                        <td><%=skill.getTipo()%></td>
                        <td><%=skill.getDescrizione()%></td>
                        <td>
                          <%for(int index = 0; index< calciatori.size();index ++) {%>
                          <a href="SpecificMartialArtist?fighter=<%=uri.get(index)%>"><%=calciatori.get(index)%></a>,
                          <%}%>
                        </td>
                      </tr>
                      <%}}%>

                    </table>
                </div>
              </div>
    </div>
    <div class="accordion-item">
              <h2 class="accordion-header" id="headingFive">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                  <h3>Info Tecniche di Judo</h3>
                </button>
              </h2>
              <div id="collapseFive" class="accordion-collapse collapse" aria-labelledby="headingFive">
                <div class="accordion-body">
                    <table class="table table-striped table-hover">
                            <%
                              ArrayList<JudoTechniqueBean> judoTechniques = dao.doRetrieveJudoTechniquesWithNameAndMartialArtist();
                              if (judoTechniques != null) { %>

                            <tr>
                              <th>Nome Tecnica</th>
                              <th>Tipo di Tecnica</th>
                              <th>Descrizione Tecnica</th>
                              <th >Questi Fighter praticano questa Tecnica di Judo</th>
                            </tr>

                            <%
                              for (JudoTechniqueBean skill : judoTechniques)
                              {
                                ArrayList<String> calciatori = new ArrayList<>();
                                ArrayList<String> uri = new ArrayList<>();
                                for(MartialArtistBean sc : skill.getPlayers()){
                                  calciatori.add(sc.getName());
                                  uri.add(sc.getUri());
                                }
                            %>
                            <tr id="<%= skill.getNome() %>">
                              <td><%=skill.getNome()%></td>
                              <td><%=skill.getTipo()%></td>
                              <td><%=skill.getDescrizione()%></td>
                              <td>
                                <%for(int index = 0; index< calciatori.size();index ++) {%>
                                <a href="SpecificMartialArtist?fighter=<%=uri.get(index)%>"><%=calciatori.get(index)%></a>,
                                <%}%>
                              </td>
                            </tr>
                            <%}}%>

                          </table>
                </div>
              </div>
    </div>
    <div class="accordion-item">
              <h2 class="accordion-header" id="headingSix">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseSix" aria-expanded="false" aria-controls="collapseSix">
                  <h3>Info Tecniche di Grappling</h3>
                </button>
              </h2>
              <div id="collapseSix" class="accordion-collapse collapse" aria-labelledby="headingSix">
                <div class="accordion-body">
                <table class="table table-striped table-hover">
                            <%
                              ArrayList<GrapplingTechniqueBean> grapplingTechniques = dao.doRetrieveGrapplingTechniquesWithNameAndMartialArtist();
                              if (grapplingTechniques != null) { %>

                            <tr>
                              <th>Nome Tecnica</th>
                              <th>Tipo di Tecnica</th>
                              <th>Descrizione Tecnica</th>
                              <th >Questi Fighter praticano questa Tecnica di Grappling</th>
                            </tr>

                            <%
                              for (GrapplingTechniqueBean skill : grapplingTechniques)
                              {
                                ArrayList<String> calciatori = new ArrayList<>();
                                ArrayList<String> uri = new ArrayList<>();
                                for(MartialArtistBean sc : skill.getPlayers()){
                                  calciatori.add(sc.getName());
                                  uri.add(sc.getUri());
                                }
                            %>
                            <tr id="<%= skill.getNome() %>">
                              <td><%=skill.getNome()%></td>
                              <td><%=skill.getTipo()%></td>
                              <td><%=skill.getDescrizione()%></td>
                              <td>
                                <%for(int index = 0; index< calciatori.size();index ++) {%>
                                <a href="SpecificMartialArtist?fighter=<%=uri.get(index)%>"><%=calciatori.get(index)%></a>,
                                <%}%>
                              </td>
                            </tr>
                            <%}}%>

                          </table>
                </div>
              </div>
    </div>
</div>





<!-- Footer -->
<jsp:include page="./parts/footer.jsp" />
<!-- Footer -->

<script src="scripts/cards.js"></script>
</body>
</html>