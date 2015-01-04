package hystrixtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration
public class RemoteCallServiceHystrixTest {

    @Autowired
    private RemoteCallService remoteCallService;

    @Test
    public void testRemoteCall() throws Exception {
        assertThat(this.remoteCallService.call("test"), is("FALLBACK: test"));
        assertThat(this.remoteCallService.call("test"), is("FALLBACK: test"));
        assertThat(this.remoteCallService.call("test"), is("test"));
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableHystrix
    public static class SpringConfig {

        @Bean
        public RemoteCallService remoteCallService() {
            return new DummyRemoteCallService();
        }
    }
}
