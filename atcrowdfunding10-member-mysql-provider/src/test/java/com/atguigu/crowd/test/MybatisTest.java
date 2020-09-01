package com.atguigu.crowd.test;

import com.atguigu.crowd.CrowdMySql2000;
import com.atguigu.crowd.entity.po.MemberPO;
import com.atguigu.crowd.mapper.MemberPOMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 孔佳齐丶
 * @create 2020-08-27 11:48
 * @package PACKAGE_NAME
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrowdMySql2000.class )
public class MybatisTest {

    private Logger logger = LoggerFactory.getLogger(MybatisTest.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MemberPOMapper memberPOMapper;

/*    @Autowired
    private ProjectPOMapper projectPOMapper;

    @Test
    public void TestProjectMapper(){
        List<PortalTypeVO> portalTypeVOS = projectPOMapper.selectPortalTypeVOList();
        for (PortalTypeVO a:portalTypeVOS) {
            System.out.println(a);
        }
    }*/



    @Test
    public void testMapper(){
        BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();
        String source="123123";
        String encode = cryptPasswordEncoder.encode(source);
        MemberPO memberPO = new MemberPO(null,"jack",encode,"杰克","jack@qq.com",1,1,"jeike","123123",2);
        memberPOMapper.insert(memberPO);
    }

    @Test
    public void testMyBatis() throws SQLException {
        Connection connection = dataSource.getConnection();

        logger.debug(String.valueOf(connection));
    }
}
