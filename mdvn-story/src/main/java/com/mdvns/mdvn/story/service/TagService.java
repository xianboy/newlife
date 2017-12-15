package com.mdvns.mdvn.story.service;

import java.util.List;

public interface TagService {
    void buildTags(Long creatorId, Long id, List<Long> tags);
}
