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
	console.log("Creating user...");
	$.ajax({
		url: "/api/users",
		type: "POST",
		contentType: "application/json",
		data: JSON.stringify({
			firstName: "Moshe",
			lastName: "Zuchmer",
			username: "moshe"
		}), success: function(data) {
			console.log("Added " + data);
		}});
});