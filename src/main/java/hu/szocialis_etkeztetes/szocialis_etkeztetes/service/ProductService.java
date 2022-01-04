package hu.szocialis_etkeztetes.szocialis_etkeztetes.service;

import hu.szocialis_etkeztetes.szocialis_etkeztetes.model.Product;
import hu.szocialis_etkeztetes.szocialis_etkeztetes.model.Warehouse;
import hu.szocialis_etkeztetes.szocialis_etkeztetes.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    WarehouseService warehouseService;

    public List<Product> findAll(){return productRepository.findAll();}

    public Product findById(long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product){return productRepository.save(product);}

    public void deleteByProductId(long id){productRepository.deleteById(id);}

    public Product findProductInWarehouse(String productName, String warehouseStatus){
        for (Product product : findAll()) {
            if(product.getProductName().equalsIgnoreCase(productName) && product.getWarehouseStatus().equalsIgnoreCase(warehouseStatus)){
                return product;
            }
        }
        return null;
    }

    public String warehouseStatusCheck(String orderStatus, String warehouseStatus){
        if (orderStatus.equals("előtervezve") || orderStatus.equals("rendelve")) {
            warehouseStatus = "none";
        }
        return warehouseStatus;
    }

    public String orderStatusCheck(String orderStatus, String warehouseStatus){
        if(warehouseStatus.equals("none")){
            if(orderStatus.equals("előtervezve")){
                orderStatus = "előtervezve";
            }else if(orderStatus.equals("megrendelve")){
                orderStatus = "megrendelve";
            }
        }
        return orderStatus;
    }

    public double amountSetforWarehouse(double amount, String amountType){
        if (amountType.equals("dkg") || amountType.equals("dl")) {
            amount = amount/100;
        } else if (amountType.equals("g") || amountType.equals("ml")) {
            amount = amount / 1000;
        }
        return amount;
    }

    public int priceCheck(int price){
        return Math.max(price, 0);
    }

    public String productNewRegister(Model model, String productName, double amount, String amountType, String type, int price, String orderStatus, String warehouseStatus){
        if(amount <= 0){
            return "redirect:/productNew";
        }
        price = priceCheck(price);
        amount = amountSetforWarehouse(amount, amountType);
        amountType = "kg";
        orderStatus = orderStatusCheck(orderStatus, warehouseStatus);
        warehouseStatus = warehouseStatusCheck(orderStatus, warehouseStatus);
        Product productNew = new Product(productName, amount, amountType, type, price, orderStatus, warehouseStatus);


        if(productNew.getWarehouseStatus().equals("none")){
            saveProduct(productNew);
            model.addAttribute("searchedProduct", productNew);
        }else{
            Warehouse warehouse = warehouseService.findByName(warehouseStatus);
            double capacityCheck = warehouseService.warehouseCapacityCheck(warehouse.getCurrentQuantity(), amount, warehouse.getCapacity());
            Product productExistsAlready = findProductInWarehouse(productName, warehouseStatus);
            if (productExistsAlready != null) {
                if((int)capacityCheck < 0){
                    capacityCheck = (double)warehouse.getCapacity() - warehouse.getCurrentQuantity();
                    if((int)capacityCheck == 0){
                        productNew.setOrderStatus("kimaradt");
                        productNew.setWarehouseStatus("none");
                        saveProduct(productNew);
                        model.addAttribute("searchedProduct", productNew);
                        return "productSearch";
                    }
                    warehouse.setCurrentQuantity((int) (warehouse.getCurrentQuantity() + capacityCheck));
                    warehouseService.saveWarehouse(warehouse);
                    productExistsAlready.setAmount(productExistsAlready.getAmount() + capacityCheck);
                    saveProduct(productExistsAlready);
                    productExistsAlready.setPrice(productExistsAlready.setPrice(productExistsAlready.getPrice() + (int)(((capacityCheck)/amount) * price)));
                    productNew.setAmount(amount - capacityCheck);
                    productNew.setPrice((int) (((amount - capacityCheck) / amount) * price));
                    productNew.setOrderStatus("kimaradt");
                    productNew.setWarehouseStatus("none");
                    saveProduct(productNew);
                    List<Product> productList= new ArrayList<>();
                    productList.add(productExistsAlready);
                    productList.add(productNew);
                    model.addAttribute("searchedProducts", productList);
                }else{
                    warehouse.setCurrentQuantity((int) (warehouse.getCurrentQuantity() + amount));
                    warehouseService.saveWarehouse(warehouse);
                    productExistsAlready.setAmount(productExistsAlready.getAmount() + amount);
                    productExistsAlready.setPrice(productExistsAlready.getPrice() + price);
                    saveProduct(productExistsAlready);
                    model.addAttribute("searchedProduct", productExistsAlready);
                }
            }else{
                if(capacityCheck < 0){
                    capacityCheck = (double)warehouse.getCapacity() - warehouse.getCurrentQuantity();
                    if((int)capacityCheck == 0){
                        productNew.setOrderStatus("kimaradt");
                        productNew.setWarehouseStatus("none");
                        saveProduct(productNew);
                        model.addAttribute("searchedProduct", productNew);
                        return "productSearch";
                    }
                    warehouse.setCurrentQuantity((int) (warehouse.getCurrentQuantity() + capacityCheck));
                    warehouseService.saveWarehouse(warehouse);
                    productNew.setAmount(capacityCheck);
                    productNew.setPrice((int) ((capacityCheck/amount)*price));
                    Product productLeftOut = new Product(productName, amount-capacityCheck, amountType, type, (int)(((amount - capacityCheck)/amount)*price), "kimaradt", "none");
                    saveProduct(productNew);
                    saveProduct(productLeftOut);
                    List<Product> productList= new ArrayList<>();
                    productList.add(productNew);
                    productList.add(productLeftOut);
                    model.addAttribute("searchedProducts", productList);
                }else{
                    warehouse.setCurrentQuantity((int) (warehouse.getCurrentQuantity() + amount));
                    warehouseService.saveWarehouse(warehouse);
                    saveProduct(productNew);
                    model.addAttribute("searchedProduct", productNew);
                }
            }
        }
        return "productSearch";
    }

    public List<Product> findAllProductsByName(List<Product> allProducts, String searchProduct){
        List<Product> foundProducts = new ArrayList<>();
        String regex = new StringBuilder().append("^").append(searchProduct).toString();
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        for(Product product : allProducts){
            Matcher matcher = p.matcher(product.getProductName());
            if(matcher.find()) {
                foundProducts.add(product);
            }
        }
        return foundProducts;
    }

    public List<Product> findAllProductsByWarehouse(String warehouseName){
        List<Product> foundProducts = new ArrayList<>();
        List<Product> allProducts = findAll();
        for (Product product: allProducts) {
            if(product.getWarehouseStatus().equalsIgnoreCase(warehouseName)){
                foundProducts.add(product);
            }
        }
        return foundProducts;
    }

    public void setWarehouseToNone(String warehouseName){
        List<Product> products = findAll();
        for (Product product : products) {
            if(product.getWarehouseStatus().equals(warehouseName)){
                product.setWarehouseStatus("none");
                product.setOrderStatus("kimaradt");
                saveProduct(product);
            }
        }
    }

    public List<Product> findByWarehouseStatusNone(){
        List<Product> products = findAll();
        List<Product> warehouseStatusNone = new ArrayList<>();

        for (Product product: products) {
            if(product.getWarehouseStatus().equals("none")){
                warehouseStatusNone.add(product);
            }
        }
        return warehouseStatusNone;
    }

    public String productUpdate(Model model, long id, String productName, double amount, String amountType, String type, int price, String orderStatus, String warehouseStatus) {
        if(amount <= 0){
            model.addAttribute("product", findById(id));
            return "productUpdate";
        }
        Product originalProduct = findById(id);
        Warehouse originalWarehouse = warehouseService.findByName(originalProduct.getWarehouseStatus());
        if(originalWarehouse != null){
        originalWarehouse.setCurrentQuantity((int) (originalWarehouse.getCurrentQuantity() - originalProduct.getAmount()));
        warehouseService.saveWarehouse(originalWarehouse);
        }
        deleteByProductId(id);
        return productNewRegister(model, productName, amount, amountType, type, price, orderStatus, warehouseStatus);
    }
}
