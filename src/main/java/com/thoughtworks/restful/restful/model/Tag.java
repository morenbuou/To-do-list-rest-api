package com.thoughtworks.restful.restful.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    @Column(name = "user_id")
    private long userId;
}
