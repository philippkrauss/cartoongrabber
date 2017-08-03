<!doctype html>

<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Cartoon strips for ${date}</title>
    <script>
        function setImageVisible(id) {
            var img = document.getElementById('img_' + id);
            img.style.display = 'inline';
            var div = document.getElementById('div_' + id);
            div.style.display = 'none'
        }
    </script>
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
Downloaded from <a href="${cartoon.sourceUrl}">${cartoon.sourceUrl}</a> <br/>
<#if isOldMap.isOld(cartoon) >
<img id="img_${cartoon.name}" src="${cartoon.imageUrl}" style="display: none"/>
<div id="div_${cartoon.name}">
    <h2>No new cartoon for ${date}! <a href="javascript:setImageVisible('${cartoon.name}')">Show anyway!</a></h2>
</div>
<#else>
<img src="${cartoon.imageUrl}"/>
</#if>
<br/>
</#list>
<br/><a href="dates">Back to date selection...</a>
</body>
</html>