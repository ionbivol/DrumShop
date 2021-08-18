package org.online.shop.entity;

import javax.persistence.*;

@Entity
@Table(name = "cart")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer cartId;

    private String paymentMode;

    private Boolean isOrder; //

    private String invoiceNo;

    private Boolean sold; //dupa ce facem cumbaratura il punem pe sold = fols

    private Integer userId;

}
