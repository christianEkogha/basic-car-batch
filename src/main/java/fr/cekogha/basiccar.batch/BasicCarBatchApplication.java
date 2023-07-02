package fr.cekogha.basiccar.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "fr.cekogha.basiccar.batch.*")
public class BasicCarBatchApplication implements CommandLineRunner {

	@Autowired
	JobLauncher launcher;

	@Autowired
	@Qualifier("car job")
	Job carJob;

	public static void main(String[] args) {
		SpringApplication.run(BasicCarBatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		JobParameters params = new JobParametersBuilder()
				.addString("jsonFilePath", args[0])
				.toJobParameters();
		launcher.run(carJob, params);
	}
}
