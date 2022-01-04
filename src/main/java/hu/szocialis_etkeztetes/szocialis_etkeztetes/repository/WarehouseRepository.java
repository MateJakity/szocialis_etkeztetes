package hu.szocialis_etkeztetes.szocialis_etkeztetes.repository;

import hu.szocialis_etkeztetes.szocialis_etkeztetes.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    public Warehouse findByName(String name);
}
