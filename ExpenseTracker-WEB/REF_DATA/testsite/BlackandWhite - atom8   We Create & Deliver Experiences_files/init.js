    $(window).resize(function ()
    {
        RepositionNav();
    });

$(function ()
{
    $("body").queryLoader2(
    {
        barColor: "#cccccc",
        backgroundColor: "#FFFFFF",
        percentage: true,
        barHeight: 1,
        completeAnimation: "grow",
        minimumTime: 100,
        onComplete: function ()
        {
            $("#content").fadeIn(1000);
        },
    });
    $('#featured').orbit();
    $('.footer_thin').mouseenter(function ()
    {
        $('.footer_thin').animate(
        {
            height: 111
        }, 800);
        $(this).dequeue();
    });
    $('.footer_thin, #content, .header').mouseout(function ()
    {
        $('.footer_thin').animate(
        {
            height: 25
        }, 800);
        $(this).dequeue();
    });
    RepositionNav();
    $('#about_img').parallax("0%", 1300, 0.4, true, false, true, false);
    $('.service_graphic_panel').parallax("0%", 2000, 0.4, true, false, false, true);
    $('.cc2').parallax("0%", 4950, 0.4, true, false, false, true);
    $('.cc1').parallax("0%", 4750, 0.4, true, false, true, false);
    var deck = new $.scrolldeck(
    {
        slides: '.slide',
        buttons: '#navigationMenu ul li a',
        easing: 'easeOutBack' //easeInOutExpo
    });
    $('.company_circle').click(function (e)
    {
        e.preventDefault();
        $("#active_about").removeClass("company_circle_ac team_circle_ac career_circle_ac").addClass("company_circle_ac");
        $('#about_img').removeClass('company_visual team_visual career_visual').fadeOut(500).queue(function ()
        {
            $(this).addClass('company_visual')
            $(this).dequeue();
        }).fadeIn(1000);
        $('.team_content').hide();
        $('.career_content').hide();
        $('.company_content').fadeIn("slow");
    });
    $('.team_circle').click(function (e)
    {
        e.preventDefault();
        $("#active_about").removeClass("company_circle_ac team_circle_ac career_circle_ac").addClass("team_circle_ac");
        $('#about_img').removeClass('company_visual team_visual career_visual').fadeOut(500).queue(function ()
        {
            $(this).addClass('team_visual')
            $(this).dequeue();
        }).fadeIn(1000);
        $('.team_content').fadeIn("slow");
        $('.company_content').hide();
        $('.career_content').hide();
    });
    $('.career_circle').click(function (e)
    {
        e.preventDefault();
        $("#active_about").removeClass("company_circle_ac team_circle_ac career_circle_ac").addClass("career_circle_ac");
        $('#about_img').removeClass('company_visual team_visual career_visual').fadeOut(500).queue(function ()
        {
            $(this).addClass('career_visual')
            $(this).dequeue();
        }).fadeIn(1000);
        $('.company_content').hide();
        $('.team_content').hide();
        $('.career_content').fadeIn("slow");
    });
    $('#mobile_service').click(function (e)
    {
        e.preventDefault();
        $(".serviceswrapper").hide();
        $("#service_comm").show("slide", {
            direction: "right"
        }, 1000);
        $("#rmd").removeClass("selected").addClass("selected");
        $("#wdd,#cuud").removeClass("selected");
        $('#service_uicnt').hide();
        $('#service_mobcnt').hide();
        $('#service_webcnt').fadeIn("slow");
    });
    $('#creative_service').click(function (e)
    {
        e.preventDefault();
        $(".serviceswrapper").hide();
        $("#service_comm").show("slide", {
            direction: "right"
        }, 1000);
        $("#cuud").removeClass("selected").addClass("selected");
        $("#wdd,#rmd").removeClass("selected");
        $('#service_webcnt').hide();
        $('#service_mobcnt').hide();
        $('#service_uicnt').fadeIn("slow");
    });
    $('#webdesign_service').click(function (e)
    {
        e.preventDefault();
        $(".serviceswrapper").hide();
        $("#service_comm").show("slide", {
            direction: "right"
        }, 1000);
        $("#wdd").removeClass("selected").addClass("selected");
        $("#cuud,#rmd").removeClass("selected");
        $('#service_webcnt').hide();
        $('#service_uicnt').hide();
        $('#service_mobcnt').fadeIn("slow");
    });
    $('#wdd').click(function (e)
    {
        e.preventDefault();
        $("#wdd").removeClass("selected").addClass("selected");
        $("#cuud,#rmd").removeClass("selected");
        $('#service_visuals').removeClass('web_visual ui_visual mobile_visual').fadeOut(500).queue(function ()
        {
            $(this).addClass('web_visual')
            $(this).dequeue();
        }).fadeIn(1000);
        $('#service_uicnt').hide();
        $('#service_mobcnt').hide();
        $('#service_webcnt').fadeIn("slow");
    });
    $('#cuud').click(function (e)
    {
        e.preventDefault();
        $("#cuud").removeClass("selected").addClass("selected");
        $("#wdd,#rmd").removeClass("selected");
        $('#service_visuals').removeClass('web_visual ui_visual mobile_visual').fadeOut(500).queue(function ()
        {
            $(this).addClass('ui_visual')
            $(this).dequeue();
        }).fadeIn(1000);
        $('#service_webcnt').hide();
        $('#service_mobcnt').hide();
        $('#service_uicnt').fadeIn("slow");
    });
    $('#rmd').click(function (e)
    {
        e.preventDefault();
        $("#rmd").removeClass("selected").addClass("selected");
        $("#wdd,#cuud").removeClass("selected");
        $('#service_visuals').removeClass('web_visual ui_visual mobile_visual').fadeOut(500).queue(function ()
        {
            $(this).addClass('mobile_visual')
            $(this).dequeue();
        }).fadeIn(1000);
        $('#service_webcnt').hide();
        $('#service_uicnt').hide();
        $('#service_mobcnt').fadeIn("slow");
    });
    $("#backServices").click(function (e)
    {
        e.preventDefault();
        $("#service_comm").hide();
        $(".serviceswrapper").show("slide", {
            direction: "left"
        }, 1000);
    });
    $("#web_portfolio_screen, #ui_portfolio_screen, #mobile_portfolio_screen").click(function (e)
    {
        e.preventDefault();
        var idS = $(this).attr("id");
        var totalImages = 12;
        var path = "web";
        switch (idS)
        {
        case "web_portfolio_screen":
            totalImages = 12;
            path = "web";
            $(".ui_portfolio, .mobile_portfolio").hide();
            $(".web_portfolio").show();
            break;
        case "ui_portfolio_screen":
            totalImages = 2;
            path = "UI";
            $(".web_portfolio, .mobile_portfolio").hide();
            $(".ui_portfolio").show();
            break;
        case "mobile_portfolio_screen":
            totalImages = 2;
            path = "mobile";
            $(".ui_portfolio, .web_portfolio").hide();
            $(".mobile_portfolio").show();
            break;
        default:
        }
        $("#web_portfolio_screen li, #ui_portfolio_screen li, #mobile_portfolio_screen li").removeClass("selected");
        $("#" + idS + " li").addClass("selected");
        $("#" + idS + "_panel").s3Slider(
        {
            timeOut: 1000
        });
    });
    $("#carrerNav").click(function (e)
    {
        e.preventDefault();
        $("#contact_info").fadeOut(400, function ()
        {
            $(".enquiryform").hide("fast", function ()
            {
                $(".rightside_contact_nomedia").show();
                $(".careerform").fadeIn(1000);
                $("#backContact").fadeIn(1000);
            });
        });
    });
    $("#enquiryNav").click(function (e)
    {
        e.preventDefault();
        $("#contact_info").fadeOut(500, function ()
        {
            $(".careerform").hide("fast", function ()
            {
                $(".rightside_contact_nomedia").show();
                $(".enquiryform").fadeIn(1000);
                $("#backContact").fadeIn(1000);
            });
        });
    });
    $("#backContact").click(function (e)
    {
        e.preventDefault();
        $(".careerform, .enquiryform, .rightside_contact_nomedia").hide();
        $("#contact_info").fadeIn(1000);
        $("#backContact").fadeOut(1000);
    });
    $('#web_portfolio_screen_panel').s3Slider(
    {
        timeOut: 1000
    });
    ///Resume upload
    $('#file_upload').uploadify(
    {
        'swf': 'uploadify.swf',
        'uploader': 'resume.php',
        'method': 'post',
        'folder': '/bw_2012/uploads',
        'auto': true,
        'multi': false,
        'fileTypeDesc': 'Document Files',
        'removeCompleted': false,
        'fileTypeExts': '*.pdf; *.doc; *.docx; *.txt;*.PDF; *.DOC; *.DOCX; *.TXT;',
        'onUploadSuccess': function (file, data, response)
        {
            if (data != 'error' && data != '')
            {
                var getfilename = $('#filename').val() + data + "@";
                var stlen = getfilename.length;
                $('#filename').val(getfilename.substr(0, stlen));
                //$('#file_upload-queue').hide();
            }
            else
            {
                $("#msgbox12").html('Could not upload your files').removeClass().addClass('messageboxokhome').fadeTo(900);
            }
        }
        // Put your options here
    });
});