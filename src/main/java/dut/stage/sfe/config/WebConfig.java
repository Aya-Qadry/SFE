package dut.stage.sfe.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        exposeDirectory("product-photos", registry);
        exposeDirectory("users-photos", registry);

    } 
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/signup").setViewName("signup");
        registry.addViewController("/login").setViewName("login");
         registry.addViewController("/adminhome").setViewName("adminhome");
        registry.addViewController("/403").setViewName("403");
        registry.addViewController("/vendorhome").setViewName("vendorhome");
        registry.addViewController("/list").setViewName("list");
        registry.addViewController("/add").setViewName("add");
        registry.addViewController("/requests").setViewName("requests");
        registry.addViewController("/passwordportal").setViewName("passwordportal");
        registry.addViewController("/settings").setViewName("settings");
        registry.addViewController("/customer").setViewName("customer");
        registry.addViewController("/addcategory").setViewName("addcategory");
        registry.addViewController("/listproducts").setViewName("listproducts");

    }

    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        if (dirName.startsWith("../"))
            dirName = dirName.replace("../", "");
        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/" + uploadPath + "/");
    }

}
