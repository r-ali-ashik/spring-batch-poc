/*
package cloud.ap.poc.config;

import cloud.ap.poc.dto.SalesInfoDto;
import cloud.ap.poc.entity.SalesInfo;
import cloud.ap.poc.processor.SalesInfoItemProcessor;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@RequiredArgsConstructor
public class SalesInfoJobConfig {
    private final EntityManagerFactory entityManagerFactory;
    private final SalesInfoItemProcessor salesInfoItemProcessor;


    @Bean
    public Step fromFileIntoDateBase(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("fromFileIntoDateBase", jobRepository)
                .<SalesInfoDto, Future<SalesInfo>>chunk(10, transactionManager)
                .reader(salesInfoFileReader())
                .processor(asyncItemProcessor())
                .writer(asyncItemWriter())
                .taskExecutor(taskExecutor())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Job importSalesInfoJob(JobRepository jobRepository, Step step) {
        return new JobBuilder("importSalesInfo", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }


    public FlatFileItemReader<SalesInfoDto> salesInfoFileReader() {
        return new FlatFileItemReaderBuilder<SalesInfoDto>()
                .resource(new ClassPathResource("/data/Pascoal-Store.csv"))
                .name("salesInfoFileReader")
                .delimited()
                .delimiter(",")
                .names(new String[]{"product", "seller", "sellerId", "price", "city", "category"})
                .linesToSkip(1)
                .targetType(SalesInfoDto.class)
                .build();

    }

    public JpaItemWriter<SalesInfo> salesInfoItemWriter() {
        return new JpaItemWriterBuilder<SalesInfo>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("batch-job-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    @Bean
    public AsyncItemProcessor<SalesInfoDto, SalesInfo> asyncItemProcessor() {
        var asyncItemProcessor = new AsyncItemProcessor<SalesInfoDto, SalesInfo>();
        asyncItemProcessor.setDelegate(salesInfoItemProcessor);
        asyncItemProcessor.setTaskExecutor(taskExecutor());
        return asyncItemProcessor;
    }

    @Bean
    public AsyncItemWriter<SalesInfo> asyncItemWriter() {
        var asyncItemWriter = new AsyncItemWriter<SalesInfo>();
        asyncItemWriter.setDelegate(salesInfoItemWriter());
        return asyncItemWriter;
    }

}
*/
