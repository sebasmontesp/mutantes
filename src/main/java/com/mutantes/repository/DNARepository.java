package com.mutantes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.mutantes.entity.DNAEntity;

@Repository
public interface DNARepository extends JpaRepository<DNAEntity, Integer>{
	
	@Query("SELECT COUNT(o) FROM DNAEntity o WHERE o.result = :result")
	int countByResult(@Param("result") String result);

}
