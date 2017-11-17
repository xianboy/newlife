package com.mdvns.mdvn.requirement.service;

import com.mdvns.mdvn.requirement.domain.Requirement;
import org.springframework.http.ResponseEntity;

/**
 * create interface
 */
public interface CreateSrevice {
   /*新建requirement*/
    ResponseEntity<?> create(Requirement requirement);
}
