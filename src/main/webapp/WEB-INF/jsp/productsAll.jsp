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
                  <a class="nav-link nav-hover fs-3  mx-2 mx-lg-2 active" href="/warehouseHandling">Raktározás</a>
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


<div class="container mt-5">
    <div class="row">
        <div class="column col-3">
            <nav class="sidebar">
                <ul class="nav flex-column p-3">
                    <li class="nav-item">
                        Termékek
                    </li>
                    <c:if test="${sessionScope.adminRoleType == 'true' || sessionScope.warehouseRoleType == 'true'}">
	                    <li class="nav-item">
		                    <a class="nav-link sidebar-link" href="${pageContext.request.contextPath}/productNew"> Új termék felvétele</a>
	                    </li>
	                </c:if>
	                <c:if test="${sessionScope.warehouseRoleType == 'false' && sessionScope.chefRoleType == 'true' && sessionScope.adminRoleType == 'false'}">
	                    <li class="nav-item">
		                    <a class="nav-link sidebar-link" href="${pageContext.request.contextPath}/productNew">Új Termék kérése</a>
	                    </li>
	                </c:if>
	                    <li class="nav-item">
		                    <a class="nav-link sidebar-link" id="current" href="${pageContext.request.contextPath}/productsAll">Terméklista</a>
	                    </li>
                    <form action="productSearch" class="form-outline input-group input-group-sm mt-2 px-3 rounded" method="post">
                        <input type="text" class="form-control" placeholder="Termék keresése" name="searchProduct"/>
                        <button type="submit" class="btn btn-outline-primary">Keresés</button>
                    </form>
                    <li class="nav-item">
                        Raktárak
                    </li>
    	            <c:if test="${sessionScope.adminRoleType == 'true'}">
                        <li class="nav-item">
                            <a class="nav-link sidebar-link" href="${pageContext.request.contextPath}/warehouseNew"> Új Raktár hozzáadása</a>
                        </li>
                    </c:if>
                    <li class="nav-item">
                        <a class="nav-link sidebar-link" href="${pageContext.request.contextPath}/warehouseHandling">Raktár lista</a>
                    </li>
                </ul>
            </nav>
        </div>
        <div class="column col-9">
            <div class="container-sm">
                <table class="table table-striped">
                    <tr>
                        <th>
                            Termék neve
                        </th>
                        <th>
                            Mennyisége
                        </th>
                        <th>
                            Mértékegysége
                        </th>
                        <th>
                            Termék típusa
                        </th>
                        <th>
                            Termék ára
                        </th>
                        <th>
                            Rendelés állapota
                        </th>
                        <th>
                            Tároló raktár neve
                        </th>
                        <c:if test="${sessionScope.adminRoleType == 'true' || sessionScope.warehouseRoleType == 'true'}">
                            <th>
                                Törlés
                            </th>
                            <th>
                                Módosítás
                            </th>
                        </c:if>
                    </tr>
                    <c:forEach items="${products}" var="p">
                        <tr id="myRow" >
                            <td>
                                ${p.productName}
                            </td>
                            <td>
                                ${p.amount}
                            </td>
                            <td>
                                ${p.amountType}
                            </td>
                            <td class="types">
                                ${p.type}
                            </td>
                            <td>
                                ${p.price} Ft
                            </td>
                            <td>
                                ${p.orderStatus}
                            </td>
                            <td>
                                ${p.warehouseStatus}
                            </td>
                            <c:if test="${sessionScope.adminRoleType == 'true' || sessionScope.warehouseRoleType == 'true'}">
                                <td>
                                    <a onclick='return confirm("Biztosan törölni szeretné a terméket?");' href="${pageContext.request.contextPath}/product/delete?id=${p.id}" class="btn btn-danger sm">Törlés</a>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/product/productUpdate?id=${p.id}" class="btn btn-warning sm">Módosítás</a>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>

</body>
</html>