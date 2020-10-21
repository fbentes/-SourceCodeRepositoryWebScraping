package com.sourcecoderepositorywebscraping.util.concurrency;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sourcecoderepositorywebscraping.util.exception.WaitTimeToRequestException;

@Component
@Deprecated
public class RequestReentrantLock implements RequestConcurrency {

	Logger logger = LoggerFactory.getLogger(RequestReentrantLock.class);

	private int totalSilmultaneousRequisitionPermissioned = 1;
	
	private ReentrantLock mutex = new ReentrantLock();
	
	private int amountSilmultaneousRequisition = 0;

	Instant startTime = Instant.now();
	
	@Override
	public int getAmountSilmultaneousRequisition() {
		return amountSilmultaneousRequisition;
	}

	@Override
	public void decrementSilmultaneousRequisition() {
		
		try {
            mutex.lock();

            if(amountSilmultaneousRequisition > 0) {
    			amountSilmultaneousRequisition --;
    		}
            
        } finally {
            mutex.unlock();
        }
	}	

	@Override
	public void incrementSilmultaneousRequisition() {
		
		try {
			
            mutex.lock();

    		amountSilmultaneousRequisition ++;
    		
        } finally {
            mutex.unlock();
        }
	}
	
	public void waitUntilFreeSpaceInTheQueue() throws WaitTimeToRequestException {
		
		boolean logged = false;
		
		while(amountSilmultaneousRequisition >= totalSilmultaneousRequisitionPermissioned) {

			// Wait at less one requisition to finish of the 3 ...
			
			if(!logged && amountSilmultaneousRequisition >= totalSilmultaneousRequisitionPermissioned) {
				
				logger.info("");
				logger.info("waitUntilFreeSpaceInTheQueue()  ........ amountSilmultaneousRequisition =  " + amountSilmultaneousRequisition);
				logger.info("");
				
				logged = true;
			}
			
			Duration interval = Duration.between(startTime, Instant.now());
			
			if(interval.getSeconds() >= 1000) {
				throw new WaitTimeToRequestException();
			}
		}		
	}	
}
