package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/project") //all CREATE, GET, UPDATE, DELETE will be under same end point
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController( ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> listAllProjects(){

        return ResponseEntity.ok(new ResponseWrapper("Project list successfully retrieved from DB",
                projectService.listAllProjects(), HttpStatus.OK));
    }

    @GetMapping("/{projectCode}")
    public ResponseEntity<ResponseWrapper> getProjectByProjectCode(@PathVariable("projectCode") String projectCode){

        return ResponseEntity.ok(new ResponseWrapper("Project by project code: "+projectCode+" successfully retrieved from DB",
                projectService.getByProjectCode(projectCode),HttpStatus.OK));
    }


    @PostMapping
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO projectDTO){
        projectService.save(projectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).
                body(new ResponseWrapper("Project is successfully created",
                        HttpStatus.CREATED));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO projectDTO){
        projectService.update(projectDTO);
        return ResponseEntity.ok(new ResponseWrapper("User is successfully updated", projectDTO, HttpStatus.OK));
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable("userName") String projectCode){
        projectService.delete(projectCode);
        return ResponseEntity.ok(new ResponseWrapper("Project by projectCode: "+projectCode+" has been deleted from DB record",
                HttpStatus.OK));

//        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseWrapper("User is successfully deleted",
//                HttpStatus.NO_CONTENT));
        //204 - HttpStatus.NO_CONTENT
        //If above way used no body will be returned in Response body

    }


    @GetMapping("/manager/project-status")
    public ResponseEntity<ResponseWrapper> getProjectByManager(){
        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully retrieved from DB",
                projectService.listAllProjectDetails(), HttpStatus.OK));
    }


    @PutMapping("/manager/complete/{projectCode}")
    public ResponseEntity<ResponseWrapper> managerCompleteProject(@PathVariable("projectCode") String projectCode){
        projectService.complete(projectCode);
        return ResponseEntity.ok(new ResponseWrapper("Project by projectCode: " + projectCode+
                "  successfully retrieved from DB",
                HttpStatus.OK));
    }




}
