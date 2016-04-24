$(function() {
   $('a[href*="#"]:not([href="#"])').click(function() {
      if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'') && location.hostname == this.hostname) {
         var target = $(this.hash);
         target = target.length ? target : $('[name=' + this.hash.slice(1) +']');
         if (target.length) {
            var top = target.offset().top;
            if(this.hash == "#top") top = 0;
            $('html, body').animate({
               scrollTop: top
            }, 1000);
            return false;
         }
      }
   });
});

jQuery(document).ready(function($){
   var offset = 300, offset_opacity = 1200, scroll_top_duration = 700, $back_to_top = $('.to-top');
   $(window).scroll(function(){
      ($(this).scrollTop() > offset) ? $back_to_top.addClass('to-is-visible') : $back_to_top.removeClass('to-is-visible');
   });
   $back_to_top.on('click', function(event){
      event.preventDefault();
      $('body,html').animate({
         scrollTop: 0 ,
      }, scroll_top_duration);
   });
});
