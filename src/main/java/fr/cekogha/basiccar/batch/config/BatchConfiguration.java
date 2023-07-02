package fr.cekogha.basiccar.batch.config;

import fr.cekogha.basiccar.batch.entity.Car;
import fr.cekogha.basiccar.batch.repository.CarRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.support.DatabaseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilder jobBuilder;

    @Autowired
    private StepBuilder stepBuilder;
    @Autowired
    private CarRepository carRepository;
    @Value("${batch.config.chunk}")
    private int chunk;

    @Bean
    public Job job(){
        return jobBuilder.start(step())
                .repository(jobRepository())
                .build();
    }

    @Bean
    private JobRepository jobRepository() {
        return null;
    }

    @Bean
    public Step step(){
        return stepBuilder.<Car, Car>chunk(chunk)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public JsonItemReader reader(){
        return null;
    }

    @Bean
    public ItemProcessor<Car,Car> processor(){
        return null;
    }

    @Bean
    public ItemWriter<Car> writer(){
        return null;
    }
}
