package com.mmk.provider;

import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.common.Status;
import com.sankuai.inf.leaf.server.exception.LeafServerException;
import com.sankuai.inf.leaf.server.exception.NoKeyException;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author hk
 * @date 2019/7/9
 */
public class LeafImpl {
    protected String get(@PathVariable("key") String key, Result id) {
        Result result;
        if (key == null || key.isEmpty()) {
            throw new NoKeyException();
        }

        result = id;
        if (result.getStatus().equals(Status.EXCEPTION)) {
            throw new LeafServerException(result.toString());
        }
        return String.valueOf(result.getId());
    }
}
