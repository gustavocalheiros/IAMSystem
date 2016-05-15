<%@page import="fr.grlc.iamcore.datamodel.Identity" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="res/favicon.ico">

    <title>Update Identity</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/jumbotron-narrow.css" rel="stylesheet">
    
    
	<script type="text/javascript" src="js/ajax-requests.js"></script>

  </head>

  <body>

    <div class="container">
      <div class="header clearfix">
        <nav>
          <ul class="nav nav-pills pull-right">
            <li role="presentation" class="active"><a href="home.html">Home</a></li>
          </ul>
        </nav>
        <h3 class="text-muted">I AM Web</h3>
      </div>

      <div id="pageTitle" class="jumbotron">
        <h1>Update Identity</h1>
        <h4 id="errorMsg" style="color:red;" hidden="true"></h4>
      </div>
      
	<% Identity identity = (Identity)request.getAttribute("identity"); %>
      <div class="row marketing">
        <div id="creationTable">
<!--           <h4>ID</h4> -->
          <input type="text" style="display:none;" name="id" value="<%= identity.getId() %>" class="form-control">
<!-- 		  <br> -->
          <h4>First Name</h4>
          <input type="text" name="fname" value="<%= identity.getFirstName() %>" class="form-control" required autofocus>
		  <br>
          <h4>Last Name</h4>
          <input type="text" name="lname" value="<%= identity.getLastName() %>" class="form-control" required>
		  <br>
          <h4>Email</h4>
          <input type="email" name="email" value="<%= identity.getEmail() %>" class="form-control" required readonly>
          <br>
          <h4>Birth Date</h4>
          <input type="date" name="birthdate" value="<%= identity.getBirthDate() %>" class="form-control" required>
        </div>
        <br><br><br>
        
        <div style="text-align: center;">
        	<p><a class="btn btn-lg btn-danger" onclick="handleRequest('UpdateIdentity')" role="button">update!</a></p>
		</div>
		
      </div>

      <footer class="footer">
        <p>&copy; 2016 Gustavo Calheiros</p>
      </footer>

    </div> <!-- /container -->
  </body>
</html>
