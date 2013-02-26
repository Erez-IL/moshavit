/**
 * Created with IntelliJ IDEA.
 * User: erez
 * Date: 2/22/13
 * Time: 9:27 PM
 * To change this template use File | Settings | File Templates.
 */
//Create a template page by template name
var getTemplateHBS = function (templateName, callback) {
	$.get("/templates/" + templateName + ".hbs", function (data) {
		var template = Handlebars.compile(data);
		console.log("Compiled " + templateName + " template.");
		callback(template);
	});
};
//JSON from User Detail's
var stringifyUserJSON = function () {
	return JSON.stringify({
		username: $('#username').val(),
		firstName: $('#firstName').val(),
		lastName: $('#lastName').val(),
		email: $('#email').val(),
		membership: $('#membership').val(),
		password: $('#password').val()
	})
};
//JSON from Message Detail's
var stringifyMessageJSON = function () {
	return JSON.stringify({
		subject: $('#subject').val(),
		messageText: $('#messageText').val(),
		author: {
			id: 1,
			firstName: "",
			lastName: "",
			membership: "resident",
			username: "erez",
			email: ""
		}
	});
};

//update Option to Users Table
var updateOptionOnUsersTable = function () {
	$('#UserTableList tbody tr').click(function () {
		var idNum = $(this).find("td.ID").text();
		getTemplateHBS("updateUserForm", function (template) {
			var jsonID = "/api/users/" + idNum;
			$.getJSON(jsonID, function (users) {
				console.log("Got users: ", users);
				var updateUserForm = template(users);
				$('div.userFormContainer').append(updateUserForm);
				bindUserFormElements();
			});
			//remove the UserTableList
			$('#UserTableList').remove();
		});
	});
};
//restore UserTableList old style
var restoreUsersTable = function () {
	//empty the userFormContainer
	$('div.userFormContainer').empty();
	renderUsers();

};

//restore UserTableList  new Style
var buildUsersTable = function () {
	//remove the userFormModel
	$('#myModal').modal('hide')
	$('#myModal').remove();
	renderUsersTable();
};

//restore Messages Form
var buildMessageForm = function () {
	renderMessageForm();
};
var renderMessageForm = function () {
	getTemplateHBS("messageForm", function (template) {
		$.getJSON("/api/boardMessage", function (messages) {
			console.log("Got Messages: ", messages);
			var messageForm = template(messages);
			$('body').append(messageForm);
		});
	});
};
var renderUsers = function () {
	getTemplateHBS("users", function (template) {
		$.getJSON("/api/users", function (users) {
			console.log("Got users: ", users);
			var usersTable = template(users);
			$('div.users').append(usersTable);
			//update Option to Table
			updateOptionOnUsersTable();
		});
	});
};
var renderUsersTable = function () {
	renderUsers();
};
var getCurrentUserSession = function () {
	$.get("/api/users/login", function (user) {
		var username = (typeof user !== 'undefined') ? user.username : "Guest";
		$('#sessionUsername').text(username);
	});
};
$(document).ready(function () {
	renderUsersTable();
	bindUserFormElements();
	renderMessageForm();
	getCurrentUserSession();
	$("#addUser").click(function () {
		getTemplateHBS("registerUserForm", function (template) {
			$('div.userFormContainer').append(template);
			bindUserFormElements();
			//remove the UserTableList
			$('#UserTableList').remove();
		});
	});
	//new Style of reg form
	$("#addNew").click(function () {
		getTemplateHBS("newRegisterForm", function (template) {
			$('body').append(template);
			bindUserFormElements();
			//remove the UserTableList
			$('#UserTableList').remove();
			$('#myModal').modal('show');
		});
	});
	//add message form
	$("#addMessage").click(function () {
		getTemplateHBS("addMessageForm", function (template) {
			$('body').append(template);
			$("#messageForm").remove();
			bindUserFormElements();
			//remove the UserTableList
			$('#UserTableList').remove();
			bindMessageFormElements();
		});
	});
	$('#logoutButton').click(function(){logout()});
	$('#loginButton').click(function(){
		login(prompt("Enter Username "),prompt("Enter Password "));
	});
});

