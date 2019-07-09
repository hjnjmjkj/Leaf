package com.sankuai.inf.leaf.server;

import com.mmk.provider.LeafSegmentImpl;
import com.mmk.spi.LeafSegment;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LeafServerApplicationTests {
	@Reference
	private LeafSegment leafSegment;
	@Test
	public void contextLoads() {
	}
	@Test
	public void TestLeafSegment() {
		System.out.println(leafSegment.getSegmentID("leaf-segment-test"));
	}

}
