package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/task") //all CREATE, GET, UPDATE, DELETE will be under same end point

public class TaskController {

    private final TaskService taskService;

    public TaskController( TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getAllTasks(){

        return ResponseEntity.ok(new ResponseWrapper("Task list successfully retrieved from DB",
                taskService.listAllTasks(), HttpStatus.OK));
    }

    @GetMapping("/{taskId}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getTaskById(@PathVariable("taskId") Long taskId ){

        return ResponseEntity.ok(new ResponseWrapper("Task by id: "+taskId+" successfully retrieved from DB",
                taskService.findById(taskId),HttpStatus.OK));
    }


    @PostMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> createTask(@RequestBody TaskDTO taskDTO){
        taskService.save(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).
                body(new ResponseWrapper("Task is successfully created",
                        HttpStatus.CREATED));
    }


    @DeleteMapping("/{taskId}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> deleteTask(@PathVariable("taskId") Long taskId){
        taskService.delete(taskId);
        return ResponseEntity.ok(new ResponseWrapper("Task by id: "+taskId+" has been deleted from DB record",
                HttpStatus.OK));

    }


    @PutMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> updateTask(@RequestBody TaskDTO taskDTO){
        taskService.update(taskDTO);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully updated", HttpStatus.OK));

    }

    @GetMapping("/employee/pending-tasks")
    @RolesAllowed("Employee")
    public ResponseEntity<ResponseWrapper> employeePendingTasks(){
        return ResponseEntity.ok(new ResponseWrapper("Tasks that are OPEN status successfully retrieved",
                taskService.listAllTasksByStatus(Status.COMPLETE),
                HttpStatus.OK));
    }

    @PutMapping("/employee/update-status")
    @RolesAllowed("Employee")
    public ResponseEntity<ResponseWrapper> employeeUpdateTasks(@RequestBody TaskDTO taskDTO){
        taskService.updateStatus(taskDTO);

        return ResponseEntity.ok(new ResponseWrapper("Task status successfully updated",
                HttpStatus.OK));
    }

    @GetMapping("/employee/archive")
    @RolesAllowed("Employee")
    public ResponseEntity<ResponseWrapper> employeeArchivedTasks(){
        return ResponseEntity.ok(new ResponseWrapper("Tasks status are completed successfully retrieved from DB record",
                taskService.listAllTasksByStatus(Status.COMPLETE),
                HttpStatus.OK));

    }


}
