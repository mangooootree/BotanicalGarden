<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">
<@c.page>
    <div align="center">
        <a href="showForm">New plant</a>
        <br><br>
        <#if showForm??>
            <form action="newPlant" method="post">
                <input type="text" name="name" placeholder="plant name" required>
                <select name="type">
                    <#list types as type>
                        <option>${type.name()}</option>
                    </#list>
                </select>
                <input type="submit">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            </form>
        </#if>
    </div>
    <br>
    <table align="center" cellpadding="30">
        <tr>
            <td>
                ID
            </td>
            <td>
                Name
            </td>
            <td>
                Type
            </td>
            <#if isAdmin>
                <td>
                    Action
                </td>
            </#if>
        </tr>
        <#list plants as plant>
            <#if plant.planted>
                <#assign color = "#ffffff">
            <#else>
                <#assign color = "#ffffcc">
            </#if>
            <tr bgcolor="${color}">
                <td>
                    ${plant.id}
                </td>
                <td>
                    ${plant.name}
                </td>
                <td>
                    ${plant.plantType.name()}
                </td>
                <td>
                    <#if plant.isPlanted()>
                        <a href="/curePlant?id=${plant.id}">cure</a>
                        <a href="/removePlant?id=${plant.id}">remove</a>
                    </#if>
                    <#if isAdmin>
                        <br>
                        <div style="text-align: right"><a href="/deletePlant?id=${plant.id}">x</a></div>
                    </#if>
                </td>
            </tr>
        </#list>
    </table>
</@c.page>