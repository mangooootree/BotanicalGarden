<#include "security.ftl">
<div style="background-color: rgba(203,255,203,0.46)">
    <table align="center" width="90%">
        <tr>
            <td align="left"><h1>Botanical Garden System</h1></td>
            <td align="center"><h1><a href="/plants">Plants</a> &nbsp;&nbsp;<a href="/tasks">Tasks</a></h1></td>
            <td align="right"><h3><#if user??>${name}</#if></h3></td>
            <td align="right"><@logout></@logout> </td>
        </tr>

    </table>
</div>
<br><br>