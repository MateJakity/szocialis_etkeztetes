<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" %>


 <!-- BootStrap 5.1.1 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
    <!-- BootStrap 5.1.1 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>
   <title>
        Szociális Otthon Étkeztetés Főoldal
    </title>

   <style>
        .nav-hover:hover{
            padding-bottom: 0;
            border-bottom: 1px solid yellow;
        }

        .active{
            background: #F6BE00;
            border-radius: 5px;
        }

        .active:hover{
            border-bottom: 0;
            padding-bottom: 0.5rem;
        }

        .sidebar{
            background: #eeeeee;
            border-radius: 5px;
        }

        .sidebar-link{
            color: #333;
        }

        .sidebar-link:hover{
            color: #F6BE00;
        }

       @media (max-width: 768px) {
           .navbar .navbar-nav .navbar-toggler{
           display: inline-block;
           float: none;
           vertical-align: top;
           margin-top: 1rem;
           }

           .navbar .navbar-collapse {
           text-align: center;
           }

           .navbar-toggler {
             width: 100%;
             float: none;
             margin: 1rem 0 1rem 0;
           }
       }

       </style>
</head>
<body>

<nav class="navbar navbar-expand-md container-fluid navbar-dark bg-dark">
    <div class="container">
        <div class="container-fluid">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarToggler" aria-controls="navbarToggler" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarToggler">
              <a class="navbar-brand active me-auto mb-2 mb-lg-0" href="/index">Kerekerdő Szeretetotthon</a>
              <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                  <a class="nav-link fs-3 nav-hover  mx-2 mx-lg-2 " href="/forumHandling" >Fórum</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link nav-hover fs-3  mx-2 mx-lg-2" href="/warehouseHandling">Raktározás</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link fs-3 nav-hover  mx-2 mx-lg-2 " href="/personalMessageHandling" >Üzenetek</a>
                </li>
                <c:if test="${sessionScope.adminRoleType == 'true'}">
                    <li class="nav-item">
                        <a class="nav-link fs-3 nav-hover  mx-2 mx-lg-2 " href="/userHandling" >Beállítások</a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.adminRoleType == 'false'}">
                    <li class="nav-item">
                        <a class="nav-link fs-3 nav-hover  mx-2 mx-lg-2 " href="userHandling/userUpdate?id=${sessionScope.uid}" >Beállítások</a>
                    </li>
                </c:if>
              </ul>
              <a class="btn btn-light btn-outline-danger" href="/logout">Kilépés</a>
            </div>
          </div>
    </div>
</nav>


<div class="container">
    <div class="container-fluid d-flex flex-column justify-content-center align-items-center">
        <h1 class="py-3">Üdvözöljük a Kerekerdő Szeretetotthon raktárprogramjában!</h1>
    </div>
    <div class="bg-light py-2">
        <h2 class="p-2 text-center"><ins>Hirdetmények</ins></h2>
        <c:forEach items="${calls}" var="m">
            <p class="p-2 fw-bold">${m.messageBody}</p>
        </c:forEach>
    </div>
</div>


</body>
</html>