package com.castle.theme.adminlte;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.castle.theme.adminlte.ztree.ZTreeLazyList;
import com.castle.theme.adminlte.ztree.ZTreeLazyListSerializer;
import com.castle.theme.adminlte.ztree.ZTreeNode;
import com.castle.theme.adminlte.ztree.ZTreeNodeSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Configuration
public class AdminLTEConfigBean extends WebMvcConfigurerAdapter implements InitializingBean {

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/adminlte/**").addResourceLocations("classpath:/META-INF/adminlte/").setCachePeriod(60 * 60 * 24 * 40);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(ZTreeNode.class, new ZTreeNodeSerializer<>());
		simpleModule.addSerializer(ZTreeLazyList.class, new ZTreeLazyListSerializer<>());
		objectMapper.registerModule(simpleModule);
	}

}
