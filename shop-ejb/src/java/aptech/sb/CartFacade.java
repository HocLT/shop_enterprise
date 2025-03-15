/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package aptech.sb;

import aptech.dto.CartItemDto;
import aptech.entity.OrderDetail;
import aptech.entity.Orders;
import aptech.entity.Product;
import jakarta.ejb.Stateful;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author quang
 */
@Stateful
public class CartFacade implements CartFacadeLocal {
    
    @Inject
    private ProductFacadeLocal productFacade;
    
    @Inject
    private OrdersFacadeLocal ordersFacadeLocal;
    
    private List<CartItemDto> carts;

    public CartFacade() {
        carts = new ArrayList<>();
    }

    /**
     * @return the carts
     */
    @Override
    public List<CartItemDto> getCarts() {
        return carts;
    }

    @Override
    public void addCart(int pid, int quantity) {
        boolean existed = false;
        for (CartItemDto item : carts) {
            if (item.getProduct().getId() == pid) {
                item.addQuantity(quantity);
                existed = true;
                break;
            }
        }
        
        if (!existed) {
            Product p = productFacade.find(pid);
            CartItemDto item = new CartItemDto();
            item.setProduct(p);
            item.setQuantity(quantity);
            carts.add(item);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveCart() {
        Orders ord = new Orders();
        ord.setOrderAt(LocalDate.now());
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItemDto item: carts) {
            OrderDetail od = new OrderDetail();
            od.setProduct(item.getProduct());
            od.setPrice(item.getProduct().getPrice());
            od.setQuantity(item.getQuantity());
            
            orderDetails.add(od);
        }
        ord.setOrderDetailCollection(orderDetails);
        ordersFacadeLocal.create(ord);
        carts.clear();  //xóa item cũ
    }
    
    
}
