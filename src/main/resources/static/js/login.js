 $(function () {
	    $("#submitLoginButton").on("click", function (e) {
            var loginFrm = document.getElementById("submitLoginFrm");
            loginFrm.submit();
	        /*var datum = {};
	            datum["username"] = loginFrm.username.value;
	            datum["password"] = loginFrm.password.value;
	            var json = JSON.stringify(datum);
	            console.log(json);
	            $.post({
	                url: "/api/perform_login",
	                data: json,
	                processData: false,
	                contentType: "application/json",
	                success: function (data) {
	                    console.log("data submitted successfully"+data.full_name);
	                },
	                failure: function (data) {
	                    console.log("login failed"+data);
	                    $("#errorMessage").html(data);
	                }
	            });*/
	       })
	});