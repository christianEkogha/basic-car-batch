package fr.cekogha.basiccar.batch.repository;

import fr.cekogha.basiccar.api.entity.Car;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarRepository extends CassandraRepository<Car, UUID> {

    List<Car> findByName(String name);
}
