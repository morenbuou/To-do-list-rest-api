package com.thoughtworks.restful.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Accessors(chain = true)
public class Tag {
    @Id
    @GeneratedValue
    private long id;
    private String label;
    private String value;
    private long userId;
}
