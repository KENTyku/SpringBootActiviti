package ru.kentyku.springbootactiviti;


import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MyApp {

    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);

    }

    @Bean
    public CommandLineRunner init(final RepositoryService repositoryService,
                                  final RuntimeService runtimeService,
                                  final TaskService taskService) {

        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                System.out.println("Number of process definitions:" + repositoryService.createProcessDefinitionQuery().count());

                System.out.println("Number of tasks : " + taskService.createTaskQuery().count());

                ProcessInstance processInstance1 = runtimeService.startProcessInstanceByKey("oneTaskProcess");
                ProcessInstance processInstance2 = runtimeService.startProcessInstanceByKey("financialReport");

                System.out.println("Number of tasks after process start: " + taskService.createTaskQuery().count());

                List<Task> tasks = taskService.createTaskQuery().active().list();
                for (Task task: tasks                     ) {
                    taskService.complete(task.getId());

                }

                System.out.println(tasks.toString());
            }
        };

    }
}