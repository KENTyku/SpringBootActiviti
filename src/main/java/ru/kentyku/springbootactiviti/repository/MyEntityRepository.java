package ru.kentyku.springbootactiviti.repository;

import javax.swing.text.html.parser.Entity;

import org.springframework.data.repository.CrudRepository;
import ru.kentyku.springbootactiviti.entity.FieldAccessJPAEntity;

public interface MyEntityRepository extends CrudRepository<FieldAccessJPAEntity,Long> {
}
