/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package aptech.sb;

import aptech.entity.OrderDetail;
import jakarta.ejb.Local;
import java.util.List;

/**
 *
 * @author quang
 */
@Local
public interface OrderDetailFacadeLocal {

    void create(OrderDetail orderDetail);

    void edit(OrderDetail orderDetail);

    void remove(OrderDetail orderDetail);

    OrderDetail find(Object id);

    List<OrderDetail> findAll();

    List<OrderDetail> findRange(int[] range);

    int count();
    
}
