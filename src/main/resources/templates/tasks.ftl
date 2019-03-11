<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">
<@c.page>
    <table align="center" cellpadding="30">
        <tr>
            <td>
                ID
            </td>
            <td>
                Title
            </td>
            <td>
                Date
            </td>
            <td>
                Plant
            </td>
            <td>
                Done
            </td>
            <td>
                Comment
            </td>
            <td>
                Actions
            </td>
            <#if isAdmin>
                <td>
                    Add comment
                </td>
            </#if>
        </tr>
        <#list tasks as task>

            <#if task.isDone()>
                <#assign color = "#ffffcc">
            <#else>
                <#assign color = "#ffffff">
            </#if>

            <tr bgcolor="${color}">
                <td>
                    ${task.id}
                </td>
                <td>
                    ${task.title}
                </td>
                <td>
                    ${task.date}
                </td>
                <td>
                    ${task.plant.id}
                    <br>
                    ${task.plant.name}
                    <br>
                    ${task.plant.plantType.name()}
                </td>
                <td>
                    <#if task.isDone()>
                        Yes
                    <#else>
                        No
                    </#if>
                </td>
                <td>
                    ${(task.comment)!}
                </td>
                <td>
                    <#if isAdmin>
                    <a href="/deleteTask?id=${task.id}">remove</a>
                    </#if>
                        <#if !task.isDone()>
                            <br>
                            <a href="/setDone?id=${task.id}">set done</a>
                        </#if>
                </td>
                    <#if isAdmin>
                    <td>
                        <#if !task.isDone()>
                            <form action="/newComment" method="post">
                                <textarea name="comment" required maxlength=2700></textarea>
                                <br>
                                <input type="submit" title="add">
                                <input type="hidden" name="id" value="${task.id}"/>
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            </form>
                        </#if>
                    </td>
                    </#if>
            </tr>
        </#list>
    </table>
</@c.page>