<!doctype html>

<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Cartoon strips for ${date}</title>
</head>

<body>
<h1>Cartoons for ${date}</h1>
created by <a href="https://github.com/philippkrauss/cartoongrabber">cartoongrabber</a><br/>
<a href="dates">Back to date selection...</a><br/><br/>

<#list cartoons as cartoon>
<a href="#ref_${cartoon.name}">${cartoon.name}</a><br/>
</#list>
<#list cartoons as cartoon>
<h1 id="ref_${cartoon.name}">${cartoon.name}</h1>
<img src="${cartoon.imageUrl}"/><br/>
Downloaded from <a href="${cartoon.sourceUrl}">${cartoon.sourceUrl}</a>
<br/>
</#list>
<br/><a href="dates">Back to date selection...</a>
</body>
</html>