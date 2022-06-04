package com.zakharenko.lab03.dao.impl.role;

import com.zakharenko.lab03.dao.AbstractDao;
import com.zakharenko.lab03.entity.Playlist;
import com.zakharenko.lab03.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends AbstractDao<Role> implements RoleDao {
    public RoleDaoImpl() {
        setClazz(Role.class);
    }
}
