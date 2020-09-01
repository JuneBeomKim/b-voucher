package BikeRental;

import BikeRental.config.kafka.KafkaProcessor;

import java.util.Optional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    VoucherRepository voucherRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRentalCancelled_RentalCancel(@Payload RentalCancelled rentalCancelled){

        if(rentalCancelled.isMe()){


            Optional<Voucher> orderOptional = voucherRepository.findById(rentalCancelled.getVoucherId()); 
            Voucher voucher = orderOptional.get(); 

            //voucher
            //voucherCnt 개수 조정 (+1)
            //Voucher voucher = new Voucher();

            System.out.println("wheneverRentalCancelled_RentalCancel");
            System.out.println(voucher.getVoucherCnt());
            // System.out.println(rentalCancelled.getVoucherCnt());
            
            System.out.println("##### listener RentalCancel : " + rentalCancelled.toJson());

            voucher.setId(rentalCancelled.getVoucherId());
            voucher.setUserId(rentalCancelled.getUserId());
            // voucher.setVoucherCnt(rentalCancelled.getVoucherCnt());
            voucher.setVoucherCnt(voucher.getVoucherCnt()+1L);
            // System.out.println(voucher.getVoucherCnt()+1L);

            voucherRepository.save(voucher);
        }
    }

}
