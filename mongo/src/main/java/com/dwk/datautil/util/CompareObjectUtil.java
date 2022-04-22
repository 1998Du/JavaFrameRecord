package com.dwk.datautil.util;

import java.util.Map;

/**
 *
 * Description:
 *      比对对象属性时需要先对每个属性都进行判空
 * @author: mushi
 * @Date: 2021/4/12 15:18
 */
public class CompareObjectUtil {

    /**
     * 比较两个文档对象内的属性是否都相同
     */
    public boolean isSame(Object objOne,Object objTwo){

        boolean isSame = false;
        boolean versionIsSame = true,
                queryNameIsSame = true,
                adminRequireIsSame = true,
                loginRequireIsSame = true,
                superRequireIsSame = true;

        //将反射的对象转换成对应类型
        QueryMetaData one = (QueryMetaData) objOne;
        QueryMetaData two = (QueryMetaData) objTwo;

        String version1 = one.getVersion();
        String version2 = two.getVersion();
        if (version1!=null && version2!=null){
            versionIsSame = version1.equals(version2);
        }

        String queryName1 = one.getQueryName();
        String queryName2 = two.getQueryName();
        if (queryName1!=null && queryName2!=null){
            queryNameIsSame = queryName1.equals(queryName2);
        }

        String isAdminRequire1 = Boolean.toString(one.isAdminRequire());
        String isAdminRequire2 = Boolean.toString(two.isAdminRequire());
        if (isAdminRequire1!=null && isAdminRequire2!=null){
            adminRequireIsSame = isAdminRequire1.equals(isAdminRequire2);
        }

        String isLoginRequire1 = Boolean.toString(one.isLoginRequire());
        String isLoginRequire2 = Boolean.toString(two.isLoginRequire());
        if (isLoginRequire1!=null && isLoginRequire2!=null){
            loginRequireIsSame = isLoginRequire1.equals(isLoginRequire2);
        }

        String isSuperRequire1 = Boolean.toString(one.isSuperRequire());
        String isSuperRequire2 = Boolean.toString(two.isSuperRequire());
        if (isSuperRequire1!=null && isSuperRequire2!=null){
            superRequireIsSame = isSuperRequire1.equals(isSuperRequire2);
        }

        boolean queryDefineInfoIsSame = false;
        //queryDefineInfo对象
        String name = one.getQueryDefineInfo().getClass().getName();
        String name1 = two.getQueryDefineInfo().getClass().getName();
        if (name.equals(name1)){

            switch (name){
                //转换对象类型
                case "com..cloud.datacenter.entity.nquery.MySqlQueryDefine" :
                    MySqlQueryDefine m1 = (MySqlQueryDefine) ((QueryMetaData) objOne).getQueryDefineInfo();
                    MySqlQueryDefine m2 = (MySqlQueryDefine) ((QueryMetaData) objTwo).getQueryDefineInfo();
                    queryDefineInfoIsSame = compareMySqlQueryDefine(m1,m2);
                    break;
                case "com..cloud.datacenter.entity.nquery.CustomQueryDefine" :
                    CustomQueryDefine c1 = (CustomQueryDefine) ((QueryMetaData) objOne).getQueryDefineInfo();
                    CustomQueryDefine c2 = (CustomQueryDefine) ((QueryMetaData) objTwo).getQueryDefineInfo();
                    queryDefineInfoIsSame = compareCustomQueryDefine(c1,c2);
                    break;
                case "com..cloud.datacenter.entity.nquery.ProxyQueryDefine" :
                    ProxyQueryDefine p1 = (ProxyQueryDefine) ((QueryMetaData) objOne).getQueryDefineInfo();
                    ProxyQueryDefine p2 = (ProxyQueryDefine) ((QueryMetaData) objTwo).getQueryDefineInfo();
                    queryDefineInfoIsSame = compareProxyQueryDefine(p1,p2);
                    break;
                case "com..cloud.datacenter.entity.nquery.MongoQueryDefine" :
                    MongoQueryDefine mongo1 = (MongoQueryDefine) ((QueryMetaData) objOne).getQueryDefineInfo();
                    MongoQueryDefine mongo2 = (MongoQueryDefine) ((QueryMetaData) objTwo).getQueryDefineInfo();
                    queryDefineInfoIsSame = compareMongoQueryDefine(mongo1,mongo2);
                    break;
                case "com..cloud.datacenter.entity.nquery.RestQueryDefine" :
                    RestQueryDefine r1 = (RestQueryDefine) ((QueryMetaData) objOne).getQueryDefineInfo();
                    RestQueryDefine r2 = (RestQueryDefine) ((QueryMetaData) objTwo).getQueryDefineInfo();
                    queryDefineInfoIsSame = compareRestQueryDefine(r1,r2);
                    break;
                case "com..cloud.datacenter.entity.nquery.ElasticQueryDefine" :
                    ElasticQueryDefine e1 = (ElasticQueryDefine) ((QueryMetaData) objOne).getQueryDefineInfo();
                    ElasticQueryDefine e2 = (ElasticQueryDefine) ((QueryMetaData) objTwo).getQueryDefineInfo();
                    queryDefineInfoIsSame = compareElasticQueryDefine(e1,e2);
                    break;
                default:
            }

            //所有属性都相同
            boolean allFieldSame = versionIsSame && queryNameIsSame && adminRequireIsSame && loginRequireIsSame && superRequireIsSame && queryDefineInfoIsSame;
            if (allFieldSame){
                isSame = true;
            }
            return isSame;

        }else{
            return isSame;
        }
    }




    /**
     * 比对超类QueryDefineInfo对象
     */
    private boolean compareQueryDefineInfo(QueryDefineInfo q1, QueryDefineInfo q2){
        boolean isSame = false;

        boolean defineNameIsSame = true,
                returnTypeIsSame = true,
                exporeExcelHeaderIsSame = true,
                exporeExcelHanderJSIsSame = true,
                exporeExcelContentIsSame = true,
                exporeExcelContentJSIsSame = true,
                queryType = true,
                connectId = true,
                pageQuery = true,
                isWithRowNumber = true,
                defineList = true,
                columeDefines = true;

        String defineName1 = q1.getDefineName();
        String defineName2 = q2.getDefineName();
        if (defineName1!=null && defineName2!=null){
            defineNameIsSame = defineName1.equals(defineName2);
        }

        String queryType1 = q1.getQueryType();
        String queryType2 = q2.getQueryType();
        if (queryType1!=null && queryType2!=null){
            queryType = queryType1.equals(queryType2);
        }

        ReturnType returnType1 = q1.getReturnType();
        ReturnType returnType2 = q2.getReturnType();
        if (returnType1 !=null || returnType2 !=null){
            returnTypeIsSame = (returnType1.toString()).equals(returnType2.toString());
        }

        String connectId1 = q1.getConnectId();
        String connectId2 = q2.getConnectId();
        if (connectId1!=null && connectId2!=null){
            connectId = connectId1.equals(connectId2);
        }

        String pageQuery1 = Boolean.toString(q1.isPageQuery());
        String pageQuery2 = Boolean.toString(q2.isPageQuery());
        if (pageQuery1!=null && pageQuery2!=null){
            pageQuery = pageQuery1.equals(pageQuery2);
        }

        String isWithRowNumber1 = Boolean.toString(q1.isWithRowNumber());
        String isWithRowNumber2 = Boolean.toString(q2.isWithRowNumber());
        if (isWithRowNumber1!=null && isWithRowNumber2!=null){
            isWithRowNumber = isWithRowNumber1.equals(isWithRowNumber2);
        }

        String exporeExcelHeader1 = q1.getExporeExcelHeader();
        String exporeExcelHeader2 = q2.getExporeExcelHeader();
        if (exporeExcelHeader1 !=null && exporeExcelHeader2 !=null){
            exporeExcelHeaderIsSame = exporeExcelHeader1.equals(exporeExcelHeader2);
        }

        String exporeExcelHeaderJS1 = q1.getExporeExcelHeaderJS();
        String exporeExcelHeaderJS2 = q2.getExporeExcelHeaderJS();
        if (exporeExcelHeaderJS1 !=null && exporeExcelHeaderJS2 !=null){
            exporeExcelHanderJSIsSame = exporeExcelHeaderJS1.equals(exporeExcelHeaderJS2);
        }

        String exporeExcelContent1 = q1.getExporeExcelContent();
        String exporeExcelContent2 = q2.getExporeExcelContent();
        if (exporeExcelContent1 !=null && exporeExcelContent2 !=null){
            exporeExcelContentIsSame = exporeExcelContent1.equals(exporeExcelContent2);
        }

        String exporeExcelContentJS1 = q1.getExporeExcelContentJS();
        String exporeExcelContentJS2 = q2.getExporeExcelContentJS();
        if (exporeExcelContentJS1 !=null && exporeExcelContentJS2 !=null){
            exporeExcelContentJSIsSame = exporeExcelContentJS1.equals(exporeExcelContentJS2);
        }

        String defineList1 = q1.getDefineList().toString();
        String defineList2 = q2.getDefineList().toString();
        if (defineList1!=null && defineList2!=null){
            defineList = defineList1.equals(defineList2);
        }

        String columnDefines1 = q1.getColumnDefines().toString();
        String columnDefines2 = q2.getColumnDefines().toString();
        if (columnDefines1!=null && columnDefines2!=null){
            columeDefines = columnDefines1.equals(columnDefines2);
        }

        boolean allSame = defineNameIsSame && queryType && returnTypeIsSame && connectId && pageQuery && isWithRowNumber && exporeExcelHeaderIsSame
                && exporeExcelHanderJSIsSame && exporeExcelContentIsSame && exporeExcelContentJSIsSame && defineList && columeDefines;
        if (allSame){
            isSame = true;
        }
        return isSame;
    }

    /**
     * 比对MySqlQueryDefine对象
     */
    private boolean compareMySqlQueryDefine(MySqlQueryDefine m1, MySqlQueryDefine m2){
        boolean isSame = false;
        boolean querySqlReplace = true,
                querySql = true,
                queryCountSql = true,
                defaultOrderBy = true,
                defaultSortBy = true,
                sqlFormats = true;

        boolean superObj = compareQueryDefineInfo(m1, m2);

        String querySql1 = m1.getQuerySql();
        String querySql2 = m2.getQuerySql();
        if (querySql1!=null && querySql2!=null){
            querySql = querySql1.equals(querySql2);
        }

        String queryCountSql1 = m1.getQueryCountSql();
        String queryCountSql2 = m2.getQueryCountSql();
        if (queryCountSql1!=null && queryCountSql2!=null){
            queryCountSql = queryCountSql1.equals(queryCountSql2);
        }

        String defaultOrderBy1 = m1.getDefaultOrderBy();
        String defaultOrderBy2 = m2.getDefaultOrderBy();
        if (defaultOrderBy1!=null && defaultOrderBy2!=null){
            defaultOrderBy = defaultOrderBy1.equals(defaultOrderBy2);
        }

        String defaultSortBy1 = m1.getDefaultSortBy();
        String defaultSortBy2 = m2.getDefaultSortBy();
        if (defaultSortBy1!=null && defaultSortBy2!=null){
            defaultSortBy = defaultSortBy1.equals(defaultSortBy2);
        }

        String querySqlReplace1 = m1.getQuerySqlReplace();
        String querySqlReplace2 = m2.getQuerySqlReplace();
        if (querySqlReplace1 !=null && querySqlReplace2 !=null){
            querySqlReplace = querySqlReplace1.equals(querySqlReplace2);
        }

        String sqlFormats1 = m1.getSqlFormats().toString();
        String sqlFormats2 = m2.getSqlFormats().toString();
        if (sqlFormats1!=null && sqlFormats2!=null){
            sqlFormats = sqlFormats1.equals(sqlFormats2);
        }

        if (querySql && queryCountSql && defaultOrderBy && defaultSortBy && querySqlReplace && sqlFormats &&superObj){
            isSame = true;
        }
        return isSame;
    }

    /**
     * 比对CustomQueryDefine对象
     */
    private boolean compareCustomQueryDefine(CustomQueryDefine c1, CustomQueryDefine c2){
        boolean isSame = false;
        boolean queryHandler = true;

        boolean superObj = compareQueryDefineInfo(c1, c2);

        String queryHandler1 = c1.getQueryHandler();
        String queryHandler2 = c2.getQueryHandler();
        if (queryHandler1!=null && queryHandler2!=null){
            queryHandler = queryHandler1.equals(queryHandler2);
        }

        if (queryHandler && superObj){
            isSame = true;
        }
        return isSame;
    }

    /**
     * 比对ProxyQueryDefine对象
     */
    private boolean compareProxyQueryDefine(ProxyQueryDefine p1, ProxyQueryDefine p2){
        boolean isSame = false;
        boolean originQueryName = true,
                baseParams = true;


        boolean superObj = compareQueryDefineInfo(p1, p2);

        String originQueryName1 = p1.getOriginQueryName();
        String originQueryName2 = p2.getOriginQueryName();
        if (originQueryName1!=null && originQueryName2!=null){
            originQueryName = originQueryName1.equals(originQueryName2);
        }

        String baseParams1 = p1.getBaseParams().toString();
        String baseParams2 = p2.getBaseParams().toString();
        if (baseParams1!=null && baseParams2!=null){
            baseParams = baseParams1.equals(baseParams2);
        }

        if (superObj && originQueryName && baseParams){
            isSame = true;
        }
        return isSame;
    }

    /**
     * 比对MongoQueryDefine对象
     */
    private boolean compareMongoQueryDefine(MongoQueryDefine m1, MongoQueryDefine m2){
        boolean isSame = false;
        boolean aggregateBson = true,
                baseFilter = true,
                projection = true,
                baseSort = true,
                formats = true,
                collectionName = true;

        boolean superObj = compareQueryDefineInfo(m1, m2);

        String aggregateBson1 = m1.getAggregateBson().toString();
        String aggregateBson2 = m2.getAggregateBson().toString();
        if (aggregateBson1!=null && aggregateBson2!=null){
            aggregateBson = aggregateBson1.equals(aggregateBson2);
        }

        String baseFilter1 = m1.getBaseFilter();
        String baseFilter2 = m2.getBaseFilter();
        if (baseFilter1!=null && baseFilter2!=null){
            baseFilter = baseFilter1.equals(baseFilter2);
        }

        String baseSort1 = m1.getBaseSort();
        String baseSort2 = m2.getBaseSort();
        if (baseSort1!=null && baseSort2!=null){
            baseSort = baseSort1.equals(baseSort2);
        }

        String projection1 = m1.getProjection();
        String projection2 = m2.getProjection();
        if (projection1!=null && projection2!=null){
            projection = projection1.equals(projection2);
        }

        String formats1 = m1.getFormats().toString();
        String formats2 = m2.getFormats().toString();
        if (formats1!=null && formats2!=null){
            formats = formats1.equals(formats2);
        }

        String collectionName1 = m1.getCollectionName();
        String collectionName2 = m2.getCollectionName();
        if (collectionName1!=null && collectionName2!=null){
            collectionName = collectionName1.equals(collectionName2);
        }

        boolean allSame = superObj && aggregateBson && baseFilter && baseSort && projection && formats && collectionName;
        if (allSame){
            isSame = true;
        }
        return isSame;
    }

    /**
     * 比对RestQueryDefine对象
     */
    private boolean compareRestQueryDefine(RestQueryDefine r1, RestQueryDefine r2){
        boolean isSame = false;
        boolean restUrl = true,
                contentType = true,
                method = true,
                headParams = true,
                paramCastMap = true,
                responseHandler = true,
                totalPageHandler = true,
                format = true;

        boolean superObj = compareQueryDefineInfo(r1, r2);

        String restUrl1 = r1.getRestUrl();
        String restUrl2 = r2.getRestUrl();
        if (restUrl1!=null && restUrl2!=null){
            restUrl = restUrl1.equals(restUrl2);
        }

        String contentType1 = r1.getContentType();
        String contentType2 = r2.getContentType();
        if (contentType1!=null && contentType2!=null){
            contentType = contentType1.equals(contentType2);
        }

        String method1 = r1.getMethod();
        String method2 = r2.getMethod();
        if (method1!=null && method2!=null){
            method = method1.equals(method2);
        }

        String headParams1 = r1.getHeadParams().toString();
        String headParams2 = r2.getHeadParams().toString();
        if (headParams1!=null && headParams2!=null){
            headParams = headParams1.equals(headParams2);
        }

        Map<String, String> paramCastMap1 = r1.getParamCastMap();
        Map<String, String> paramCastMap2 = r2.getParamCastMap();
        if (paramCastMap1!=null && paramCastMap2!=null){
            paramCastMap = paramCastMap1.equals(paramCastMap2);
        }

        String responseHandler1 = r1.getResponseHandler();
        String responseHandler2 = r2.getResponseHandler();
        if (responseHandler1!=null && responseHandler2!=null){
            responseHandler = responseHandler1.equals(responseHandler2);
        }

        String totalPageHandler1 = r1.getTotalPageHandler();
        String totalPageHandler2 = r2.getTotalPageHandler();
        if (totalPageHandler1!=null && totalPageHandler2!=null){
            totalPageHandler = totalPageHandler1.equals(totalPageHandler2);
        }

        String format1 = r1.getFormat().toString();
        String format2 = r2.getFormat().toString();
        if (format1!=null && format2!=null){
            format = format1.equals(format2);
        }

        boolean allSame = superObj && restUrl && contentType && method && headParams && paramCastMap && responseHandler && totalPageHandler && format;
        if (allSame){
            isSame = true;
        }
        return isSame;
    }

    /**
     * 比对ElasticQueryDefine对象
     */
    private boolean compareElasticQueryDefine(ElasticQueryDefine e1, ElasticQueryDefine e2){
        boolean isSame = false;
        boolean baseOrderFieldName = true,
                baseOrderIsAsc = true,
                baseQuery = true,
                indexName = true,
                formats = true,
                groupFields = true,
                statisticsFields = true;

        boolean superObj = compareQueryDefineInfo(e1, e2);
        LmOrderField baseOrder1 = e1.getBaseOrder();
        LmOrderField baseOrder2 = e2.getBaseOrder();
        if (baseOrder1.getFieldName()!=null && baseOrder2.getFieldName()!=null){
            baseOrderFieldName = baseOrder1.getFieldName().equals(baseOrder2.getFieldName());
        }

        String baseOrderIsAsc1 = Boolean.toString(baseOrder1.isAsc());
        String baseOrderIsAsc2 = Boolean.toString(baseOrder2.isAsc());
        if (baseOrderIsAsc1!=null && baseOrderIsAsc2!=null){
            baseOrderIsAsc = baseOrderIsAsc1.equals(baseOrderIsAsc2);
        }

        String baseQuery1 = e1.getBaseQuery();
        String baseQuery2 = e2.getBaseQuery();
        if (baseQuery1!=null && baseQuery2!=null){
            baseQuery = baseQuery1.equals(baseQuery2);
        }

        String formats1 = e1.getFormats().toString();
        String formats2 = e2.getFormats().toString();
        if (formats1!=null && formats2!=null){
            formats = formats1.equals(formats2);
        }

        String indexName1 = e1.getIndexName();
        String indexName2 = e2.getIndexName();
        if (indexName1!=null && indexName2!=null){
            indexName = indexName1.equals(indexName2);
        }

        String groupFields1 = e1.getGroupFields().toString();
        String groupFields2 = e2.getGroupFields().toString();
        if (groupFields1!=null && groupFields2!=null){
            groupFields = groupFields1.equals(groupFields2);
        }

        String statisticsFields1 = e1.getStatisticsFields().toString();
        String statisticsFields2 = e2.getStatisticsFields().toString();
        if (statisticsFields1!=null && statisticsFields2!=null){
            statisticsFields = statisticsFields1.equals(statisticsFields2);
        }

        boolean allSame = superObj && baseOrderFieldName && baseOrderIsAsc && baseQuery && formats && indexName && groupFields && statisticsFields;
        if (allSame){
            isSame = true;
        }
        return isSame;
    }

}
