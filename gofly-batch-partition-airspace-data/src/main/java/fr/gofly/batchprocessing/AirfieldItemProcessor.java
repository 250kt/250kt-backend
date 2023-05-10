package fr.gofly.batchprocessing;

import fr.gofly.model.Airfield;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class AirfieldItemProcessor implements ItemProcessor<Airfield, Airfield> {
    private static final Logger log = LoggerFactory.getLogger(AirfieldItemProcessor.class);

    @Override
    public Airfield process(final Airfield airfield) throws Exception{
        return null;
    }
}
