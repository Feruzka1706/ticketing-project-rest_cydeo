package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;


@RestController
@RequestMapping("/api/v1/project") //all CREATE, GET, UPDATE, DELETE will be under same end point
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController( ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    @RolesAllowed({"Admin","Manager"})
    public ResponseEntity<ResponseWrapper> listAllProjects(){

        return ResponseEntity.ok(new ResponseWrapper("Project list successfully retrieved from DB",
                projectService.listAllProjects(), HttpStatus.OK));
    }

    @GetMapping("/{projectCode}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getProjectByProjectCode(@PathVariable("projectCode") String projectCode){

        return ResponseEntity.ok(new ResponseWrapper("Project by project code: "+projectCode+" successfully retrieved from DB",
                projectService.getByProjectCode(projectCode),HttpStatus.OK));
    }


    @PostMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO projectDTO){
        projectService.save(projectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).
                body(new ResponseWrapper("Project is successfully created",
                        HttpStatus.CREATED));
    }

    @PutMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO projectDTO){
        projectService.update(projectDTO);
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully updated", projectDTO, HttpStatus.OK));
    }

    @DeleteMapping("/{userName}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable("userName") String projectCode){
        projectService.delete(projectCode);
        return ResponseEntity.ok(new ResponseWrapper("Project by projectCode: "+projectCode+" has been deleted from DB record",
                HttpStatus.OK));

    }


    @GetMapping("/manager/project-status")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getProjectByManager(){
        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully retrieved from DB",
                projectService.listAllProjectDetails(), HttpStatus.OK));
    }


    @PutMapping("/manager/complete/{projectCode}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> managerCompleteProject(@PathVariable("projectCode") String projectCode){
        projectService.complete(projectCode);
        return ResponseEntity.ok(new ResponseWrapper("Project by projectCode: " + projectCode+
                "  successfully retrieved from DB",
                HttpStatus.OK));
    }




}
