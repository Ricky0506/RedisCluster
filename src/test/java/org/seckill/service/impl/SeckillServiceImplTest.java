package org.seckill.service.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.service.ISeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml" })
public class SeckillServiceImplTest {
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private ISeckillService seckillService;

	@Test
	public void testExportSeckillUrl() {
		
		long id = 1000;
		long userPhone = 12411111112L;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		System.out.println(exposer.toString());

		String md5 = exposer.getMd5();
		
		SeckillExecution excution = seckillService.executeSeckill(id, userPhone, md5);
		logger.info(excution.toString());
	}

	@Test
	public void testExecuteSeckill() {
		
		long id = 1000L;
		long userPhone = 12711111111L;
		String md5 = "9b8082b22ded08718a4255e9f482a80c";
		SeckillExecution excution = seckillService.executeSeckill(id, userPhone, md5);
		logger.info(excution.toString());
		
	}
}
