package com.thoughtworks.restful.restful.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {

    @JsonProperty
    private UUID id;
    private String name;
    private String status;

    private Date date;

}
