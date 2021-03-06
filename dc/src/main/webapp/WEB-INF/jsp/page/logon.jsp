<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>会员登录</title>
<script type="text/javascript" src="/js/jquery.js"></script>
<link type="text/css" href="/style/main.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="chrome-extension://imamemhokkdleoelohnmkimbmpfglcil/css/capture.css"><style id="__huaban_Style">#__huaban_Container {font-family: 'helvetica neue', arial, sans-serif; position: absolute; padding-top: 37px; z-index: 100000002; top: 0; left: 0; background-color: transparent; opacity: 1;hasLayout:-1;}#__huaban_Overlay {position: fixed; z-index: 100000001; top: 0; right: 0; bottom: 0; left: 0; background-color: #f2f2f2; opacity: .95;}* html #__huaban_Overlay {position: absolute;}#__huaban_Control {position:relative; z-index: 100000; float: left; background-color: #fcf9f9; border: solid #ccc; border-width: 0 1px 1px 0; height: 200px; width: 200px; opacity: 1;}* html #__huaban_Control {position:static;}#__huaban_Control img {position: relative; padding: 0; display: block; margin: 82px auto 0; -ms-interpolation-mode: bicubic;}#__huaban_Control a {position: fixed; z-index: 10001; right: 0; top: 0; left: 0; height: 24px; padding: 12px 0 0; text-align: center; font-size: 14px; line-height: 1em; text-shadow: 0 1px #fff; color: #211922; font-weight: bold; text-decoration: none; background: #fff url(http://huaban.com/img/fullGradient07Normal.png) 0 0 repeat-x; border-bottom: 1px solid #ccc; -mox-box-shadow: 0 0 2px #d7d7d7; -webkit-box-shadow: 0 0 2px #d7d7d7;}* html #__huaban_Control a {position: absolute; width: 100%;}#__huaban_Control a:hover {color: #fff; text-decoration: none; background-color: #1389e5; border-color: #1389e5; text-shadow: 0 -1px #46A0E6;}#__huaban_Control a:active {height: 23px; padding-top: 13px; background-color: #211922; border-color: #211922; background-image: url(http://huaban.com/img/fullGradient07Inverted.png); text-shadow: 0 -1px #211922;}.__huabanImagePreview {position: relative; padding: 0; margin: 0; float: left; background-color: #fff; border: solid #e7e7e7; border-width: 0 1px 1px 0; height: 200px; width: 200px; opacity: 1; z-index: 10002; text-align: center; overflow:hidden;}.__huabanImagePreview .__huabanVideoIcon {position:absolute;display:block;top:0;left:0;width:100%;height:100%;background:url(http://huaban.com/img/media_video.png) center center no-repeat;}.__huabanImagePreview .__huabanImg {border: none; height: 200px; width: 200px; opacity: 1; padding: 0; position: absolute; top: 0;}.__huabanImagePreview .__huabanImg a {margin: 0; padding: 0; position: absolute; top: 0; bottom: 0; right: 0; left: 0; display: block; text-align: center;  z-index: 1;}.__huabanImagePreview .__huabanImg a:hover {background-color: #fcf9f9; border: none;}.__huabanImagePreview .__huabanImg .ImageToPin {max-height: 200px; max-width: 200px; width: auto !important; height: auto !important;}.__huabanImagePreview img.__huaban_PinIt {border: none; position: absolute; top: 82px; left: 42px; display: none; padding: 0; background-color: transparent; z-index: 100;}.__huabanImagePreview strong {text-indent: -9999px; position: absolute; top: 82px; display: none; height: 32px; background: url(http://huaban.com/img/bm_pin_sprite.png?20120801) no-repeat 0 0;}.__huabanImagePreview strong.__huaban_ThunderPin {width: 24px; left: 52px; background-position: 0 0;}.__huabanImagePreview strong.__huaban_ThunderPin:hover {background-position: 0 -50px;}.__huabanImagePreview strong.__huaban_ThunderPin:active {background-position: 0 -100px;}.__huabanImagePreview strong.__huaban_Pin {width: 72px; left: 75px; background-position: -40px 0;}.__huabanImagePreview strong.__huaban_Pin:hover {background-position: -40px -50px;}.__huabanImagePreview strong.__huaban_Pin:active {background-position: -40px -100px;}.__huabanImagePreview img.__huaban_vidind {border: none; position: absolute; top: 75px; left: 75px; padding: 0; background-color: transparent; z-index: 99;}.__huabanDimensions { color: #000; position: relative; margin-top: 180px; text-align: center; font-size: 10px; z-index:10003; display: inline-block; background: white; border-radius: 4px; padding: 0 2px;}#__huaban_Button, #__huaban_Button *, #__huaban_Container, #__huaban_Container * { -webkit-box-sizing: content-box; -moz-box-sizing: content-box; -ms-box-sizing: content-box; box-sizing: content-box;}#__huaban_Button { display: block; position: absolute; z-index: 999999999 !important; color: #211922; text-shadow: 0 1px #eaeaea; font: 12px/1 'Helvetica Neue',Helvetica,Arial,Sans-serif; text-align: center; padding: 0; margin: 0; cursor: pointer;}#__huaban_Button a {text-decoration: none; color: #211922; display: inline-block; text-align: center; line-height: 14px; height: 14px; border-radius: 2px; -webkit-border-radius: 2px; -moz-border-radius: 2px; -ms-border-radius: 2px; -o-border-radius: 2px; cursor: pointer; position: absolute; top: 0; left: 0; height: 14px; margin: 0 2px; padding: 5px 8px; border: 1px solid #555; border: 1px solid rgba(140, 126, 126, .5); background-color: #fff;}#__huaban_Button a:hover {text-decoration: none; background-image: -webkit-linear-gradient(top, #fefeff, #efefef); background-image: -moz-linear-gradient(top, #fefeff, #efefef); box-shadow: inset 0 1px rgba(255,255,255,0.35), 0 1px 1px rgba(35,24,24,0.75); -o-box-shadow: inset 0 1px rgba(255,255,255,0.35), 0 1px 1px rgba(35,24,24,0.75); -ms-box-shadow: inset 0 1px rgba(255,255,255,0.35), 0 1px 1px rgba(35,24,24,0.75); -moz-box-shadow: inset 0 1px rgba(255,255,255,0.35), 0 1px 1px rgba(35,24,24,0.75); -webkit-box-shadow: inset 0 1px rgba(255,255,255,0.35), 0 1px 1px rgba(35,24,24,0.75);}#__huaban_Button a:active {text-decoration: none; background-image: -webkit-linear-gradient(top, #fefeff, #efefef); background-image: -moz-linear-gradient(top, #fefeff, #efefef); box-shadow: inset 0 1px 2px rgba(34,25,25,0.25), 0 0 1px rgba(232,230,230,0.5); -o-box-shadow: inset 0 1px 2px rgba(34,25,25,0.25), 0 0 1px rgba(232,230,230,0.5); -ms-box-shadow: inset 0 1px 2px rgba(34,25,25,0.25), 0 0 1px rgba(232,230,230,0.5); -moz-box-shadow: inset 0 1px 2px rgba(34,25,25,0.25), 0 0 1px rgba(232,230,230,0.5); -webkit-box-shadow: inset 0 1px 2px rgba(34,25,25,0.25), 0 0 1px rgba(232,230,230,0.5);}#__huaban_Button a strong {position: relative; line-height: 12px;}#__huaban_Button a.thunderpin {margin-right: 0; border-right: none; width: 14px; padding: 5px 0 5px 4px; border-top-right-radius: 0; border-bottom-right-radius: 0; -webkit-border-top-right-radius: 0; -webkit-border-bottom-right-radius: 0; -moz-border-top-right-radius: 0; -moz-border-bottom-right-radius: 0; -ms-border-top-right-radius: 0; -ms-border-bottom-right-radius: 0; -o-border-top-right-radius: 0; -o-border-bottom-right-radius: 0;}#__huaban_Button a.thunderpin em {background: url(http://huaban.com/img/ActionIcons10.png?20120801) no-repeat -30px 0; position: relative; display: inline-block; width: 10px; height: 10px; top: 1px; left: -2px;}#__huaban_Button a.thunderpin:hover em {background-image-postion: -30px -10px;}#__huaban_Button a.thunderpin:active em {background-image-postion: -30px -20px;}#__huaban_Button a.pin {left: 20px; width: 64px; margin-left: 0; *margin-left: -2px; border-top-left-radius: 0; border-bottom-left-radius: 0; -webkit-border-top-left-radius: 0; -webkit-border-bottom-left-radius: 0; -moz-border-top-left-radius: 0; -moz-border-bottom-left-radius: 0; -ms-border-top-left-radius: 0; -ms-border-bottom-left-radius: 0; -o-border-top-left-radius: 0; -o-border-bottom-left-radius: 0;}.__huaban_Button_share {text-indent: -9999px; width: 80px; height: 24px; padding:0; background: url(http://huaban.com/img/sharebutton_sprite.png?20120801) no-repeat 0 -80px; background: url(http://huaban.com/img/sharebutton_sprite.png?20120801) no-repeat 0 -80px !important; border: none;}.__huaban_Button_share:hover {background-position: 0 -120px; background-position: 0 -120px !important;}.__huaban_Button_share_top {background-position: 0 -80px; background-position: 0 -80px !important;}.__huaban_Button_share_top:hover {background-position: 0 -120px; background-position: 0 -120px !important;}.__huaban_Button_share_bottom {background-position: 0 0; background-position: 0 0 !important;}.__huaban_Button_share_bottom:hover {background-position: 0 -40px; background-position: 0 -40px !important;}.__huaban_thunder_tip { height: 16px; position: absolute; z-index: 999999999 !important; background: #000; background: rgba(0,0,0,0.5); color: #ddd; line-height: 16px; padding: 5px; border-radius: 2px; margin-left: 2px; }.__huabanImagePreview .__huaban_thunder_tip {height: 12px; line-height: 12px; padding: 10px; font-size: 14px; top: 50%; left: 50%; margin-left: -48px; margin-top: -18px;}.__huaban_thunder_tip_success {color: #fff;font-weight: bold; height: 32px;}.__huaban_thunder_tip p {font-weight: normal; text-align: center; margin-top: 2px;}.__huaban_thunder_tip a {color: #fff;}.__huaban_thunder_tip_failed {height: 32px; font-weight: bold; color: #fff;background: #c90000; background: rgba(201, 0, 0, .5); }.__huaban_thunder_tip_failed p {margin: 0 2px; font-weight: normal; font-size: 12px;}.__huaban_thunder_tip span { padding-left: 18px; position: relative;}.__huaban_thunder_tip span em { background: url(http://huaban.com/img/bm_pin_sprite.png?20120801) no-repeat 0px -150px; display: inline-block; height: 16px; width: 16px; position: absolute; left: 0; top: 50%; margin-top: -8px;}.__huaban_thunder_tip_success span em { background: url(http://huaban.com/img/bm_pin_sprite.png?20120801) no-repeat 0px -150px;}.__huaban_thunder_tip_failed span em { background: url(http://huaban.com/img/bm_pin_sprite.png?20120801) no-repeat -40px -150px;}.__huaban_thunder_tip_ing span em { background: url(http://huaban.com/img/thunder_motion.gif?20120801) no-repeat 2px 2px;}.__huabanImagePreview .__huaban_thunder_tip_failed {height: 32px; width: 140px; margin-left: -80px; margin-top: -26px;}.__huabanImagePreview .__huaban_thunder_tip_success {height: 32px; width: 88px; margin-left: -52px; margin-top: -26px;}.__huabanImagePreview .__huaban_thunder_tip_ing {width: 72px;}.__huabanImagePreview .__huaban_thunder_tip_success p, .__huabanImagePreview .__huaban_thunder_tip_failed p {margin-top: 9px; font-size: 12px; display: block;}</style></head>
<body huaban_screen_capture_injected="true">
<!-- start-->
<div id="login">
<div id="login_header"></div>
<form id="command" name="myFrm" action="/logon" method="post">
<div id="conTop">

		<dl>
			<dt><label id="userIcon" for="userName"></label></dt>
			<dd><input id="userName" name="userName" class="inputText" placeholder="请输入用户名" type="text" value=""></dd>
		</dl>
		
		<dl>
			<dt><label id="pswIcon" for="password"></label></dt>
			<dd><input id="password" name="password" class="inputText" placeholder="请输入密码" type="password" value=""></dd>
		</dl>
		
</div>
<div id="conBottom"></div>
<div id="loginNav">
	<a id="submitBt" href="javascript:submit();">登录</a>
</div>
</form>
</div>
<!-- 开发嵌入end-->
<script>
	$(document).ready(function() {
			var userName = getCookie("userName");
			if (userName != null && userName != "") {
				$("#userName").val(userName);
			}
		//}
	});
	

	document.onkeypress = keypress;
	if (this.parent != this) {
		this.parent.location = "/logon";
	}


	function submit() {

		var email = $("#userName").val();
			document.myFrm.submit();

	}

	/**
	 * 图片验证码刷新
	 */

	function keypress(e) {
		var currKey = 0, e = e || event;
		if (e.keyCode == 13)
			submit();
	}
</script>

</body>
</html>