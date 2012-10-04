package com.utils.spring;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;


@SuppressWarnings({"unchecked", "rawtypes"})
public class HibernateHelper extends HibernateDaoSupport {
	
	public <M> M get(Class<M> type, long id) {
		
		return type.cast(getHibernateTemplate().get(type, id));
	}
	
	public Criteria createCriteria (Class<?> clazz) {
		return super.getSession().createCriteria(clazz);
	}
	
	public void saveOrUpdate (Object object) throws HibernateHelperException{
		try{
			 HibernateTemplate helper = super.getHibernateTemplate();
			helper.saveOrUpdate(object);
		}catch (DataAccessException e){
			 throw new HibernateHelperException(e);
		}
	}
	
	public List<?> find (String query) {
		try{
		   HibernateTemplate helper = super.getHibernateTemplate();
		   return helper.find(query);
		   
		}catch (DataAccessException e){
			e.printStackTrace();
			return Collections.EMPTY_LIST;
		}
	}
	
	public List<?> find (final String sql, final int fetchSize, final int pageNumber) {
		try{
			 HibernateTemplate template = getHibernateTemplate();
		        return (List<?>) template.executeFind(new HibernateCallback() {
		            public Object doInHibernate(Session session) throws HibernateException, SQLException {
		                Query query = session.createQuery(sql);
		                query.setMaxResults(fetchSize);
		                query.setFirstResult(pageNumber);
		                return query.list();
		            }
		        });
		}catch (DataAccessException e){
			return Collections.EMPTY_LIST;
		}
	}
	
	public Object findUniqueResult (String query)  {
		try {
			HibernateTemplate helper = super.getHibernateTemplate();
			return DataAccessUtils.uniqueResult(helper.find(query));
		} catch (IncorrectResultSizeDataAccessException e){
			return null;
		}
	}
	
	public void delete (Object object) throws HibernateHelperException {
		try{
			HibernateTemplate helper = super.getHibernateTemplate();
			helper.delete(object);
		}catch (DataAccessException e){
			throw new HibernateHelperException(e);
		}
	}
	
	public <M> List<M> listAll(Class<M> type) {
		return (List<M>) getHibernateTemplate().loadAll(type);
	}

	public <T> List<T> find(String queryName, Object[] values) {
		return getHibernateTemplate().findByNamedQuery(queryName, values);
	}

	public <T> List<T> find(String queryName, String[] paramNames, Object[] values) {
		return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, paramNames, values);
	}
	
	public void executeNamedUpdate(String queryName) {
		executeNamedUpdateWithParams(queryName, null, null);
	}
	
	public void executeNamedUpdateWithParams(String queryName, String[] paramNames, Object[] paramValues) {
		Query query= super.getSession().getNamedQuery(queryName);
		setParameters(query, paramNames, paramValues);
		query.executeUpdate();
	}

	private void setParameters(Query query, String[] paramNames, Object[] paramValues) {
		if (paramNames != null && paramValues != null) {
			Assert.isTrue(paramNames.length == paramValues.length, "Invalid length of parameters in query");
			if (paramNames != null && paramNames.length > 0) {
				for (int i = 0; i < paramNames.length; i++) {
					String name = paramNames[i];
					Object value = paramValues[i];
					if (value != null && Collection.class.isAssignableFrom(value.getClass())) {
						query.setParameterList(name, (Collection) value);
					} else {
						query.setParameter(name, value);
					}
				}
			}
		}
	}


}

