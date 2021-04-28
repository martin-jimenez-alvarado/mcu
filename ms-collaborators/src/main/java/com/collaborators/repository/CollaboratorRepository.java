package com.collaborators.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.collaborators.dao.entity.Collaborator;

@Repository
public interface CollaboratorRepository extends ReactiveMongoRepository<Collaborator, String>{

}
