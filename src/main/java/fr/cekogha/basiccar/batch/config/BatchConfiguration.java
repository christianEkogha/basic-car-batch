package fr.cekogha.basiccar.batch.config;

import fr.cekogha.basiccar.batch.entity.Car;
import fr.cekogha.basiccar.batch.exception.BusinessException;
import fr.cekogha.basiccar.batch.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.ExceptionClassifierSkipPolicy;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.batch.support.DatabaseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private CarRepository carRepository;

    @Value("${spring.application.config.chunk}")
    private int chunk;
    @Value("${spring.application.config.skip.limit}")
    private int skipLimit;

    @Bean(name="car job")
    public Job job(){
        return new JobBuilder("car job")
                .start(step())
                .build();
    }

    @Bean
    public Step step(){
        return new StepBuilder("step job")
                .<Car, Car>chunk(chunk)
                .reader(reader(null))
                .writer(writer())
                .faultTolerant()
                .skipPolicy(skipPolicy())
                .skipLimit(skipLimit)
                .build();
    }

    @Bean(name="skip policy")
    public SkipPolicy skipPolicy() {
        return (throwable, skipCount) -> {
                if(throwable instanceof BusinessException)
                    return true;
                return false;
            };
    }

    @Bean
    @StepScope
    public JsonItemReader reader(@Value("#jobParameters['jsonPath']") String jsonFilePath){
        return new JsonItemReaderBuilder<Car>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(Car.class))
                .resource(new FileSystemResource(jsonFilePath))
                .name("carJsonItemReader")
                .build();
    }

    @Bean
    public ItemWriter<Car> writer(){
        return new ItemWriter<Car>() {
            Logger logger = LoggerFactory.getLogger(ItemWriter.class);
            @Override
            public void write(Chunk<? extends Car> chunk) throws Exception {
                chunk.getErrors()
                        .stream()
                        .forEach(exception -> logger.error("Cause : {}, Message ; {}", exception.getCause(), exception.getMessage()));
                chunk.getItems()
                        .forEach(car -> carRepository.save(car));
            }
        };
    }
}
