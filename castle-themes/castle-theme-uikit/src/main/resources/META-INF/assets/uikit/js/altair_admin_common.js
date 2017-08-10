/*
*  Altair Admin
*  author: tzd
*
*  1. Init functions
*  2. Helpers / Variables
*  3. Common functions & variables
*
* */

// 1. Init common functions on document ready

    $(function() {
        "use strict";

        // page onload functions
        altair_page_onload.init();

        // main header
        altair_main_header.init();

        // main sidebar
        altair_main_sidebar.init();
        // secondary sidebar
        altair_secondary_sidebar.init();

        // top bar
        altair_top_bar.init();

        // page heading
        altair_page._heading();
        // page aside
        altair_page._aside();
        // page aside
        altair_page._overflow();

        // material design
        altair_md.init();

        // forms
        altair_forms.init();

        // truncate text helper
        altair_helpers.truncate_text($('.truncate-text'));

        // full screen
        altair_helpers.full_screen();

        // table check
        altair_helpers.table_check();

        // print page
        altair_helpers.print_page();

        // scroll to top
        //altair_helpers.scroll_to_top();

        // uikit custom
        altair_uikit.accordion_user_selected();

    });


// 2. Helpers

    /* Detect hi-res devices */
    function isHighDensity() {
        return ((window.matchMedia && (window.matchMedia('only screen and (min-resolution: 124dpi), only screen and (min-resolution: 1.3dppx), only screen and (min-resolution: 48.8dpcm)').matches || window.matchMedia('only screen and (-webkit-min-device-pixel-ratio: 1.3), only screen and (-o-min-device-pixel-ratio: 2.6/2), only screen and (min--moz-device-pixel-ratio: 1.3), only screen and (min-device-pixel-ratio: 1.3)').matches)) || (window.devicePixelRatio && window.devicePixelRatio > 1.3));
    }

    /* Calculate Scrollbar Width (http://chris-spittles.co.uk/jquery-calculate-scrollbar-width/) */
    function scrollbarWidth(){var a=jQuery('<div style="width: 100%; height:200px;">test</div>'),b=jQuery('<div style="width:200px;height:150px; position: absolute; top: 0; left: 0; visibility: hidden; overflow:hidden;"></div>').append(a),c=a[0],a=b[0];jQuery("body").append(a);c=c.offsetWidth;b.css("overflow","scroll");a=a.clientWidth;b.remove();return c-a};

    /* random id generator */
    function randID_generator() {
        var randLetter = String.fromCharCode(65 + Math.floor(Math.random() * 26));
        return randLetter + Date.now();
    }

    /* detect IE */
    function detectIE(){var a=window.navigator.userAgent,b=a.indexOf("MSIE ");if(0<b)return parseInt(a.substring(b+5,a.indexOf(".",b)),10);if(0<a.indexOf("Trident/"))return b=a.indexOf("rv:"),parseInt(a.substring(b+3,a.indexOf(".",b)),10);b=a.indexOf("Edge/");return 0<b?parseInt(a.substring(b+5,a.indexOf(".",b)),10):!1};

    /* reverse array */
    jQuery.fn.reverse = [].reverse;

    /* serialize form */
    $.fn.serializeObject = function() {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function() {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };

    /* hex 2 rgba conversion */
    function hex2rgba(hex,opacity){
        hex = hex.replace('#','');
        r = parseInt(hex.substring(0,2), 16);
        g = parseInt(hex.substring(2,4), 16);
        b = parseInt(hex.substring(4,6), 16);

        result = 'rgba('+r+','+g+','+b+','+opacity/100+')';
        return result;
    }

    /* Modernizr test for localStorage */
    function lsTest(){
        var test = 'test';
        try {
            localStorage.setItem(test, test);
            localStorage.removeItem(test);
            return true;
        } catch(e) {
            return false;
        }
    }

    // selectize plugin
    if(typeof $.fn.selectize != 'undefined') {

        // inline dropdown
        Selectize.define('dropdown_after', function(options) {
            this.positionDropdown = (function () {
                var $control = this.$control,
                    position = $control.position(),
                    position_left = position.left,
                    position_top = position.top + $control.outerHeight(true) + 32;

                this.$dropdown.css({
                    width : $control.outerWidth(),
                    top   : position_top,
                    left  : position_left
                });

            });
        });

        // tooltip
        Selectize.define('tooltip', function(options) {
            var self = this;
            this.setup = (function() {
                var original = self.setup;
                return function() {
                    original.apply(this, arguments);
                    var $wrapper = this.$wrapper,
                        $input = this.$input;
                    if($input.attr('title')) {
                        $wrapper
                            .attr('title', $input.attr('title'))
                            .attr('data-uk-tooltip', $input.attr('data-uk-tooltip'));
                    }
                };
            })();
        });

    }



// 3. Common functions & variables

    // 3.1 material design easing
    // 3.2 page onload init functions
    // 3.3 page content
    // 3.4 forms
    // 3.5 main sidebar / main menu (left)
    // 3.6 secondary sidebar (right)
    // 3.7 top bar
    // 3.8 main header
    // 3.9 material design
    // 3.10 common helpers
    // 3.11 uikit custom


    // 3.1 material design easing
    easing_swiftOut = [ 0.35,0,0.25,1 ];
    bez_easing_swiftOut = $.bez(easing_swiftOut);

    var $body = $('body'),
        $html = $('html'),
        $document = $(document),
        $window = $(window),
        $page_content = $('#page_content'),
        $page_content_inner = $('#page_content_inner'),
        $sidebar_main = $('#sidebar_main'),
        $sidebar_main_toggle = $('#sidebar_main_toggle'),
        $sidebar_secondary = $('#sidebar_secondary'),
        $sidebar_secondary_toggle = $('#sidebar_secondary_toggle'),
        $topBar = $('#top_bar'),
        $pageHeading = $('#page_heading'),
        $pageAside = $('#page_aside'),
        $pageOverflow = $('#page_overflow'),
        $header_main = $('#header_main'),
        header__main_height = 48;


    // 3.2 page init functions
    altair_page_onload = {
        init: function() {
            $window.on('load',function(){
                // fire functions on window load
                altair_helpers.hierarchical_show();
                altair_helpers.hierarchical_slide();
                // add animations when all partials are loaded
                $body.removeClass('disable_transitions');
            });
        }
    };

    // 3.3 page content
    altair_page_content = {
        hide_content_sidebar: function() {
            if(!$body.hasClass('header_double_height')) {
                //$page_content.css('max-height',$html.height() - 40);
                $html.css({
                    'paddingRight': scrollbarWidth(),
                    'overflow': 'hidden'
                });
            }
        },
        show_content_sidebar: function() {
            if(!$body.hasClass('header_double_height')) {
                //$page_content.css('max-height','');
                $html.css({
                    'paddingRight': '',
                    'overflow': ''
                });
            }
        }
    };

    // 3.4 forms
    altair_forms = {
        init: function() {
            altair_forms.textarea_autosize();
            altair_forms.select_elements();
            altair_forms.switches();
            altair_forms.dynamic_fields();
        },
        textarea_autosize: function() {
            $textarea = $('textarea.md-input').not('.no_autosize');
            if($textarea.hasClass('selecize_init')) {
                autosize.destroy($textarea);
            }
            autosize($textarea);
            $textarea.addClass('selecize_init');
        },
        select_elements: function(parent) {

            var $selectize = parent ? $(parent).find('select') : $("[data-md-selectize],.data-md-selectize");

            $selectize.each(function(){
                var $this = $(this);
                if(!$this.hasClass('selectized')) {
                    var thisPosBottom = $this.attr('data-md-selectize-bottom'),
                        sel_plugins = ['tooltip'];

                    $this
                        .after('<div class="selectize_fix"></div>')
                        .selectize({
                            plugins: sel_plugins,
                            hideSelected: true,
                            dropdownParent: 'body',
                            onDropdownOpen: function($dropdown) {
                                $dropdown
                                    .hide()
                                    .velocity('slideDown', {
                                        begin: function() {
                                            if (typeof thisPosBottom !== 'undefined') {
                                                $dropdown.css({'margin-top':'0'})
                                            }
                                        },
                                        duration: 200,
                                        easing: easing_swiftOut
                                    })
                            },
                            onDropdownClose: function($dropdown) {
                                $dropdown
                                    .show()
                                    .velocity('slideUp', {
                                        complete: function() {
                                            if (typeof thisPosBottom !== 'undefined') {
                                                $dropdown.css({'margin-top': ''})
                                            }
                                        },
                                        duration: 200,
                                        easing: easing_swiftOut
                                    });
                            },
                            onChange: function(value) {
                                if( !!$this.attr('data-parsley-id')) {
                                    $this.parsley().validate();
                                }
                            },
                            onInitialize: function() {
                                if($this[0].selectize.isRequired) {
                                    $this.prop('required', true);
                                }
                            }
                        });
                }
            });

            // dropdowns
            var $selectize_inline = $("[data-md-selectize-inline]");

            $selectize_inline.each(function(){
                var $this = $(this);
                if(!$this.hasClass('selectized')) {
                    var thisPosBottom = $this.attr('data-md-selectize-bottom');
                    $this
                        .after('<div class="selectize_fix"></div>')
                        .closest('div').addClass('uk-position-relative')
                        .end()
                        .selectize({
                            plugins: [
                                'dropdown_after'
                            ],
                            dropdownParent: $this.closest('div'),
                            hideSelected: true,
                            onDropdownOpen: function($dropdown) {
                                $dropdown
                                    .hide()
                                    .velocity('slideDown', {
                                        begin: function() {
                                            if (typeof thisPosBottom !== 'undefined') {
                                                $dropdown.css({'margin-top':'0'})
                                            }
                                        },
                                        duration: 200,
                                        easing: easing_swiftOut
                                    })
                            },
                            onDropdownClose: function($dropdown) {
                                $dropdown
                                    .show()
                                    .velocity('slideUp', {
                                        complete: function() {
                                            if (typeof thisPosBottom !== 'undefined') {
                                                $dropdown.css({'margin-top': ''})
                                            }
                                        },
                                        duration: 200,
                                        easing: easing_swiftOut
                                    });
                            },
                            onChange: function(value) {
                                if( !!$this.attr('data-parsley-id')) {
                                    $this.parsley().validate();
                                }
                            }
                        });
                }
            })

        },
        // switchery plugin
        switches: function(parent) {
            var $elem = parent ? $(parent).find('[data-switchery]') : $('[data-switchery]');
            if($elem.length) {
                $elem.each(function() {
                    if(!$(this).siblings('.switchery').length) {
                        var $this = this,
                            this_size = $($this).attr('data-switchery-size'),
                            this_color = $($this).attr('data-switchery-color'),
                            this_secondary_color = $($this).attr('data-switchery-secondary-color');

                        var switchery = new Switchery($this, {
                            color: (typeof this_color !== 'undefined') ? hex2rgba(this_color,50) : hex2rgba('#009688',50),
                            jackColor: (typeof this_color !== 'undefined') ? hex2rgba(this_color,100) : hex2rgba('#009688',100),
                            secondaryColor: (typeof this_secondary_color !== 'undefined') ? hex2rgba(this_secondary_color,50) : 'rgba(0, 0, 0,0.26)',
                            jackSecondaryColor: (typeof this_secondary_color !== 'undefined') ? hex2rgba(this_secondary_color,50) : '#fafafa',
                            className: 'switchery' + ( (typeof this_size !== 'undefined') ? ' switchery-'+ this_size : '' )
                        });

                        $(this).data('ObjSwitchery', switchery);

                    }
                })
            }
        },
        parsley_validation_config: function() {
            window.ParsleyConfig = {
                excluded: 'input[type=button], input[type=submit], input[type=reset], input[type=hidden], input.exclude_validation',
                trigger: 'change',
                errorsWrapper: '<div class="parsley-errors-list"></div>',
                errorTemplate: '<span></span>',
                errorClass: 'md-input-danger',
                successClass: 'md-input-success',
                errorsContainer: function (ParsleyField) {
                    var element = ParsleyField.$element;
                    return element.closest('.parsley-row');
                },
                classHandler: function (ParsleyField) {
                    var element = ParsleyField.$element;
                    if( element.is(':checkbox') || element.is(':radio') || element.parent().is('label') || $(element).is('[data-md-selectize]') ) {
                        return element.closest('.parsley-row');
                    }
                }
            };
        },
        parsley_extra_validators: function() {
            window.ParsleyConfig = window.ParsleyConfig || {};
            window.ParsleyConfig.validators = window.ParsleyConfig.validators || {};

            window.ParsleyConfig.validators.date = {
                fn: function (value) {

                    var matches = /^(\d{2})[.\/](\d{2})[.\/](\d{4})$/.exec(value);
                    if (matches == null) return false;

                    var parts = value.split(/[.\/-]+/),
                        day = parseInt(parts[1], 10),
                        month = parseInt(parts[0], 10),
                        year = parseInt(parts[2], 10);

                    if (year == 0 || month == 0 || month > 12) {
                        return false;
                    }
                    var monthLength = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
                    if (year % 400 == 0 || (year % 100 != 0 && year % 4 == 0)) {
                        monthLength[1] = 29;
                    }
                    return day > 0 && day <= monthLength[month - 1];
                },
                priority: 256
            };
        },
        dynamic_fields: function(parent,resize,fields_on_load) {
            function add_fields(wrapper,template,index) {

                var source   = $("#"+template).html(),
                    tmpl = Handlebars.compile(source),
                    theCompiledHtml = tmpl({
                        index: index ? index : 0,
                        counter:  index ? '__' + index : '__' + 0
                    });

                wrapper.append(theCompiledHtml);

                // initialize inputs
                altair_md.inputs(wrapper);
                // initialize checkboxes
                altair_md.checkbox_radio(wrapper.find('[data-md-icheck]'));
                // initialize switches
                altair_forms.switches(wrapper);
                // initialize selectize
                altair_forms.select_elements(wrapper);

            }
            var $dynamic_fields = parent ? $(parent).find('[data-dynamic-fields]') : $('[data-dynamic-fields]');
            $dynamic_fields.each(function () {
                var $this = $(this).attr('dynamic-fields-counter',0),
                    this_template = $this.data('dynamicFields');

                if(!fields_on_load) {
                    add_fields($this,this_template);
                }

                $this
                    // add section
                    .on('click','.btnSectionClone',function (e) {
                        e.preventDefault();
                        $this.find('.btnSectionClone').replaceWith('<a href="#" class="btnSectionRemove"><i class="material-icons md-24">&#xE872;</i></a>');

                        var index = parseInt($this.attr('dynamic-fields-counter')) + 1;
                        $this.attr('dynamic-fields-counter',index);

                        add_fields($this,this_template,index);

                        if(resize) {
                            $window.resize();
                        }
                    })
                    // remove section
                    .on('click', '.btnSectionRemove', function(e) {
                        e.preventDefault();
                        var $this = $(this);
                        $this.closest('.form_section').next('.form_hr').remove().end().remove();
                        if(resize) {
                            $window.resize();
                        }
                    });

            });
        }
    };

    // 3.5 main sidebar (left)
    altair_main_sidebar = {
        init: function() {
            if($sidebar_main.length) {
                // check if browser support localstorage
                if(lsTest()) {
                    // check if mini sidebar is enabled
                    if(localStorage.getItem("altair_sidebar_mini") !== null) {
                        $body.addClass('sidebar_mini');
                    }
                    if(localStorage.getItem("altair_sidebar_slim") !== null) {
                        $body.addClass('sidebar_slim');
                    }
                }

                if ($body.hasClass('sidebar_mini')) {

                    // small sidebar
                    altair_main_sidebar.mini_sidebar();

                    setTimeout(function() {
                        $window.resize();
                    }, 280);

                } else if($body.hasClass('sidebar_slim')) {

                    // slim sidebar
                    altair_main_sidebar.slim_sidebar();

                    // custom scroller
                    altair_helpers.custom_scrollbar($sidebar_main);

                    // menu
                    altair_main_sidebar.sidebar_menu();

                    setTimeout(function() {
                        $window.resize();
                    }, 280);

                } else {

                    $sidebar_main_toggle.on('click', function(e) {
                        e.preventDefault();
                        ( $body.hasClass('sidebar_main_active') || ($body.hasClass('sidebar_main_open') && $window.width() >= 1220) ) ? altair_main_sidebar.hide_sidebar() : altair_main_sidebar.show_sidebar();
                    });
                    // hide sidebar (outside click/esc key pressed)
                    $document.on('click keyup', function(e) {
                        if( $body.hasClass('sidebar_main_active') && $window.width() < 1220 ) {
                            if (
                                ( !$(e.target).closest($sidebar_main).length && !$(e.target).closest($sidebar_main_toggle).length )
                                || ( e.keyCode == 27 )
                            ) {
                                altair_main_sidebar.hide_sidebar();
                            }
                        }
                    });

                    // custom scroller
                    altair_helpers.custom_scrollbar($sidebar_main);

                    if( $body.hasClass('sidebar_main_active') && $window.width() < 1220 ) {
                        altair_page_content.hide_content_sidebar();
                    } else {
                        altair_page_content.show_content_sidebar();
                    }
                    if( $body.hasClass('sidebar_main_open') && $window.width() < 1220 ) {
                        $body.removeClass('sidebar_main_open')
                    }

                    // menu
                    altair_main_sidebar.sidebar_menu();
                    // swipe to open (touch devices)
                    altair_main_sidebar.swipe_open();

                }

                // language switcher
                altair_main_sidebar.lang_switcher();
            }
        },
        hide_sidebar: function() {

            $body.addClass('sidebar_main_hiding').removeClass('sidebar_main_active sidebar_main_open');
            if( $window.width() < 1220 ) {
                altair_page_content.show_content_sidebar();
            }
            setTimeout(function() {
                $body.removeClass('sidebar_main_hiding');
                $window.resize();
            },290);

        },
        show_sidebar: function() {

            $body.addClass('sidebar_main_active');
            if( $window.width() < 1220 && !$body.find('#page_overflow').length ) {
                altair_page_content.hide_content_sidebar();
            }
            setTimeout(function() {
                $window.resize();
            },290);

        },
        sidebar_menu: function() {
            // check for submenu
            $sidebar_main.find('.menu_section > ul').find('li').each(function() {
                var hasChildren = $(this).children('ul').length;
                if(hasChildren) {
                    $(this).addClass('submenu_trigger')
                }
            });
            // toggle sections
            $('.submenu_trigger > a').on('click',function(e) {
                e.preventDefault();
                var $this = $(this);
                var slideToogle = $this.next('ul').is(':visible') ? 'slideUp' : 'slideDown';
                // accordion mode
                var accordion_mode = $sidebar_main.hasClass('accordion_mode');
                $this.next('ul')
                    .velocity(slideToogle, {
                        duration: 400,
                        easing: easing_swiftOut,
                        begin: function() {
                            if(slideToogle == 'slideUp') {
                                $(this).closest('.submenu_trigger').removeClass('act_section')
                            } else {
                                if(accordion_mode) {
                                    $this.closest('li').siblings('.submenu_trigger').each(function() {
                                        $(this).children('ul').velocity('slideUp', {
                                            duration: 400,
                                            easing: easing_swiftOut,
                                            begin: function() {
                                                $(this).closest('.submenu_trigger').removeClass('act_section')
                                            }
                                        })
                                    })
                                }
                                $(this).closest('.submenu_trigger').addClass('act_section')
                            }
                        },
                        complete: function() {
                            if(slideToogle !== 'slideUp') {
                                var scrollContainer = $sidebar_main.find(".scroll-content").length ? $sidebar_main.find(".scroll-content") :  $sidebar_main.find(".scrollbar-inner");
                                $this.closest('.act_section').velocity("scroll", {
                                    duration: 500,
                                    easing: easing_swiftOut,
                                    container: scrollContainer
                                });
                            }
                        }
                    });
            });

            // open section/add classes if children has class .act_item
            $sidebar_main.find('.act_item')
                .each(function() {
                    var $this = $(this);
                    // top parent
                    $this.parents('.submenu_trigger').last().addClass('current_section');
                    // all parents
                    $this.parents('.submenu_trigger').addClass('act_section').children('ul').show();
                    // active item
                    $this.children('ul').show();
                });
        },
        lang_switcher: function() {
            var $lang_switcher = $('#lang_switcher');

            if($lang_switcher.length) {
                $lang_switcher.selectize({
                    options: [
                        {id: 1, title: 'English', value: 'gb'},
                        {id: 2, title: 'French', value: 'fr'},
                        {id: 3, title: 'Chinese', value: 'cn'},
                        {id: 4, title: 'Dutch', value: 'nl'},
                        {id: 5, title: 'Italian', value: 'it'},
                        {id: 6, title: 'Spanish', value: 'es'},
                        {id: 7, title: 'German', value: 'de'},
                        {id: 8, title: 'Polish', value: 'pl'}
                    ],
                    render: {
                        option: function(data, escape) {
                            return  '<div class="option">' +
                                '<i class="item-icon flag-' + escape(data.value).toUpperCase() + '"></i>' +
                                '<span>' + escape(data.title) + '</span>' +
                                '</div>';
                        },
                        item: function(data, escape) {
                            return '<div class="item"><i class="item-icon flag-' + escape(data.value).toUpperCase() + '"></i></div>';
                        }
                    },
                    valueField: 'value',
                    labelField: 'title',
                    searchField: 'title',
                    create: false,
                    hideSelected: true,
                    onDropdownOpen: function($dropdown) {
                        $dropdown
                            .hide()
                            .velocity('slideDown', {
                                begin: function() {
                                    $dropdown.css({'margin-top':'-33px'})
                                },
                                duration: 200,
                                easing: easing_swiftOut
                            })
                    },
                    onDropdownClose: function($dropdown) {
                        $dropdown
                            .show()
                            .velocity('slideUp', {
                                complete: function() {
                                    $dropdown.css({'margin-top':''})
                                },
                                duration: 200,
                                easing: easing_swiftOut
                            })
                    }
                });

                $lang_switcher.next().children('.selectize-input').find('input').attr('readonly',true);

            }

        },
        swipe_open: function() {
            if( $body.hasClass('sidebar_main_swipe') && Modernizr.touch) {
                $body.append('<div id="sidebar_swipe_area" style="position: fixed;left: 0;top:0;z-index:1000;width:16px;height:100%"></div>');

                var sidebar_swipe_area = document.getElementById("sidebar_swipe_area");

                mc = new Hammer.Manager(sidebar_swipe_area);
                mc.add(new Hammer.Swipe({
                    threshold: 0,
                    pointers: 2,
                    velocity: 0
                }));

                mc.on("swiperight", function() {
                    if (!$body.hasClass('sidebar_main_active')) {
                        altair_main_sidebar.show_sidebar();
                    }
                });

            }
        },
        mini_sidebar: function() {

            $sidebar_main_toggle.hide();

            $sidebar_main.find('.menu_section > ul').children('li').each(function() {
                var hasChildren = $(this).children('ul').length;
                if(hasChildren) {
                    $(this).addClass('sidebar_submenu');
                    if($(this).find('.act_item').length) {
                        $(this).addClass('current_section');
                    }
                } else {
                    $(this).attr({
                        'data-uk-tooltip': "{pos:'right'}"
                    });
                }
            });

            $body
                .addClass('sidebar_mini')
                .removeClass('sidebar_main_active sidebar_main_open sidebar_main_swipe');

        },
        slim_sidebar: function() {
            $sidebar_main_toggle.hide();

            $body
                .addClass('sidebar_slim sidebar_slim_inactive')
                .removeClass('sidebar_main_active sidebar_main_open sidebar_main_swipe');

            $sidebar_main
                .mouseenter(function() {
                    $body.removeClass('sidebar_slim_inactive');
                    $body.addClass('sidebar_slim_active');
                })
                .mouseleave(function() {
                    $body.addClass('sidebar_slim_inactive');
                    $body.removeClass('sidebar_slim_active');
                })

        }
    };

    // secondary sidebar (right)
    altair_secondary_sidebar = {
        init: function() {
            if($sidebar_secondary.length) {
                $sidebar_secondary_toggle.removeClass('sidebar_secondary_check');

                $sidebar_secondary_toggle.on('click', function (e) {
                    e.preventDefault();
                    $body.hasClass('sidebar_secondary_active') ? altair_secondary_sidebar.hide_sidebar() : altair_secondary_sidebar.show_sidebar();
                });

                // hide sidebar (outside/esc click)
                $document.on('click keydown', function(e) {
                    if($body.hasClass('sidebar_secondary_persisten')) {
                        return;
                    }
                    if( $body.hasClass('sidebar_secondary_active') && ( ( !$(e.target).closest($sidebar_secondary).length && !$(e.target).closest($sidebar_secondary_toggle).length ) || (e.which == 27) )) {
                        altair_secondary_sidebar.hide_sidebar();
                    }
                });

                // hide page sidebar on page load
                if ( $body.hasClass('sidebar_secondary_active') ) {
                    altair_secondary_sidebar.hide_sidebar();
                }

                // custom scroller
                altair_helpers.custom_scrollbar($sidebar_secondary);

                // chat section
                altair_secondary_sidebar.chat_sidebar();

            }
        },
        hide_sidebar: function() {
            $body.removeClass('sidebar_secondary_active');
        },
        show_sidebar: function() {
            $body.addClass('sidebar_secondary_active');
        },
        chat_sidebar: function() {
            if($sidebar_secondary.find('.md-list.chat_users').length) {

                $('.md-list.chat_users').children('li').on('click',function() {
                    $('.md-list.chat_users').velocity("transition.slideRightBigOut", {
                        duration: 280,
                        easing: easing_swiftOut,
                        complete: function() {
                            $sidebar_secondary
                                .find('.chat_box_wrapper')
                                .addClass('chat_box_active')
                                .velocity("transition.slideRightBigIn", {
                                    duration: 280,
                                    easing: easing_swiftOut,
                                    begin: function() {
                                        $sidebar_secondary.addClass('chat_sidebar')
                                    }
                                })
                        }
                    });
                });

                $sidebar_secondary
                    .find('.chat_sidebar_close')
                    .on('click',function() {
                        $sidebar_secondary
                            .find('.chat_box_wrapper')
                            .removeClass('chat_box_active')
                            .velocity("transition.slideRightBigOut", {
                                duration: 280,
                                easing: easing_swiftOut,
                                complete: function () {
                                    $sidebar_secondary.removeClass('chat_sidebar')
                                    $('.md-list.chat_users').velocity("transition.slideRightBigIn", {
                                        duration: 280,
                                        easing: easing_swiftOut
                                    })
                                }
                            })
                    });

                if($sidebar_secondary.find('.uk-tab').length) {
                    $sidebar_secondary.find('.uk-tab').on('change.uk.tab',function(event, active_item, previous_item) {
                        if($(active_item).hasClass('chat_sidebar_tab') && $sidebar_secondary.find('.chat_box_wrapper').hasClass('chat_box_active')) {
                            $sidebar_secondary.addClass('chat_sidebar')
                        } else {
                            $sidebar_secondary.removeClass('chat_sidebar')
                        }
                    })
                }
            }
        }
    };

    // top bar
    altair_top_bar = {
        init: function () {
            if($topBar.length) {
                $body.addClass('top_bar_active');
            }
        }
    };

    altair_page = {
        // page heading
        _heading: function () {
            if($pageHeading.length) {
                $body.addClass('page_heading_active');
            }
        },
        // page aside
        _aside: function () {
            if($pageAside.length) {
                function calculateHeight() {
                    var viewportHeight = $window.height(),
                        asideTop = $pageAside.offset().top;
                    $pageAside.height(viewportHeight - asideTop);
                }
                calculateHeight();
                $window.on('debouncedresize',function() {
                    calculateHeight();
                });

                // toggle aside
                $('.page_aside_toggle').on('click', function (e) {
                    e.preventDefault();
                    $body.toggleClass('page_aside_collapsed');
                })

            }
        },
        _overflow: function () {
            if($pageOverflow.length) {
                function calculateHeight() {
                    var viewportHeight = $window.height(),
                        overflowTop = $pageOverflow.offset().top,
                        height = viewportHeight - overflowTop;

                    if($pageOverflow.children('.uk-overflow-container').length) {
                        $pageOverflow.children('.uk-overflow-container').height(height);
                    } else {
                        $pageOverflow.height(height);
                    }

                }
                calculateHeight();
                $window.on('debouncedresize',function() {
                    calculateHeight();
                });
            }
        }
    };

    // main header
    altair_main_header = {
        init: function() {
            altair_main_header.search_activate();
            altair_main_header.search_autocomplete();
        },
        search_activate: function() {

            $('#main_search_btn').on('click',function(e) {
                e.preventDefault();
                altair_main_header.search_show();
            });

            // hide main search
            $(document).on('click keydown', function(e) {
                if( !$body.hasClass('main_search_persistent') && $body.hasClass('main_search_active') ) {
                    if (
                        ( !$(e.target).closest('.header_main_search_form').length && !$(e.target).closest('#main_search_btn').length )
                        || ( e.which == 27 )
                    ) {
                        altair_main_header.search_hide();
                    }
                }
            });

            $('.header_main_search_close').on('click', function() {
                altair_main_header.search_hide();
            });

            if($body.hasClass('main_search_persistent')) {
                altair_main_header.search_show();
            }
        },
        search_show: function() {
            $header_main
                .children('.header_main_content')
                .velocity("transition.slideUpBigOut", {
                    duration: 280,
                    easing: easing_swiftOut,
                    begin: function() {
                        $body.addClass('main_search_active');
                    },
                    complete: function() {
                        $header_main
                            .children('.header_main_search_form')
                            .velocity("transition.slideDownBigIn", {
                                duration: 280,
                                easing: easing_swiftOut,
                                complete: function() {
                                    $('.header_main_search_input').focus();
                                }
                            })
                    }
                });
        },
        search_hide: function() {
            $header_main
                .children('.header_main_search_form')
                .velocity("transition.slideUpBigOut", {
                    duration: 280,
                    easing: easing_swiftOut,
                    begin: function() {
                        $header_main.velocity("reverse");
                        $body.removeClass('main_search_active');
                    },
                    complete: function() {
                        $header_main
                            .children('.header_main_content')
                            .velocity("transition.slideDownBigIn", {
                                duration: 280,
                                easing: easing_swiftOut,
                                complete: function() {
                                    $('.header_main_search_input').blur().val('');
                                }
                            })
                    }
                });
        },
        search_autocomplete: function() {
            // 'on item selected' event
            $('.header_main_search_form').children('[data-uk-autocomplete]').on('selectitem.uk.autocomplete', function(event, data, acobject) {
                console.log(event);
                console.log(data);
                console.log(acobject);
            });
        }
    };

    // material design
    altair_md = {
        init: function() {
            altair_md.inputs();
            altair_md.checkbox_radio();
            altair_md.card_fullscreen();
            altair_md.card_expand();
            altair_md.card_overlay();
            altair_md.card_single();
            altair_md.card_panel();
            altair_md.card_progress();
            altair_md.list_outside();
            // FAB transitions
            altair_md.fab_speed_dial();
            altair_md.fab_toolbar();
            altair_md.fab_sheet();
            altair_md.wave_effect();
        },
        // card toggle fullscreen
        card_fullscreen: function() {
            $('.md-card-fullscreen-activate').on('click',function() {
                var $thisCard = $(this).closest('.md-card');

                if(!$thisCard.hasClass('md-card-fullscreen')) {
                    // get card atributes
                    var mdCard_h = $thisCard.height(),
                        mdCardToolbarFixed = $(this).hasClass('toolbar_fixed'),
                        mdCard_w = $thisCard.width(),
                        body_scroll_top = $body.scrollTop(),
                        mdCard_offset = $thisCard.offset();

                    // create placeholder for card
                    $thisCard.after('<div class="md-card-placeholder" style="width:'+ mdCard_w+'px;height:'+ mdCard_h+'px;"/>');
                    // add overflow hidden to #page_content (fix for ios)
                    //$body.addClass('md-card-fullscreen-active');
                    // add width/height to card (preserve original size)
                    $thisCard
                        .addClass('md-card-fullscreen')
                        .css({
                            'width': mdCard_w,
                            'height': mdCard_h,
                            'left': mdCard_offset.left,
                            'top': mdCard_offset.top - body_scroll_top
                        })
                        // animate card to top/left position
                        .velocity(
                            {
                                left: 0,
                                top: 0
                            },
                            {
                                duration: 400,
                                easing: easing_swiftOut,
                                begin: function(elements) {
                                    // add back button
                                    var $toolbar = $thisCard.find('.md-card-toolbar');
                                    if($toolbar.length) {
                                        $toolbar.prepend('<span class="md-icon md-card-fullscreen-deactivate material-icons uk-float-left">&#xE5C4;</span>');
                                    } else {
                                        $thisCard.append('<span class="md-icon md-card-fullscreen-deactivate material-icons uk-position-top-right" style="margin:10px 10px 0 0">&#xE5CD;</span>')
                                    }
                                    altair_page_content.hide_content_sidebar();
                                }
                            }
                        // resize card to full width/height
                        ).velocity(
                            {
                                height: '100%',
                                width: '100%'
                            },
                            {
                                duration: 400,
                                easing: easing_swiftOut,
                                complete: function(elements) {
                                    // show fullscreen content
                                    $thisCard.find('.md-card-fullscreen-content').velocity("transition.slideUpBigIn", {
                                        duration: 400,
                                        easing: easing_swiftOut,
                                        complete: function(elements) {
                                            // activate onResize callback for some js plugins
                                            $(window).resize();
                                        }
                                    });
                                    if(mdCardToolbarFixed) {
                                        $thisCard.addClass('mdToolbar_fixed')
                                    }
                                }
                            }
                        );
                }
            });

            $page_content.on('click', '.md-card-fullscreen-deactivate', function() {
                // get card placeholder width/height and offset
                var $thisPlaceholderCard = $('.md-card-placeholder'),
                    mdPlaceholderCard_h = $thisPlaceholderCard.height(),
                    mdPlaceholderCard_w = $thisPlaceholderCard.width(),
                    body_scroll_top = $body.scrollTop(),
                    mdPlaceholderCard_offset_top = $thisPlaceholderCard.offset().top - body_scroll_top,
                    mdPlaceholderCard_offset_left = $thisPlaceholderCard.offset().left,
                    $thisCard = $('.md-card-fullscreen'),
                    mdCardToolbarFixed = $thisCard.hasClass('mdToolbar_fixed');

                    $thisCard
                        // resize card to original size
                        .velocity(
                            {
                                height: mdPlaceholderCard_h,
                                width: mdPlaceholderCard_w
                            },
                            {
                                duration: 400,
                                easing: easing_swiftOut,
                                begin: function(elements) {
                                    // hide fullscreen content
                                    $thisCard.find('.md-card-fullscreen-content').velocity("transition.slideDownOut",{ duration: 275, easing: easing_swiftOut });
                                    if(mdCardToolbarFixed) {
                                        $thisCard.removeClass('mdToolbar_fixed')
                                    }
                                },
                                complete: function(elements) {
                                    // activate onResize callback for js plugins
                                    $window.resize();
                                    // remove back button
                                    $thisCard.find('.md-card-fullscreen-deactivate').remove();
                                    altair_page_content.show_content_sidebar();
                                }
                            }
                        )
                        // move card to original position
                        .velocity(
                            {
                                left: mdPlaceholderCard_offset_left,
                                top: mdPlaceholderCard_offset_top
                            },
                            {
                                duration: 400,
                                easing: easing_swiftOut,
                                complete: function(elements) {
                                    // remove some styles added by velocity.js
                                    $thisCard.removeClass('md-card-fullscreen').css({
                                        width: '',
                                        height: '',
                                        left: '',
                                        top: ''
                                    });
                                    // remove card placeholder
                                    $thisPlaceholderCard.remove();
                                    // remove overflow:hidden from #page_content (ios fix)
                                    $body.removeClass('md-card-fullscreen-active');
                                }
                            }
                        );
            });
        },
        card_expand: function() {
            // expand elements
            $(".md-expand").velocity("transition.expandIn", { stagger: 175, drag: true });
            $(".md-expand-group").children().velocity("transition.expandIn", { stagger: 175, drag: true });
        },
        card_overlay: function() {
            var $md_card = $('.md-card');

            // replace toggler icon (x) when overlay is active
            $md_card.each(function() {
                var $this = $(this);
                if($this.hasClass('md-card-overlay-active')) {
                    $this.find('.md-card-overlay-toggler').html('&#xE5CD;')
                }
            });

            // toggle card overlay
            $md_card.on('click','.md-card-overlay-toggler', function(e) {
                e.preventDefault();
                if(!$(this).closest('.md-card').hasClass('md-card-overlay-active')) {
                    $(this)
                        .html('&#xE5CD;')
                        .closest('.md-card').addClass('md-card-overlay-active');

                } else {
                    $(this)
                        .html('&#xE5D4;')
                        .closest('.md-card').removeClass('md-card-overlay-active');
                }
            })
        },
        card_single: function() {
            var $md_card_single = $('.md-card-single');
            if($md_card_single && $body.hasClass('header_double_height')) {
                function md_card_content_height() {
                    var content_height = $window.height() - ((header__main_height * 2) + 12);
                    $md_card_single.find('.md-card-content').innerHeight(content_height);
                }
                md_card_content_height();
                $window.on('debouncedresize',function() {
                    md_card_content_height();
                });
            }
        },
        card_panel: function() {

            $('.md-card-close').on('click',function(e) {
                e.preventDefault();
                var $this = $(this),
                    thisCard = $this.closest('.md-card'),
                    removeCard = function() {
                        $(thisCard).remove();
                    };
                altair_md.card_show_hide(thisCard,undefined,removeCard)
            });

            $('.md-card-toggle').on('click',function(e) {
                e.preventDefault();
                var $this = $(this),
                    thisCard = $this.closest('.md-card');

                $(thisCard).toggleClass('md-card-collapsed').children('.md-card-content').slideToggle('280', bez_easing_swiftOut);

                $this.velocity({
                    scale: 0,
                    opacity: 0.2
                }, {
                    duration: 280,
                    easing: easing_swiftOut,
                    complete: function() {
                        $(thisCard).hasClass('md-card-collapsed') ? $this.html('&#xE313;') : $this.html('&#xE316;');
                        $this.velocity('reverse');
                        $window.resize();
                    }
                });

            });

            $('.md-card-collapsed').each(function() {
                var $card = $(this),
                    $this_toggle = $card.find('.md-card-toggle');

                $this_toggle.html('&#xE313;');
                $card.children('.md-card-content').hide();
            })

        },
        card_show_hide: function(card,begin_callback,complete_callback,callback_element) {
            $(card)
                .velocity({
                    scale: 0,
                    opacity: 0.2
                }, {
                    duration: 400,
                    easing: easing_swiftOut,
                    // on begin callback
                    begin: function () {
                        if (typeof begin_callback !== 'undefined') {
                            begin_callback(callback_element);
                        }
                    },
                    // on complete callback
                    complete: function () {
                        if (typeof complete_callback !== 'undefined') {
                            complete_callback(callback_element);
                        }
                    }
                })
                .velocity('reverse');
        },
        card_progress: function(card,percent,steps) {
            var $toolbar_progress = card ? $(card).children('.md-card-toolbar') : $('[data-toolbar-progress]');
            $toolbar_progress.each(function() {
                var $this = $(this),
                    bg_percent = percent ? parseInt(percent) : parseInt($this.attr('data-toolbar-progress')),
                    progress_steps = $this.attr('data-toolbar-progress-steps');

                if(steps || progress_steps) {
                    if(bg_percent > 66) {
                        var bg_color = '#dcedc8';
                    } else if(bg_percent > 33) {
                        var bg_color = '#ffecb3';
                    } else {
                        var bg_color = '#ffcdd2';
                    }
                } else {
                    var bg_color_default = $this.attr('data-toolbar-bg-default');
                    if(!bg_color_default) {
                        var bg_color = $this.css('backgroundColor');
                        $this.attr('data-toolbar-bg-default',bg_color)
                    } else {
                        var bg_color = bg_color_default;
                    }
                }

                if(percent) {
                    $this.attr('data-toolbar-progress',percent);
                }

                var bg_theme = '#fff';

                $this
                    .css({ 'background': '-moz-linear-gradient(left, '+bg_color+' '+bg_percent+'%, '+ bg_theme+' '+(bg_percent)+'%)'})
                    .css({ 'background': 'linear-gradient(to right,  '+bg_color+' '+bg_percent+'%, '+bg_theme+' '+(bg_percent)+'%)'})
                    .css({ 'background': '-webkit-linear-gradient(left, '+bg_color+' '+bg_percent+'%, '+bg_theme+' '+(bg_percent)+'%)'});

            })
        },
        list_outside: function() {
            var $md_list_outside_wrapper = $('.md-list-outside-wrapper');
            if($md_list_outside_wrapper && $body.hasClass('header_double_height')) {
                function md_list_outside_height() {
                    // check header height
                    var content_height = $window.height() - ((header__main_height * 2) + 10);
                    $md_list_outside_wrapper.height(content_height);
                }
                md_list_outside_height();
                $window.on('debouncedresize',function() {
                    md_list_outside_height();
                });
                altair_helpers.custom_scrollbar($md_list_outside_wrapper);
            }
        },
        inputs: function(parent) {
            var $mdInput = (typeof parent === 'undefined') ? $('.md-input') : $(parent).find('.md-input');
            $mdInput.each(function() {
                if(!$(this).closest('.md-input-wrapper').length) {
                    var $this = $(this),
                        extraClass = '';

                    if($this.is('[class*="uk-form-width-"]')) {
                        var elClasses = $this.attr('class').split (' ');
                        for(var i = 0; i < elClasses.length; i++){
                            var classPart = elClasses[i].substr(0,14);
                            if(classPart == "uk-form-width-"){
                                var extraClass = elClasses[i];
                            }
                        }
                    }

                    if( $this.prev('label').length ) {
                        $this.prev('label').andSelf().wrapAll('<div class="md-input-wrapper"/>');
                    } else if($this.siblings('[data-uk-form-password]').length) {
                        $this.siblings('[data-uk-form-password]').andSelf().wrapAll('<div class="md-input-wrapper"/>');
                    } else {
                        $this.wrap('<div class="md-input-wrapper"/>');
                    }
                    $this.closest('.md-input-wrapper').append('<span class="md-input-bar '+extraClass+'"/>');

                    altair_md.update_input($this);
                }
                $body
                    .on('focus', '.md-input', function() {
                        $(this).closest('.md-input-wrapper').addClass('md-input-focus')
                    })
                    .on('blur', '.md-input', function() {
                        $(this).closest('.md-input-wrapper').removeClass('md-input-focus');
                        if(!$(this).hasClass('label-fixed')) {
                            if($(this).val() != '') {
                                $(this).closest('.md-input-wrapper').addClass('md-input-filled')
                            } else {
                                $(this).closest('.md-input-wrapper').removeClass('md-input-filled')
                            }
                        }
                    })
                    .on('change', '.md-input', function() {
                        altair_md.update_input($(this));
                    });
            })
        },
        checkbox_radio: function(checkbox) {
            var mdCheckbox = (typeof checkbox === 'undefined') ? $("[data-md-icheck],.data-md-icheck") : $(checkbox);
            mdCheckbox.each(function() {
                if( !$(this).next('.iCheck-helper').length ) {
                    $(this)
                        .iCheck({
                            checkboxClass: 'icheckbox_md',
                            radioClass: 'iradio_md',
                            increaseArea: '20%'
                        })
                        // validate inputs on change (parsley)
                        .on('ifChanged', function(event){
                            if ( !!$(this).data('parsley-multiple') ) {
                                $(this).parsley().validate();
                            }
                        });
                }
            });
        },
        update_input: function(object) {
            // clear wrapper classes
            object.closest('.uk-input-group').removeClass('uk-input-group-danger uk-input-group-success');
            object.closest('.md-input-wrapper').removeClass('md-input-wrapper-danger md-input-wrapper-success md-input-wrapper-disabled');

            if(object.hasClass('md-input-danger')) {
                if(object.closest('.uk-input-group').length) {
                    object.closest('.uk-input-group').addClass('uk-input-group-danger')
                }
                object.closest('.md-input-wrapper').addClass('md-input-wrapper-danger')
            }
            if(object.hasClass('md-input-success')) {
                if(object.closest('.uk-input-group').length) {
                    object.closest('.uk-input-group').addClass('uk-input-group-success')
                }
                object.closest('.md-input-wrapper').addClass('md-input-wrapper-success')
            }
            if(object.prop('disabled')) {
                object.closest('.md-input-wrapper').addClass('md-input-wrapper-disabled')
            }
            if(object.val() !== '') {
                object.closest('.md-input-wrapper').addClass('md-input-filled')
            } else {
                object.closest('.md-input-wrapper').removeClass('md-input-filled')
            }
            if(object.hasClass('label-fixed')) {
                object.closest('.md-input-wrapper').addClass('md-input-filled')
            }
        },
        fab_speed_dial: function() {
            function activateFAB(obj) {
                obj.closest('.md-fab-wrapper').addClass('md-fab-active');
                obj.velocity({
                    scale: 0
                }, {
                    duration: 140,
                    easing: easing_swiftOut,
                    complete: function() {
                        obj
                            .velocity({ scale: 1 },{
                                duration: 140,
                                easing: easing_swiftOut
                            })
                            .children().toggle()
                    }
                })
            }
            function deactivateFAB(obj) {
                obj.closest('.md-fab-wrapper').removeClass('md-fab-active');
                obj.velocity({
                    scale: 0
                }, {
                    duration: 140,
                    easing: easing_swiftOut,
                    complete: function() {
                        obj
                            .velocity({ scale: 1 },{
                                duration: 140,
                                easing: easing_swiftOut
                            })
                            .children().toggle()
                    }
                })
            }
            var $fab = $('.md-fab-speed-dial,.md-fab-speed-dial-horizontal');
            $fab.each(function () {
                var $this = $(this),
                    $thisBtn =  $this.children('.md-fab').append('<i class="material-icons md-fab-action-close" style="display:none">&#xE5CD;</i>');

                if($this.is('[data-fab-hover]')) {
                    var deactiveateFabTimeout;
                    $this
                        .on('mouseenter',function() {
                            $this.addClass('md-fab-over');
                            clearTimeout(deactiveateFabTimeout);
                            setTimeout(function() {
                                activateFAB($thisBtn);
                            },100);
                        })
                        .on('mouseleave',function() {
                            deactivateFAB($thisBtn);
                            deactiveateFabTimeout = setTimeout(function() {
                                $this.removeClass('md-fab-over');
                            },500);
                        })
                } else {
                    $thisBtn
                        .on('click',function() {
                            var $this = $(this);
                            if(!$this.closest('.md-fab-wrapper').hasClass('md-fab-active')) {
                                activateFAB($this);
                            } else {
                                deactivateFAB($this);
                            }
                        })
                        .closest('.md-fab-wrapper').find('.md-fab-small')
                        .on('click',function() {
                            deactivateFAB($thisBtn);
                        });
                }
            })
        },
        fab_toolbar: function() {
            var $fab_toolbar = $('.md-fab-toolbar');

            if($fab_toolbar) {
                $fab_toolbar
                    .children('i')
                    .on('click', function(e) {
                        e.preventDefault();

                        var toolbarItems = $fab_toolbar.children('.md-fab-toolbar-actions').children().length;

                        $fab_toolbar.addClass('md-fab-animated');

                        var FAB_padding = !$fab_toolbar.hasClass('md-fab-small') ? 16 : 24,
                            FAB_size = !$fab_toolbar.hasClass('md-fab-small') ? 64 : 44;

                        setTimeout(function() {
                            $fab_toolbar
                                .width((toolbarItems*FAB_size + FAB_padding))
                        },140);

                        setTimeout(function() {
                            $fab_toolbar.addClass('md-fab-active');
                        },420);

                    });

                $document.on('click scroll', function(e) {
                    if( $fab_toolbar.hasClass('md-fab-active') ) {
                        if (!$(e.target).closest($fab_toolbar).length) {

                            $fab_toolbar
                                    .css('width','')
                                    .removeClass('md-fab-active');

                            setTimeout(function() {
                                $fab_toolbar.removeClass('md-fab-animated');
                            },140);

                        }
                    }
                });
            }
        },
        fab_sheet: function() {
            var $fab_sheet = $('.md-fab-sheet');

            if($fab_sheet) {
                $fab_sheet
                    .children('i')
                    .on('click', function(e) {
                        e.preventDefault();

                        var sheetItems = $fab_sheet.children('.md-fab-sheet-actions').children('a').length;

                        $fab_sheet.addClass('md-fab-animated');

                        setTimeout(function() {
                            $fab_sheet
                                .width('240px')
                                .height(sheetItems*40 + 8);
                        },140);

                        setTimeout(function() {
                            $fab_sheet.addClass('md-fab-active');
                        },280);

                    });

                $document.on('click scroll', function(e) {
                    if( $fab_sheet.hasClass('md-fab-active') ) {
                        if (!$(e.target).closest($fab_sheet).length) {

                            $fab_sheet
                                .css({
                                    'height':'',
                                    'width':''
                                })
                                .removeClass('md-fab-active');

                            setTimeout(function() {
                                $fab_sheet.removeClass('md-fab-animated');
                            },140);

                        }
                    }
                });
            }
        },
        wave_effect: function() {
            if(!$html.hasClass('lte-ie9')) {
                Waves.attach('.md-btn-wave,.md-fab-wave', ['waves-button']);
                Waves.attach('.md-btn-wave-light,.md-fab-wave-light', ['waves-button', 'waves-light']);
                Waves.attach('.wave-box', ['waves-float']);
                Waves.init({
                    delay: 300
                });
            }
        }
    };

    // common helpers
    altair_helpers = {
        truncate_text: function($object) {
            $object.each(function() {
                $(this).dotdotdot({
                    watch: "window"
                });
            })
        },
        custom_scrollbar: function($object) {

            if(!$object.children('.scrollbar-inner').length) {
                $object.wrapInner("<div class='scrollbar-inner'></div>");
            }
            if(Modernizr.touch) {
                $object.children('.scrollbar-inner').addClass('touchscroll');
            } else {
                $object.children('.scrollbar-inner').scrollbar({
                    //disableBodyScroll: true,
                    scrollx: false,
                    duration: 100
                });
            }
        },
        hierarchical_show: function(element) {
            var $hierarchical_show = $('.hierarchical_show');

            if($hierarchical_show.length) {

                $hierarchical_show.each(function() {

                    var $this = $(this),
                        thisChildrenLength = $this.children().length,
                        baseDelay = 100;

                    $this.children().each(function(index) {
                        $(this).css({
                            '-webkit-animation-delay': (index * baseDelay) + "ms",
                            'animation-delay': (index * baseDelay) + "ms"
                        });
                    });

                    $this.waypoint({
                        handler: function() {
                            $this.addClass('hierarchical_show_inView');
                            setTimeout(function() {
                                $this
                                    .removeClass('hierarchical_show hierarchical_show_inView fast_animation')
                                    .children()
                                    .css({
                                        '-webkit-animation-delay': '',
                                        'animation-delay': ''
                                    });
                            }, (thisChildrenLength*baseDelay)+1200 );
                            this.destroy();
                        },
                        context: 'window',
                        offset: '90%'
                    });

                })
            }
            if(element) {
                var $this = $(element).addClass('hierarchical_show hierarchical_show_inView'),
                    thisChildrenLength = $this.children().length,
                    baseDelay = 100;

                $this.children().each(function(index) {
                    $(this).css({
                        '-webkit-animation-delay': (index * baseDelay) + "ms",
                        'animation-delay': (index * baseDelay) + "ms"
                    });
                });

                $this.addClass('');
                setTimeout(function() {
                    $this
                        .removeClass('hierarchical_show hierarchical_show_inView fast_animation')
                        .children()
                        .css({
                            '-webkit-animation-delay': '',
                            'animation-delay': ''
                        });
                }, (thisChildrenLength*baseDelay)+1200 );

            }
        },
        hierarchical_slide: function(element) {

            var $hierarchical_slide = $('.hierarchical_slide');
            if($hierarchical_slide.length) {

                $hierarchical_slide.each(function() {
                    var $this = $(this),
                        $thisChildren = $this.attr('data-slide-children') ? $this.children($this.attr('data-slide-children')) : $this.children(),
                        thisChildrenLength = $thisChildren.length,
                        thisContext = $this.attr('data-slide-context') ? $this.closest($this.attr('data-slide-context'))[0] : 'window',
                        baseDelay = 100;

                    if(thisChildrenLength >= 1) {

                        $thisChildren.each(function(index) {
                            $(this).css({
                                '-webkit-animation-delay': (index * baseDelay) + "ms",
                                'animation-delay': (index * baseDelay) + "ms"
                            })
                        });

                        $this.waypoint({
                            handler: function() {
                                $this.addClass('hierarchical_slide_inView');
                                setTimeout(function() {
                                    $this.removeClass('hierarchical_slide hierarchical_slide_inView');
                                    $thisChildren.css({
                                        '-webkit-animation-delay': '',
                                        'animation-delay': ''
                                    });
                                }, (thisChildrenLength*baseDelay)+1200 );
                                this.destroy();
                            },
                            context: thisContext,
                            offset: '90%'
                        });

                    }
                })

            }

            if(element) {

                var $this = $(element).addClass('hierarchical_slide hierarchical_slide_inView'),
                    $thisChildren = $this.attr('data-slide-children') ? $this.children($this.attr('data-slide-children')) : $this.children(),
                    thisChildrenLength = $thisChildren.length,
                    thisContext = $this.attr('data-slide-context') ? $this.closest($this.attr('data-slide-context'))[0] : 'window',
                    baseDelay = 100;

                console.log($thisChildren);

                if(thisChildrenLength >= 1) {

                    $thisChildren.each(function(index) {
                        $(this).css({
                            '-webkit-animation-delay': (index * baseDelay) + "ms",
                            'animation-delay': (index * baseDelay) + "ms"
                        })
                    });

                    setTimeout(function() {
                        $this.removeClass('hierarchical_slide hierarchical_slide_inView');
                        $thisChildren.css({
                            '-webkit-animation-delay': '',
                            'animation-delay': ''
                        });
                    }, (thisChildrenLength*baseDelay)+1200 );

                }

            }
        },
        content_preloader_show: function(style,variant,container,width,height) {

            if(!$body.find('.content-preloader').length) {
                var image_density = isHighDensity() ? '@2x' : '',
                    width = width ? width : 48,
                    height = height ? height : 48;

                var preloader_content = (style == 'regular')
                    ? '<img src="assets/img/spinners/spinner' + image_density + '.gif" alt="" width="32" height="32">'
                    : '<div class="md-preloader"><svg xmlns="http://www.w3.org/2000/svg" version="1.1" height="'+height+'" width="'+width+'" viewbox="0 0 75 75"><circle cx="37.5" cy="37.5" r="33.5" stroke-width="8"/></svg></div>';

                var thisContainer = (typeof container !== 'undefined') ? $(container) : $body;

                thisContainer.append('<div class="content-preloader content-preloader-'+variant+'" style="height:'+height+'px;width:'+width+'px;margin-left:-'+width/2+'px">' + preloader_content + '</div>');
                setTimeout(function() {
                    $('.content-preloader').addClass('preloader-active');
                }, 0);
            }
        },
        content_preloader_hide: function() {
            if($body.find('.content-preloader').length) {
                // hide preloader
                $('.content-preloader').removeClass('preloader-active');
                // remove preloader
                preloader_timeout = window.setTimeout(function() {
                    $('.content-preloader').remove();
                }, 500);
            }
        },
        color_picker: function(object,pallete,callback) {
            if(object) {
                var cp_id = randID_generator(),
                    cp_pallete = pallete ? pallete : ['#e53935','#d81b60','#8e24aa','#5e35b1','#3949ab','#1e88e5','#039be5','#0097a7','#00897b','#43a047','#689f38','#ef6c00','#f4511e','#6d4c41','#757575','#546e7a'],
                    cp_pallete_length = cp_pallete.length,
                    cp_wrapper = $('<div class="cp_altair" id="'+cp_id+'"/>');

                for(var $i=0;$i<cp_pallete_length;$i++) {
                    var span = (cp_pallete[$i].substring(0, 6) == "md-bg-" ) ?
                        '<span data-color=' + cp_pallete[$i] + ' class="' + cp_pallete[$i] + '"></span>'
                        :
                        '<span data-color=' + cp_pallete[$i] + ' style="background:' + cp_pallete[$i] + '"></span>';
                    cp_wrapper.append(span);
                }

                cp_wrapper.append('<input type="hidden">');

                $body.on('click', '#'+cp_id+' span',function() {
                    $(this)
                        .addClass('active_color')
                        .siblings().removeClass('active_color')
                        .end()
                        .closest('.cp_altair').find('input').val($(this).attr('data-color'));
                        if(typeof callback == 'function') {
                            callback.call(this);
                        }
                });

                return object.append(cp_wrapper);

            }
        },
        retina_images: function() {
            if (typeof $.fn.dense !== "undefined") {
                $('img')
                    // set resolution cap at "2"
                    .attr('data-dense-cap',2)
                    .dense({
                        glue: "@",
                        ping: false
                    });
            }
        },
        full_screen: function() {
            $('#full_screen_toggle').on('click',function(e) {
                e.preventDefault();
                screenfull.toggle();
                $window.resize();
            })
        },
        table_check: function() {
            $table_check = $('.table_check');
            $table_check.each(function() {
                var $this = $(this),
                    $checkAll = $this.find('.check_all'),
                    $checkRow = $this.find('.check_row');

                $checkAll
                    .on('ifChecked',function() {
                        $checkRow.iCheck('check');
                    })
                    .on('ifUnchecked',function() {
                        $checkRow.iCheck('uncheck');
                    });

                $checkRow
                    .on('ifChecked',function(event) {
                        $(event.currentTarget).closest('tr').addClass('row_checked');
                    })
                    .on('ifUnchecked',function(event) {
                        $(event.currentTarget).closest('tr').removeClass('row_checked');
                    })


            });
        },
        ie_fix: function() {
            if(detectIE()) {
                $('svg,canvas,video').each(function() {
                    $(this).css('height', 0);
                });
                setTimeout(function() {
                    $('svg,canvas,video').each(function() {
                        var $this = $(this),
                            height = $(this).attr('height'),
                            width = $(this).attr('width');

                        if(height) {
                            $this.css('height', height);
                        }
                        if(width) {
                            $this.css('width', width);
                        }
                        var peity = $this.prev('.peity_data');
                        if(peity.length) {
                            peity.peity().change()
                        }
                    });
                }, 1000)
            }
        },
        print_page: function() {
            $body.on('click','#page_print',function(e) {
                e.preventDefault();
                UIkit.modal.confirm('Do you want to print this page?', function () {
                    // hide sidebar
                    altair_main_sidebar.hide_sidebar();
                    // wait for dialog to fully hide
                    setTimeout(function () {
                        window.print();
                    }, 300)
                }, {
                    labels: {
                        'Ok': 'print'
                    }
                });
            })
        },
        scroll_to_top: function() {
            var $scrollTop = $('#scrollTop');


            if ($scrollTop) {
                $scrollTop.on('click', function(e) {
                    e.preventDefault();
                    UIkit.Utils.scrollToElement(
                        UIkit.$(
                            $scrollTop,
                            {
                                offset: 48,
                                duration: 600
                            }
                        )
                    )
                })
            }

            $(window).on('scroll', function (event) {
                var scroll = $(window).scrollTop();
                if ($scrollTop && scroll > 48) {
                    $scrollTop.fadeIn()
                } else {
                    $scrollTop.fadeOut()
                }
            });


        }
    };

    // uikit custom
    altair_uikit = {
        reinitialize_grid_margin: function() {
            $("[data-uk-grid-margin]").each(function() {
                var element = $(this);
                if (!element.data("gridMargin")) {
                    $.UIkit.gridMargin(element, $.UIkit.Utils.options(element.attr("data-uk-grid-margin")));
                }
            });
            $window.resize();
        },
        accordion_user_selected: function() {
            // open user defined accordion section on page load
            $('[data-accordion-section-open]').each(function() {
                var $elem = $(this);
                setTimeout(function() {
                    var data = $elem.data();
                        sectOpen = data.accordionSectionOpen.toString().split(",");

                    // close all accordion sections
                    $elem.children('.uk-accordion-title.uk-active').each(function() {
                        $(this).trigger('click');
                    });
                    if(!data.accordion.options.collapse && (sectOpen.length > 1)) {
                        // open multiple sections
                        for(var $i = 0;$i <= sectOpen.length;$i++) {
                            $elem.children('.uk-accordion-title').eq(sectOpen[$i] - 1).trigger('click')
                        }
                    } else {
                        $elem.children('.uk-accordion-title').eq(sectOpen[0] - 1).trigger('click')
                    }
                },220);
            })
        }
    };