<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/pages/common/head.jsp"%>
<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>供应商管理页面 >> 供应商信息查看页面</span>
    </div>
    <div class="providerView">
        <p><strong>供应商编码：</strong><span>${provider.proCode }</span></p>
        <p><strong>供应商名称：</strong><span>${provider.proName }</span></p>
        <p><strong>供应商负责人：</strong><span>${provider.proContact}</span></p>
        <p><strong>供应商电话：</strong><span>${provider.proPhone}</span></p>
        <p><strong>供应商传真：</strong><span>${provider.proFax}</span></p>
        <p><strong>供应商地址：</strong><span>${provider.proAddress }</span></p>

        <div class="providerAddBtn">
            <input type="button" id="back" name="back" value="返回" >
        </div>
    </div>
</div>
</section>
<%@include file="/WEB-INF/pages/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/providerview.js"></script>