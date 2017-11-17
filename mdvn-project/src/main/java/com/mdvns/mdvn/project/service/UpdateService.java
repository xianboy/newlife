package com.mdvns.mdvn.project.service;

import org.springframework.http.ResponseEntity;

public interface UpdateService {
    ResponseEntity<?> status(Integer status, Integer id);
}
