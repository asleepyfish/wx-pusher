package com.asleepyfish.test;

import com.asleepyfish.dto.IdentityInfo;
import com.asleepyfish.repository.DistrictInfoRepository;
import com.asleepyfish.repository.IdentityInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author: asleepyfish
 * @Date: 2022/9/10 14:36
 * @Description: TODO
 */
@SpringBootTest
public class JpaTest {
    @Resource
    private IdentityInfoRepository identityInfoRepository;

    @Resource
    private DistrictInfoRepository districtInfoRepository;

    @Test
    public void testUpdate() throws Exception {
        System.out.println(districtInfoRepository.findById(101010100).get());
        IdentityInfo identityInfo = new IdentityInfo();
        identityInfo.setAppId("1");
        identityInfo.setAppSecret("1");
        identityInfo.setOpenId("1");
        identityInfo.setPublicId("1");
        identityInfoRepository.save(identityInfo);

/*        IdentityInfo identityInfo1 = new IdentityInfo();
        identityInfo1.setAppId("1");
        identityInfo1.setAppSecret("1");
        identityInfo1.setOpenId("1");
        identityInfo1.setLatitude("2");
        identityInfo1.setLongitude("2");
        identityInfo1.setPrecision("2");

        IdentityInfo identityInfo = identityInfoRepository.findById(new IdentityInfoKey("1", "1", "1")).orElse(null);
        if (identityInfo != null) {
            WxOpUtils.copyPropertiesNotNull(identityInfo1, identityInfo);
            identityInfoRepository.save(identityInfo);
        } else {
            identityInfoRepository.save(identityInfo1);
        }*/
    }
}
