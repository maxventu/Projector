package com.itechart.security.hibernate.filter;

import org.hibernate.mapping.PersistentClass;

import java.util.List;

/**
 * Factory for producing of security filter configurations.
 * Filter configuration bases on {@link PersistentClass}
 *
 * @author andrei.samarou
 * @see FilterConfig
 */
public interface FilterFactory {

    List<FilterConfig> createFilters(PersistentClass persistentClass);
}
