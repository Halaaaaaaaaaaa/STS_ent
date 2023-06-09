<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>STS ENTERTAINMENT</title>

<meta name="keywords"
	content="clip-path, animation, scale, effect, background, hover" />
<meta name="author" content="Codrops" />
<link rel="shortcut icon" href="img/favicon.png">
<link rel="stylesheet" href="https://use.typekit.net/cze1cgq.css">
<link rel="stylesheet" type="text/css" href="css/base.css" />
<script type="text/javascript" src="js/header.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/webfont/1.6.26/webfont.js"></script>
<script src="https://cdn.jsdelivr.net/npm/gsap@3.11/dist/gsap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.7.1/gsap.min.js"></script>
<script src="https://unpkg.com/imagesloaded@5/imagesloaded.pkgd.min.js"></script>
<script defer
	src="https://use.fontawesome.com/releases/v5.0.6/js/all.js"></script>
</head>
<body>
	<header>
		<nav role="navigation">
			<div id="menuToggle">
				<input type="checkbox" /> <span></span> <span></span> <span></span>
				<ul id="menu">
					<c:choose>
						<c:when test="${empty sessionScope.loginUser}">
							<div class="frame__menu" style="text-align: center;">
								<a href="login_form">Login</a>&emsp;&emsp;&emsp; <a
									href="signup_form">Sign Up</a>
							</div>
						</c:when>
						<c:otherwise>
							<div class="frame__menu" style="text-align: center;">
								<a href="mypage">My Page</a>&emsp;
								<a href="cs_insertF">고객센터</a>&emsp;
								<a href="logout">LOGOUT</a>
							</div>
						</c:otherwise>
					</c:choose>
					<hr>
					<li><a href="index">Home</a></li>
					<li><a href="notice">Notice</a></li>
					<li><a href="total_ent_main?category=1">Concert</a></li>
					<li><a href="total_ent_main?category=2">Theater</a></li>
					<li><a href="total_ent_main?category=3">Museum</a></li>

					<hr style="margin-top:400px;">
					<div class="admin">
						<a href="adminlogin_form">@ Admin</a>
					</div>
				</ul>
			</div>
		</nav>
		<nav class="headerlogo">
			<a>
				<div>
					<img id="headerlogo" src="img/HEADER_LOGO_sts.png"
						onclick="location='index'">
				</div>
			</a>
		</nav>
		<nav class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav ml-auto">
				<li class="nav-item active"><a class="nav-link" href="index">Home</a></li>
				<li class="nav-item"><a class="nav-link" href="notice">Notice</a></li>
				<li class="nav-item dropdown">
              		<a href="#">Content</a>
					<ul>
				      <li><a href="total_ent_main?category=1">Concert</a></li>
				      <li><a href="total_ent_main?category=2">Theater</a></li>
				      <li><a href="total_ent_main?category=3">Museum</a></li>
				    </ul>
            	</li>
			</ul>
			
			<div class="formm" align="right">
				<input class="form-control" type="search" placeholder="Search"
					aria-label="Search">
				<button class="btn" type="submit">
					<i class="fa fa-search" aria-hidden="true"></i>
				</button>
			</div>
		</nav>
	</header>
</body>
</html>