package se331.bokipassport.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitApp implements ApplicationListener<ApplicationReadyEvent> {

    /* Repository variables here */

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent application) {
        // TODO write init object here
    }
}
