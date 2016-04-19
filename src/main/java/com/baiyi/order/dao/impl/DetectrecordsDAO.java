package com.baiyi.order.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.baiyi.order.dao.IDetectrecordsDAO;
import com.baiyi.order.pojo.Detectrecords;
import com.baiyi.order.util.BeanUtil;
import com.baiyi.order.util.Log4JFactory;
import com.baiyi.order.util.PageBean;

@Repository
public class DetectrecordsDAO implements IDetectrecordsDAO {

	@Resource
	private HibernateTemplate hibernateTemplate;

	private static final Logger log = LoggerFactory.getLogger(DetectrecordsDAO.class);

	protected void initDao() {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.baiyi.tvset.dao.IDetectrecordsDAO#save(com.baiyi.tvset.dao.
	 * Detectrecords)
	 */
	public void save(Detectrecords transientInstance) {
		log.debug("saving Detectrecords instance");
		try {
			hibernateTemplate.save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.baiyi.tvset.dao.IDetectrecordsDAO#delete(com.baiyi.tvset.dao.
	 * Detectrecords)
	 */
	public void delete(Detectrecords persistentInstance) {
		log.debug("deleting Detectrecords instance");
		try {
			hibernateTemplate.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.baiyi.tvset.dao.IDetectrecordsDAO#findById(java.lang.Integer)
	 */
	public Detectrecords findById(java.lang.Integer id) {
		log.debug("getting Detectrecords instance with id: " + id);
		try {
			Detectrecords instance = (Detectrecords) hibernateTemplate.get("com.baiyi.tvset.dao.Detectrecords", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.baiyi.tvset.dao.IDetectrecordsDAO#findByExample(com.baiyi.tvset.dao.
	 * Detectrecords)
	 */
	public List findByExample(Detectrecords instance) {
		log.debug("finding Detectrecords instance by example");
		try {
			List results = hibernateTemplate.findByExample(instance);
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.baiyi.tvset.dao.IDetectrecordsDAO#findByProperty(java.lang.String,
	 * java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Detectrecords instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Detectrecords as model where model." + propertyName + "= ?";
			return hibernateTemplate.find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.baiyi.tvset.dao.IDetectrecordsDAO#findByNo(java.lang.Object)
	 */
	public List findByNo(Object no) {
		return findByProperty(NO, no);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.baiyi.tvset.dao.IDetectrecordsDAO#findByStation(java.lang.Object)
	 */
	public List findByStation(Object station) {
		return findByProperty(STATION, station);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.baiyi.tvset.dao.IDetectrecordsDAO#findByStatus(java.lang.Object)
	 */
	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.baiyi.tvset.dao.IDetectrecordsDAO#findByLine(java.lang.Object)
	 */
	public List findByLine(Object line) {
		return findByProperty(LINE, line);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.baiyi.tvset.dao.IDetectrecordsDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all Detectrecords instances");
		try {
			String queryString = "from Detectrecords";
			return hibernateTemplate.find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.baiyi.tvset.dao.IDetectrecordsDAO#merge(com.baiyi.tvset.dao.
	 * Detectrecords)
	 */
	public Detectrecords merge(Detectrecords detachedInstance) {
		log.debug("merging Detectrecords instance");
		try {
			Detectrecords result = (Detectrecords) hibernateTemplate.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.baiyi.tvset.dao.IDetectrecordsDAO#attachDirty(com.baiyi.tvset.dao.
	 * Detectrecords)
	 */
	public void attachDirty(Detectrecords instance) {
		log.debug("attaching dirty Detectrecords instance");
		try {
			hibernateTemplate.saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.baiyi.tvset.dao.IDetectrecordsDAO#attachClean(com.baiyi.tvset.dao.
	 * Detectrecords)
	 */
	public void attachClean(Detectrecords instance) {
		log.debug("attaching clean Detectrecords instance");
		try {
			hibernateTemplate.lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IDetectrecordsDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IDetectrecordsDAO) ctx.getBean("DetectrecordsDAO");
	}

	public void deleteRecord(final Date endDate) {
		this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String sql = "delete from Detectrecords where addtime <:endDate";
				Query query = session.createQuery(sql);
				query.setDate("endDate", endDate);
				query.executeUpdate();
				return null;
			}
		});
	}

	public List findByPage(final String no, final String begindate, final String enddate, final String queryline, final String querystatus, final PageBean pageBean) {
		try {
			return (List) this.hibernateTemplate.execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
					StringBuilder countStr = new StringBuilder("select count(dr.id) from Detectrecords as dr where 1=1");
					StringBuilder searchStr = new StringBuilder("from Detectrecords as dr where 1=1");

					if (no != null) {
						countStr.append(" and dr.no=:no");
						searchStr.append(" and dr.no=:no");
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date begin = null;
					Date end = null;
					if (begindate != null) {
						try {
							begin = sdf.parse(begindate);
							countStr.append(" and dr.addtime >=:beginDate");
							searchStr.append(" and dr.addtime >=:beginDate");
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					if (enddate != null) {
						try {
							end = sdf.parse(enddate);
							countStr.append(" and dr.addtime <=:endDate");
							searchStr.append(" and dr.addtime <=:endDate");
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					if (queryline != null) {
						countStr.append(" and dr.line=:line");
						searchStr.append(" and dr.line=:line");
					}
					if (querystatus != null) {
						countStr.append(" and dr.status=:status");
						searchStr.append(" and dr.status=:status");
					}
					searchStr.append(" order by dr.id desc");
					Query countQuery = session.createQuery(countStr.toString());
					Query searchQuery = session.createQuery(searchStr.toString());

					if (no != null) {
						countQuery.setString("no", no);
						searchQuery.setString("no", no);
					}
					if (begin != null) {
						// countQuery.setDate("beginDate", begin);
						// searchQuery.setDate("beginDate", begin);
						countQuery.setTimestamp("beginDate", begin);
						searchQuery.setTimestamp("beginDate", begin);
					}
					if (end != null) {
						countQuery.setTimestamp("endDate", end);
						searchQuery.setTimestamp("endDate", end);
						// searchQuery.setDate("endDate", end);
					}
					if (queryline != null) {
						countQuery.setString("line", queryline);
						searchQuery.setString("line", queryline);
					}
					if (querystatus != null) {
						countQuery.setString("status", querystatus);
						searchQuery.setString("status", querystatus);
					}
					Integer maxCount = new Integer(((Long) countQuery.uniqueResult()).toString());
					Integer startPage = pageBean.getStartPage();
					int maxPage = 0;
					Integer pageSize = pageBean.getPageSize();
					if (maxCount <= 0) {
						startPage = 1;
						maxPage = 1;
					} else {
						if (maxCount % pageSize == 0) {
							maxPage = maxCount / pageSize;
						} else {
							maxPage = maxCount / pageSize + 1;
						}
					}
					if (startPage > maxPage) {
						startPage = maxPage;
					}
					if (startPage <= 1) {
						startPage = 1;
					}
					pageBean.setStartPage(startPage);
					pageBean.setMaxPage(maxPage);
					pageBean.setRowCount(maxCount);
					return searchQuery.setFirstResult((startPage - 1) * pageSize).setMaxResults(pageSize).list();
				}

			});
		} catch (RuntimeException re) {
			Log4JFactory.instance().error(BeanUtil.WEBSITE + " DetectrecordsDAO findByPage ERROR", re);
			throw re;
		}
	}
}