package com.castle.elfinder;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import com.castle.web.ServletSupport;

@Configuration
@ComponentScan(basePackages = { "br.com.trustsystems.elfinder" }, useDefaultFilters = false, includeFilters = { @Filter({ Controller.class }),
		@Filter({ ServletSupport.class }) })
@ServletSupport
public class ElfinderServletConfig {

}
