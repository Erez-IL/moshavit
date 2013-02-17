//Create a template page by template name
var getTemplate = function (templateName, callback) {
	$.get("/templates/" + templateName + ".hbs", function (data) {
		var template = Handlebars.compile(data);
		console.log("Compiled " + templateName + " template.");
		callback(template);
	});
};

//get current date to string dd/mm/YY
var getDateNow = function () {
	var currentTime = new Date();
	var month = currentTime.getMonth() + 1;
	var day = currentTime.getDate();
	var year = currentTime.getFullYear();
	return "" + day + "/" + month + "/" + year + "";
};

//update Option to Users Table
var updateOptionToUsersTable = function () {
	$('#UserTableList tbody tr').click(function () {
		var idNum = $(this).find("td.ID").text();
		getTemplate("updateUserForm", function (template) {
			var jsonID = "/api/users/" + idNum;
			$.getJSON(jsonID, function (users) {
				console.log("Got users: ", users);
				var usersTable = template(users);
				$('div.registrationForm').append(usersTable);
			});
			//remove the UserTableList
			$('#UserTableList').remove();
		});
	});
};

getTemplate("users", function (template) {
	$.getJSON("/api/users", function (users) {
		console.log("Got users: ", users);
		var usersTable = template(users);
		$('div.users').append(usersTable);
		//update Option to Table
		updateOptionToUsersTable();
	});
});

$("#addUser").click(function () {
	getTemplate("registerUserForm", function (template) {
		$('div.registrationForm').append(template);
		//remove the UserTableList
		$('#UserTableList').remove();
	});
});



