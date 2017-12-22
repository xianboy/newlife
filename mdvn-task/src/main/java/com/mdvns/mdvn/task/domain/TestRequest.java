package com.mdvns.mdvn.task.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TestRequest {

    private Long creatorId;
    private String hostSerialNo;
    private Object delivery;
}
