<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="/MMATechniques_Ontology">MMA Techniques Ontology</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link <% if (request.getRequestURI().equals("/MMATechniques_Ontology/")) { %>active<% } %>" aria-current="page" href="/MMATechniques_Ontology">Lista Fighter</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <% if (request.getRequestURI().equals("/MMATechniques_Ontology/BallonDOR.jsp")) { %>active<% } %>" href="/MMATechniques_Ontology/BallonDOR.jsp">The Ultimate Fighter</a>
                </li>
            </ul>
        </div>
    </div>
</nav>