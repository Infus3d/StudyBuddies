package testing.roundtrip.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import testing.roundtrip.demo.model.Trivia;

public interface TriviaRepository extends JpaRepository<Trivia, Long>{

}
