package com.thoughtworks.restful.restful.service;

import com.thoughtworks.restful.restful.model.Tag;
import com.thoughtworks.restful.restful.model.User;
import com.thoughtworks.restful.restful.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;

    public List<Tag> getTagList() {
        User user = User.getPrinciple();
        return tagRepository.findByUserIdOrderById(user.getId());
    }
}
