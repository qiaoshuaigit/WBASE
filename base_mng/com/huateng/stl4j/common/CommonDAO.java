package com.huateng.stl4j.common;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import om.util.ToString;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.type.Type;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huateng.ebank.common.CommonException;
import com.huateng.ebank.common.ErrorCode;
import com.huateng.ebank.common.ExceptionUtil;

public class CommonDAO extends HibernateDaoSupport {
	private static final Logger logger = Logger.getLogger(CommonDAO.class);
	private static final String myName = CommonDAO.class.getName();
	private static final Logger daoMonitorLogger = Logger.getLogger("DAOMonitor.CommonDAO");
	private static final long minTimeDuration = 100000000L; //100毫秒
	private DataSource dataSource = null;

	private void logTimeDuration(long millisecond, String message) {
		try {
			StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
			StringBuilder logInfo = new StringBuilder();
			int index = 1;
			for(; index < stackTraceElements.length; index ++) {
				if(!stackTraceElements[index].getClassName().equals(myName)) {
					break;
				}
			}
			logInfo.append(stackTraceElements[index].getClassName()).append("(line:").append(stackTraceElements[index].getLineNumber()).append(") ");
			logInfo.append("time duration is ").append(millisecond).append("ms");
			if(null != message) {
				logInfo.append(",").append(message);
			}
			stackTraceElements = null;
			daoMonitorLogger.info(logInfo.toString());
			logInfo = null;
		} catch(Throwable t) {
		}
	}
	
	public CommonDAO() {
		super();
	}

	public static CommonDAO getInstance() {
		return (CommonDAO) ApplicationContextUtils.getBean("CommonDAO");
	}

	public Object get(Class poClass, Serializable id) {
		try {
			long startTime = System.nanoTime();
			try {
				return getHibernateTemplate().get(poClass.getName(), id);
			} finally {
				long timeDuration = System.nanoTime() - startTime;
				if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
					logTimeDuration(timeDuration/1000/1000, ToString.connect("get(", poClass.getName(), ",id=", ToString.valueOf(id), ")"));
				}
			}
		} catch (RuntimeException re) {
			logger.error(new StringBuilder("find ").append(poClass.getSimpleName())
				.append(" error").toString(), re);
			throw re;
		}
	}

	public Object getForUpdate(Class poClass, Serializable id) {
		try {
			long startTime = System.nanoTime();
			try {
				return getHibernateTemplate().get(poClass.getName(), id, LockMode.UPGRADE);
			} finally {
				long timeDuration = System.nanoTime() - startTime;
				if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
					logTimeDuration(timeDuration/1000/1000, ToString.connect("getForUpdate(", poClass.getName(), ",id=", ToString.valueOf(id), ")"));
				}
			}
		} catch (RuntimeException re) {
			logger.error(new StringBuilder("findForUpdate ").append(poClass.getSimpleName())
				.append(" error").toString(), re);
			throw re;
		}
	}

	public List findByHQL(String hql) {
		try {
			long startTime = System.nanoTime();
			try {
				return getHibernateTemplate().find(hql);
			} finally {
				long timeDuration = System.nanoTime() - startTime;
				if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
					logTimeDuration(timeDuration/1000/1000, hql);
				}
			}
		} catch (RuntimeException re) {
			logger.error(new StringBuilder("findByHQL ").append(hql)
				.append(" error").toString(), re);
			throw re;
		}
	}

	public List findByHQL(String hql, Object[] values) {
		try {
			long startTime = System.nanoTime();
			try {
				return getHibernateTemplate().find(hql, values);
			} finally {
				long timeDuration = System.nanoTime() - startTime;
				if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
					logTimeDuration(timeDuration/1000/1000, ToString.connect("hql=", hql, ",values=", ToString.valueOf(values)));
				}
			}
		} catch (RuntimeException re) {
			logger.error(new StringBuilder("findByHQL ").append(hql)
				.append(" error").toString(), re);
			throw re;
		}
	}

	public List findByWhere(Class poClass, String where) {
		try {
			long startTime = System.nanoTime();
			StringBuilder hql = new StringBuilder("from ").append(poClass.getName()).append(" po where ").append(where);
			try {
				return getHibernateTemplate().find(hql.toString());
			} finally {
				long timeDuration = System.nanoTime() - startTime;
				if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
					logTimeDuration(timeDuration/1000/1000, hql.toString());
				}
			}
		} catch (RuntimeException re) {
			logger.error(new StringBuilder("findByWhere ").append(poClass.getSimpleName())
				.append(" error").toString(), re);
			throw re;
		}
	}

	public List findByWhere(Class poClass, String where, Object[] values) {
		try {
			long startTime = System.nanoTime();
			StringBuilder hql = new StringBuilder("from ").append(poClass.getName()).append(" po where ").append(where);
			try {
				return getHibernateTemplate().find(hql.toString(), values);
			} finally {
				long timeDuration = System.nanoTime() - startTime;
				if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
					logTimeDuration(timeDuration/1000/1000, ToString.connect("hql=", hql.toString(), ",values=", ToString.valueOf(values)));
				}
			}
		} catch (RuntimeException re) {
			logger.error(new StringBuilder("findByWhere ").append(poClass.getSimpleName())
					.append(" error").toString(), re);
				throw re;
		}
	}

	public List findByNamedQuery(final String name, final Serializable[] params) {
		long startTime = System.nanoTime();
		try {
			return findByNamedQuery(name, params, -1, -1);
		} finally {
			long timeDuration = System.nanoTime() - startTime;
			if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
				logTimeDuration(timeDuration/1000/1000, ToString.connect("name=", name, ",params=", ToString.valueOf(params)));
			}
		}
    }

    public List findByNamedQuery(final String name, final Serializable[] params, final int begin, final int count) {
    	long startTime = System.nanoTime();
    	try {
			return getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query query = session.getNamedQuery(name);
					if (null != params) {
						for (int i = 0; i < params.length; i++) {
							query.setParameter(i, params[i]);
						}
					}
					if (begin >= 0) {
						query.setFirstResult(begin);
						query.setMaxResults(count);
					}
					return query.list();
				}
			});
		} finally {
			long timeDuration = System.nanoTime() - startTime;
			if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
				logTimeDuration(timeDuration/1000/1000,
					ToString.connect("name=", name, ",params=", ToString.valueOf(params), ",begin=", String.valueOf(begin), ",count=", String.valueOf(count)));
			}
		}
    }

    public List findByNamedQuery(final String name, final Map params) {
    	long startTime = System.nanoTime();
    	try {
    		return findByNamedQuery(name, params, -1, -1);
    	} finally {
    		long timeDuration = System.nanoTime() - startTime;
			if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
				logTimeDuration(timeDuration/1000/1000, ToString.connect("name=", name, ",params=", ToString.valueOf(params)));
    		}
		}
    }

    public List findByNamedQuery(final String name, final Map params, final int begin, final int count) {
    	long startTime = System.nanoTime();
    	try {
    		return getHibernateTemplate().executeFind(
    	            new HibernateCallback() {
    	                public Object doInHibernate(Session session)
    	                    throws HibernateException, SQLException {
    	                    Query query = session.getNamedQuery(name);
    	                    if (null != params) {
    	                        for (Iterator i = params.entrySet().iterator(); i.hasNext(); ) {
    	                            Map.Entry entry = (Map.Entry) i.next();
    	                            if (entry.getValue() instanceof Collection){
    	                            	query.setParameterList((String) entry.getKey(), (Collection)entry.getValue());
    	                            }else{
    	                            	query.setParameter((String) entry.getKey(), entry.getValue());
    	                            }
    	                        }
    	                    }
    	                    if( begin >= 0 ) {
    	                        query.setFirstResult(begin);
    	                        query.setMaxResults(count);
    	                    }
    	                    return query.list();
    	                }
    	            }
    	        );
    	} finally {
    		long timeDuration = System.nanoTime() - startTime;
			if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
				logTimeDuration(timeDuration/1000/1000,
					ToString.connect("name=", name, ",params=", ToString.valueOf(params), ",begin=", String.valueOf(begin), ",count=", String.valueOf(count)));
    		}
		}
    }

    public List findByExample(Object valueBean) {
    	long startTime = System.nanoTime();
    	try {
    		return getHibernateTemplate().findByExample(valueBean);
    	} finally {
    		long timeDuration = System.nanoTime() - startTime;
			if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
				logTimeDuration(timeDuration/1000/1000, ToString.connect("findByExample(", ToString.valueOf(valueBean), ")"));
    		}
		}
    }

    public List findByValueBean(String queryString, Object valueBean) {
    	long startTime = System.nanoTime();
    	try {
    		return getHibernateTemplate().findByValueBean(queryString, valueBean);
    	} finally {
    		long timeDuration = System.nanoTime() - startTime;
			if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
				logTimeDuration(timeDuration/1000/1000, ToString.connect("findByValueBean(", queryString, ",", ToString.valueOf(valueBean), ")"));
    		}
		}
    }

    public List findByNamedQueryAndValueBean(String queryName, Object valueBean) {
    	long startTime = System.nanoTime();
    	try {
    		return getHibernateTemplate().findByNamedQueryAndValueBean(queryName, valueBean);
    	} finally {
    		long timeDuration = System.nanoTime() - startTime;
			if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
				logTimeDuration(timeDuration/1000/1000, ToString.connect("findByNamedQueryAndValueBean(", queryName, ",",  ToString.valueOf(valueBean), ")"));
    		}
		}
    }

	public Serializable insert(Object po) {
		try {
			long startTime = System.nanoTime();
			try {
				return getHibernateTemplate().save(po);
			} finally {
				long timeDuration = System.nanoTime() - startTime;
				if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
					logTimeDuration(timeDuration/1000/1000, po.getClass().getName());
				}
			}
		} catch (RuntimeException re) {
			logger.error(new StringBuilder("insert ").append(po.getClass().getSimpleName())
				.append(" error").toString(), re);
			throw re;
		}
	}

	public void saveOrUpdate(Object po) {
		try {
			long startTime = System.nanoTime();
			try {
				getHibernateTemplate().saveOrUpdate(po);
			} finally {
				long timeDuration = System.nanoTime() - startTime;
				if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
					logTimeDuration(timeDuration/1000/1000, po.getClass().getName());
				}
			}
		} catch (RuntimeException re) {
			logger.error(new StringBuilder("saveOrUpdate ").append(po.getClass().getSimpleName())
				.append(" error").toString(), re);
			throw re;
		}
	}

	public void update(Object po) {
		try {
			long startTime = System.nanoTime();
			try {
				getHibernateTemplate().update(po);
			} finally {
				long timeDuration = System.nanoTime() - startTime;
				if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
					logTimeDuration(timeDuration/1000/1000, po.getClass().getName());
				}
			}
		} catch (RuntimeException re) {
			logger.error(new StringBuilder("update ").append(po.getClass().getSimpleName())
				.append(" error").toString(), re);
			throw re;
		}
	}

	public void delete(Object po) {
		try {
			long startTime = System.nanoTime();
			try {
				getHibernateTemplate().delete(po);
			} finally {
				long timeDuration = System.nanoTime() - startTime;
				if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
					logTimeDuration(timeDuration/1000/1000, po.getClass().getName());
				}
			}
		} catch (RuntimeException re) {
			logger.error(new StringBuilder("delete ").append(po.getClass().getSimpleName())
				.append(" error").toString(), re);
			throw re;
		}
	}

	public void delete(Class poClass, Serializable id) {
		try {
			long startTime = System.nanoTime();
			try {
				Object o = get(poClass, id);
				if(null != o) {
					getHibernateTemplate().delete(o);
				}
			} finally {
				long timeDuration = System.nanoTime() - startTime;
				if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
					logTimeDuration(timeDuration/1000/1000, ToString.connect(poClass.getName(), ",id=", ToString.valueOf(id)));
				}
			}
		} catch (RuntimeException re) {
			logger.error(new StringBuilder("delete ").append(poClass.getSimpleName())
				.append(" error").toString(), re);
			throw re;
		}
	}

	/**
	 * Execute the update or delete statement
	 * @param hql
	 * @return
	 */
	public Integer executeUpdate(final String hql) {
//		long startTime = System.nanoTime();
		try {
			return executeUpdate(hql, null);
		} finally {
//			long timeDuration = System.nanoTime() - startTime;
//			if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
//				logTimeDuration(timeDuration/1000/1000, hql);
//			}
		}
	}

	/**
	 * Execute the update or delete statement
	 * @param hql
	 * @param params
	 * @return
	 */
	public Integer executeUpdate(final String hql, final Serializable[] params) {
		Integer count = new Integer(-1);
		try {
			long startTime = System.nanoTime();
			try {
				count = (Integer)this.getHibernateTemplate().execute(new HibernateCallback() {
	                public Object doInHibernate(Session session) throws HibernateException, SQLException {
	                	Query query = session.createQuery(hql);
	                	if (null != params) {
	                        for (int i = 0; i < params.length; i ++) {
	                            query.setParameter(i, params[i]);
	                        }
	                    }
	                	return new Integer(query.executeUpdate());
	                }
				});
			} finally {
				long timeDuration = System.nanoTime() - startTime;
				if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
					logTimeDuration(timeDuration/1000/1000, ToString.connect("hql=", hql, ",params=", ToString.valueOf(params)));
				}
			}
		} catch (RuntimeException re) {
			logger.error(new StringBuilder("executeUpdate ").append(hql).append(" error").toString(), re);
			throw re;
		}
		return count;
	}

	protected void refresh(Object obj) {
		getHibernateTemplate().refresh(obj);
    }

    public void clear() {
		getHibernateTemplate().getSessionFactory().getCurrentSession().clear();
    }

    public void flush() {
		getHibernateTemplate().flush();
    }

    public int getNextValueOfSequences(String seqName) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int nextValue = 0;
		try {
			conn = DataSourceUtils.getConnection(dataSource);
			stmt = conn.createStatement();
			//rs = stmt.executeQuery(new StringBuilder("values nextval for ").append(seqName).toString());
			rs = stmt.executeQuery(new StringBuilder(" select ").append(seqName).append(".nextval from dual ").toString());
			if(rs.next()) {
				nextValue = rs.getInt(1);
			}
		} catch(Exception e) {
			logger.error(new StringBuilder("Get NEXT VALUE FOR ").append(seqName).append(" Error").toString(), e);
			throw new RuntimeException(new StringBuilder("Get NEXT VALUE FOR ").append(seqName).append(" Error").toString());
		} finally {
			if(null != rs)      try { rs.close(); }      catch(Exception e) { }
			if(null != stmt)    try { stmt.close(); }    catch(Exception e) { }
			if(null != conn)    DataSourceUtils.releaseConnection(conn, dataSource);
		}
		return nextValue;
    }

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public PageQueryResult pageQueryByHQL(PageQueryConditionWithCount queryCondition) throws CommonException {
		try {
			long startTime = System.nanoTime();
			try {
				PageQueryCallbackWithCount callback = new PageQueryCallbackWithCount(
						queryCondition);
				PageQueryResult returnPageQueryResult = (PageQueryResult) this
					.getHibernateTemplate().execute(callback);
				return returnPageQueryResult;
			} finally {
				long timeDuration = System.nanoTime() - startTime;
				if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
					logTimeDuration(timeDuration/1000/1000, ToString.valueOf(queryCondition));
				}
			}
		} catch (Exception e) {
			logger.error("pageQueryByHQL(PageQueryConditionWithCount)", e); //$NON-NLS-1$

			ExceptionUtil.throwCommonException(e.getMessage(),
					ErrorCode.ERROR_CODE_DAO, e);
		}
		return null;
	}
	
	public PageQueryResult pageQueryByHQL(PageQueryCondition queryCondition) throws CommonException {
		try {
			long startTime = System.nanoTime();
			try {
				PageQueryCallback callback = new PageQueryCallback(queryCondition);
				PageQueryResult returnPageQueryResult = (PageQueryResult) this.getHibernateTemplate().execute(callback);
				return returnPageQueryResult;
			} finally {
				long timeDuration = System.nanoTime() - startTime;
				if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
					logTimeDuration(timeDuration/1000/1000, ToString.valueOf(queryCondition));
				}
			}
		} catch (Exception e) {
			ExceptionUtil.throwCommonException("PageQueryByHQL Error", ErrorCode.ERROR_CODE_DAO, e);
		}
		return null;
	}
	
	class PageQueryCallback implements HibernateCallback {
		private PageQueryCondition queryCondition = null;

		public PageQueryCallback(PageQueryCondition queryCondition) {
			this.queryCondition = queryCondition;
		}

		public Object doInHibernate(Session session) throws HibernateException {
			Query queryObject = session.createQuery(queryCondition.getQueryString());
			Object[] values = queryCondition.getObjArray();
			Type[] types = queryCondition.getTypeArray();

			if(null != values) {
				for (int i = 0; i < values.length; i++) {
					if (types != null) {
						queryObject.setParameter(i, values[i], types[i]);
					} else {
						queryObject.setParameter(i, values[i]);
					}
				}
			}

			ScrollableResults sr = null;
			try {
				PageQueryResult queryResult = new PageQueryResult();
				queryResult.setQueryResult(new ArrayList());
				queryResult.setTotalCount(0);

				sr = queryObject.scroll();
				if(false == sr.last()) {
					return queryResult;
				}

				int totalCount = sr.getRowNumber();
				queryResult.setTotalCount(totalCount + 1);

				int pageSize = queryCondition.getPageSize();
				int pageIndex = queryCondition.getPageIndex() - 1;

				int startRowNum = pageIndex * pageSize;
				if(false == sr.setRowNumber(startRowNum)) {
					return queryResult;
				}

				List list = queryResult.getQueryResult();
				for (int i = 0; i < pageSize; i++) {
					list.add(sr.get());
					if (false == sr.next()) {
						break;
					}
				}
				return queryResult;
			} finally {
				if (null != sr)
					sr.close();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> findListBySqlQuery(final String queryString) {
		long startTime = System.nanoTime();
		try {
			return (List<Object[]>) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					List<Object[]> listResult = new ArrayList<Object[]>();
					Statement stmt  = null;
					ResultSet resultSet = null;
					try {
						stmt = session.connection().createStatement();
						resultSet = (ResultSet)stmt.executeQuery(queryString);
						while(resultSet.next()) {
							ResultSetMetaData rsmd = resultSet.getMetaData();
							int columnCount = rsmd.getColumnCount();
							Object[] arrObj = new Object[columnCount];
							for(int i = 0; i < arrObj.length; i ++) {
								arrObj[i] = resultSet.getObject(i + 1);
							}
							listResult.add(arrObj);
						}
					} finally {
						if(null != resultSet) resultSet.close();
						if(null != stmt) stmt.close();
					}
					return listResult;
				}
			});
		} finally {
			long timeDuration = System.nanoTime() - startTime;
			if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
				logTimeDuration(timeDuration/1000/1000, queryString);
			}
		}
	}
	
	public List findListBySqlQuery(final String queryString, final Class poClass) {
		long startTime = System.nanoTime();
		try {
			return (List) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					return session.createSQLQuery(queryString)
						.addEntity("po", poClass).list();
						//.addScalar("id", Hibernate.INTEGER).addScalar("custName")
						//.addScalar("bankNo")
						//.setResultTransformer(Transformers.aliasToBean(poClass)).list();
						//.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				}
			});
		} finally {
			long timeDuration = System.nanoTime() - startTime;
			if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
				logTimeDuration(timeDuration/1000/1000, ToString.connect("queryString=", queryString, ",", poClass.getName()));
			}
		}
	}
	
	/**
	 * @Description: 使用count(*)获取总记录数，效率高，局限性对于union不支持，请使用pageQueryByHQL
	 */
	public PageQueryResult pageQueryByHQLWithCount(
			PageQueryCondition queryCondition) throws CommonException {
		if (logger.isDebugEnabled()) {
			logger.debug("pageQueryByQL(PageQueryCondition) - start"); //$NON-NLS-1$
		}

		try {
			long startTime = System.nanoTime();
			try {
				PageQueryCallbackWithCount callback = new PageQueryCallbackWithCount(
						queryCondition);
				PageQueryResult returnPageQueryResult = (PageQueryResult) this
						.getHibernateTemplate().execute(callback);
				if (logger.isDebugEnabled()) {
					logger.debug("pageQueryByQL(PageQueryCondition) - end"); //$NON-NLS-1$
				}
				return returnPageQueryResult;
			} finally {
				long timeDuration = System.nanoTime() - startTime;
				if(daoMonitorLogger.isInfoEnabled() && timeDuration >= minTimeDuration) {
					logTimeDuration(timeDuration/1000/1000, ToString.valueOf(queryCondition));
				}
			}
		} catch (Exception e) {
			logger.error("pageQueryByQL(PageQueryCondition)", e); //$NON-NLS-1$

			ExceptionUtil.throwCommonException(e.getMessage(),
					ErrorCode.ERROR_CODE_DAO, e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("pageQueryByQL(PageQueryCondition) - end"); //$NON-NLS-1$
		}
		return null;
	}
	
	class PageQueryCallbackWithCount implements HibernateCallback {
		private PageQueryCondition queryCondition = null;
		private PageQueryConditionWithCount queryConditionWithCount = null;

		public PageQueryCallbackWithCount(PageQueryCondition queryCondition) {
			this.queryCondition = queryCondition;
		}
		
		public PageQueryCallbackWithCount(PageQueryConditionWithCount queryConditionWithCount) {
			this.queryConditionWithCount = queryConditionWithCount;
		}
		
		public Object doInHibernate(Session session) throws HibernateException {
			if (logger.isDebugEnabled()) {
				logger.debug("doInHibernate(Session) - start");
			}
			if(null != queryCondition) {
				Query queryObject = session.createQuery(queryCondition.getQueryString());
				String countHQL = HqlUtils.transferCountHQL(queryCondition.getQueryString());
				Query queryCount = session.createQuery(countHQL);
				Long totleCount = (Long) queryCount.uniqueResult();

				PageQueryResult queryResult = new PageQueryResult();
				queryResult.setQueryResult(new ArrayList());
				queryResult.setTotalCount(totleCount.intValue());
				if (totleCount > 0) {
					int pageSize = queryCondition.getPageSize();
					int pageIndex = queryCondition.getPageIndex() - 1;
					int startRowNum = pageIndex * pageSize;
					queryObject.setFirstResult(startRowNum);
					queryObject.setMaxResults(pageSize);
					ScrollableResults sr = queryObject.scroll();
					while (sr.next()) {
						queryResult.getQueryResult().add(sr.get());
					}

				}
				if (logger.isDebugEnabled()) {
					logger.debug("doInHibernate(Session) - end"); //$NON-NLS-1$
				}
				return queryResult;
			}
			else if(null != queryConditionWithCount) {
				Query queryObject = session.createQuery(queryConditionWithCount.getQueryString());
				Query queryCount = session.createQuery(queryConditionWithCount.getCountHQL());
				Long totleCount = (Long) queryCount.uniqueResult();

				PageQueryResult queryResult = new PageQueryResult();
				queryResult.setQueryResult(new ArrayList());
				queryResult.setTotalCount(totleCount.intValue());
				if (totleCount > 0) {
					int pageSize = queryConditionWithCount.getPageSize();
					int pageIndex = queryConditionWithCount.getPageIndex() - 1;
					int startRowNum = pageIndex * pageSize;
					queryObject.setFirstResult(startRowNum);
					queryObject.setMaxResults(pageSize);
					ScrollableResults sr = queryObject.scroll();
					while (sr.next()) {
						queryResult.getQueryResult().add(sr.get());
					}

				}
				if (logger.isDebugEnabled()) {
					logger.debug("doInHibernate(Session) - end"); //$NON-NLS-1$
				}
				return queryResult;
			}
			return null;
		}
	}
}
