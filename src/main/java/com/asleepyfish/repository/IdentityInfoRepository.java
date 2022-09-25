package com.asleepyfish.repository;

import com.asleepyfish.dto.IdentityInfo;
import com.asleepyfish.dto.IdentityInfoKey;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: asleepyfish
 * @Date: 2022/9/9 16:51
 * @Description: 身份信息存储
 */
public interface IdentityInfoRepository extends JpaRepository<IdentityInfo, IdentityInfoKey> {
}
