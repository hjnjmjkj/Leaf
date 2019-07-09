package com.sankuai.inf.leaf.server.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.sankuai.inf.leaf.IDGen;
import com.sankuai.inf.leaf.common.ZeroIDGen;
import com.sankuai.inf.leaf.segment.SegmentIDGenImpl;
import com.sankuai.inf.leaf.segment.dao.IDAllocDao;
import com.sankuai.inf.leaf.segment.dao.impl.IDAllocDaoImpl;
import com.sankuai.inf.leaf.server.SegmentService;
import com.sankuai.inf.leaf.server.exception.InitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hk
 * @date 2019/7/9
 */
@Configuration
public class IDGenConfig {
    private Logger logger = LoggerFactory.getLogger(IDGenConfig.class);
    @NacosValue(value = "${leaf.segment.enable:true}", autoRefreshed = false)
    private String segmentFlag;
    @NacosValue(value = "${leaf.jdbc.url}", autoRefreshed = false)
    private String url;
    @NacosValue(value = "${leaf.jdbc.username}", autoRefreshed = false)
    private String username;
    @NacosValue(value = "${leaf.jdbc.password}", autoRefreshed = false)
    private String password;
    @NacosValue(value = "${leaf.snowflake.enable:false}", autoRefreshed = false)
    private String snowflakeFlag;
    @NacosValue(value = "${leaf.snowflake.zk.address}", autoRefreshed = false)
    private String zkAddress;
    @NacosValue(value = "${leaf.snowflake.port}", autoRefreshed = false)
    private String port;

    @Bean("segmentIDGen")
    public IDGen getSegmentIDGen() throws Exception {
        IDGen idGen;
        if (Boolean.parseBoolean(segmentFlag)) {

            // Config dataSource
            DruidDataSource dataSource; dataSource = new DruidDataSource();
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.init();

            // Config Dao
            IDAllocDao dao = new IDAllocDaoImpl(dataSource);

            // Config ID Gen
            idGen = new SegmentIDGenImpl();
            ((SegmentIDGenImpl) idGen).setDao(dao);
            if (idGen.init()) {
                logger.info("Segment Service Init Successfully");
            } else {
                throw new InitException("Segment Service Init Fail");
            }
            return idGen;
        } else {
            logger.info("Zero ID Gen Service Init Successfully");
            return new ZeroIDGen();
        }
    }
}
