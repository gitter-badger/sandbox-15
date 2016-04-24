$(window).load(function () {
	$('a').each(function(index, value) {
		if($(this).has('span')) {
			var title = $(this).find("span");
			if(title.hasClass('p-title')) {
				if($(this).find("img").length > 0) {
					var opacity = 0.8;
					var oldBGColor = title.css('background-color');
					oldBGColor = oldBGColor.replace(/rgb/i, "rgba");
					var newBGColor = oldBGColor.replace(/\)/i,',' + opacity + ')');
					title.attr('style', 'background-color: ' + newBGColor + ' !important');
				} else {
					title.addClass('noFade');
				}
			}
		}
	});

	var classes = $("#projects li").map(function() {
		return $(this).attr("class").split(/\s+/);
	});
	var classList = distinctList(classes);
	var tagList = '<ul id="project-tag"></ul>';
	var tagItem = '<a class="active noSelect"><li>all</li></a>';

	$.each(classList, function(index, value){
		var value = value.replace("-", " ");
		tagItem += '<a><li>'+value+'</li></a>';
	});

	$("#projects").before($(tagList).append(tagItem));
	$('#project-tag a').on('click', function(e) {
		var getText = $(this).text().replace(" ", "-");
		if(getText == 'all') {
			$("#projects li").fadeIn();
		} else {
			$("#projects li").fadeOut();
			$("#projects li." + getText).fadeIn();
		}

		$('#project-tag a').removeClass('active');
		$('#project-tag a').removeClass('noSelect');
		$(this).addClass('active');
		$(this).addClass('noSelect');

		e.preventDefault();
	});
});

function distinctList(inputArray) {
	var i;
	var length = inputArray.length;
	var outputArray = [];
	var temp = {};
	for (i = 0; i < length; i++) {
		temp[inputArray[i]] = 0;
	}
	for (i in temp) {
		outputArray.push(i);
	}
	return outputArray;
}
