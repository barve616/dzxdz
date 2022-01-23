package com.rmyh.dzxdz.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.rmyh.dzxdz.repository"})
public class MybatisPlusConfig {
	@Value("${spring.application.name}")
	private String applicationName;



	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		// 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
		// paginationInterceptor.setOverflow(false);
		// 设置最大单页限制数量，默认 500 条，-1 不受限制
		paginationInterceptor.setLimit(500);
		// 开启 count 的 join 优化,只针对部分 left join
		paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));

		System.out.println("这是一个Bean");
		return paginationInterceptor;
	}


	@Bean
	public IdentifierGenerator idGenerator() {
		return new IdentifierGenerator() {
			@Override
			public Number nextId(Object entity) {
				Long l = System.currentTimeMillis();
				return l;
			}

			@Override
			public String nextUUID(Object entity) {
				Long l = System.currentTimeMillis();
				return String.valueOf(l);
			}
		};
	}

}
