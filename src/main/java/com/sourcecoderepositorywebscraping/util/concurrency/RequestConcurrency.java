package com.sourcecoderepositorywebscraping.util.concurrency;

@Deprecated
public interface RequestConcurrency {

	int getAmountSilmultaneousRequisition();

	void decrementSilmultaneousRequisition();

	void incrementSilmultaneousRequisition();

	void waitUntilFreeSpaceInTheQueue();
}