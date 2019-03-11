<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Botanical garden</title>
    </head>
    <body>
    <#include "navbar.ftl">
    <#nested>
    </div>
    </body>
    </html>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit"><#if user??>Sign Out<#else>Log in</#if></button>
    </form>
</#macro>