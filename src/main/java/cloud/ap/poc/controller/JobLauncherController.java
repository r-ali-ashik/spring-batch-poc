package cloud.ap.poc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "batch-job")
public class JobLauncherController {

    private final JobLauncher jobLauncher;
    private final Job job;

    @PutMapping(value = "start")
    public ResponseEntity<String> startJob() throws Exception {
        jobLauncher.run(job, new JobParameters());
        return ResponseEntity.ok("Job has started");
    }
}
