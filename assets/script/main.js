var lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In posuere magna sit amet massa fermentum, sed mollis eros molestie. Mauris non suscipit sapien. Ut euismod ornare arcu ac finibus. Quisque interdum neque non malesuada consectetur. Phasellus fringilla sit amet orci eget dapibus. Suspendisse luctus vehicula eleifend. Nulla efficitur dolor odio, sit amet tempus nisi efficitur a. Duis eleifend tortor in nisl rhoncus, et volutpat justo luctus. Praesent pretium magna porttitor libero ultrices, laoreet sagittis felis placerat. Fusce bibendum, nisi gravida tincidunt luctus, magna elit condimentum risus, a fringilla nisi libero vel elit. Mauris et magna eu mauris gravida fermentum ac ut neque. Maecenas sed nunc malesuada elit maximus ultrices quis vitae velit.";

function addLorem(paragraphs, target) {
	for (i = 0; i < paragraphs; i++){
		$("#" + target).append("<p>" + lorem + "</p>");
	}
}

function addLorem2(paragraphs, target) {
	var loremSplit = lorem.replaceAll(". ", ".\r\n")
	for (i = 0; i < paragraphs; i++) {
		$("#" + target).append("<p>" + loremSplit + "</p>");
	}
}

var footerHeight = 0,  footerTop = 0, $footer = $("footer");
$(window).bind("load", function() {
	positionFooter();

	$(window).scroll(positionFooter).resize(positionFooter)
});

function positionFooter() {
		footerHeight = $footer.height();
		footerTop = ($(window).scrollTop()  + $(window).height() - footerHeight) + "px";
		if (($(document.body).height() + footerHeight) < $(window).height()) {
			$footer.css({position: "absolute"});/*.animate({top: footerTop})*/
			$footer.css({top: footerTop});
		} else {
			$footer.css({position: "static"});
		}
	}

$(window).load(function() {
	$(".p-title").each(function() {
		$(this).textfit('bestfit');
	});
	$("#projects") > $('img').each(function() {
		$(this).error(function() {
			$(this).hide();
		});
	});
});
