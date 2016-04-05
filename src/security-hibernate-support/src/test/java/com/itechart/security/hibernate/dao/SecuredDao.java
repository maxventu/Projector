package com.itechart.security.hibernate.dao;

import com.itechart.security.core.annotation.AclFilter;
import com.itechart.security.core.annotation.AclFilterRule;
import com.itechart.security.core.model.acl.Permission;
import com.itechart.security.hibernate.aop.HibernateSecuredDao;
import com.itechart.security.hibernate.model.TestObject;
import com.itechart.security.hibernate.model.TestObjectExt;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Dao containing a set of methods that allows to test security annotations
 *
 * @author andrei.samarou
 */
public class SecuredDao implements HibernateSecuredDao {

    private SessionFactory sessionFactory;

    /////////////////////// Bad declarations ////////////////////////

    @AclFilter
    public void doBadDeclaration1() {
    }

    @AclFilter
    public <T> T[] doBadDeclaration2() {
        return null;
    }

    @AclFilter
    public <T> T doBadDeclaration3() {
        return null;
    }

    @AclFilter
    public <T> List<T> doBadDeclaration4() {
        return null;
    }

    @AclFilter
    public <K, V> Map<K, V> doBadDeclaration5() {
        return null;
    }

    @AclFilter
    @SuppressWarnings("UnusedParameters")
    public void doBadDeclaration6(List<TestObject> objects) {
    }

    @AclFilter({
            @AclFilterRule(type = TestObject.class),
            @AclFilterRule(type = TestObject.class, inherit = true)
    })
    public void doBadDeclaration7() {
    }

    @AclFilterRule(type = TestObject.class)
    @AclFilterRule(type = TestObject.class, permissions = Permission.WRITE)
    public void doBadDeclaration8() {
    }

    @AclFilterRule(type = TestObject.class, permissions = Permission.READ)
    @AclFilterRule(type = TestObject.class, permissions = Permission.WRITE)
    public void doBadDeclaration9() {
    }

    /////////////////////// Good declarations ////////////////////////

    @AclFilter
    public TestObject doGoodDeclaration1() {
        return null;
    }

    @AclFilter
    public TestObject[] doGoodDeclaration2() {
        return null;
    }

    @AclFilter
    public List<TestObject> doGoodDeclaration3() {
        return null;
    }

    @AclFilter
    public List<List<TestObject>> doGoodDeclaration4() {
        return null;
    }

    @AclFilter
    public <T> Map<T, TestObject> doGoodDeclaration5() {
        return null;
    }

    @AclFilter(@AclFilterRule(type = TestObject.class))
    public <T> T[] doGoodDeclaration6() {
        return null;
    }

    @AclFilter(@AclFilterRule(type = TestObject.class))
    public <T> List<T> doGoodDeclaration7() {
        return null;
    }

    @AclFilter(@AclFilterRule(type = TestObject.class))
    public <K, V> Map<K, V> doGoodDeclaration8() {
        return null;
    }

    @AclFilterRule(type = TestObject.class)
    public void doGoodDeclaration9() {
    }

    @AclFilter({
            @AclFilterRule(type = TestObject.class),
            @AclFilterRule(type = TestObject.class, permissions = Permission.READ)
    })
    public void doGoodDeclaration10() {
    }

    @AclFilterRule(type = TestObject.class)
    @AclFilterRule(type = TestObject.class)
    public void doGoodDeclaration11() {
    }

    //////////////////////////////////////////////////////////////////////

    @AclFilter
    @SuppressWarnings("unchecked")
    public Map<TestObject, TestObjectExt> doDeclarationWithTwoImplicitTypes(List<String> forEnabledFilters) {
        // putting enabled filter's names to forEnabledFilters to analyze them in caller
        Session session = getSessionFactory().getCurrentSession();
        forEnabledFilters.addAll(((Set<String>) getSessionFactory().getDefinedFilterNames()).stream()
                .filter(filterName -> session.getEnabledFilter(filterName) != null).collect(Collectors.toList()));
        return null;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Required
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
