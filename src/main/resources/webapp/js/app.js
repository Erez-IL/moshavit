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
//JSON createJsonFromForm
var stringifyJSON = function () {
	return JSON.stringify({
		username: $('#username').val(),
		firstName: $('#firstName').val(),
		lastName: $('#lastName').val(),
		email: $('#email').val(),
		membership: $('#membership').val()
	})
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


$(document).ready(function() {
	renderUsersTable();
	bindUserFormElements();
	$("#addUser").click(function () {
		getTemplateHBS("registerUserForm", function (template) {
			var registrationForm = template({});
			$('div.userFormContainer').append(registrationForm);
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

});
