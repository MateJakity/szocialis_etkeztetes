<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" %>


 <!-- BootStrap 5.1.1 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
    <!-- BootStrap 5.1.1 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>
   <title>
        Szociális Otthon Étkeztetés Raktározás
    </title>

    <script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function(event) {
            let myDiv = document.getElementById("displayIt");
            myDiv.style.display = "none";
            document.getElementById("orderSelect").onchange = function(){
            myDiv.style.display = (this.selectedIndex == 2) ? "block" : "none";
            }
    });
    </script>

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
                  <a class="nav-link nav-hover fs-3  mx-2 mx-lg-2  active" href="/warehouseHandling">Raktározás</a>
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
		                    <a class="nav-link sidebar-link" id="current" href="${pageContext.request.contextPath}/productNew"> Új termék felvétele</a>
	                    </li>
	                </c:if>
	                <c:if test="${sessionScope.warehouseRoleType == 'false' && sessionScope.chefRoleType == 'true' && sessionScope.adminRoleType == 'false'}">
	                    <li class="nav-item">
		                    <a class="nav-link sidebar-link" id="current" href="${pageContext.request.contextPath}/productNew">Új Termék kérése</a>
	                    </li>
	                </c:if>
	                    <li class="nav-item">
		                    <a class="nav-link sidebar-link" href="${pageContext.request.contextPath}/productsAll">Terméklista</a>
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
                <form action="productNew" method="post">
                    <label class="form-label fw-bold" for="productName">Termék neve</label>
                    <br>
                    <input class="form-control" type="text" id="productName" name="productName" placeholder="A termék neve" required/>
                    <br>
                    <div class="row">
                        <div class="column col-3">
                            <label class="form-label fw-bold" for="amount">A termék mennyisége</label>
                            <br>
                            <input class="form-control" type="number" id="amount" name="amount" placeholder="A termék mennyisége" required/>
                        </div>
                        <div class="column col-3">
                            <label class="form-label fw-bold" for="amountType">Mértékegysége</label>
                            <br>
                            <select name="amountType" id="amountType" class="form-select" >
                                <option value="kg" selected>kilogramm</option>
                                <option value="dkg">dekagramm</option>
                                <option value="g">gramm</option>
                                <option value="l">liter</option>
                                <option value="dl">deciliter</option>
                                <option value="ml">mililiter</option>
                            </select>
                        </div>
                    </div>
                    <br>
                    <label class="form-label fw-bold" for="type">A termék fajtája</label>
                    <br>
                    <select name="type" id="type" class="form-select" >
                        <option value="hús" selected>Hús</option>
                        <option value="tej">Tej</option>
                        <option value="zöldség">Zöldség</option>
                        <option value="egyéb">Egyéb</option>
                    </select>
                    <br>
                    <c:choose>
                        <c:when test="${sessionScope.warehouseRoleType == 'true' || sessionScope.adminRoleType == 'true'}">
                            <label class="form-label fw-bold" for="price">Ára(forintban értendő)</label>
                            <input class="form-control" type="number" id="price" name="price" placeholder="1500" required/>
                            <br>
                            <label class="form-label fw-bold" for="orderStatus">A rendelés állapota</label>
                            <br>
                            <select name="orderStatus" id="orderSelect" class="form-select" >
                                <option value="előtervezve" selected>Előtervezve</option>
                                <option value="rendelve">Megrendelve</option>
                                <option value="raktárban">Raktárban</option>
                            </select>
                            <div id="displayIt" class="py-2">
                                <label class="form-label fw-bold" for="warehouseStatus">Melyik Raktárba tartozik</label>
                                <select class="form-select" name="warehouseStatus">
                                    <option value="none">Válasszon raktárt!</option>
                                    <c:forEach items="${warehouses}" var="w">
                                        <option value="${w.name}">${w.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </c:when>
                        <c:when test="${sessionScope.chefRoleType == 'true' && sessionScope.warehouseRoleType == 'false' && sessionScope.adminRoleType == 'false'}">
                            <input hidden id="price" name="price" value="0"/>
                            <input hidden id="orderStatus" name="orderStatus" value="előtervezve">
                            <input hidden id="warehouseStatus" name="warehouseStatus" value="none">
                            </c:when>
                    </c:choose>
                    <br>
                    <input class="btn btn-primary" type="submit" value="Termék felvétele">
                </form>
                <br>
                <a href="/warehouseHandling">
                    <button class="btn btn-secondary">Vissza</button>
                </a>
            </div>
        </div>
    </div>
</body>
</html>