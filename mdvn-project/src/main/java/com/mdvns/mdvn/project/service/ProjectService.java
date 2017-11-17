package com.mdvns.mdvn.project.service;

import com.mdvns.mdvn.project.domain.Project;
import org.springframework.http.ResponseEntity;

public interface ProjectService {
    /*create project*/
    ResponseEntity<?> create(Project project);


}
