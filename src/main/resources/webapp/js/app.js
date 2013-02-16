var getTemplate = function (templateName, callback) {
	$.get("/templates/" + templateName + ".hbs", function (data) {
		var template = Handlebars.compile(data);
		console.log("Compiled " + templateName + " template.");
		callback(template);
	});
};
getTemplate("users", function (template) {
	$.getJSON("/api/users", function (users) {
		console.log("Got users: ", users);
		var usersTable = template(users);
		$('div.users').append(usersTable);

	});
});

$("#addUser").click(function () {
			getTemplate("registerUserForm", function (template) {
				$('div.registrationForm').append(template);

				//remove the UserTableList
								var div = document.getElementById("UserTableList");
								div.parentNode.removeChild(div);
			})
		}
);


