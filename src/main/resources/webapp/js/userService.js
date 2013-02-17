/**
 * Created with IntelliJ IDEA.
 * User: erez
 * Date: 2/15/13
 * Time: 11:02 PM
 * To change this template use File | Settings | File Templates.
 */
$(function () { $("input,select,textarea").not("[type=submit]").jqBootstrapValidation(); });
$("#addUserFromForm").click(function () {
	console.log("Creating user...");
	$.ajax({
		url: "/api/users",
		type: "POST",
		contentType: "application/json",
		data: JSON.stringify({
			username: document.getElementById('username').value,
			firstName: document.getElementById('firstName').value,
			lastName: document.getElementById('lastName').value,
			email: document.getElementById('email').value,
			dateOfIssue: getDateNow(),
			membership: document.getElementById('membership').value,
			dateOfLastUpdate: getDateNow()
		}), success: function (data) {
			console.log("Added " + data);
			//restore UserTableList and close this form
			restoreUsersTable();
		}});
});

//restore UserTableList
var restoreUsersTable = function () {
	//remove the Register Form
	var div = document.getElementById("RegisterForm");
	if (div !== null)div.parentNode.removeChild(div);
	//remove the Update Form
	var div = document.getElementById("updateForm");
	if (div !== null)div.parentNode.removeChild(div);
	getTemplate("users", function (template) {
		$.getJSON("/api/users", function (users) {
			console.log("Got users: ", users);
			var usersTable = template(users);
			$('div.users').append(usersTable);
			updateOptionToUsersTable();
		});
	});
};
$("#cancelForm").click(function () {
	restoreUsersTable();
});

$("#updateUserFromForm").click(function () {
	console.log("Update user...");
	$.ajax({
		url: "/api/users/update",
		type: "POST",
		contentType: "application/json",
		data: JSON.stringify({
			id: document.getElementById('userID').value,
			username: document.getElementById('username').value,
			firstName: document.getElementById('firstName').value,
			lastName: document.getElementById('lastName').value,
			dateOfIssue: document.getElementById('dateOfIssue').value,
			email: document.getElementById('email').value,
			membership: document.getElementById('membership').value,
			dateOfLastUpdate: getDateNow()
		}), success: function (data) {
			console.log("Update  " + data);
			//restore UserTableList and close this form
			restoreUsersTable();
		}});
});
