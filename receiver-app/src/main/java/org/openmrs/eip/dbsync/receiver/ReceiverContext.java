package org.openmrs.eip.dbsync.receiver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.ProducerTemplate;
import org.openmrs.eip.dbsync.SyncContext;
import org.openmrs.eip.dbsync.receiver.management.entity.SiteInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Holds contextual data for the receiver
 */
public final class ReceiverContext {
	
	protected static final Logger log = LoggerFactory.getLogger(ReceiverContext.class);
	
	public static final int MAX_COUNT = 1000;
	
	public static final long DELAY_MILLS = 15000;
	
	public static final String PROP_REC_CONSUMER_DELAY = "receiver.consumer.delay";
	
	private static boolean isStopping = false;
	
	private static Map<String, SiteInfo> siteNameAndInfoMap = null;
	
	/**
	 * Turn on a flag which is monitored by sync message consumers to allow them to gracefully stop
	 * message consumption and processing before the application comes to a stop.
	 */
	public static void setStopSignal() {
		isStopping = true;
		log.info("Received application stop signal");
	}
	
	/**
	 * Checks if the application stop signal has been received
	 * 
	 * @return true if the application is stopping otherwise false
	 */
	public static boolean isStopSignalReceived() {
		return isStopping;
	}
	
	private static Map<String, SiteInfo> getSiteNameAndInfoMap() {
		synchronized (ReceiverContext.class) {
			if (siteNameAndInfoMap == null) {
				log.info("Loading sites...");
				
				ProducerTemplate producerTemplate = SyncContext.getBean(ProducerTemplate.class);
				String siteClass = SiteInfo.class.getSimpleName();
				final String siteQueryUri = "jpa:" + siteClass + " ?query=SELECT s FROM " + siteClass + " s";
				List<SiteInfo> sites = producerTemplate.requestBody(siteQueryUri, null, List.class);
				siteNameAndInfoMap = new HashMap(sites.size());
				sites.stream().forEach((site) -> siteNameAndInfoMap.put(site.getIdentifier().toLowerCase(), site));
				
				if (log.isDebugEnabled()) {
					log.debug("Loaded sites: " + sites);
				}
				
				log.info("Successfully loaded " + sites.size() + " site(s)");
			}
		}
		
		return siteNameAndInfoMap;
	}
	
	/**
	 * Gets {@link SiteInfo} that matches the specified identifier
	 *
	 * @return {@link SiteInfo} object
	 */
	public static SiteInfo getSiteInfo(String identifier) {
		return getSiteNameAndInfoMap().get(identifier.toLowerCase());
	}
	
}
