<%@ page import="model.bean.SoccerPlayerBean" %>
<%@ page import="model.bean.MartialArtistBean" %>
<%@ page import="model.bean.KickboxingTechniqueBean" %>
<%@ page import="model.bean.KarateTechniqueBean" %>
<%@ page import="model.bean.JudoTechniqueBean" %>
<%@ page import="model.bean.GrapplingTechniqueBean" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
    import="model.bean.*"
%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="utils.ConverterJavaToJSUtil" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%@ page import="model.dao.SkillsFootballOntologyDAO" %>

<%
    SkillsFootballOntologyDAO dao = new SkillsFootballOntologyDAO();

    MartialArtistBean player = (MartialArtistBean) request.getAttribute("player");
    
   	ArrayList<KickboxingTechniqueBean> kickboxingTechniques = (ArrayList<KickboxingTechniqueBean>) request.getAttribute("kickboxingTechniques");
    ArrayList<KarateTechniqueBean> karateTechniques = (ArrayList<KarateTechniqueBean>) request.getAttribute("karateTechniques");
    ArrayList<JudoTechniqueBean> judoTechniques = (ArrayList<JudoTechniqueBean>) request.getAttribute("judoTechniques");
    ArrayList<GrapplingTechniqueBean> grapplingTechniques = (ArrayList<GrapplingTechniqueBean>) request.getAttribute("grapplingTechniques");
        
    ArrayList<MartialArtistBean> relatedPlayers = (ArrayList<MartialArtistBean>) request.getAttribute("relatedPlayers");
    
    if (player == null) {
        response.sendRedirect("./Index.jsp");
        return;
    }

%>

<!DOCTYPE html>
<html lang="it">
<head>
    <jsp:include page="./parts/head.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link rel="stylesheet" href="./styles/specificPlayer.css">
</head>

<body>

<jsp:include page="./parts/header.jsp" />

<div class="container_cover"></div>
<div class="container_image_player">
    <div class="image_player">
        <style>
            .image_player {
                background-image: url("<%= player.getThumbnail() %>");
            }
            .image_player_sample {
                background-image: url("img/sample-soccer-player.png")!important;
            }
        </style>
        <script>
            var img = new Image();
            img.onerror = function () {
                document.getElementsByClassName("image_player")[0].classList.add("image_player_sample");
            };
            img.src = "<%= player.getThumbnail() %>";
        </script>
    </div>
</div>
<div class="container">
    <p><b>Descrizione:</b> <%= player.getComment() %></p>
    <div class="row">
        <div class="col-lg-6">
            <p><b>Fighter:</b> <%= player.getName()%></p>
        </div>
        <div class="col-lg-6">
            <p><b>Team:</b> <a href="<%= player.getTeamUri() %>" target="_blank"><%= player.getTeam()%></a></p>
        </div>
    </div>
    <div class="row">
            <div class="col-lg-6">
                <p><b>Peso:</b> <%= player.getWeight()%> Kg</p>
            </div>
            <div class="col-lg-6">
                <p><b>Altezza:</b> <%= player.getHeight()%> Cm</p>
            </div>
    </div>
    <%if(kickboxingTechniques.isEmpty()) {
    %>
    <h3 class="margin-25"><%= player.getName() %> non pratica tecniche di Kickboxing!</h3>
    <%} else {
    %> 
    <h3 class="margin-25"><%= player.getName() %> pratica le seguenti tecniche di Kickboxing:</h3>
     <%} 
    %>   
        <div class="row">
            <%
            for(KickboxingTechniqueBean skill : kickboxingTechniques) {
                %>
            <div class="col-lg-6">
                <p><b><%= skill.getNome() %></b> <a href="http://localhost:8080/MMATechniquesOntology#<%= skill.getNome() %>"><i class="fa-solid fa-arrow-up-right-from-square"></i></a>: <%= skill.getDescrizione() %></p>
            </div>
            <%
            }
            %>
        </div>
        <%if(karateTechniques.isEmpty()) {
	    %>
	    <h3 class="margin-25"><%= player.getName() %> non pratica tecniche di Karate!</h3>
	    <%} else {
	    %> 
	    <h3 class="margin-25"><%= player.getName() %> pratica le seguenti tecniche di Karate:</h3>
	     <%} 
	    %> 
        <div class="row">
            <%
            for(KarateTechniqueBean skill : karateTechniques) {
                %>
            <div class="col-lg-6">
                <p><b><%= skill.getNome() %></b> <a href="http://localhost:8080/MMATechniquesOntology#<%= skill.getNome() %>"><i class="fa-solid fa-arrow-up-right-from-square"></i></a>: <%= skill.getDescrizione() %></p>
            </div>
            <%
            }
            %>
        </div>
        <%if(judoTechniques.isEmpty()) {
	    %>
	    <h3 class="margin-25"><%= player.getName() %> non pratica tecniche di Judo!</h3>
	    <%} else {
	    %> 
	    <h3 class="margin-25"><%= player.getName() %> pratica le seguenti tecniche di Judo:</h3>
	     <%} 
	    %> 
        <div class="row">
            <%
            for(JudoTechniqueBean skill : judoTechniques) {
                %>
            <div class="col-lg-6">
                <p><b><%= skill.getNome() %></b> <a href="http://localhost:8080/MMATechniquesOntology#<%= skill.getNome() %>"><i class="fa-solid fa-arrow-up-right-from-square"></i></a>: <%= skill.getDescrizione() %></p>
            </div>
            <%
            }
            %>
        </div>
        <%if(grapplingTechniques.isEmpty()) {
	    %>
	    <h3 class="margin-25"><%= player.getName() %> non pratica tecniche di Grappling!</h3>
	    <%} else {
	    %> 
	    <h3 class="margin-25"><%= player.getName() %> pratica le seguenti tecniche di Grappling:</h3>
	     <%} 
	    %> 
        <div class="row">
            <%
            for(GrapplingTechniqueBean skill : grapplingTechniques) {
                %>
            <div class="col-lg-6">
                <p><b><%= skill.getNome() %></b> <a href="http://localhost:8080/MMATechniquesOntology#<%= skill.getNome() %>"><i class="fa-solid fa-arrow-up-right-from-square"></i></a>: <%= skill.getDescrizione() %></p>
            </div>
            <%
            }
            %>
        </div>
</div>
<div class="container">
    <div class="row">
        <div class="col-lg-6">
            <div>
                <canvas id="radarSkills"></canvas>
            </div>
            <script>
                <%  // conversion arrays from java to js
                    ArrayList<String> skillNames = new ArrayList<String>();
                    String[] skillValues=new String[0];

                    for (KickboxingTechniqueBean skill : player.getSkills()) {
                        skillNames.add(skill.getNome());
                        List<String> list = new ArrayList<>(Arrays.asList(skillValues));
                        
                        
                        
                        
                        
                       	list.add(skill.getValSkill());
                        skillValues = list.toArray(new String[0]);
                    }

                    String arrJSLabels = ConverterJavaToJSUtil.getArrayString(skillNames);
                    String arrJSdata = ConverterJavaToJSUtil.getArrayString(skillValues);
                %>
                const data = {
                    labels: <%= arrJSLabels %>,
                    datasets: [{
                        label: "Skills  <%= player.getName() %>",
                        data: <%= arrJSdata %>,
                        fill: true,
                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                        borderColor: 'rgb(255, 99, 132)',
                        pointBackgroundColor: 'rgb(255, 99, 132)',
                        pointBorderColor: '#fff',
                        pointHoverBackgroundColor: '#fff',
                        pointHoverBorderColor: 'rgb(255, 99, 132)'
                    }]
                };

                console.log(data);

                const config = {
                    type: 'radar',
                    data: data,
                    options: {
                        elements: {
                            line: {
                                borderWidth: 3
                            }
                        }
                    },
                };

                const myChart = new Chart(
                    document.getElementById('radarSkills'),
                    config
                );
            </script>
        </div>
        <div class="col-lg-6">
            <table class="table table-striped table-hover">
                <tr>
                    <th>Skill</th>
                    <th>Valore</th>
                </tr>
                <%
                    for (KickboxingTechniqueBean skill : player.getSkills()) {
                    	
                    	String valoreSkill = null;
                    	
                    	if(skill.getValSkill().contains("0.")) {
                        	String percentual = skill.getValSkill().replace("0.", "");
                        	valoreSkill = percentual + "%";
                        } else {
                        	valoreSkill = skill.getValSkill();
                        }
                %>
                <tr>
                    <td><a href="#<%=  skill.getUri()%>"><%= skill.getNome() %></a></td>
                    <td><%= valoreSkill %></td>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
    </div>
</div>
<div class="container">
    <h3 class="margin-25">Fighter che combattono nelle stesse categorie di <%=player.getName()%></h3>
    <div class="row">
    <% for (MartialArtistBean sp : relatedPlayers) { %>
    <div class="col-lg-4">
        <div data-uri="<%= sp.getUri() %>" data-uri2="<%= sp.getCurrWeightClass() %>" class="card card_player" style="width: 15rem; height: 32rem; margin: 5rem;" data-thumb="<%= sp.getThumbnail() %>">
            <img class="card-img-top" src="<%=sp.getThumbnail()%>" alt="Card image cap">
            <img src="./img/sample-soccer-player.png" class="card-img-top img_sample" style="display: none;">
            <div class="card-body">
                <h5 class="card-title"><%=sp.getName()%></h5>
                <p class="card-text"><%=sp.getCurrWeightClass()%></p>
            </div>
            <ul class="list-group list-group-flush">
                <li class="list-group-item">Altezza: <%=sp.getHeight()%> Cm</li>
                <li class="list-group-item">Peso: <%=sp.getWeight()%> Kg</li>

            </ul>
        </div>
    </div>
    <% } %>
    </div>
</div>




<!-- Footer -->
<jsp:include page="./parts/footer.jsp" />
<!-- Footer -->

<script src="scripts/cards.js"></script>
</body>
</html>