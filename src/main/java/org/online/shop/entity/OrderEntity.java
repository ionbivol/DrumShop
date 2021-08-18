package org.online.shop.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    private Date orderDate;

    private String product;

    private String userName;

    private String address;

    private String paymentMode;
}
