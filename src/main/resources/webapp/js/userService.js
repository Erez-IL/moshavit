/**
 * Created with IntelliJ IDEA.
 * User: erez
 * Date: 2/15/13
 * Time: 11:02 PM
 * To change this template use File | Settings | File Templates.
 */
var bindUserFormElements = function () {
	$("#addUserFromForm").click(function () {
		console.log("Creating user...");
		$.ajax({
			url: "/api/users",
			type: "POST",
			contentType: "application/json",
			data: stringifyUserJSON(), success: function (data) {
				console.log("Added " + data);
				//restore UserTableList and close this form
				restoreUsersTable();
			}});
	});

	$("#cancelForm").click(function () {
		restoreUsersTable();
	});

	$("#updateUserFromForm").click(function () {
		console.log("Update user...");
		$.ajax({
			url: "/api/users/" + $('#userID').val(),
			type: "PUT",
			contentType: "application/json",
			data: stringifyUserJSON(), success: function (data) {
				console.log("Update  " + data);
				//restore UserTableList and close this form
				restoreUsersTable();
			}});
	});

//new style JS
	$('.closeForm').click(function () {
		buildUsersTable();
	});

	$("#submitForm").click(function () {
		console.log("Creating user...");
		$.ajax({
			url: "/api/users",
			type: "POST",
			contentType: "application/json",
			data: stringifyUserJSON(), success: function (data) {
				console.log("Added " + data);
				//restore UserTableList and close this form
				buildUsersTable();
			}});
	});

	$("#updateSubmitForm").click(function () {
		console.log("Update user...");
		$.ajax({
			url: "/api/users/" + $('#userID').val(),
			type: "PUT",
			contentType: "application/json",
			data: stringifyUserJSON(), success: function (data) {
				console.log("Update  " + data);
				//restore UserTableList and close this form
				buildUsersTable();
			}});
	});
	$(function () { $("input,select,textarea").not("[type=submit]").jqBootstrapValidation(); });
};
var logout = function(){
	$.get( "/api/users/logout");
	console.log("User Session Cleared");
	document.getElementById('sessionUsername').innerHTML ="Guest";
};
var login = function(username, password) {
	$.ajax({
		url: "/api/users/login",
		type: "POST",
		// the default:
		contentType: "application/x-www-form-urlencoded",
		data: {
			username: username,
			password: password
		},
		success: function(data) {
			console.log("Logged in as " + username + " successfully");
			document.getElementById('sessionUsername').innerHTML=username;
			$.get("/api/users/login", function (user) {
			CurrentSessionUser=user;
			});
		},
		error: function( jqXHR, textStatus, errorThrown) {
			console.log("Failed logging in " + username + ": " +  errorThrown)
			document.getElementById('sessionUsername').innerHTML = "Guest";
		}
	});
};

