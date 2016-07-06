
/*  ------------------
    Remove Preloader
    ------------------  */

$(window).load(function () {
    $('#preloader').delay(350).fadeOut('slow', function () {
        //$('.profile-page, .portfolio-page, .service-page, .contact-page').hide();
    });
});

$(document).ready(function () {

    'use strict';

    /*  ---------------------
         Homepage Responsive
        ---------------------  */

    function homepageResponsive() {

        // Homepage Main Portions Responsive

        var windowsWidth = $(window).width(),
            windowsHeight = $(window).height();

        if (windowsWidth > 767) {

            $('.introduction , .menu').css({
                width: '50%',
                height: '100%'
            });

        } else {

            $('.introduction , .menu').css({
                width: '100%',
                height: '50%'
            });

        }

        // Homepage Profile Image Responsive

        var introWidth = $('.introduction').width(),
            introHeight = $('.introduction').height(),
            bgImage = $('.introduction').find('img'),
            menuBgImages = $('.menu > div img');

        if (introWidth > introHeight) {

            bgImage.css({
                width: '100%',
                height: 'auto'
            });
            menuBgImages.css({
                width: '100%',
                height: 'auto'
            });

        } else {

            bgImage.css({
                width: 'auto',
                height: '100%'
            });
            menuBgImages.css({
                width: 'auto',
                height: '100%'
            });

        }

    }

    $(window).on('load resize', homepageResponsive);

    /*  --------------
         Menu Settings
        --------------  */

    function removeHash () {
        history.pushState("", document.title, window.location.pathname
            + window.location.search);
    }

    function hideBoots4Menu()
    {
        var introWidth = $('.introduction').width(),
            menuWidth = $('.menu').width();

        $('.introduction').animate({
            left: '-' + introWidth
        }, 1000, 'easeOutQuart');
        $('.menu').animate({
            left: menuWidth
        }, 1000, 'easeOutQuart', function () {
            $('.home-page').css({
                visibility: 'hidden'
            });
        });
    }


    // Hide Menu
    $('.menu').on('click', '.menu_button' , function () {
        hideBoots4Menu();
    });

//    var mapCanvas = document.getElementById('map-canvas');
//    var mapOptions = {
//        center: new google.maps.LatLng(40.565934, -122.388118),
//        zoom: 16,
//        scrollwheel: false,
//        mapTypeId: google.maps.MapTypeId.ROADMAP
//    };
//    var map = new google.maps.Map(mapCanvas, mapOptions)

//    var marker = new google.maps.Marker({
//            position: new google.maps.LatLng(40.565234, -122.388118),
//            title:"Boots4 Office"
//        });
//
//        // To add the marker to the map, call setMap();
//        marker.setMap(map);

    //google.maps.event.addDomListener(window, 'load', initialize);


    // Show Reletive Page Onclick

    $('.menu').on('click', 'div.menu_button' , function(){
        var selectedPage = $(this).data('url_target');
        window.location.hash = selectedPage;
        $('#'+selectedPage).fadeIn(1200);
        $(window).scrollTop(0);
    });


    $('.menu').on('click', 'div.profile-btn', function () {
        setTimeout(function(){
            $('.count').each(function () {
                $(this).prop('Counter',0).animate({
                    Counter: $(this).text()
                }, {
                    duration: 1500,
                    easing: 'swing',
                    step: function (now) {
                        $(this).text(Math.ceil(now));
                    }
                });
            });
        }, 100);
    });

    $('.menu').on('click', 'div.portfolio-btn', function () {
        setTimeout(function(){
            $('#projects').mixItUp();
        }, 100);
    });



    $('.menu').on('click','div.contact-btn', function () {
        setTimeout(function(){
            //google.maps.event.trigger(map,'resize');
        },100);
    });


    // Close Button, Hide Menu

    $('body').on('click', '.close-btn', function () {
        window.location.hash="";
        $('.home-page').css({
            visibility: 'visible'
        });
        $('.introduction, .menu').animate({
            left: 0
        }, 1000, 'easeOutQuart');
        $('.page').fadeOut(800);
        removeHash ();
        $(window).scrollTop(0);
    });

    /*  ----------------------------------------
         Tooltip Starter for Social Media Icons
        ----------------------------------------  */

    $('.intro-content .social-media [data-toggle="tooltip"]').tooltip({
        placement: 'bottom'
    });

    $('.contact-details .social-media [data-toggle="tooltip"]').tooltip();



    /*----------------------script for owl carousel sponsors---------------------*/

        $("#sponsor-list").owlCarousel({
                 
            autoPlay: 3000, //Set AutoPlay to 3 seconds
            stopOnHover: true,
            items : 3,
            itemsDesktop: [1200,3],
            itemsDesktopSmall: [991,3],
            itemsTablet: [767,2],
            itemsTabletSmall: [625,2],
            itemsMobile: [479,1]
        });



    /*  -------------------------------
         PopUp ( for portfolio page )
        -------------------------------  */

    $(function () {
        $('.show-popup').popup({
            keepInlineChanges: true,
            speed: 500
        });
    });

    // location redirect to first load
    if(window.location.hash !== "" && window.location.hash) {
        var redirectPage = window.location.hash.slice(1);
        $('*[data-url_target="'+redirectPage+'"]').trigger('click');
    }
    

});
