package com.study.repository;

import com.study.constants.DocumentType;
import com.study.entity.Person;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {

    @Query("SELECT COUNT(p) > 0 FROM Person p JOIN p.identityDocuments d " +
            "WHERE p.firstName = :firstName " +
            "AND p.middleName = :middleName " +
            "AND p.lastName = :lastName " +
            "AND d.type = :type " +
            "AND d.series = :series")
    boolean existsByFullNameAndIdentityDocuments(
            @Param("firstName") String firstName,
            @Param("middleName") String middleName,
            @Param("lastName") String lastName,
            @Param("type") DocumentType type,
            @Param("series") String series
    );

    Page<Person> findAllByVisibleTrue(Pageable pageable);
}

