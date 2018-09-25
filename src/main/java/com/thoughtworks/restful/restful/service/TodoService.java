package com.thoughtworks.restful.restful.service;

import com.google.common.base.Strings;
import com.thoughtworks.restful.restful.model.Tag;
import com.thoughtworks.restful.restful.model.TagCriteria;
import com.thoughtworks.restful.restful.model.Todo;
import com.thoughtworks.restful.restful.model.User;
import com.thoughtworks.restful.restful.repository.TagRepository;
import com.thoughtworks.restful.restful.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.*;

@Service
public class TodoService {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    TagRepository tagRepository;

    public List<Todo> getTodoListByUserId(Pageable pageable) {
        return todoRepository.findByUserId(getPrincipal().getId(), pageable);
    }

    public Todo getTodoByUserIdAndId(Long id) {
        return todoRepository.findByUserIdAndId(getPrincipal().getId(), id);
    }

    public Todo getTodoById(Long id) {
        return todoRepository.findOne(id);
    }

    public List<Todo> getTodoListByTodoTagValue(String value) {
        return todoRepository.findByUserIdAndTags_Value(getPrincipal().getId(), value);
    }

    public Todo saveOrUpdate(Todo toDo) {
        User user = getPrincipal();
        toDo.setUserId(user.getId());
        Set<Tag> tags = new HashSet<>();
        for (Tag tag : toDo.getTags()) {
            Tag item = tagRepository.findByLabelAndUserId(tag.getLabel(), user.getId());
            tags.add(item != null ? item.setUserId(user.getId()) : tag.setUserId(user.getId()));
        }
        toDo.setTags(tags);
        return todoRepository.save(toDo);
    }

    public void delete(Long id) {
        todoRepository.delete(id);
    }

    public List<Todo> getTodoListByTagsAndDate(TagCriteria tagCriteria) {
        return todoRepository.findAll((root, query, cb) -> {
            List<Predicate> predicatesList = new ArrayList<>();
            if (!Strings.isNullOrEmpty(tagCriteria.getName())) {
                Predicate namePredicate = cb.like(root.get("name"), '%' + tagCriteria.getName() + '%');
                predicatesList.add(namePredicate);
            }
            if (tagCriteria.getStart() != null && tagCriteria.getEnd() != null) {
                Predicate betweenPredicate = cb.between(root.get("date"), tagCriteria.getStart(), tagCriteria.getEnd());
                predicatesList.add(betweenPredicate);
            } else if (tagCriteria.getStart() != null) {
                Predicate startPredicate = cb.greaterThanOrEqualTo(root.get("date"), tagCriteria.getStart());
                predicatesList.add(startPredicate);
            } else if (tagCriteria.getEnd() != null) {
                Predicate endPredicate = cb.lessThanOrEqualTo(root.get("date"), tagCriteria.getEnd());
                predicatesList.add(endPredicate);
            }
            if (tagCriteria.getTags() != null && tagCriteria.getTags().size() > 0) {
                for (Tag tag : tagCriteria.getTags()) {
                    predicatesList.add(cb.isMember(tag, root.get("tags")));
                }
            }
            Predicate userPredicate = cb.equal(root.get("userId"), getPrincipal().getId());
            predicatesList.add(userPredicate);

            Predicate[] predicates = new Predicate[predicatesList.size()];
            return cb.and(predicatesList.toArray(predicates));
        }, new Sort(new Sort.Order(Sort.Direction.DESC,"date")));
    }


    private User getPrincipal() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
