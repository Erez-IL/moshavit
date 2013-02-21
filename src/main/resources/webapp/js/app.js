$(document).ready(function() {
	renderUsers();

	$("#addUser").click(function () {
		getTemplate("registerUserForm", function (template) {
			var registrationForm = template({});
			$('div.userFormContainer').append(registrationForm);
			bindUserFormElements();
			//remove the UserTableList
			$('#UserTableList').remove();
		});
	});
});

//Create a template page by template name
var getTemplate = function (templateName, callback) {
	$.get("/templates/" + templateName + ".hbs", function (data) {
		var template = Handlebars.compile(data);
		console.log("Compiled " + templateName + " template.");
		callback(template);
	});
};

//JSON createJsonFromForm
var createJsonFromForm = function () {
	return JSON.stringify({
		username: $('#username').val(),
		firstName: $('#firstName').val(),
		lastName: $('#lastName').val(),
		email: $('#email').val(),
		membership: $('#membership').val()
	})
};

//update Option to Users Table
var updateOptionToUsersTable = function () {
	$('#UserTableList tbody tr').click(function () {
		var idNum = $(this).find("td.ID").text();
		getTemplate("updateUserForm", function (template) {
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

//restore UserTableList
var restoreUsersTable = function () {
	//empty the userFormContainer
	$('div.userFormContainer').empty();
	renderUsers();
};

renderUsers = function () {
	getTemplate("users", function (template) {
		$.getJSON("/api/users", function (users) {
			console.log("Got users: ", users);
			var usersTable = template(users);
			$('div.users').append(usersTable);
			//update Option to Table
			updateOptionToUsersTable();
		});
	});
};

