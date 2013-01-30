$.get("/api/user", function(users) {
	$('body').appendChild(users);
})