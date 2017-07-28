<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello!</title>
</head>
<body>
<h2>There are ${dates?size} cartoons available</h2>
<#list dates as date>
<a href="cartoons?date=${date}">${date}</a><br/>
</#list>
</body>
</html>