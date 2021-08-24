package org.openmrs.eip.dbsync.camel;

import org.apache.camel.Exchange;
import org.springframework.context.ApplicationContext;

public class FakeOpenmrsProducer extends AbstractOpenmrsProducer {

    public FakeOpenmrsProducer(final OpenmrsEndpoint endpoint,
                               final ApplicationContext applicationContext,
                               final ProducerParams params) {
        super(endpoint, applicationContext, params);
    }

    @Override
    public void process(final Exchange exchange) throws Exception {
        // Nothing
    }
}
