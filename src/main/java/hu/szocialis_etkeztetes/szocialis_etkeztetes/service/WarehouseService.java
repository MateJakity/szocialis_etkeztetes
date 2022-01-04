package hu.szocialis_etkeztetes.szocialis_etkeztetes.service;

import hu.szocialis_etkeztetes.szocialis_etkeztetes.model.Product;
import hu.szocialis_etkeztetes.szocialis_etkeztetes.model.Warehouse;
import hu.szocialis_etkeztetes.szocialis_etkeztetes.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ProductService productService;

    public List<Warehouse> findAll(){return warehouseRepository.findAll();}

    public Warehouse findByName(String name){
        return warehouseRepository.findByName(name);
    }

    public Warehouse findById(long id) {
        return warehouseRepository.findById(id).orElse(null);
    }

    public Warehouse saveWarehouse(Warehouse warehouse){return warehouseRepository.save(warehouse);}

    public void deleteByWarehouseId(long id){warehouseRepository.deleteById(id);}

    public void deleteWarehouse(long id){
        Warehouse warehouse = findById(id);
        productService.setWarehouseToNone(warehouse.getName());
        deleteByWarehouseId(id);
    }

    public double warehouseCapacityCheck(double currentQuantity, double amount, int warehouseCapacity){
        return (double)warehouseCapacity - (currentQuantity + amount);
    }

    public void updateWarehouse(long id, String name, String address, int capacity){
        Warehouse warehouse = findById(id);
        if(warehouse != null){
            List<Product> productList = productService.findAllProductsByWarehouse(warehouse.getName());
            for (Product product : productList) {
                product.setWarehouseStatus(name);
                productService.saveProduct(product);
            }
            warehouse.setName(name);
            warehouse.setAddress(address);
            if ((double)capacity >= warehouse.getCurrentQuantity()) {
            warehouse.setCapacity(capacity);
            }
            saveWarehouse(warehouse);
        }
    }
}
