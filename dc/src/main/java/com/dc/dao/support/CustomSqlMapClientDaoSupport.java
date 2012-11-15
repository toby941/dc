/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dc.dao.support;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.util.Assert;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * Convenient super class for iBATIS SqlMapClient data access objects. Requires a SqlMapClient to be set, providing a
 * SqlMapClientTemplate based on it to subclasses.
 * <p>
 * Instead of a plain SqlMapClient, you can also pass a preconfigured SqlMapClientTemplate instance in. This allows you
 * to share your SqlMapClientTemplate configuration for all your DAOs, for example a custom SQLExceptionTranslator to
 * use.
 * 
 * @author Juergen Hoeller
 * @since 24.02.2004
 * @see #setSqlMapClient
 * @see #setSqlMapClientTemplate
 * @see org.springframework.orm.ibatis.SqlMapClientTemplate
 * @see org.springframework.orm.ibatis.SqlMapClientTemplate#setExceptionTranslator
 */
public abstract class CustomSqlMapClientDaoSupport extends DaoSupport {

    protected Logger log = Logger.getLogger(this.getClass());

    private SqlMapClientTemplate sqlMapClientTemplate = new SqlMapClientTemplate();

    private boolean externalTemplate = false;

    /**
     * Set the JDBC DataSource to be used by this DAO. Not required: The SqlMapClient might carry a shared DataSource.
     * 
     * @see #setSqlMapClient
     */
    public final void setDataSource(DataSource dataSource) {
        if (!externalTemplate) {
            sqlMapClientTemplate.setDataSource(dataSource);
        }
    }

    /**
     * Return the JDBC DataSource used by this DAO.
     */
    public final DataSource getDataSource() {
        return sqlMapClientTemplate.getDataSource();
    }

    /**
     * 加入了自动注解的注释，不需要在app-data-access.xml中进行配置 Set the iBATIS Database Layer SqlMapClient to work with. Either this or a
     * "sqlMapClientTemplate" is required.
     * 
     * @see #setSqlMapClientTemplate
     */
    @Autowired
    public final void setSqlMapClient(SqlMapClient sqlMapClient) {
        if (!externalTemplate) {
            sqlMapClientTemplate.setSqlMapClient(sqlMapClient);
        }
    }

    /**
     * Return the iBATIS Database Layer SqlMapClient that this template works with.
     */
    public final SqlMapClient getSqlMapClient() {
        return sqlMapClientTemplate.getSqlMapClient();
    }

    /**
     * Set the SqlMapClientTemplate for this DAO explicitly, as an alternative to specifying a SqlMapClient.
     * 
     * @see #setSqlMapClient
     */
    public final void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
        Assert.notNull(sqlMapClientTemplate, "SqlMapClientTemplate must not be null");
        this.sqlMapClientTemplate = sqlMapClientTemplate;
        externalTemplate = true;
    }

    /**
     * Return the SqlMapClientTemplate for this DAO, pre-initialized with the SqlMapClient or set explicitly.
     */
    public final SqlMapClientTemplate getSqlMapClientTemplate() {
        return sqlMapClientTemplate;
    }

    @Override
    protected final void checkDaoConfig() {
        if (!externalTemplate) {
            sqlMapClientTemplate.afterPropertiesSet();
        }
    }

    /**
     * 根据参数查询列表总数
     * 
     * @param statementName iBATIS语句名称
     * @param params 参数
     * @return
     */
    public Integer selectCount(String statementName, Map<String, Object> params) {
        Integer devCount = 0;

        Object objDevCount = getSqlMapClientTemplate().queryForObject(statementName, params);
        if (null != objDevCount) {
            devCount = Integer.parseInt(objDevCount.toString());
        }
        return devCount;
    }

    /**
     * 根据参数分页查询列表信息
     * 
     * @param params
     * @return
     */
    public List<?> selectListbyPaging(String statementName, Map<String, Object> params) {
        Integer pageSize = Integer.valueOf(params.get("pageSize").toString());
        Integer currentPage = Integer.valueOf(params.get("currentPage").toString()) - 1;
        Integer fromRecord = pageSize * currentPage;
        params.put("fromRecord", fromRecord + "");
        List<?> record = getSqlMapClientTemplate().queryForList(statementName, params);
        return record;
    }

    /**
     * 根据参数查询列表信息
     * 
     * @param statementName
     * @param params
     * @return
     */
    public List<?> selectList(String statementName, Map<String, Object> params) {
        return getSqlMapClientTemplate().queryForList(statementName, params);
    }
}
