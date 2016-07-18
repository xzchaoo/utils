package com.github.xzchaoo.utils.beanutils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/7/16.
 */
public class BeanUtils {
	private static final Logger log = LoggerFactory.getLogger(BeanUtils.class);

	/**
	 * 属性复制, 忽略所有异常, 必须要有相应的get/set方法!
	 *
	 * @param to
	 * @param from
	 */
	public static void copyPropertiesQuietly(Object to, Object from) {
		try {
			org.apache.commons.beanutils.BeanUtils.copyProperties(to, from);
		} catch (Exception e) {
			log.warn("copyPropertiesQuietly", e);
		}
	}
}
