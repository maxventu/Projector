package com.itechart.security.dao.impl;

import com.itechart.security.dao.RoleDao;
import com.itechart.security.model.persistent.Role;
import org.springframework.stereotype.Repository;

/**
 * @author andrei.samarou
 */
@Repository
public class RoleDaoImpl extends BaseHibernateDao<Role> implements RoleDao {
}