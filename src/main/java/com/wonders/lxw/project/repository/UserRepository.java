package com.wonders.lxw.project.repository;

import com.wonders.lxw.project.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lixuanwu on 15/10/30.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Cacheable(cacheManager = "userCacheManager", cacheNames = "userCache", key = "'lxw:user:token:' + #p0")
    User findByToken(String id);
}
