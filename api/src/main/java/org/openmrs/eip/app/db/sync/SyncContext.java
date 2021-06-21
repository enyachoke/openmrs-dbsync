package org.openmrs.eip.app.db.sync;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Holds contextual data for the application
 */
@Component
public class SyncContext implements ApplicationContextAware {
	
	private static ApplicationContext appContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		appContext = applicationContext;
	}
	
	/**
	 * Gets the {@link SyncMode} in which the application is running
	 * 
	 * @return SyncMode
	 */
	public static SyncMode getMode() {
		return appContext.getBean(SyncMode.class);
	}
	
	/**
	 * Gets the bean matching the specified type from the application context
	 *
	 * @return an instance of the bean matching the specified type
	 */
	public static <T> T getBean(Class<T> clazz) {
		return appContext.getBean(clazz);
	}
	
}
