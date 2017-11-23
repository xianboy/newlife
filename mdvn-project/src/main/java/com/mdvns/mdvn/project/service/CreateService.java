package com.mdvns.mdvn.project.service;

import com.mdvns.mdvn.project.domain.CreateProjectRequest;
import com.mdvns.mdvn.project.domain.Project;
import org.springframework.http.ResponseEntity;

public interface CreateService {
    /*create project*/
    ResponseEntity<?> create(CreateProjectRequest createProjectRequest);


}
