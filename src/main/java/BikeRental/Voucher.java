package BikeRental;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Voucher_table")
public class Voucher {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long userId;
    private Long voucherCnt;

    @PostPersist
    public void onPostPersist(){

        System.out.println("######onPostPersist ######" );
        VoucherBought voucherBought = new VoucherBought();
        BeanUtils.copyProperties(this, voucherBought);
        voucherBought.publishAfterCommit();


    }

    @PreUpdate
    public void onPreUpdate(){

        System.out.println("this : " + this);
        System.out.println("id : " + this.getId());
        System.out.println("VoucherCNT : " + this.getVoucherCnt());
        // System.out.println(this.getVoucherCnt()-1L);

        VoucherUpdated voucherUpdated = new VoucherUpdated();
        // voucherUpdated.setVoucherCnt(this.getVoucherCnt()-1L);
      
        BeanUtils.copyProperties(this, voucherUpdated);
        voucherUpdated.publishAfterCommit();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getVoucherCnt() {
        return voucherCnt;
    }

    public void setVoucherCnt(Long voucherCnt) {
        //voucher 개수 조정

        this.voucherCnt = voucherCnt;
    }




}
