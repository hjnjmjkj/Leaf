package com.mmk.provider;

import com.mmk.spi.IDService;
import com.sankuai.inf.leaf.server.SegmentService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

/**
 * @author hk
 * @date 2019/7/8
 */
@Service
public class LeafSegmentImpl extends LeafImpl implements IDService {
    @Autowired
    SegmentService segmentService;
    @Override
    public String getSegmentID(String key) {
        return get(key, segmentService.getId(key));
    }
}
