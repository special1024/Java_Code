<%--
  Created by IntelliJ IDEA.
  User: 39458
  Date: 2021/1/26
  Time: 12:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--分页条的开始--%>
<div id="page_nav">
    <%--如果已经是一页，则不显示首页和上一页--%>
    <c:if test="${requestScope.page.pageNo > 1}">
        <a href="${requestScope.page.url}&pageNo=1">首页</a>
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo - 1}">上一页</a>
    </c:if>

    <%--页码输出的开始--%>
    <c:choose>
        <%--情况 1：如果总页码小于等于 5 的情况，页码的范围是：1-总页码--%>
        <c:when test="${requestScope.page.pageTotal <= 5}">
            <c:set var="begin" value="1"></c:set>
            <c:set var="end" value="${requestScope.page.pageTotal}"></c:set>
            <%--<c:forEach begin="1" end="${requestScope.page.pageTotal}" var="i">
                <c:if test="${i == requestScope.page.pageNo}">
                    【${i}】
                </c:if>
                <c:if test="${i != requestScope.page.pageNo}">
                    <a href="${requestScope.page.url}&pageNo=${i}">${i}</a>
                </c:if>
            </c:forEach>--%>
        </c:when>
        <%--情况 2：总页码大于 5 的情况。假设一共 10 页--%>
        <c:when test="${requestScope.page.pageTotal > 5}">
            <c:choose>
                <%--小情况 1：当前页码为前面 3 个：1，2，3 的情况，页码范围是：1-5.--%>
                <c:when test="${requestScope.page.pageNo <= 3}">
                    <c:set var="begin" value="1"></c:set>
                    <c:set var="end" value="5"></c:set>
                    <%--<c:forEach begin="1" end="5" var="i">
                        <c:if test="${i == requestScope.page.pageNo}">
                            【${i}】
                        </c:if>
                        <c:if test="${i != requestScope.page.pageNo}">
                            <a href="${requestScope.page.url}&pageNo=${i}">${i}</a>
                        </c:if>
                    </c:forEach>--%>
                </c:when>
                <%--小情况 2：当前页码为最后 3 个，8，9，10，页码范围是：总页码减 4 - 总页码--%>
                <c:when test="${requestScope.page.pageNo >= requestScope.page.pageTotal - 3}">
                    <c:set var="begin" value="${requestScope.page.pageTotal - 4}"></c:set>
                    <c:set var="end" value="${requestScope.page.pageTotal}"></c:set>
                    <%--<c:forEach begin="${requestScope.page.pageTotal - 4}" end="${requestScope.page.pageTotal}" var="i">
                        <c:if test="${i == requestScope.page.pageNo}">
                            【${i}】
                        </c:if>
                        <c:if test="${i != requestScope.page.pageNo}">
                            <a href="${requestScope.page.url}&pageNo=${i}">${i}</a>
                        </c:if>
                    </c:forEach>--%>
                </c:when>
                <%--小情况 3：4，5，6，7，页码范围是：当前页码减 2 - 当前页码加 2--%>
                <c:otherwise>
                    <c:set var="begin" value="${requestScope.page.pageNo - 2}"></c:set>
                    <c:set var="end" value="${requestScope.page.pageNo + 2}"></c:set>
                    <%--<c:forEach begin="${requestScope.page.pageNo - 2}" end="${requestScope.page.pageNo + 2}" var="i">
                        <c:if test="${i == requestScope.page.pageNo}">
                            【${i}】
                        </c:if>
                        <c:if test="${i != requestScope.page.pageNo}">
                            <a href="${requestScope.page.url}&pageNo=${i}">${i}</a>
                        </c:if>
                    </c:forEach>--%>
                </c:otherwise>
            </c:choose>
        </c:when>
    </c:choose>

    <c:forEach begin="${begin}" end="${end}" var="i">
        <c:if test="${i == requestScope.page.pageNo}">
            【${i}】
        </c:if>
        <c:if test="${i != requestScope.page.pageNo}">
            <a href="${requestScope.page.url}&pageNo=${i}">${i}</a>
        </c:if>
    </c:forEach>

    <%--页码输出的结束--%>

    <%--<a href="">3</a>
    【${requestScope.page.pageNo}】
    <a href="">5</a>--%>
    <%--如果已经是最后一页，则不显示下一页和末页--%>
    <c:if test="${requestScope.page.pageNo < requestScope.page.pageTotal}">
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo + 1}">下一页</a>
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageTotal}">末页</a>
    </c:if>

    共${requestScope.page.pageTotal}页，${requestScope.page.pageTotalCount}条记录
    到第<input value="${param.pageNo}" name="pn" id="pn_input"/>页
    <input id="searchPageBtn" type="button" value="确定">

    <script type="text/javascript">
        $(function () {
            //跳到指定页码
            $("#searchPageBtn").click(function () {
                var pageNo = $("#pn_input").val();

                var pageTotal = ${requestScope.page.pageTotal};
                if(pageNo < 1 || pageNo > pageTotal) {
                    return false;
                }

                //javascript语言提供了一个location地址栏对象
                //它有一个属性href，可以获取浏览器地址栏中的地址
                //href属性可读，可写
                location.href = "${pageScope.basePath}${requestScope.page.url}&pageNo=" + pageNo;
            });
        });
    </script>

</div>
<%--分页条的结束--%>