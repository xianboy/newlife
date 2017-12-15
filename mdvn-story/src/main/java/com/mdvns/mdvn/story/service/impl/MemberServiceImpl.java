package com.mdvns.mdvn.story.service.impl;

import com.mdvns.mdvn.common.bean.MemberRequest;
import com.mdvns.mdvn.story.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    @Override
    public Integer buildMembers(Long creatorId, Long id, List<MemberRequest> members) {
        return null;
    }
}

