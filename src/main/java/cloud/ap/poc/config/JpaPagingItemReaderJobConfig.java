//package cloud.ap.poc.config;
//
//import cloud.ap.poc.entity.SalesInfo;
//import cloud.ap.poc.entity.SellerInfo;
//import cloud.ap.poc.util.PropertyMapperUtil;
//import jakarta.persistence.EntityManagerFactory;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.integration.async.AsyncItemProcessor;
//import org.springframework.batch.integration.async.AsyncItemWriter;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.database.JpaItemWriter;
//import org.springframework.batch.item.database.JpaPagingItemReader;
//import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
//import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.task.TaskExecutor;
//import org.springframework.lang.NonNull;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import java.util.concurrent.Future;
//import java.util.concurrent.ThreadPoolExecutor;
//
//@Slf4j
//@Configuration
//@RequiredArgsConstructor
//public class JpaPagingItemReaderJobConfig {
//    private final EntityManagerFactory entityManagerFactory;
//
//
//    @Bean
//    public Step sellerInfoSyncStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("sellerInfoSyncStep", jobRepository)
//                .<SalesInfo, Future<SellerInfo>>chunk(10, transactionManager)
//                .reader(jpaPagingItemReader(entityManagerFactory))
//                .processor(asyncItemProcessor())
//                .writer(asyncItemWriter())
//                .taskExecutor(taskExecutor())
//                .allowStartIfComplete(true)
//                .build();
//    }
//
//    @Bean
//    public Job sellerInfoSyncJob(JobRepository jobRepository, Step step) {
//        return new JobBuilder("sellerInfoSyncJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(step)
//                .build();
//    }
//
//
//    @Bean
//    public JpaPagingItemReader<SalesInfo> jpaPagingItemReader(EntityManagerFactory entityManagerFactory) {
//        return new JpaPagingItemReaderBuilder<SalesInfo>()
//                .name("jpaPagingItemReader")
//                .entityManagerFactory(entityManagerFactory)
//                .queryString("SELECT si FROM SalesInfo si")
//                .pageSize(1000)
//                .build();
//    }
//
//    public JpaItemWriter<SellerInfo> jpaItemWriter() {
//        return new JpaItemWriterBuilder<SellerInfo>()
//                .entityManagerFactory(entityManagerFactory)
//                .build();
//    }
//
//    @Bean
//    public TaskExecutor taskExecutor() {
//        var executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(5);
//        executor.setMaxPoolSize(5);
//        executor.setQueueCapacity(10);
//        executor.setThreadNamePrefix("batch-job-");
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        return executor;
//    }
//
//    @Bean
//    public AsyncItemProcessor<SalesInfo, SellerInfo> asyncItemProcessor() {
//        var asyncItemProcessor = new AsyncItemProcessor<SalesInfo, SellerInfo>();
//        asyncItemProcessor.setDelegate(itemProcessor());
//        asyncItemProcessor.setTaskExecutor(taskExecutor());
//        return asyncItemProcessor;
//    }
//
//    @Bean
//    public AsyncItemWriter<SellerInfo> asyncItemWriter() {
//        var asyncItemWriter = new AsyncItemWriter<SellerInfo>();
//        asyncItemWriter.setDelegate(jpaItemWriter());
//        return asyncItemWriter;
//    }
//
//    @Bean
//    public ItemProcessor<SalesInfo, SellerInfo> itemProcessor() {
//        return new ItemProcessor<SalesInfo, SellerInfo>() {
//            @Override
//            public SellerInfo process(@NonNull SalesInfo salesInfo) throws Exception {
//                log.info("{}", salesInfo);
//                SellerInfo sellerInfo = PropertyMapperUtil.map(salesInfo, SellerInfo.class);
//                sellerInfo.setId(null);
//                return sellerInfo;
//            }
//        };
//    }
//
//}
