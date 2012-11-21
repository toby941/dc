<%@ page contentType="text/html; charset=UTF-8"%>
<c:choose>
  <c:when test="${not empty command.errors }">
    <div class="stop mt">
         <div>
           <h3>提交提示</h3>
            <ul>
              <c:forEach items="${command.errors}" var="errorinfo">
              <li style="color: red;">. ${errorinfo.value}</li>
              </c:forEach>
           </ul>
        </div>
    </div>
  </c:when>
</c:choose>