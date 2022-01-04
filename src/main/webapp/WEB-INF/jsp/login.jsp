<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" %>
 <!-- BootStrap 5.1.1 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
    <!-- BootStrap 5.1.1 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>
    <title>
        Szociális Otthon Étkeztetés Login
    </title>
    <style>

    .login {
      min-height: 100vh;
    }

    .bg-image {
      background-image: url("https://images.unsplash.com/photo-1506368249639-73a05d6f6488?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=687&q=80") ;
      background-size: cover;
      background-position: center;
    }

    .login-heading {
      font-weight: 300;
    }

    .btn-login {
      font-size: 0.9rem;
      letter-spacing: 0.05rem;
      padding: 0.75rem 1rem;
    }

    </style>
</head>
<body>
<div class="container-fluid ps-md-0">
  <div class="row g-0">
    <div class="d-none d-md-flex col-md-4 col-lg-6 bg-image"></div>
    <div class="col-md-8 col-lg-6">
      <div class="login d-flex align-items-center py-5">
        <div class="container">
          <div class="row">
            <div class="col-md-9 col-lg-8 mx-auto">
              <h3 class="login-heading mb-4">Kerekerdő Szeretetotthon Bejelentkező Felület</h3>
              <form action="${pageContext.request.contextPath}/index" method="post">
                <div class="form-floating mb-3">
                  <input type="text" class="form-control" id="floatingInput" placeholder="Felhasználó neve" name="userName">
                  <label for="floatingInput">Felhasználó</label>
                </div>
                <div class="form-floating mb-3">
                  <input type="password" class="form-control" id="floatingPassword" placeholder="Jelszó" name="password">
                  <label for="floatingPassword">Jelszó</label>
                </div>

                <div class="d-grid">
                  <button class="btn btn-lg btn-primary btn-login text-uppercase fw-bold mb-2" type="submit">Bejelentkezés</button>
                </div>

              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>