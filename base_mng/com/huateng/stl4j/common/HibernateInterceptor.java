package com.huateng.stl4j.common;

import java.io.Serializable;
import java.util.Iterator;

import om.util.OmUtils;

import org.hibernate.CallbackException;
import org.hibernate.EntityMode;
import org.hibernate.Hibernate;
import org.hibernate.Interceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

import com.huateng.common.CommonFunction;
import com.huateng.ebank.common.GlobalInfo;

public class HibernateInterceptor implements Interceptor {
	public boolean onLoad(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) throws CallbackException {

		for (int i = 0; i < types.length; i++) {
			if (null != state[i] && Hibernate.STRING.equals(types[i])) {
				state[i] = ((String) state[i]).trim();
			}
		}
		return true;
	}

	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) throws CallbackException {
		int length = propertyNames.length;
		
		GlobalInfo gl = GlobalInfo.getCurrentInstanceWithoutException();
		for (int i = 0; i < length; i++) {
			if (propertyNames[i].equalsIgnoreCase("LASTUPDTIME")) {
				try {
					if (types[i].equals(Hibernate.STRING) || types[i].equals(Hibernate.CHARACTER)) {
						currentState[i] = OmUtils.format(OmUtils.TIME_NUM_PATTERN, new java.util.Date());
					}
				} catch (Exception ex) {
				}
			}
			else if (propertyNames[i].equalsIgnoreCase("LASTUPDOPRNO")) {
				try {
					if (types[i].equals(Hibernate.STRING) || types[i].equals(Hibernate.CHARACTER)) {
						if(null != gl && !OmUtils.isEmpty(gl.getOprNo())) {
							currentState[i] = gl.getOprNo();
						}
					}
				} catch (Exception ex) {
				}
			}
		}
		return true;
	}

	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) throws CallbackException {
		int length = propertyNames.length;
		
		GlobalInfo gl = GlobalInfo.getCurrentInstanceWithoutException();
		for (int i = 0; i < length; i++) {
			if (propertyNames[i].equalsIgnoreCase("LASTUPDTIME")) {
				try {
					if (types[i].equals(Hibernate.STRING) || types[i].equals(Hibernate.CHARACTER)) {
						state[i] = OmUtils.format(OmUtils.TIME_NUM_PATTERN, new java.util.Date());
					}
				} catch (Exception ex) {
				}
			}
			else if (propertyNames[i].equalsIgnoreCase("CRTTIME")) {
				try {
					if (types[i].equals(Hibernate.STRING) || types[i].equals(Hibernate.CHARACTER)) {
						if(OmUtils.isEmpty((String) state[i])) {
							state[i] = OmUtils.format(OmUtils.TIME_NUM_PATTERN, new java.util.Date());
						}
					}
				} catch (Exception ex) {
				}
			}
			else if (propertyNames[i].equalsIgnoreCase("LASTUPDOPRNO")) {
				try {
					if (types[i].equals(Hibernate.STRING) || types[i].equals(Hibernate.CHARACTER)) {
						if(null != gl && !OmUtils.isEmpty(gl.getOprNo())) {
							state[i] = gl.getOprNo();
						}
					}
				} catch (Exception ex) {
				}
			}
		}
		return true;
	}

	public void onDelete(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) throws CallbackException {

	}

	public void preFlush(Iterator entities) throws CallbackException {

	}

	public void postFlush(Iterator entities) throws CallbackException {
	}

	public Boolean isUnsaved(Object entity) {
		return null;
	}

	public int[] findDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		return null;
	}

	public Object instantiate(Class clazz, Serializable id)
			throws CallbackException {
		return null;
	}

	public void afterTransactionBegin(Transaction arg0) {
		// TODO Auto-generated method stub
	}

	public void afterTransactionCompletion(Transaction arg0) {
		// TODO Auto-generated method stub
	}

	public void beforeTransactionCompletion(Transaction arg0) {
		// TODO Auto-generated method stub
	}

	public Object getEntity(String arg0, Serializable arg1)
			throws CallbackException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getEntityName(Object arg0) throws CallbackException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object instantiate(String arg0, EntityMode arg1, Serializable arg2)
			throws CallbackException {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean isTransient(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void onCollectionRecreate(Object arg0, Serializable arg1)
			throws CallbackException {
		// TODO Auto-generated method stub
	}

	public void onCollectionRemove(Object arg0, Serializable arg1)
			throws CallbackException {
		// TODO Auto-generated method stub
	}

	public void onCollectionUpdate(Object arg0, Serializable arg1)
			throws CallbackException {
		// TODO Auto-generated method stub
	}

	public String onPrepareStatement(String arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
}
