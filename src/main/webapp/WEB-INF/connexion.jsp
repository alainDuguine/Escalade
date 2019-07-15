<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inscription</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <link href="https://fonts.googleapis.com/css?family=Merienda&display=swap" rel="stylesheet">
</head>
<body>
<%@ include file= "header.jsp"%>
<section class="mainDiv" id="mainDivCenter">
    <div id="inscriptionDiv">
        <h1>Connexion :</h1>
        <p class="${empty listErreur ? 'successConnect' : 'echecConnect'}">${resultat}</p>
        <form method="post" action="connexion.do">
            <div class="erreur">
                <div></div>
                <div>${listErreur['email']}</div>
            </div>
            <div class="connexionForm">
                <label for="email">Adresse e-mail :</label>
                <input type="email" name="email" id="email" placeholder="name@example.com" required="required" value="<c:out value="${param.email}"/>">
            </div>
            <div class="erreur">
                <div></div>
                <div>${listErreur['password']}</div>
            </div>
            <div class="connexionForm">
                <label for="password">Mot de passe :</label>
                <input type="password" name="password" id="password" required="required" >
            </div>
            <div>
                <input type="checkbox" name="session" id="session">
                <label for="session" class="connexionLabel">Se souvenir de moi</label>
            </div>
            <br>
            <span class="connexionLabel"><a href="">Mot de passe oublié</a></span>
            <div class="bouton">
                <input type="submit" value="Se Connecter">
            </div>
            <span class="connexionLabel">Pas encore membre ? <a href="inscription.do">Inscrivez-vous !</a></span>
        </form>
    </div>
</section>
<%@include file="social.jsp"%>
</body>
</html>
