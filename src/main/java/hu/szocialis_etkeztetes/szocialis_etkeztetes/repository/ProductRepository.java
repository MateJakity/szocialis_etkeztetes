package hu.szocialis_etkeztetes.szocialis_etkeztetes.repository;

import hu.szocialis_etkeztetes.szocialis_etkeztetes.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public Product findByProductName(String productName);
}
