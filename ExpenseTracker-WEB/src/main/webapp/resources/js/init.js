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
     
    
    $("#web_portfolio_screen, #ui_portfolio_screen").click(function (e)
    {
        e.preventDefault();
        var idS = $(this).attr("id");
        switch (idS)
        {
        case "web_portfolio_screen":
           
            $(".ui_portfolio, .mobile_portfolio").hide();
            $(".web_portfolio").show();
            break;
        case "ui_portfolio_screen":
            
            $(".web_portfolio, .mobile_portfolio").hide();
            $(".ui_portfolio").show();
            break;
        
        default:
        }
        $("#web_portfolio_screen li, #ui_portfolio_screen li").removeClass("selected");
        $("#" + idS + " li").addClass("selected");
        $("#" + idS + "_panel").s3Slider(
        {
            timeOut: 2000
        });
    });
   
    
    
    $('#web_portfolio_screen_panel').s3Slider(
    {
        timeOut: 2000
    });
    
});