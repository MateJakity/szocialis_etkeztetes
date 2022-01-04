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
            border: 2px solid #86b300;
        }

        .sidebar-link{
            color: #333;
        }

        .sidebar-link:hover{
            color: #F6BE00;
        }

        #current{
            background: #86b300;
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
              <a class="navbar-brand me-auto mb-2 mb-lg-0" href="/index">Kerekerdő Szeretetotthon</a>
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
                        <a class="nav-link fs-3 nav-hover  mx-2 mx-lg-2 active" href="${pageContext.request.contextPath}/userHandling" >Beállítások</a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.adminRoleType == 'false'}">
                    <li class="nav-item">
                        <a class="nav-link fs-3 nav-hover  mx-2 mx-lg-2  active" href="${pageContext.request.contextPath}/userHandling/userUpdate?id=${sessionScope.uid}" >Beállítások</a>
                    </li>
                </c:if>
              </ul>
              <a class="btn btn-light btn-outline-danger" href="/logout">Kilépés</a>
            </div>
          </div>
    </div>
</nav>

<div class="container mt-5">
        <div class="row">
            <div class="column col-3">
                <nav class="sidebar">
                    <ul class="nav flex-column p-3">
                        <c:if test="${sessionScope.adminRoleType == 'true'}">
	                        <li class="nav-item">
		                        <a class="nav-link sidebar-link" href="${pageContext.request.contextPath}/userNew"> Új alkalmazott felvétele</a>
	                        </li>
	                        <li class="nav-item">
		                        <a class="nav-link sidebar-link" href="${pageContext.request.contextPath}/userHandling"> Alkalmazottak lista</a>
	                        </li>
	                    </c:if>
	                    <c:if test="${sessionScope.uid == user.id}">
	                    <li class="nav-item">
		                    <a class="nav-link sidebar-link" id="current" href="${pageContext.request.contextPath}/userHandling/userUpdate?id=${sessionScope.uid}">Saját beállításaim módosítása</a>
	                    </li>
	                    </c:if>
	                    <c:if test="${sessionScope.uid != user.id}">
		                    <a class="nav-link sidebar-link" id="current" href="${pageContext.request.contextPath}/userHandling/userUpdate?id=${user.id}">${user.userName} módosítása</a>
	                    </c:if>
                    </ul>
                </nav>
            </div>
            <div class="column col-9">
                <div class="container-sm">
                    <form action="userUpdate" method="post">
                    <input name="id" hidden value="${user.id}">
                    <label class="form-label fw-bold" for="displayName">Teljes neve</label>
                    <br>
                    <input class="form-control" type="text" id="displayName" name="displayName" value="${user.displayName}" required/>
                    <br>
                    <label class="form-label fw-bold" for="userName">Felhasználói név(bejelentkezéshez)</label>
                    <br>
                    <input class="form-control" type="text" id="userName" name="userName" value="${user.userName}" required/>
                    <br>
                    <label class="form-label fw-bold" for="password">Jelszó</label>
                    <br>
                    <input class="form-control" type="password" id="password" name="password" placeholder="Titkos jelszó"required/>
                    <br>
                    <label class="form-label fw-bold" for="roleName">Szerepköre</label>
                    <br>
                    <c:if test="${sessionScope.adminRoleType == 'false'}">
                    <input disabled class="form-control" type="text" id="roleName" name="roleName" value="${user.roleName}" required/>
                    </c:if>
                    <c:if test="${sessionScope.adminRoleType == 'true'}">
                    <input class="form-control" type="text" id="roleName" name="roleName" value="${user.roleName}" required/>
                    </c:if>
                    <br>
                    <c:if test="${sessionScope.adminRoleType == 'true'}">
                        <p class="fw-bold">Szeretne-e "admin" jogosultságokat adni az alkalmazottnak?</p>
                        <p><mark>Eddig "Admin" jogosultsága ${user.adminRoleType ? "volt" : "nem volt"}. </mark><p>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="adminRoleType" id="adminRoleType" value="true">
                            <label class="form-check-label" for="adminRoleType">
                                Igen
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="adminRoleType" id="adminRoleType" value="false" checked>
                            <label class="form-check-label" for="adminRoleType">
                                Nem
                            </label>
                        </div>
                        <p class="fw-bold">Szeretne-e "szakács" jogosultságokat adni az alkalmazottnak?</p>
                        <p><mark>Eddig szakács jogosultsága ${user.chefRoleType ? "volt" : "nem volt"}. </mark><p>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="chefRoleType" id="chefRoleType" value="true">
                            <label class="form-check-label" for="chefRoleType">
                                Igen
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="chefRoleType" id="chefRoleType" value="false" checked>
                            <label class="form-check-label" for="chefRoleType">
                                Nem
                            </label>
                            </div>
                            <p class="fw-bold">Szeretne-e "raktáros" jogosultságokat adni az új alkalmazottnak?</p>
                                <p><mark>Eddig "raktáros" jogosultsága ${user.warehouseRoleType ? "volt" : "nem volt"}. </mark><p>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="warehouseRoleType" id="warehouseRoleType" value="true">
                                <label class="form-check-label" for="warehouseRoleType">
                                    Igen
                                </label>
                            </div>
                            <div class="form-check">
                            <input class="form-check-input" type="radio" name="warehouseRoleType" id="warehouseRoleType" value="false" checked>
                            <label class="form-check-label" for="warehouseRoleType">
                                Nem
                            </label>
                            </div>
                            <p class="fw-bold">Szeretné a felhasználót deaktiválni?</p>
                            <p><mark>Eddig "Aktív" ${user.active ? "volt" : "nem volt"}. </mark><p>
                            <div class="form-check">
                            <input class="form-check-input" type="radio" name="active" id="active" value="true">
                            <label class="form-check-label" for="active">
                                Igen
                            </label>
                            </div>
                            <div class="form-check">
                            <input class="form-check-input" type="radio" name="active" id="active" value="false" checked>
                            <label class="form-check-label" for="active">
                                Nem
                            </label>
                            </div>
                    </c:if>
                    <c:if test="${sessionScope.adminRoleType == 'false'}">
                        <input hidden name="roleName" value="${user.roleName}">
                        <input hidden name="adminRoleType" value="${user.adminRoleType}">
                        <input hidden name="chefRoleType" value="${user.chefRoleType}">
                        <input hidden name="warehouseRoleType" value="${user.warehouseRoleType}">
                        <input hidden name="active" value="${user.active}">
                    </c:if>
                    <input class="btn btn-primary" type="submit" value="Módosítás">
                    </form>
                    <c:if test="${sessionScope.adminRoleType == 'true'}">
                        <a href="/userHandling">
                            <button class="btn btn-secondary">Vissza</button>
                        </a>
                    </c:if>
                </div>
            </div>
        </div>
</div>
</body>
</html>