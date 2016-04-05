package com.itechart.security.model.persistent.acl;

import com.itechart.security.core.model.acl.Permission;
import com.itechart.security.core.model.acl.SecurityAcl;
import com.itechart.security.model.persistent.BaseEntity;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

/**
 * ACL entity.
 * Represents a single domain object instance with some additional information
 * like ownership and inheritance. Contains permissions for principals on this object
 *
 * @author andrei.samarou
 */
@Entity
@Table(name = "acl_object_identity")
public class Acl extends BaseEntity implements SecurityAcl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private AclObjectKey objectKey;

    @Column(name = "parent_id", updatable = false)
    private Long parentId;

    @Column(name = "owner_id", updatable = false)
    private Long ownerId;

    @OneToMany(fetch = EAGER, mappedBy = "acl", cascade = ALL, orphanRemoval = true)
    private Set<AclEntry> entries;

    @Column(name = "inheriting")
    private boolean inheriting;

    public Acl() {
    }

    public Acl(AclObjectKey objectKey) {
        this(objectKey, null, null);
    }

    public Acl(AclObjectKey objectKey, Long parentId, Long ownerId) {
        Assert.notNull(objectKey);
        this.objectKey = objectKey;
        this.parentId = parentId;
        this.ownerId = ownerId;
        this.inheriting = parentId != null;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public AclObjectKey getObjectKey() {
        return objectKey;
    }

    public Long getParentId() {
        return parentId;
    }

    @Override
    public Long getOwnerId() {
        return ownerId;
    }

    protected Set<AclEntry> getEntries() {
        return entries;
    }

    protected void addEntry(AclEntry entry) {
        if (entries == null) {
            entries = new HashSet<>();
        }
        entries.add(entry);
    }

    public boolean isInheriting() {
        return inheriting;
    }

    public void setInheriting(boolean inheriting) {
        this.inheriting = inheriting;
    }

    public boolean addPermission(Long principalId, Permission permission) {
        return addPermissions(principalId, Collections.singleton(permission));
    }

    public boolean addPermissions(Long principalId, Set<Permission> permissions) {
        AclEntry entry = findEntry(principalId);
        if (entry == null) {
            addEntry(new AclEntry(this, principalId, permissions));
            return true;
        } else {
            boolean result = true;
            for (Permission permission : permissions) {
                result &= entry.addPermission(permission);
            }
            return result;
        }
    }

    public void setPermissions(Long principalId, Set<Permission> permissions) {
        AclEntry entry = findEntry(principalId);
        if (entry == null) {
            addEntry(new AclEntry(this, principalId, permissions));
        } else {
            entry.setPermissions(permissions);
        }
    }

    public void removePrincipal(Long principalId) {
        AclEntry entry = findEntry(principalId);
        if (entry != null) entries.remove(entry);
    }

    public boolean removePermission(Long principalId, Permission permission) {
        AclEntry entry = findEntry(principalId);
        return entry != null && entry.removePermission(permission);
    }

    public boolean hasPermission(Long principalId, Permission permission) {
        AclEntry entry = findEntry(principalId);
        return entry != null && entry.hasPermission(permission);
    }

    public Set<Permission> getPermissions(Long principalId) {
        AclEntry entry = findEntry(principalId);
        return entry != null ? entry.getPermissions() : null;
    }

    public Map<Long, Set<Permission>> getPermissions() {
        if (entries == null) {
            return Collections.emptyMap();
        }
        Map<Long, Set<Permission>> result = new HashMap<>(entries.size());
        for (AclEntry entry : entries) {
            result.put(entry.getPrincipalId(), entry.getPermissions());
        }
        return result;
    }

    public void denyAll(Long principalId) {
        setPermissions(principalId, null);
    }

    private AclEntry findEntry(Long principalId) {
        if (entries != null) {
            for (AclEntry entry : entries) {
                if (entry.getPrincipalId().equals(principalId)) {
                    return entry;
                }
            }
        }
        return null;
    }
}