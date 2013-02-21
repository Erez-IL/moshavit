bindUserFormElements = function() {
	$("#addUserFromForm").click(function () {
		console.log("Creating user...");
		$.ajax({
			url: "/api/users",
			type: "POST",
			contentType: "application/json",
			data: createJsonFromForm(), success: function (data) {
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
			url: "/api/users/"+$('#userID').val(),
			type: "PUT",
			contentType: "application/json",
			data: createJsonFromForm(), success: function (data) {
				console.log("Update  " + data);
				//restore UserTableList and close this form
				restoreUsersTable();
			}});
	});

	$(function () { $("input,select,textarea").not("[type=submit]").jqBootstrapValidation(); });
};
