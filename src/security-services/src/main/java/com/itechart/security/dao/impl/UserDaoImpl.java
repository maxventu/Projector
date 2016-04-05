package com.itechart.security.dao.impl;

import com.itechart.security.dao.UserDao;
import com.itechart.security.model.filter.UserFilter;
import com.itechart.security.model.persistent.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author andrei.samarou
 */
@Repository
public class UserDaoImpl extends BaseHibernateDao<User> implements UserDao {

    @Override
    public User findByName(String userName) {
        return findObject("from User u where u.userName = ?", userName);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findUsers(UserFilter filter) {
        return getHibernateTemplate().executeWithNativeSession(session -> {
            Criteria criteria = createFilterCriteria(filter, session);
            return executePagingDistinctCriteria(session, criteria, filter);
        });
    }

    @Override
    public int countUsers(UserFilter filter) {
        return getHibernateTemplate().executeWithNativeSession(session -> {
            Criteria criteria = createFilterCriteria(filter, session);
            appendSortingFilterConditions(criteria, filter);
            criteria.setProjection(Projections.rowCount());
            return ((Number) criteria.uniqueResult()).intValue();
        });
    }

    private Criteria createFilterCriteria(UserFilter filter, Session session) {
        Criteria criteria = session.createCriteria(User.class, "u");
        if (filter.getRoleId() != null) {
            criteria.createAlias("u.roles", "r");
            criteria.add(Restrictions.eq("r.id", filter.getRoleId()));
        }
        if (filter.getGroupId() != null) {
            criteria.createAlias("u.groups", "g");
            criteria.add(Restrictions.eq("g.id", filter.getGroupId()));
        }
        if (filter.isActive()) {
            criteria.add(Restrictions.eq("u.active", true));
        }
        if (StringUtils.hasText(filter.getText())) {
            criteria.add(Restrictions.disjunction(
                    Restrictions.ilike("u.userName", filter.getText(), MatchMode.ANYWHERE),
                    Restrictions.ilike("u.firstName", filter.getText(), MatchMode.ANYWHERE),
                    Restrictions.ilike("u.lastName", filter.getText(), MatchMode.ANYWHERE),
                    Restrictions.ilike("u.email", filter.getText(), MatchMode.ANYWHERE)
            ));
        }
        return criteria;
    }

    @Override
    public boolean changePassword(String userName, String oldPassword, String newPassword) {
        // don't use hbm query for update, because hbm try to
        // create temporary table for batch update of inheriting entities
        User user = findByName(userName);
        if (user == null || !user.getPassword().equals(oldPassword)) {
            return false;
        }
        user.setPassword(newPassword);
        update(user);
        return true;
    }

    @Override
    public boolean setUserActivity(Long userId, boolean active) {
        User user = get(userId);
        if (user == null) {
            return false;
        }
        user.setActive(active);
        update(user);
        return true;
    }
}