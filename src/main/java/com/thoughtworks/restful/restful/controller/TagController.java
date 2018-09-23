package com.thoughtworks.restful.restful.controller;

import com.thoughtworks.restful.restful.model.Tag;
import com.thoughtworks.restful.restful.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "/tags")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping
    public List<Tag> getTagListByUserId() {
        return tagService.getTagList();
    }

}
