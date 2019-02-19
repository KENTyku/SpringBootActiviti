package ru.kentyku.springbootactiviti;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import ru.kentyku.springbootactiviti.entity.FieldAccessJPAEntity;
import ru.kentyku.springbootactiviti.repository.MyEntityRepository;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MyApp {
    @Autowired
    MyEntityRepository repository;

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

                FieldAccessJPAEntity myEntity = new FieldAccessJPAEntity();
                myEntity.setValue("Hello");
                repository.save(myEntity);

                Map<String, Object> variables = new HashMap<String, Object>();
                variables.put("entityToUpdate", myEntity);

                ProcessInstance processInstance1 = runtimeService.startProcessInstanceByKey("oneTaskProcess");
                //send variables map into process
                ProcessInstance processInstance2 = runtimeService.startProcessInstanceByKey("financialReport", variables);

                System.out.println("Number of tasks after process start: " + taskService.createTaskQuery().count());

                List<Task> tasks = taskService.createTaskQuery().active().includeProcessVariables().list();
                for (Task task : tasks) {
                    if (task.getProcessVariables().get("entityToUpdate") != null) {
                        FieldAccessJPAEntity entityFromTask = (FieldAccessJPAEntity) task.getProcessVariables().get("entityToUpdate");
                        System.out.println("TEST " + entityFromTask.getValue());

                    }
                    taskService.complete(task.getId());

                }

                System.out.println(tasks.toString());
            }
        };

    }
}