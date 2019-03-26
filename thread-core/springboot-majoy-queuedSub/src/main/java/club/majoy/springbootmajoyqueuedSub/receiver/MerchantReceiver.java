package club.majoy.springbootmajoyqueuedSub.receiver;

import club.majoy.springbootmajoyqueuedPub.entity.Job;
//import club.majoy.springbootmajoyqueuedPub.entity.Job;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.CountDownLatch;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MerchantReceiver {


    private CountDownLatch latch;
    //receiveMsg

    public void receiveMsg(String msg){
        //反序列化
        try {
            ByteArrayInputStream byteIn = new ByteArrayInputStream(msg.getBytes("ISO-8859-1"));
            ObjectInputStream objIn = new ObjectInputStream(byteIn);
            Object obj =objIn.readObject();
            Job job = (Job)obj;
            log.info("receive job:{}",job );
            latch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
