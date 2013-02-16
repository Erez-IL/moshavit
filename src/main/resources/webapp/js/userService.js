/**
 * Created with IntelliJ IDEA.
 * User: erez
 * Date: 2/15/13
 * Time: 11:02 PM
 * To change this template use File | Settings | File Templates.
 */
$(function () { $("input,select,textarea").not("[type=submit]").jqBootstrapValidation(); } );
$("#addUserFromForm").click(function () {
		console.log("Creating user...");
			var currentTime = new Date();
			var month = currentTime.getMonth() + 1;
			var day = currentTime.getDate();
			var year = currentTime.getFullYear();
		$.ajax({
			url: "/api/users",
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify({
				username:  document.getElementById('username').value,
				firstName:  document.getElementById('firstName').value,
				lastName: document.getElementById('lastName').value,
				dateOfIssue: ""+ day + "/" + month + "/" + year+"",
				membership: document.getElementById('membership').value

			}), success: function(data) {
				console.log("Added " + data);

				//remove the Register Form
				var div = document.getElementById("RegisterForm");
				div.parentNode.removeChild(div);

				//retrieve UserTableList
				getTemplate("users", function (template) {
					$.getJSON("/api/users", function (users) {
						console.log("Got users: ", users);
						var usersTable = template(users);
						$('div.users').append(usersTable);
					});
				});
			}});
	});

