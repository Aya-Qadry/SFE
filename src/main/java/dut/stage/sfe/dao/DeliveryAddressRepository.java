package dut.stage.sfe.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dut.stage.sfe.model.DeliveryAddress;
import dut.stage.sfe.model.User;

@Repository
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress,String>{
    // Optional<DeliveryAddress> findById(String id);
    // @Query("SELECT a FROM DeliveryAddress a WHERE a.user_id.id = :userId")
    // DeliveryAddress findByUser_id(@Param("userId") int userId);
}
