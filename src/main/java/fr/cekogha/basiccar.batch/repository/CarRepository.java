package fr.cekogha.basiccar.batch.repository;

import fr.cekogha.basiccar.batch.entity.Car;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.UUID;

public interface CarRepository extends CassandraRepository<Car, UUID> {

    List<Car> findByBrand(String brand);
}
