//  CS3242 Operating Systems
//  Fall 2013
//  Project 4: Process Synchronization, Part 1
//  Nick Champagne and John Mutabazi
//  Date: 10/23/2013
//  File: project4a.cpp

#include "buffer.h"

using namespace std;

void *producer(void *item);
void *consumer(void *arg);

sem_t full;
sem_t empty;
pthread_mutex_t mutex =  PTHREAD_MUTEX_INITIALIZER;

int main(int argc, char *argv[]) {
	printf("main starting:");

	pthread_t pid, pid1;

	pthread_create(&pid, NULL, &producer, NULL);
	pthread_create(&pid1, NULL,& consumer, NULL);

	buf_init(); //initializing buffer

	sleep(1);
	exit(0);
}

void *producer(void *val) {
	int count = 0;
	buffer_item item;

	printf("\nProducer created");
	do {
		item = rand();

		pthread_mutex_lock(&mutex);
		insert_item(item);
		printf("producer produced %d\n", item);
		count++;

		pthread_mutex_unlock(&mutex);

	} while (true);
	printf("Number of producers produced %d", count);
	return NULL;
}

void *consumer(void *arg) {
	int count = 0;
	printf("\nConsumer created");
	do {
		pthread_mutex_lock(&mutex); //mutex acquired

		int item_Removed = remove_item();

		printf("Consumer consumed %d\n", item_Removed);
		count++;

		pthread_mutex_unlock(&mutex); //mutex released
		count++;

	} while (&count != arg);
	printf("Number of consumers produced %d ", count);
	return NULL;
}
