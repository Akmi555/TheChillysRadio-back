package theChillys.chillys_radio.configuration;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@org.springframework.context.annotation.Configuration
public class AppConfiguration {

    @Bean
    public ModelMapper modelMapper(){
      return new ModelMapper();
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
    @Bean
    public Configuration freemarkerConfiguration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_21);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateLoader(new ClassTemplateLoader(AppConfiguration.class, "/mails"));

        return configuration;
    }
}


