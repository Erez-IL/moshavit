/**
 * Created with IntelliJ IDEA.
 * User: erez
 * Date: 2/23/13
 * Time: 11:57 PM
 * To change this template use File | Settings | File Templates.
 */
var bindMessageFormElements = function () {
//new style JS
	$('.closeForm').click(function () {
		buildMessageForm();
	});

	$("#addMessageSubmit").click(function () {
		console.log("Creating message...");
		$.ajax({
			url: "/api/boardMessage",
			type: "POST",
			contentType: "application/json",
			data: stringifyMessageJSON(), success: function (data) {
				console.log("Added " + data);
				//restore Messages Form and close this form
				buildMessageForm();
			}});
	});
};
