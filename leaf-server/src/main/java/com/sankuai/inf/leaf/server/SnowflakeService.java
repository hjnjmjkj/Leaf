package com.sankuai.inf.leaf.server;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.sankuai.inf.leaf.IDGen;
import com.sankuai.inf.leaf.common.PropertyFactory;
import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.common.ZeroIDGen;
import com.sankuai.inf.leaf.server.exception.InitException;
import com.sankuai.inf.leaf.snowflake.SnowflakeIDGenImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service("SnowflakeService")
public class SnowflakeService {
    private Logger logger = LoggerFactory.getLogger(SnowflakeService.class);
    IDGen idGen;
    @NacosValue(value = "${leaf.snowflake.enable}:false", autoRefreshed = true)
    private String flag;
    @NacosValue(value = "${leaf.snowflake.zk.address}", autoRefreshed = true)
    private String zkAddress;
    @NacosValue(value = "${leaf.snowflake.port}", autoRefreshed = true)
    private String port;
    public SnowflakeService() throws InitException {
        Properties properties = PropertyFactory.getProperties();
        if (Boolean.parseBoolean(flag)) {
            idGen = new SnowflakeIDGenImpl(zkAddress, Integer.parseInt(port));
            if(idGen.init()) {
                logger.info("Snowflake Service Init Successfully");
            } else {
                throw new InitException("Snowflake Service Init Fail");
            }
        } else {
            idGen = new ZeroIDGen();
            logger.info("Zero ID Gen Service Init Successfully");
        }
    }
    public Result getId(String key) {
        return idGen.get(key);
    }
}
