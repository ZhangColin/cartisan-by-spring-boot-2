package com.cartisan.common.domains;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * @author colin
 */
@MappedSuperclass
@Data
@ToString
@EqualsAndHashCode
public class AbstractEntity implements Serializable {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", insertable= false, nullable = false, length = 19, updatable = false,
            columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", insertable= false, nullable = false, length = 19, updatable = false,
            columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updateDateTime;

}
