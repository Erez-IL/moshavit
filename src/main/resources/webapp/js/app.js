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
		membership: $('#membership').val()
	})
};
//JSON from Message Detail's
var stringifyMessageJSON = function () {
	return JSON.stringify({
		subject: $('#subject').val(),
		messageText: $('#messageText').val(),
		author: {
		        "id": 1,
		        "firstName": "",
		        "lastName": "",
		        "membership": "resident",
		        "username": "erez",
		        "email": "",
		        "dateOfIssue": {
		            "era": 1,
		            "dayOfMonth": 24,
		            "dayOfWeek": 7,
		            "dayOfYear": 55,
		            "weekyear": 2013,
		            "monthOfYear": 2,
		            "weekOfWeekyear": 8,
		            "centuryOfEra": 20,
		            "millisOfSecond": 647,
		            "millisOfDay": 3041647,
		            "yearOfEra": 2013,
		            "yearOfCentury": 13,
		            "secondOfMinute": 41,
		            "secondOfDay": 3041,
		            "minuteOfHour": 50,
		            "minuteOfDay": 50,
		            "hourOfDay": 0,
		            "year": 2013,
		            "zone": {
		                "uncachedZone": {
		                    "cachable": true,
		                    "fixed": false,
		                    "id": "Asia/Jerusalem"
		                },
		                "fixed": false,
		                "id": "Asia/Jerusalem"
		            },
		            "millis": 1361659841647,
		            "chronology": {
		                "zone": {
		                    "uncachedZone": {
		                        "cachable": true,
		                        "fixed": false,
		                        "id": "Asia/Jerusalem"
		                    },
		                    "fixed": false,
		                    "id": "Asia/Jerusalem"
		                }
		            },
		            "afterNow": false,
		            "beforeNow": true,
		            "equalNow": false
		        }
		    }
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

//restore Messages Form
var buildMessageForm = function () {
	$('div.addMessageForm').empty();
	renderMessageForm();
};
var renderMessageForm = function () {
	getTemplateHBS("messageForm", function (template) {
		$.getJSON("/api/boardMessage", function (messages) {
			console.log("Got Messages: ", messages);
			var messageForm = template(messages);
			$('div.addMessageForm').append(messageForm);
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

$(document).ready(function () {
	renderUsersTable();
	bindUserFormElements();
	renderMessageForm();
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
			bindUserFormElements();
			//remove the UserTableList
			$('#UserTableList').remove();
			bindMessageFormElements();
		});
	});
});
