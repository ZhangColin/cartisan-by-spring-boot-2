package com.cartisan.joey.coupon.domains;

import com.cartisan.common.domains.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

/**
 * @author colin
 */
@Entity
@Table(name = "cpn_store_guides")
@Getter
@EqualsAndHashCode(callSuper = true)
public class StoreGuide extends AbstractEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "guide")
    private String guide;

    private StoreGuide() {
    }

    public StoreGuide(Long storeId, String guide) {
        this.storeId = storeId;
        this.guide = guide;
    }
}
