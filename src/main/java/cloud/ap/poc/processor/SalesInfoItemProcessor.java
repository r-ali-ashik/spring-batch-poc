package cloud.ap.poc.processor;

import cloud.ap.poc.dto.SalesInfoDto;
import cloud.ap.poc.entity.SalesInfo;
import cloud.ap.poc.util.PropertyMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SalesInfoItemProcessor implements ItemProcessor<SalesInfoDto, SalesInfo> {

    @Override
    public SalesInfo process(@NonNull SalesInfoDto salesInfoDto) throws Exception {
//        Thread.sleep(200);
        log.info("Processing item: {}", salesInfoDto);
        return PropertyMapperUtil.map(salesInfoDto, SalesInfo.class);
    }
}
