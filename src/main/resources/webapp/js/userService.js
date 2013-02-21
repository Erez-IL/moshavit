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
		url: "/api/users",
		type: "PUT",
		contentType: "application/json",
		data: createJsonFromForm(), success: function (data) {
			console.log("Update  " + data);
			//restore UserTableList and close this form
			restoreUsersTable();
		}});
});

