$(window).load(function () {
	var profile = $('#github');
	profile.addClass("github-profile");
	$.getJSON('https://api.github.com/users/GOGO98901', function(data) {
		var avatar = '<img class="avatar" src="' + data.avatar_url + '" alt=" " />';
		var name = '<div class="name">' + data.name + '</div>';
		var username = '<div class="username"><a href="' + data.html_url + '">@' + data.login + '</a></div>';
		var description = '<div class="description">';
		description += 'Followers: <b>' + data.followers + '</b><br>';
		description += 'Following: <b>' + data.following + '</b>';
		description += '</div>';
		var bar = '<ul class="data">';
		bar += '<li><span>Repos: ' + data.public_repos + '</span></li>';
		bar += '<li><span>Gists: ' + data.public_gists + '</span></li>';
		bar += '</ul>';
		profile.append(avatar + name + username + description + bar);
	});
});
