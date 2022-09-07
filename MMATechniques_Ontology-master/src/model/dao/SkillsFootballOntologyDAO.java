package model.dao;

import model.bean.*;
import org.apache.jena.query.*;

import java.util.ArrayList;

public class SkillsFootballOntologyDAO {
    private final String endpoint = "http://localhost:3030/SkillsFootball/query";
    private final String endpoint2 = "http://localhost:3030/MMATechniquesTest/sparql";


    public ArrayList<SoccerPlayerBean>  doRetrieveBest30SoccerPlayerInTheWorld() {
        ArrayList<SoccerPlayerBean> players = new ArrayList<>();

        String q = "PREFIX db: <http://dbpedia.org/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "PREFIX myonto: <http://www.semanticweb.org/tanucc/ontologies/2022/4/skillsFootball>\n" +
                "SELECT DISTINCT ?individual ?name ?currClub ?currClubURI ?currClubThumbnail ?overall ?thumbnail (GROUP_CONCAT(?position;SEPARATOR=\",\") AS ?positions)\n" +
                "WHERE {\n" +
                "\tSERVICE <http://dbpedia.org/sparql> {\n" +
                "      ?individual a dbo:SoccerPlayer .\n" +
                "      ?individual dbp:name ?name .\n" +
                "      ?individual dbp:currentclub ?currClubURI .\n" +
                "      ?currClubURI rdfs:label ?currClub.\n" +
                "      ?currClubURI dbo:thumbnail ?currClubThumbnail .\n" +
                "      ?individual dbo:thumbnail ?thumbnail .\n" +
                "      ?individual dbo:position ?posURI .\n" +
                "      ?posURI rdfs:label ?position .\n" +
                "      FILTER(LANG(?currClub) = 'en')\n" +
                "      FILTER(LANG(?position) = 'it')\n" +
                "\t}\n" +
                "    ?player a dbo:SoccerPlayer .\n" +
                "    ?player myonto:has_overall ?overall .\n" +
                "    ?player rdfs:seeAlso ?individual .\n" +
                "}\n" +
                "GROUP BY ?individual ?name ?currClub ?currClubURI ?currClubThumbnail ?overall ?thumbnail\n" +
                "ORDER BY DESC(?overall)\n" +
                "LIMIT 30";

        Query query = QueryFactory.create(q);

        // Esecuzione della querye cattura dei risultati
        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query);
        ResultSet results = qexec.execSelect();
        while (results.hasNext()) {
            QuerySolution qSolution = results.nextSolution();
            SoccerPlayerBean player = new SoccerPlayerBean();
            FootBallTeamBean team = new FootBallTeamBean();
            player.setUri(qSolution.getResource("individual").getURI());
            player.setName(qSolution.getLiteral("name").getString());
            player.setThumbnail(qSolution.getResource("thumbnail").getURI());
            player.setPosition(qSolution.getLiteral("positions").getString());
            player.setOverall(qSolution.getLiteral("overall").getInt());
            team.setUri(qSolution.getResource("currClubURI").getURI());
            team.setThumbnail(qSolution.getResource("currClubThumbnail").getURI());
            team.setName(qSolution.getLiteral("currClub").getString());
            player.setFootballTeamBean(team);
            players.add(player);
        }
        qexec.close();
        return players;
    }


    public ArrayList<MartialArtistBean>  doRetrieve30RandomMartialArtist() {
        ArrayList<MartialArtistBean> fighters = new ArrayList<>();

        String q = "PREFIX db: <http://dbpedia.org/> \n" +
                "                PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
                "                PREFIX dbo: <http://dbpedia.org/ontology/> \n" +
                "                PREFIX dbp: <http://dbpedia.org/property/> \n" +
                "                PREFIX myonto: <http://www.semanticweb.org/gerardodellobuono/ontologies/2022/6/MMATechniquesProject#> \n" +
                "                SELECT DISTINCT ?individual ?name ?currWeightClassURI ?currWeightClass ?weight ?height ?thumbnail \n" +
                "                WHERE { \n" +
                "                SERVICE <http://dbpedia.org/sparql> { \n" +
                "                ?individual a dbo:MartialArtist .\n" +
                "    \t\t\t?individual dbp:name ?name .\n" +
                "                ?individual dbo:weight ?weight .\n" +
                "    \t\t\t?individual dbo:height ?height . \n" +
                "    \t\t\t?individual dbo:thumbnail ?thumbnail .\n" +
                "    \t\t\t?individual dbp:weightClass ?currWeightClassURI .\n" +
                "    \t\t\t?currWeightClassURI rdfs:label ?currWeightClass.\n" +
                "\t\t\t    FILTER(LANG(?currWeightClass) = 'en')\n" +
                "                } \n" +
                "                    ?player a dbo:MartialArtist .  \n" +
                "                    ?player rdfs:seeAlso ?individual . \n" +
                "                } \n" +
                "                GROUP BY ?individual ?name ?currWeightClassURI ?currWeightClass ?weight ?height ?thumbnail\n" +
                "                LIMIT 30\n";

        Query query = QueryFactory.create(q);

        // Esecuzione della querye cattura dei risultati
        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint2, query);
        ResultSet results = qexec.execSelect();
        while (results.hasNext()) {
            QuerySolution qSolution = results.nextSolution();
            MartialArtistBean fighter = new MartialArtistBean();
            fighter.setUri(qSolution.getResource("individual").getURI());
            fighter.setName(qSolution.getLiteral("name").getString());
            fighter.setCurrWeightClassUri(qSolution.getResource("currWeightClassURI").getURI());
            fighter.setCurrWeightClass(qSolution.getLiteral("currWeightClass").getString());
            
            char weight = qSolution.getLiteral("weight").getString().charAt(0);
            if(weight=='1') {
            	fighter.setWeight(qSolution.getLiteral("weight").getString().substring(0, 3));
            } else {
            	fighter.setWeight(qSolution.getLiteral("weight").getString().substring(0, 2));
            }
            fighter.setHeight(qSolution.getLiteral("height").getString().substring(0, 4));
            fighter.setThumbnail(qSolution.getResource("thumbnail").getURI());
            fighters.add(fighter);
        }
        qexec.close();
        return fighters;
    }

    public ArrayList<FootBallTeamBean> doRetrieveStatsFootballTeamWithMaxAvgAndMinimunOverall() {
        ArrayList<FootBallTeamBean> result = new ArrayList<>();


        String q = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX db: <http://dbpedia.org/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "PREFIX myonto: <http://www.semanticweb.org/tanucc/ontologies/2022/4/skillsFootball>\n" +
                "\n" +
                "SELECT DISTINCT ?currClubURI ?clubn ?currClubThumbnail (CEIL(AVG(?overall)) AS ?avg_overall) (MIN(?overall) AS ?min_overall) (MAX(?overall) AS ?max_overall) \n" +
                "WHERE {\n" +
                "      SERVICE <http://dbpedia.org/sparql> {\n" +
                "    ?individual a dbo:SoccerPlayer .\n" +
                "    ?individual dbp:currentclub ?currClubURI .\n" +
                "    ?currClubURI dbo:thumbnail ?currClubThumbnail .\n" +
                "    ?currClubURI dbp:clubname ?clubn.\n" +
                "  }\n" +
                "    ?player a dbo:SoccerPlayer .\n" +
                "  \t?player myonto:has_overall ?overall .\n" +
                "  \t?player rdfs:seeAlso ?individual .\n" +
                "}\n" +
                "GROUP BY ?currClubURI ?currClubThumbnail ?clubn\n" +
                "HAVING (MAX(?overall) != MIN(?overall))\n" +
                "ORDER BY DESC(?avg_overall)\n" +
                "LIMIT 30\n";

        Query query = QueryFactory.create(q);

        // Esecuzione della querye cattura dei risultati
        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query);
        ResultSet results = qexec.execSelect();
        while (results.hasNext()) {
            FootBallTeamBean iTeam = new FootBallTeamBean();
            QuerySolution qSolution = results.nextSolution();
            iTeam.setUri(qSolution.getResource("currClubURI").getURI());
            iTeam.setName(qSolution.getLiteral("clubn").getString());
            iTeam.setThumbnail(qSolution.getResource("currClubThumbnail").getURI());
            iTeam.setAvg_overall(qSolution.getLiteral("avg_overall").getInt());
            iTeam.setMax_overall(qSolution.getLiteral("max_overall").getInt());
            iTeam.setMin_overall(qSolution.getLiteral("min_overall").getInt());
            result.add(iTeam);
        }
        qexec.close();
        return result;
    }














    public ArrayList<KickboxingTechniqueBean> doRetrieveKickboxingTechniquesWithNameAndMartialArtist() {
        ArrayList<KickboxingTechniqueBean> result = new ArrayList<>();

        String q = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n" +
                "PREFIX db: <http://dbpedia.org/> \n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
                "PREFIX dbo: <http://dbpedia.org/ontology/> \n" +
                "PREFIX dbp: <http://dbpedia.org/property/> \n" +
                "PREFIX myonto: <http://www.semanticweb.org/gerardodellobuono/ontologies/2022/6/MMATechniquesProject#> \n" +
                "                 \n" +
                "SELECT DISTINCT ?kickboxing_technique ?techniqueName ?techniqueType ?techniqueDescription (GROUP_CONCAT(?fighter; SEPARATOR=\", \") AS ?fighters) \n" +
                "(GROUP_CONCAT(?fullname;SEPARATOR=\",\") AS ?fullnames) \n" +
                "WHERE { \n" +
                "\t?kickboxing_technique a myonto:kickboxing_techniques. \n" +
                "\t?kickboxing_technique myonto:kickboxing_technique_practiced_by ?fighter. \n" +
                "\t?fighter dbp:fullname ?fullname. \n" +
                "\t?kickboxing_technique myonto:techniqueType ?techniqueType. \n" +
                "\t?kickboxing_technique myonto:techniqueName ?techniqueName. \n" +
                "\t?kickboxing_technique myonto:techniqueDescription ?techniqueDescription. \n" +
                "} \n" +
                "GROUP BY ?kickboxing_technique ?techniqueName ?techniqueType ?techniqueDescription \n" +
                "ORDER BY ASC(?techniqueName)\n";

        Query query = QueryFactory.create(q);

        // Esecuzione della querye cattura dei risultati
        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint2, query);
        ResultSet results = qexec.execSelect();
        while (results.hasNext()) {
            KickboxingTechniqueBean skill_row = new KickboxingTechniqueBean();
            QuerySolution qSolution = results.nextSolution();
            ArrayList<MartialArtistBean> soccerPlayers = new ArrayList<>();
            String[] resource = qSolution.getLiteral("fighters").getString().split(",");
            String[] full_name = qSolution.getLiteral("fullnames").getString().split(",");
            for(int i=0; i<resource.length;i++){
                MartialArtistBean iPlayer = new MartialArtistBean();
                iPlayer.setUri(resource[i]);
                iPlayer.setName(full_name[i]);
                soccerPlayers.add(i,iPlayer);
            }
            skill_row.setPlayers(soccerPlayers);
            skill_row.setNome(qSolution.getLiteral("techniqueName").getString());
            skill_row.setTipo(qSolution.getLiteral("techniqueType").getString());
            skill_row.setDescrizione(qSolution.getLiteral("techniqueDescription").getString());
            result.add(skill_row);

        }
        qexec.close();
        return result;
    } 


    public ArrayList<KarateTechniqueBean> doRetrieveKarateTechniquesWithNameAndMartialArtist() {
        ArrayList<KarateTechniqueBean> result = new ArrayList<>();

        String q = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n" +
                "PREFIX db: <http://dbpedia.org/> \n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
                "PREFIX dbo: <http://dbpedia.org/ontology/> \n" +
                "PREFIX dbp: <http://dbpedia.org/property/> \n" +
                "PREFIX myonto: <http://www.semanticweb.org/gerardodellobuono/ontologies/2022/6/MMATechniquesProject#> \n" +
                "                 \n" +
                "SELECT DISTINCT ?karate_technique ?techniqueName ?techniqueType ?techniqueDescription (GROUP_CONCAT(?fighter; SEPARATOR=\", \") AS ?fighters) \n" +
                "(GROUP_CONCAT(?fullname;SEPARATOR=\",\") AS ?fullnames) \n" +
                "WHERE { \n" +
                "\t?karate_technique a myonto:karate_techniques. \n" +
                "\t?karate_technique myonto:karate_technique_practiced_by ?fighter. \n" +
                "\t?fighter dbp:fullname ?fullname. \n" +
                "\t?karate_technique myonto:techniqueType ?techniqueType. \n" +
                "\t?karate_technique myonto:techniqueName ?techniqueName. \n" +
                "\t?karate_technique myonto:techniqueDescription ?techniqueDescription. \n" +
                "} \n" +
                "GROUP BY ?karate_technique ?techniqueName ?techniqueType ?techniqueDescription \n" +
                "ORDER BY ASC(?techniqueName)\n";

        Query query = QueryFactory.create(q);

        // Esecuzione della querye cattura dei risultati
        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint2, query);
        ResultSet results = qexec.execSelect();
        while (results.hasNext()) {
            KarateTechniqueBean skill_row = new KarateTechniqueBean();
            QuerySolution qSolution = results.nextSolution();
            ArrayList<MartialArtistBean> soccerPlayers = new ArrayList<>();
            String[] resource = qSolution.getLiteral("fighters").getString().split(",");
            String[] full_name = qSolution.getLiteral("fullnames").getString().split(",");
            for(int i=0; i<resource.length;i++){
                MartialArtistBean iPlayer = new MartialArtistBean();
                iPlayer.setUri(resource[i]);
                iPlayer.setName(full_name[i]);
                soccerPlayers.add(i,iPlayer);
            }
            skill_row.setPlayers(soccerPlayers);
            skill_row.setNome(qSolution.getLiteral("techniqueName").getString());
            skill_row.setTipo(qSolution.getLiteral("techniqueType").getString());
            skill_row.setDescrizione(qSolution.getLiteral("techniqueDescription").getString());
            result.add(skill_row);

        }
        qexec.close();
        return result;
    }



    public ArrayList<JudoTechniqueBean> doRetrieveJudoTechniquesWithNameAndMartialArtist() {
        ArrayList<JudoTechniqueBean> result = new ArrayList<>();

        String q = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n" +
                "PREFIX db: <http://dbpedia.org/> \n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
                "PREFIX dbo: <http://dbpedia.org/ontology/> \n" +
                "PREFIX dbp: <http://dbpedia.org/property/> \n" +
                "PREFIX myonto: <http://www.semanticweb.org/gerardodellobuono/ontologies/2022/6/MMATechniquesProject#> \n" +
                "                 \n" +
                "SELECT DISTINCT ?judo_technique ?techniqueName ?techniqueType ?techniqueDescription (GROUP_CONCAT(?fighter; SEPARATOR=\", \") AS ?fighters) \n" +
                "(GROUP_CONCAT(?fullname;SEPARATOR=\",\") AS ?fullnames) \n" +
                "WHERE { \n" +
                "\t?judo_technique a myonto:judo_techniques. \n" +
                "\t?judo_technique myonto:judo_technique_practiced_by ?fighter. \n" +
                "\t?fighter dbp:fullname ?fullname. \n" +
                "\t?judo_technique myonto:techniqueType ?techniqueType. \n" +
                "\t?judo_technique myonto:techniqueName ?techniqueName. \n" +
                "\t?judo_technique myonto:techniqueDescription ?techniqueDescription. \n" +
                "} \n" +
                "GROUP BY ?judo_technique ?techniqueName ?techniqueType ?techniqueDescription \n" +
                "ORDER BY ASC(?techniqueName)\n";

        Query query = QueryFactory.create(q);

        // Esecuzione della querye cattura dei risultati
        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint2, query);
        ResultSet results = qexec.execSelect();
        while (results.hasNext()) {
            JudoTechniqueBean skill_row = new JudoTechniqueBean();
            QuerySolution qSolution = results.nextSolution();
            ArrayList<MartialArtistBean> soccerPlayers = new ArrayList<>();
            String[] resource = qSolution.getLiteral("fighters").getString().split(",");
            String[] full_name = qSolution.getLiteral("fullnames").getString().split(",");
            for(int i=0; i<resource.length;i++){
                MartialArtistBean iPlayer = new MartialArtistBean();
                iPlayer.setUri(resource[i]);
                iPlayer.setName(full_name[i]);
                soccerPlayers.add(i,iPlayer);
            }
            skill_row.setPlayers(soccerPlayers);
            skill_row.setNome(qSolution.getLiteral("techniqueName").getString());
            skill_row.setTipo(qSolution.getLiteral("techniqueType").getString());
            skill_row.setDescrizione(qSolution.getLiteral("techniqueDescription").getString());
            result.add(skill_row);

        }
        qexec.close();
        return result;
    }



    public ArrayList<GrapplingTechniqueBean> doRetrieveGrapplingTechniquesWithNameAndMartialArtist() {
        ArrayList<GrapplingTechniqueBean> result = new ArrayList<>();

        String q = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n" +
                "PREFIX db: <http://dbpedia.org/> \n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
                "PREFIX dbo: <http://dbpedia.org/ontology/> \n" +
                "PREFIX dbp: <http://dbpedia.org/property/> \n" +
                "PREFIX myonto: <http://www.semanticweb.org/gerardodellobuono/ontologies/2022/6/MMATechniquesProject#> \n" +
                "                 \n" +
                "SELECT DISTINCT ?grappling_technique ?techniqueName ?techniqueType ?techniqueDescription (GROUP_CONCAT(?fighter; SEPARATOR=\", \") AS ?fighters) \n" +
                "(GROUP_CONCAT(?fullname;SEPARATOR=\",\") AS ?fullnames) \n" +
                "WHERE { \n" +
                "\t?grappling_technique a myonto:grappling_techniques. \n" +
                "\t?grappling_technique myonto:grappling_technique_practiced_by ?fighter. \n" +
                "\t?fighter dbp:fullname ?fullname. \n" +
                "\t?grappling_technique myonto:techniqueType ?techniqueType. \n" +
                "\t?grappling_technique myonto:techniqueName ?techniqueName. \n" +
                "\t?grappling_technique myonto:techniqueDescription ?techniqueDescription. \n" +
                "} \n" +
                "GROUP BY ?grappling_technique ?techniqueName ?techniqueType ?techniqueDescription \n" +
                "ORDER BY ASC(?techniqueName)\n";

        Query query = QueryFactory.create(q);

        // Esecuzione della querye cattura dei risultati
        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint2, query);
        ResultSet results = qexec.execSelect();
        while (results.hasNext()) {
            GrapplingTechniqueBean skill_row = new GrapplingTechniqueBean();
            QuerySolution qSolution = results.nextSolution();
            ArrayList<MartialArtistBean> soccerPlayers = new ArrayList<>();
            String[] resource = qSolution.getLiteral("fighters").getString().split(",");
            String[] full_name = qSolution.getLiteral("fullnames").getString().split(",");
            for(int i=0; i<resource.length;i++){
                MartialArtistBean iPlayer = new MartialArtistBean();
                iPlayer.setUri(resource[i]);
                iPlayer.setName(full_name[i]);
                soccerPlayers.add(i,iPlayer);
            }
            skill_row.setPlayers(soccerPlayers);
            skill_row.setNome(qSolution.getLiteral("techniqueName").getString());
            skill_row.setTipo(qSolution.getLiteral("techniqueType").getString());
            skill_row.setDescrizione(qSolution.getLiteral("techniqueDescription").getString());
            result.add(skill_row);

        }
        qexec.close();
        return result;
    }


    public ArrayList<SoccerPlayerBean> doRetrieveBallonDOr() {
        ArrayList<SoccerPlayerBean> result = new ArrayList<>();


        String q = "PREFIX dct: <http://purl.org/dc/terms/>\n" +
                "PREFIX db: <http://dbpedia.org/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "PREFIX myonto: <http://www.semanticweb.org/tanucc/ontologies/2022/4/skillsFootball>\n" +
                "\n" +
                "SELECT DISTINCT ?individual ?name ?position ?currClub ?currClubURI ?currClubThumbnail ?overall ?thumbnail_player ?label_ballondor ?comment_ballondor\n" +
                "WHERE {\n" +
                "      SERVICE <http://dbpedia.org/sparql> {\n" +
                "    ?individual a dbo:SoccerPlayer .\n" +
                "    ?individual dbp:name ?name .\n" +
                "    ?individual dbp:currentclub ?currClubURI .\n" +
                "    ?currClubURI rdfs:label ?currClub.\n" +
                "    ?currClubURI dbo:thumbnail ?currClubThumbnail .\n" +
                "    ?individual dbo:thumbnail ?thumbnail_player .\n" +
                "    ?individual dbo:position ?posURI .\n" +
                "    ?posURI rdfs:label ?position .\n" +
                "    ?individual dbo:wikiPageWikiLink <http://dbpedia.org/resource/Category:Ballon_d'Or_winners>.\n" +
                "    ?individual dbo:wikiPageWikiLink ?ballondor .\n" +
                "    ?ballondor rdfs:comment ?comment_ballondor.\n" +
                "    ?ballondor rdfs:label ?label_ballondor.\n" +
                "    FILTER(LANG(?currClub) = 'en')\n" +
                "    FILTER(LANG(?position) = 'it')\n" +
                "    FILTER(LANG(?comment_ballondor) = 'it')\n" +
                "    FILTER(LANG(?label_ballondor) = 'it')\n" +
                "    FILTER(?ballondor = <http://dbpedia.org/resource/FIFA_Ballon_d'Or>)\n" +
                "  }\n" +
                "    ?player a dbo:SoccerPlayer .\n" +
                "  \t?player myonto:has_overall ?overall .\n" +
                "  \t?player rdfs:seeAlso ?individual .\n" +
                "}\n" +
                "ORDER BY DESC(?overall)\n" +
                "LIMIT 30\n";

        Query query = QueryFactory.create(q);

        // Esecuzione della querye cattura dei risultati
        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query);
        ResultSet results = qexec.execSelect();
        while (results.hasNext()) {
            QuerySolution qSolution = results.nextSolution();
            SoccerPlayerBean spCurrent = new SoccerPlayerBean();
            FootBallTeamBean ftCurrent = new FootBallTeamBean();
            spCurrent.setUri(qSolution.getResource("individual").getURI());
            spCurrent.setName(qSolution.getLiteral("name").getString());
            spCurrent.setPosition(qSolution.getLiteral("position").getString());
            ftCurrent.setName(qSolution.getLiteral("currClub").getString());
            ftCurrent.setUri(qSolution.getResource("currClubURI").getURI());
            ftCurrent.setThumbnail(qSolution.getResource("currClubThumbnail").getURI());
            spCurrent.setFootballTeamBean(ftCurrent);
            spCurrent.setOverall(qSolution.getLiteral("overall").getInt());
            spCurrent.setThumbnail(qSolution.getResource("thumbnail_player").getURI());
            spCurrent.setLabel_BallonDOr(qSolution.getLiteral("label_ballondor").getString());
            spCurrent.setComment_BallonDOr(qSolution.getLiteral("comment_ballondor").getString());
            result.add(spCurrent);
        }
        qexec.close();
        return result;
    }
    
    
    public ArrayList<MartialArtistBean> doRetrieveTheUltimateFighter() {
        ArrayList<MartialArtistBean> result = new ArrayList<>();


        String q = "PREFIX dct: <http://purl.org/dc/terms/> \n"
        		+ "PREFIX db: <http://dbpedia.org/> \n"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
        		+ "PREFIX dbo: <http://dbpedia.org/ontology/> \n"
        		+ "PREFIX dbp: <http://dbpedia.org/property/> \n"
        		+ "PREFIX myonto: <http://www.semanticweb.org/gerardodellobuono/ontologies/2022/6/MMATechniquesProject#> \n"
        		+ " \n"
        		+ "SELECT DISTINCT ?individual ?name ?weight ?weightClass ?weightClassURI?height ?weightClass ?thumbnail_name ?label_theultimatefighter ?thumbnail_theultimatefighter ?comment_theultimatefighter \n"
        		+ "WHERE {\n"
        		+ "      SERVICE <http://dbpedia.org/sparql> { \n"
        		+ "    	?individual a dbo:MartialArtist . \n"
        		+ "    	?individual dbp:name ?name . \n"
        		+ "		?individual dbo:height ?height .\n"
        		+ "		?individual dbo:weight ?weight .\n"
        		+ "		\n"
        		+ "    	?individual dbo:wikiPageWikiLink <http://dbpedia.org/resource/Category:The_Ultimate_Fighter_winners>. \n"
        		+ "    	?individual dbo:wikiPageWikiLink ?theultimatefighter .\n"
        		+ "		?theultimatefighter rdfs:label ?label_theultimatefighter.\n"
        		+ "		?theultimatefighter dbo:thumbnail ?thumbnail_theultimatefighter.\n"
        		+ "		?theultimatefighter rdfs:comment ?comment_theultimatefighter.\n"
        		+ "		OPTIONAL {\n"
        		+ "			?individual dbo:thumbnail ?thumbnail_name .\n"
        		+ "			?individual dbo:weight ?weight .\n"
        		+ "		}\n"
        		+ "		FILTER(LANG(?comment_theultimatefighter) = 'en') \n"
        		+ "		FILTER(LANG(?label_theultimatefighter) = 'en')\n"
        		+ "		FILTER(?theultimatefighter = <http://dbpedia.org/resource/The_Ultimate_Fighter>)\n"
        		+ "    } \n"
        		+ "\n"
        		+ "  ?player a dbo:MartialArtist . \n"
        		+ "  ?player rdfs:seeAlso ?individual . \n"
        		+ "\n"
        		+ "}\n"
        		+ "GROUP BY ?individual ?name ?weight ?weightClass ?weightClassURI ?height ?weightClass ?thumbnail_name ?label_theultimatefighter ?thumbnail_theultimatefighter ?comment_theultimatefighter\n"
        		+ "ORDER BY DESC(?name) \n"
        		+ "LIMIT 30";

        Query query = QueryFactory.create(q);

        // Esecuzione della querye cattura dei risultati
        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint2, query);
        ResultSet results = qexec.execSelect();
        while (results.hasNext()) {
            QuerySolution qSolution = results.nextSolution();
            MartialArtistBean spCurrent = new MartialArtistBean();
            
            spCurrent.setUri(qSolution.getResource("individual").getURI());
            spCurrent.setName(qSolution.getLiteral("name").getString());
            
            char weight = qSolution.getLiteral("weight").getString().charAt(0);
            if(weight=='1') {
            	spCurrent.setWeight(qSolution.getLiteral("weight").getString().substring(0, 3));
            } else {
            	spCurrent.setWeight(qSolution.getLiteral("weight").getString().substring(0, 2));
            }
            
            spCurrent.setHeight(qSolution.getLiteral("height").getString().substring(0, 4));
            if (qSolution.getResource("thumbnail_name") != null) {
            	spCurrent.setThumbnail(qSolution.getResource("thumbnail_name").getURI());
            } else {
            	spCurrent.setThumbnail("no thumbnail"); 
            }
            
            spCurrent.setLabel_BallonDOr(qSolution.getLiteral("label_theultimatefighter").getString());
            spCurrent.setComment_BallonDOr(qSolution.getLiteral("comment_theultimatefighter").getString());
            spCurrent.setThumbnailTheUltimateFighter(qSolution.getResource("thumbnail_theultimatefighter").getURI());
            result.add(spCurrent);
        }
        qexec.close();
        return result;
    }
    


    public SoccerPlayerBean doSpecificPlayer(String resourcePlayer) {

        SoccerPlayerBean player = new SoccerPlayerBean();
        FootBallTeamBean team = new FootBallTeamBean();
        ArrayList<SkillBean> skills = new ArrayList<SkillBean>();

        String q = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX db: <http://dbpedia.org/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "PREFIX myonto: <http://www.semanticweb.org/tanucc/ontologies/2022/4/skillsFootball>\n" +
                "\n" +
                "SELECT DISTINCT ?name (GROUP_CONCAT(?position;SEPARATOR=\",\") AS ?positions) ?currClub ?currClubURI ?currClubThumbnail ?thumbnail ?stats ?name_stat ?stats_individual ?comment ?type_stat ?descr_stat \n" +
                "WHERE {\n" +
                "  {\n" +
                "    {\n" +
                "    SERVICE <http://dbpedia.org/sparql> {\n" +
                "    " + resourcePlayer + " dbp:name ?name .\n" +
                "     " + resourcePlayer + " dbp:currentclub ?currClubURI .\n" +
                "    ?currClubURI rdfs:label ?currClub.\n" +
                "    ?currClubURI dbo:thumbnail ?currClubThumbnail .\n" +
                "    " + resourcePlayer + " dbo:position ?posURI .\n" +
                "    ?posURI rdfs:label ?position .\n" +
                "    FILTER(LANG(?currClub) = 'en')\n" +
                "    FILTER(LANG(?position) = 'it')\n" +
                "      }\n" +
                "    }\n" +
                "    OPTIONAL\n" +
                "    {\n" +
                "      SERVICE <http://dbpedia.org/sparql> {\n" +
                "      \t" + resourcePlayer + " dbo:thumbnail ?thumbnail .\n" +
                "      }\n" +
                "    }\n" +
                "    OPTIONAL\n" +
                "        {\n" +
                "      SERVICE <http://dbpedia.org/sparql> {\n" +
                "      \t" + resourcePlayer + " rdfs:comment ?comment .\n" +
                "            FILTER((LANG(?comment) = 'it'))\n" +
                "      }\n" +
                "    }\n" +
                "}\n" +
                "  UNION\n" +
                "  {\n" +
                "    ?player a dbo:SoccerPlayer .\n" +
                "  \t?player ?p ?stats .\n" +
                "  \t?p rdfs:seeAlso ?stats_individual.\n" +
                "  \t?stats_individual myonto:has_name ?name_stat .\n" +
                "    ?stats_individual myonto:is_type ?type_stat .\n" +
                "    ?stats_individual myonto:has_description ?descr_stat .\n" +
                "  \t?player rdfs:seeAlso " + resourcePlayer + ".\n" +
                "}\n" +
                "}\n" +
                "\n" +
                "GROUP BY ?name ?currClub ?currClubURI ?currClubThumbnail ?thumbnail ?stats ?name_stat ?stats_individual ?comment ?type_stat ?descr_stat\n" +
                "ORDER BY DESC(?name)\n";

        Query query = QueryFactory.create(q);

        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query);
        ResultSet results = qexec.execSelect();
        while (results.hasNext()) {
            QuerySolution qSolution = results.nextSolution();
            if (qSolution.getLiteral("name") == null) {
                SkillBean skill = new SkillBean();
                skill.setNome(qSolution.getLiteral("name_stat").getString());
                skill.setTipo(qSolution.getLiteral("type_stat").getString());
                skill.setDescrizione(qSolution.getLiteral("descr_stat").getString());
                skill.setValSkill(qSolution.getLiteral("stats").getInt());
                skill.setUri(qSolution.getResource("stats_individual").getURI());
                skills.add(skill);
            } else {
                player.setName(qSolution.getLiteral("name").getString());
                player.setPosition(qSolution.getLiteral("positions").getString());
                team.setName(qSolution.getLiteral("currClub").getString());
                team.setUri(qSolution.getResource("currClubURI").getURI());
                team.setThumbnail(qSolution.getResource("currClubThumbnail").getURI());
                if (qSolution.getResource("thumbnail") != null) {
                    player.setThumbnail(qSolution.getResource("thumbnail").getURI());
                } else {
                    player.setThumbnail("no thumbnail");
                }
                if (qSolution.getLiteral("comment") != null) {
                    player.setComment(qSolution.getLiteral("comment").getString());
                } else {
                    player.setComment("");
                }
                player.setFootballTeamBean(team);
            }
        }
        qexec.close();
        player.setSkills(skills);

        return player;
    }



    public MartialArtistBean doSpecificMartialArtist(String resourcePlayer) {

        MartialArtistBean player = new MartialArtistBean();
        ArrayList<KickboxingTechniqueBean> skills = new ArrayList<KickboxingTechniqueBean>();

        String q = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n" +
                "PREFIX db: <http://dbpedia.org/> \n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
                "PREFIX dbo: <http://dbpedia.org/ontology/> \n" +
                "PREFIX dbp: <http://dbpedia.org/property/> \n" +
                "PREFIX myonto: <http://www.semanticweb.org/gerardodellobuono/ontologies/2022/6/MMATechniquesProject#> \n" +
                " \n" +
                "SELECT DISTINCT ?name ?weight ?height ?currTeam ?currTeamURI ?thumbnail ?stats ?stats_individual ?name_stat ?descr_stat ?comment\n" +
                "WHERE { \n" +
                "  { \n" +
                "    { \n" +
                "    SERVICE <http://dbpedia.org/sparql> { \n" +
                "      " + resourcePlayer + "   dbp:name ?name .\n" +
                "      " + resourcePlayer + " dbo:weight ?weight .\n" +
                "      " + resourcePlayer + " dbo:height ?height .\n" +
                "      }\n" +
                "\tOPTIONAL \n" +
                "    { \n" +
                "      SERVICE <http://dbpedia.org/sparql> { \n" +
                "        " + resourcePlayer + "   dbp:team ?currTeamURI .\n" +
                "\t  \t?currTeamURI rdfs:label ?currTeam.\n" +
                "\t  \tFILTER(LANG(?currTeam) = 'en')\n" +
                "      } \n" +
                "    }\n" +
                "\tOPTIONAL \n" +
                "    { \n" +
                "      SERVICE <http://dbpedia.org/sparql> { \n" +
                "        " + resourcePlayer + "   dbp:team ?currTeam .\n" +
                "\t\tFILTER(LANG(?currTeam) = 'en')  \n" +
                "      } \n" +
                "    }\n" +
                "\tOPTIONAL \n" +
                "    { \n" +
                "      SERVICE <http://dbpedia.org/sparql> { \n" +
                "        " + resourcePlayer + "   dbo:thumbnail ?thumbnail . \n" +
                "      } \n" +
                "    } \n" +
                "    OPTIONAL \n" +
                "    { \n" +
                "      SERVICE <http://dbpedia.org/sparql> { \n" +
                "        " + resourcePlayer + "   rdfs:comment ?comment .\n" +
                "\t\tFILTER(LANG(?comment) = 'en')\n" +
                "      } \n" +
                "    } \n" +
                "    } \n" +
                "\t\n" +
                "  }\n" +
                "  UNION \n" +
                "  { \n" +
                "  \t\t?player a dbo:MartialArtist . \n" +
                "  \t\t?player ?p ?stats . \n" +
                "  \t\t?p rdfs:seeAlso ?stats_individual. \n" +
                "\t\t?stats_individual myonto:propertyName ?name_stat .\n" +
                "\t\t?stats_individual myonto:propertyDescription ?descr_stat .\n" +
                "  \t\t?player rdfs:seeAlso   " + resourcePlayer + "  .\n" +
                "} \n" +
                "} \n" +
                " \n" +
                "GROUP BY ?name ?weight ?height ?currTeam ?currTeamURI ?thumbnail ?stats ?stats_individual ?name_stat ?descr_stat ?comment\n" +
                "ORDER BY DESC(?name)";



        Query query = QueryFactory.create(q);

        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint2, query);
        ResultSet results = qexec.execSelect();
        while (results.hasNext()) {
            QuerySolution qSolution = results.nextSolution();
            if (qSolution.getLiteral("name") == null) {
                KickboxingTechniqueBean skill = new KickboxingTechniqueBean();
                skill.setNome(qSolution.getLiteral("name_stat").getString());
                skill.setDescrizione(qSolution.getLiteral("descr_stat").getString());
                skill.setValSkill(qSolution.getLiteral("stats").getString());
                skill.setUri(qSolution.getResource("stats_individual").getURI());
                skills.add(skill);
            } else {
                player.setName(qSolution.getLiteral("name").getString());
                player.setTeam(qSolution.getLiteral("currTeam").getString());
                if (qSolution.getResource("currTeamURI") != null) {
                	player.setTeamUri(qSolution.getResource("currTeamURI").getURI());
                } else {
                	player.setTeamUri("");
                }
                char weight = qSolution.getLiteral("weight").getString().charAt(0);
                if(weight=='1') {
                	player.setWeight(qSolution.getLiteral("weight").getString().substring(0, 3));
                } else {
                	player.setWeight(qSolution.getLiteral("weight").getString().substring(0, 2));
                }
                
                player.setHeight(qSolution.getLiteral("height").getString().substring(0, 4));
                if (qSolution.getResource("thumbnail") != null) {
                    player.setThumbnail(qSolution.getResource("thumbnail").getURI());
                } else {
                    player.setThumbnail("no thumbnail");
                }
                if (qSolution.getLiteral("comment") != null) {
                    player.setComment(qSolution.getLiteral("comment").getString());
                } else {
                    player.setComment("");
                }
            }
        }
        qexec.close();
        player.setSkills(skills);

        return player;
    }

    public SoccerPlayerBean2 doSpecificMartialArtist2(String resourcePlayer) {

        SoccerPlayerBean2 player = new SoccerPlayerBean2();

        player.setName("ciao");
        return player;
    }

    public ArrayList<SkillBean> doSpecialSkillPlayer(String resourcePlayer) {

        SoccerPlayerBean player = new SoccerPlayerBean();
        FootBallTeamBean team = new FootBallTeamBean();
        ArrayList<SkillBean> skills = new ArrayList<SkillBean>();

        String q = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX db: <http://dbpedia.org/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "PREFIX myonto: <http://www.semanticweb.org/tanucc/ontologies/2022/4/skillsFootball>\n" +
                "\n" +
                "SELECT DISTINCT ?player (GROUP_CONCAT(?special_skillURI;SEPARATOR=\",\") AS ?special_skills_uri) (GROUP_CONCAT(?special_skill;SEPARATOR=\",\") AS ?special_skills) (GROUP_CONCAT(?special_skill_descr;SEPARATOR=\"§\") AS ?special_skills_descr)\n" +
                "WHERE {\n" +
                "  ?player a dbo:SoccerPlayer .\n" +
                "  ?player rdfs:seeAlso " + resourcePlayer + ".\n" +
                "  ?player myonto:has_special_skill ?special_skillURI .\n" +
                "  ?special_skillURI myonto:has_name ?special_skill .\n" +
                "  ?special_skillURI myonto:has_description ?special_skill_descr .\n" +
                "}\n" +
                "GROUP BY ?player";

        Query query = QueryFactory.create(q);

        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query);
        ResultSet results = qexec.execSelect();
        while (results.hasNext()) {
            QuerySolution qSolution = results.nextSolution();


            String skillNames = qSolution.getLiteral("special_skills").getString();
            String skillDescrs = qSolution.getLiteral("special_skills_descr").getString();
            String skillUris = qSolution.getLiteral("special_skills_uri").getString();

            String[] splittedSkillNames = skillNames.split(",");
            String[] splittedSkillUris = skillUris.split(",");
            String[] splittedSkillDescrs = skillDescrs.split("§");
            for (int i = 0; splittedSkillNames.length > i; i++) {
                SkillBean skill = new SkillBean();
                skill.setNome(splittedSkillNames[i]);
                skill.setDescrizione(splittedSkillDescrs[i]);
                skill.setUri(splittedSkillUris[i]);
                skills.add(skill);
            }
        }
        qexec.close();

        return skills;
    }


    public ArrayList<KickboxingTechniqueBean> doKickboxingSkillMartialArtist(String resourcePlayer) {

        ArrayList<KickboxingTechniqueBean> skills = new ArrayList<KickboxingTechniqueBean>();

        String q = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n" +
                "                PREFIX db: <http://dbpedia.org/> \n" +
                "                PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
                "                PREFIX dbo: <http://dbpedia.org/ontology/> \n" +
                "                PREFIX dbp: <http://dbpedia.org/property/> \n" +
                "                PREFIX myonto: <http://www.semanticweb.org/gerardodellobuono/ontologies/2022/6/MMATechniquesProject#> \n" +
                "                 \n" +
                "                SELECT DISTINCT ?fighter (GROUP_CONCAT(?kickboxing_techniqueURI;SEPARATOR=\",\") AS ?kickboxing_techniques_uri) (GROUP_CONCAT(?kickboxing_technique;SEPARATOR=\",\") AS ?kickboxing_techniques) (GROUP_CONCAT(?kickboxing_technique_descr;SEPARATOR=\"§\") AS ?kickboxing_techniques_descr) \n" +
                "                WHERE { \n" +
                "                  ?fighter a dbo:MartialArtist . \n" +
                "                  ?fighter rdfs:seeAlso   " + resourcePlayer + " . \n" +
                "                  ?fighter myonto:has_kickboxing_technique ?kickboxing_techniqueURI . \n" +
                "                  ?kickboxing_techniqueURI myonto:techniqueName ?kickboxing_technique . \n" +
                "                  ?kickboxing_techniqueURI myonto:techniqueDescription ?kickboxing_technique_descr . \n" +
                "                } \n" +
                "                GROUP BY ?fighter";

        Query query = QueryFactory.create(q);

        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint2, query);
        ResultSet results = qexec.execSelect();
        while (results.hasNext()) {
            QuerySolution qSolution = results.nextSolution();


            String skillNames = qSolution.getLiteral("kickboxing_techniques").getString();
            String skillDescrs = qSolution.getLiteral("kickboxing_techniques_descr").getString();
            String skillUris = qSolution.getLiteral("kickboxing_techniques_uri").getString();

            String[] splittedSkillNames = skillNames.split(",");
            String[] splittedSkillUris = skillUris.split(",");
            String[] splittedSkillDescrs = skillDescrs.split("§");
            for (int i = 0; splittedSkillNames.length > i; i++) {
                KickboxingTechniqueBean skill = new KickboxingTechniqueBean();
                skill.setNome(splittedSkillNames[i]);
                System.out.println(splittedSkillNames[i]);
                skill.setDescrizione(splittedSkillDescrs[i]);
                skill.setUri(splittedSkillUris[i]);
                skills.add(skill);
            }
        }
        qexec.close();

        return skills;
    }
    
    
    public ArrayList<KarateTechniqueBean> doKarateSkillMartialArtist(String resourcePlayer) {

        ArrayList<KarateTechniqueBean> skills = new ArrayList<KarateTechniqueBean>();

        String q = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n" +
                "                PREFIX db: <http://dbpedia.org/> \n" +
                "                PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
                "                PREFIX dbo: <http://dbpedia.org/ontology/> \n" +
                "                PREFIX dbp: <http://dbpedia.org/property/> \n" +
                "                PREFIX myonto: <http://www.semanticweb.org/gerardodellobuono/ontologies/2022/6/MMATechniquesProject#> \n" +
                "                 \n" +
                "                SELECT DISTINCT ?fighter (GROUP_CONCAT(?karate_techniqueURI;SEPARATOR=\",\") AS ?karate_techniques_uri) (GROUP_CONCAT(?karate_technique;SEPARATOR=\",\") AS ?karate_techniques) (GROUP_CONCAT(?karate_technique_descr;SEPARATOR=\"§\") AS ?karate_techniques_descr) \n" +
                "                WHERE { \n" +
                "                  ?fighter a dbo:MartialArtist . \n" +
                "                  ?fighter rdfs:seeAlso   " + resourcePlayer + " . \n" +
                "                  ?fighter myonto:has_karate_technique ?karate_techniqueURI . \n" +
                "                  ?karate_techniqueURI myonto:techniqueName ?karate_technique . \n" +
                "                  ?karate_techniqueURI myonto:techniqueDescription ?karate_technique_descr . \n" +
                "                } \n" +
                "                GROUP BY ?fighter";

        Query query = QueryFactory.create(q);

        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint2, query);
        ResultSet results = qexec.execSelect();
        while (results.hasNext()) {
            QuerySolution qSolution = results.nextSolution();


            String skillNames = qSolution.getLiteral("karate_techniques").getString();
            String skillDescrs = qSolution.getLiteral("karate_techniques_descr").getString();
            String skillUris = qSolution.getLiteral("karate_techniques_uri").getString();

            String[] splittedSkillNames = skillNames.split(",");
            String[] splittedSkillUris = skillUris.split(",");
            String[] splittedSkillDescrs = skillDescrs.split("§");
            for (int i = 0; splittedSkillNames.length > i; i++) {
            	KarateTechniqueBean skill = new KarateTechniqueBean();
                skill.setNome(splittedSkillNames[i]);
                System.out.println(splittedSkillNames[i]);
                skill.setDescrizione(splittedSkillDescrs[i]);
                skill.setUri(splittedSkillUris[i]);
                skills.add(skill);
            }
        }
        qexec.close();

        return skills;
    }
    
    
    public ArrayList<JudoTechniqueBean> doJudoSkillMartialArtist(String resourcePlayer) {

        ArrayList<JudoTechniqueBean> skills = new ArrayList<JudoTechniqueBean>();

        String q = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n" +
                "                PREFIX db: <http://dbpedia.org/> \n" +
                "                PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
                "                PREFIX dbo: <http://dbpedia.org/ontology/> \n" +
                "                PREFIX dbp: <http://dbpedia.org/property/> \n" +
                "                PREFIX myonto: <http://www.semanticweb.org/gerardodellobuono/ontologies/2022/6/MMATechniquesProject#> \n" +
                "                 \n" +
                "                SELECT DISTINCT ?fighter (GROUP_CONCAT(?judo_techniqueURI;SEPARATOR=\",\") AS ?judo_techniques_uri) (GROUP_CONCAT(?judo_technique;SEPARATOR=\",\") AS ?judo_techniques) (GROUP_CONCAT(?judo_technique_descr;SEPARATOR=\"§\") AS ?judo_techniques_descr) \n" +
                "                WHERE { \n" +
                "                  ?fighter a dbo:MartialArtist . \n" +
                "                  ?fighter rdfs:seeAlso   " + resourcePlayer + " . \n" +
                "                  ?fighter myonto:has_judo_technique ?judo_techniqueURI . \n" +
                "                  ?judo_techniqueURI myonto:techniqueName ?judo_technique . \n" +
                "                  ?judo_techniqueURI myonto:techniqueDescription ?judo_technique_descr . \n" +
                "                } \n" +
                "                GROUP BY ?fighter";

        Query query = QueryFactory.create(q);

        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint2, query);
        ResultSet results = qexec.execSelect();
        while (results.hasNext()) {
            QuerySolution qSolution = results.nextSolution();


            String skillNames = qSolution.getLiteral("judo_techniques").getString();
            String skillDescrs = qSolution.getLiteral("judo_techniques_descr").getString();
            String skillUris = qSolution.getLiteral("judo_techniques_uri").getString();

            String[] splittedSkillNames = skillNames.split(",");
            String[] splittedSkillUris = skillUris.split(",");
            String[] splittedSkillDescrs = skillDescrs.split("§");
            for (int i = 0; splittedSkillNames.length > i; i++) {
            	JudoTechniqueBean skill = new JudoTechniqueBean();
                skill.setNome(splittedSkillNames[i]);
                System.out.println(splittedSkillNames[i]);
                skill.setDescrizione(splittedSkillDescrs[i]);
                skill.setUri(splittedSkillUris[i]);
                skills.add(skill);
            }
        }
        qexec.close();

        return skills;
    }
    
    
    public ArrayList<GrapplingTechniqueBean> doGrapplingSkillMartialArtist(String resourcePlayer) {

        ArrayList<GrapplingTechniqueBean> skills = new ArrayList<GrapplingTechniqueBean>();

        String q = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n" +
                "                PREFIX db: <http://dbpedia.org/> \n" +
                "                PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
                "                PREFIX dbo: <http://dbpedia.org/ontology/> \n" +
                "                PREFIX dbp: <http://dbpedia.org/property/> \n" +
                "                PREFIX myonto: <http://www.semanticweb.org/gerardodellobuono/ontologies/2022/6/MMATechniquesProject#> \n" +
                "                 \n" +
                "                SELECT DISTINCT ?fighter (GROUP_CONCAT(?grappling_techniqueURI;SEPARATOR=\",\") AS ?grappling_techniques_uri) (GROUP_CONCAT(?grappling_technique;SEPARATOR=\",\") AS ?grappling_techniques) (GROUP_CONCAT(?grappling_technique_descr;SEPARATOR=\"§\") AS ?grappling_techniques_descr) \n" +
                "                WHERE { \n" +
                "                  ?fighter a dbo:MartialArtist . \n" +
                "                  ?fighter rdfs:seeAlso   " + resourcePlayer + " . \n" +
                "                  ?fighter myonto:has_grappling_technique ?grappling_techniqueURI . \n" +
                "                  ?grappling_techniqueURI myonto:techniqueName ?grappling_technique . \n" +
                "                  ?grappling_techniqueURI myonto:techniqueDescription ?grappling_technique_descr . \n" +
                "                } \n" +
                "                GROUP BY ?fighter";

        Query query = QueryFactory.create(q);

        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint2, query);
        ResultSet results = qexec.execSelect();
        while (results.hasNext()) {
            QuerySolution qSolution = results.nextSolution();


            String skillNames = qSolution.getLiteral("grappling_techniques").getString();
            String skillDescrs = qSolution.getLiteral("grappling_techniques_descr").getString();
            String skillUris = qSolution.getLiteral("grappling_techniques_uri").getString();

            String[] splittedSkillNames = skillNames.split(",");
            String[] splittedSkillUris = skillUris.split(",");
            String[] splittedSkillDescrs = skillDescrs.split("§");
            for (int i = 0; splittedSkillNames.length > i; i++) {
            	GrapplingTechniqueBean skill = new GrapplingTechniqueBean();
                skill.setNome(splittedSkillNames[i]);
                System.out.println(splittedSkillNames[i]);
                skill.setDescrizione(splittedSkillDescrs[i]);
                skill.setUri(splittedSkillUris[i]);
                skills.add(skill);
            }
        }
        qexec.close();

        return skills;
    }
    

    public String doRetrieveURIDBPPlayer(String resourcePlayerMyOnto) {

        String q = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX db: <http://dbpedia.org/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "PREFIX myonto: <http://www.semanticweb.org/tanucc/ontologies/2022/4/skillsFootball>\n" +
                "\n" +
                "SELECT DISTINCT ?player \n" +
                "WHERE {\n" +
                "    myonto:" + resourcePlayerMyOnto + " rdfs:seeAlso ?player .\n" +
                "}";

        Query query = QueryFactory.create(q);

        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query);
        ResultSet results = qexec.execSelect();
        while (results.hasNext()) {
            QuerySolution qSolution = results.nextSolution();
            return qSolution.getResource("player").getURI();
        }
        qexec.close();

        return null;
    }

    public String doRetrieveURIDBPMartialArtist(String resourcePlayerMyOnto) {

        String q = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n" +
                "                PREFIX db: <http://dbpedia.org/> \n" +
                "                PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
                "                PREFIX dbo: <http://dbpedia.org/ontology/> \n" +
                "                PREFIX dbp: <http://dbpedia.org/property/> \n" +
                "                PREFIX myonto: <http://www.semanticweb.org/gerardodellobuono/ontologies/2022/6/MMATechniquesProject#> \n" +
                "                 \n" +
                "                SELECT DISTINCT ?fighter  \n" +
                "                WHERE { \n" +
                "                    myonto:" + resourcePlayerMyOnto + " rdfs:seeAlso ?fighter . \n" +
                "                }";

        Query query = QueryFactory.create(q);

        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint2, query);
        ResultSet results = qexec.execSelect();
        while (results.hasNext()) {
            QuerySolution qSolution = results.nextSolution();
            return qSolution.getResource("player").getURI();
        }
        qexec.close();

        return null;
    }

    public ArrayList<SoccerPlayerBean>  doRetrieveRelatedPlayers(String uriCurrClub, String uriPlayer) {
        ArrayList<SoccerPlayerBean> players = new ArrayList<>();

        String q = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX db: <http://dbpedia.org/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "PREFIX myonto: <http://www.semanticweb.org/tanucc/ontologies/2022/4/skillsFootball>\n" +
                "\n" +
                "SELECT DISTINCT ?name ?individual ?currClub ?currClubThumbnail ?thumbnail ?overall (GROUP_CONCAT(?position;SEPARATOR=\",\") AS ?positions) \n" +
                "WHERE {\n" +
                "    SERVICE <http://dbpedia.org/sparql> {\n" +
                "    ?individual dbp:name ?name .\n" +
                "     ?individual dbp:currentclub " + uriCurrClub + " .\n" +
                "    " + uriCurrClub + " rdfs:label ?currClub.\n" +
                "    " + uriCurrClub + " dbo:thumbnail ?currClubThumbnail .\n" +
                "\t?individual dbo:thumbnail ?thumbnail .\n" +
                "    ?individual dbo:position ?posURI .\n" +
                "    ?posURI rdfs:label ?position .\n" +
                "    FILTER(LANG(?currClub) = 'en')\n" +
                "    FILTER(LANG(?position) = 'it')\n" +
                "    FILTER (?individual != " + uriPlayer + ")\n" +
                "    }\n" +
                "    ?player a dbo:SoccerPlayer .\n" +
                "\t?player myonto:has_overall ?overall .\n" +
                "  \t?stats_individual myonto:has_name ?name_stat .\n" +
                "  \t?player rdfs:seeAlso ?individual.\n" +
                "}\n" +
                "\n" +
                "GROUP BY ?name ?individual ?currClub ?currClubThumbnail ?thumbnail ?overall\n" +
                "ORDER BY DESC (?overall)\n" +
                "LIMIT 6\n";

        Query query = QueryFactory.create(q);

        // Esecuzione della querye cattura dei risultati
        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query);
        ResultSet results = qexec.execSelect();
        while (results.hasNext()) {
            QuerySolution qSolution = results.nextSolution();
            SoccerPlayerBean player = new SoccerPlayerBean();
            FootBallTeamBean team = new FootBallTeamBean();
            player.setUri(qSolution.getResource("individual").getURI());
            player.setName(qSolution.getLiteral("name").getString());
            player.setThumbnail(qSolution.getResource("thumbnail").getURI());
            player.setPosition(qSolution.getLiteral("positions").getString());
            player.setOverall(qSolution.getLiteral("overall").getInt());
            team.setUri(uriCurrClub);
            team.setThumbnail(qSolution.getResource("currClubThumbnail").getURI());
            team.setName(qSolution.getLiteral("currClub").getString());
            player.setFootballTeamBean(team);
            players.add(player);
        }
        qexec.close();
        return players;
    }
    
    
    public ArrayList<MartialArtistBean>  doRetrieveRelatedMartialArtists(String uriCurrClub, String uriPlayer) {
        ArrayList<MartialArtistBean> players = new ArrayList<>();

        String q = "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n"
        		+ "PREFIX db: <http://dbpedia.org/> \n"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
        		+ "PREFIX dbo: <http://dbpedia.org/ontology/> \n"
        		+ "PREFIX dbp: <http://dbpedia.org/property/> \n"
        		+ "PREFIX myonto: <http://www.semanticweb.org/gerardodellobuono/ontologies/2022/6/MMATechniquesProject#> \n"
        		+ " \n"
        		+ "SELECT DISTINCT ?name ?individual ?currWeightClass ?weight ?height  ?thumbnail\n"
        		+ "WHERE { \n"
        		+ "SERVICE <http://dbpedia.org/sparql> { \n"
        		+ "  ?individual dbp:name ?name .\n"
        		+ "  ?individual dbo:weight ?weight .\n"
        		+ "  ?individual dbo:height ?height . \n"
        		+ "  ?individual dbo:thumbnail ?thumbnail .\n"
        		+ "  ?individual dbp:weightClass "+ uriCurrClub +" . \n"
        		+ "  "+ uriCurrClub +" rdfs:label ?currWeightClass.  \n"
        		+ "	FILTER(LANG(?currWeightClass) = 'en')\n"
        		+ "    FILTER (?individual != "+ uriPlayer +"  ) \n"
        		+ "    } \n"
        		+ "    ?player a dbo:MartialArtist . \n"
        		+ "    ?player rdfs:seeAlso ?individual. \n"
        		+ "} \n"
        		+ " \n"
        		+ "GROUP BY ?name ?currWeightClass ?weight ?height ?individual ?thumbnail\n"
        		+ "ORDER BY DESC(?weight)\n"
        		+ "LIMIT 6";

        Query query = QueryFactory.create(q);

        // Esecuzione della querye cattura dei risultati
        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint2, query);
        ResultSet results = qexec.execSelect();
        while (results.hasNext()) {
            QuerySolution qSolution = results.nextSolution();
            MartialArtistBean player = new MartialArtistBean();
            player.setName(qSolution.getLiteral("name").getString());
            player.setCurrWeightClass(qSolution.getLiteral("currWeightClass").getString());
            
            char weight = qSolution.getLiteral("weight").getString().charAt(0);
            if(weight=='1') {
            	player.setWeight(qSolution.getLiteral("weight").getString().substring(0, 3));
            } else {
            	player.setWeight(qSolution.getLiteral("weight").getString().substring(0, 2));
            }
            player.setHeight(qSolution.getLiteral("height").getString().substring(0, 4));
            player.setUri(qSolution.getResource("individual").getURI());
            player.setThumbnail(qSolution.getResource("thumbnail").getURI());
            players.add(player);
        }
        qexec.close();
        return players;
    }
    
    

    public boolean hasPlayerWonBallonDor(String uriPlayer) {

        String q = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "\n" +
                "ASK  {\n" +
                "  SERVICE <http://dbpedia.org/sparql> {\n" +
                "\t" + uriPlayer + " dbo:wikiPageWikiLink <http://dbpedia.org/resource/Category:Ballon_d'Or_winners>.\n" +
                "  }\n" +
                "}";

        Query query = QueryFactory.create(q);

        // Esecuzione della querye cattura dei risultati
        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query);
        boolean result = qexec.execAsk();
        qexec.close();
        return result;
    }
}
