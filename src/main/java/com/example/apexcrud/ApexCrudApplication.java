package com.example.apexcrud;

import com.example.apexcrud.model.Role;
import com.example.apexcrud.repositories.RoleRepository;
import com.example.apexcrud.utils.AppConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@EnableCaching
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class ApexCrudApplication  implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(ApexCrudApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(this.passwordEncoder.encode("xyz"));
        try{
            Role role = new Role();
            role.setId(AppConstants.ROLE_ADMIN);
            role.setName("ROLE_ADMIN");

            Role role1 = new Role();
            role1.setId(AppConstants.ROLE_NORMAL);
            role1.setName("ROLE_NORMAL");

            List<Role> roles = List.of(role, role1);
            List<Role> resultRoles = this.roleRepository.saveAll(roles);
            resultRoles.forEach(r->{
                System.out.println(r.getName());
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
