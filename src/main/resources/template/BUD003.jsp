<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link th:href="@{/resources/css/test.css}" rel="stylesheet" />


<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

<title>Course Registration</title>
</head>

<body>
	<div id="testheader">
		<div class="container">
			<div class=row>
				<div class="col-md-5 ">
					<a href="MNU001.jsp"><h3>Student Registration</h3></a>
				</div>
				<div class="col-md-6">
					<p>User: ${sessionScope.loginName}</p>
					<p>Current Date : ${sessionScope.date}</p>
				</div>
				<div class="col-md-1">
					<input type="button" class="btn-basic" id="lgnout-button"
						value="Log Out" onclick="location.href='/MvcProjectWithSpring/logOut'">
				</div>
			</div>
		</div>

	</div>
	<!-- <div id="testsidebar">Hello World </div> -->
	<div class="container">
		<div class="sidenav">

			<button class="dropdown-btn">
				Class Management <i class="fa fa-caret-down"></i>
			</button>

			<div class="dropdown-container">
           <a href="courseAddPage">Course Registration </a>
          <a href="stuAddPage">Student Registration </a>
          <a href="stuSearchPage">Student Search </a>
        </div>
        <a href="searchUserPage">Users Management</a>
		</div>
		<div class="main_contents">
			<div id="sub_content">
				<form action="courseAdd" method="post" th:object="${cBean}">

					<h2 class="col-md-6 offset-md-2 mb-5 mt-4">Course Registration</h2>
					<div>${errorFill}</div>
					<div class="row mb-4">
						<div class="col-md-2"></div>

						<label for="courseId" class="col-md-2 col-form-label">ID</label>
						<div class="col-md-4">


							<input type="text" class="form-control" id="id" th:field="*{courseId}" readonly="readonly" />
						</div>
					</div>

					<div class="row mb-4">
						<div class="col-md-2"></div>

						<label path="courseName" class="col-md-2 col-form-label">Name</label>
						<div class="col-md-4">
							<input type="text" class="form-control" id="name" path="courseName" />
						</div>
					</div>


					<div class="row mb-4">
						<div class="col-md-4"></div>

						<div class="col-md-6">


							<button type="submit" class="btn btn-secondary col-md-2"
								data-bs-toggle="modal" data-bs-target="#exampleModal">Add</button>


						</div>
				</form>
			</div>
		</div>
	</div>
	<div id="testfooter">
		<span>Copyright &#169; ACE Inspiration 2022</span>
	</div>
	<script>
		/* Loop through all dropdown buttons to toggle between hiding and showing its dropdown content - This allows the user to have multiple dropdowns without any conflict */
		var dropdown = document.getElementsByClassName("dropdown-btn");
		var i;
		for (i = 0; i < dropdown.length; i++) {
			dropdown[i].addEventListener("click", function() {
				this.classList.toggle("active");
				var dropdownContent = this.nextElementSibling;
				if (dropdownContent.style.display === "block") {
					dropdownContent.style.display = "none";
				} else {
					dropdownContent.style.display = "block";
				}
			});
		}
	</script>
</body>

</html>