package com.mdvns.mdvn.story.service;

import com.mdvns.mdvn.common.bean.MemberRequest;

import java.util.List;

public interface MemberService {
    Integer buildMembers(Long creatorId, Long id, List<MemberRequest> members);
}
