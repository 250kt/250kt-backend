package fr.gofly.config;

import fr.gofly.model.Airfield;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class BatchConfigAirfield {
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private DataSource dataSource;

    @Bean
    public Job job(JobRepository jobRepository, Step step){
        return new JobBuilder("job", jobRepository)
            .start(step)
            .build();
    }

    @Bean
    public Step step(ItemReader<Airfield> reader, ItemWriter<Airfield> writer, JobRepository jobRepository){
        return new StepBuilder("step", jobRepository)
                .<Airfield, Airfield>chunk(100, transactionManager)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public StaxEventItemReader<Airfield> itemReader() {
        return new StaxEventItemReaderBuilder<Airfield>()
                .name("itemReader")
                .resource(new FileSystemResource("gofly-batch-partition-airspace-data/files/import.xml"))
                .addFragmentRootElements("Ad")
                .unmarshaller(airfieldMarshaller())
                .build();
    }

    public XStreamMarshaller airfieldMarshaller(){
        XStreamMarshaller marshaller = new XStreamMarshaller();

        Map<String, String> alias = new HashMap<>();
        alias.put("Ad", "fr.gofly.model.Airfield");

        marshaller.setAliases(alias);
        return marshaller;
    }

    @Bean
    public JdbcBatchItemWriter<Airfield> writer(){
        JdbcBatchItemWriter<Airfield> writer = new JdbcBatchItemWriter<Airfield>();
        writer.setDataSource(dataSource);
        writer.setSql(
                "insert into airfield " +
                        "(airfield_id, " +
                        "airfield_accept_vfr, " +
                        "airfield_fullname, " +
                        "airfield_map_name, " +
                        "airfield_phone, " +
                        "airfield_situation, " +
                        "airfield_statut, " +
                        "airfield_altitude, " +
                        "airfield_latitude_arp, " +
                        "airfield_longitude_arp) " +
                        "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );
        writer.setItemPreparedStatementSetter(new AirfieldItemPreparedStatementSetter());
        return writer;
    }
}
