package prototype.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
 
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "prototype")
public class AppConfig {
     
    @Bean
    public MessageSource messageSource() {
    	ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }
    
    @Bean
    public LocaleResolver localeResolver(){
    	CookieLocaleResolver resolver = new CookieLocaleResolver();
    	resolver.setDefaultLocale(new Locale("en"));
    	resolver.setCookieName("myLocaleCookie");
    	resolver.setCookieMaxAge(4800);
    	return resolver;
    }
    
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
    
    public void addInterceptors(InterceptorRegistry registry) {
    	LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
    	interceptor.setParamName("mylocale");
    	registry.addInterceptor(interceptor);
    }
    
}
