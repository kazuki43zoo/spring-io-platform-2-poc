<!DOCTYPE html>
<meta charset="utf-8">
<meta name="contextPath" content="${pageContext.request.contextPath}" />
<sec:csrfMetaTags />
<title>Home</title>
<link rel="stylesheet" href="<c:url value="/resources/app/css/styles.css" />">
<script src="<c:url value="/resources/vendor/jquery/jquery-2.1.4.js" />"></script>
<script type="text/javascript">
    var contextPath = $("meta[name='contextPath']").attr("content");

    function getMessage() {
        $message = $("#message");
        $message.text("");
        $.ajax("http://spring.example.io:8080" + contextPath + "/cros/global/message", {
            type : "GET",
            data : $("#inputForm").serialize(),
            dataType : "text"
            }).done(function(json) {
                $message.text(json);
            }).fail(function(xhr) {
                $message.text(xhr.statusText);
            }
        );
        return false;
    }
</script>
</head>
<body>
    <div id="wrapper">
        <h1>Hello world!</h1>
        <p>The time on the server is ${serverTime}.</p>
    </div>
    <form id="inputForm">
        Message ID : <input type="text" name="messageId"> <br>
        Message Arguments : <input type="text" name="args"><input type="text" name="args"><input type="text" name="args"><br>
        <button type="button" onclick="getMessage()">Get</button>
    </form>
    <div id="message"></div>
</body>
</html>
