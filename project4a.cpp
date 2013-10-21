//  CS3242 Operating Systems
//  Fall 2013
//  Project 4: Process Synchronization, Part 1
//  Nick Champagne and John Mutabazi
//  Date: 10/23/2013
//  File: project4a.cpp

#include <cstdlib>
#include <stdlib.h>
#include <iostream>
#include <pthread.h>
#include <cstdio>
#include "buffer.h"
#include <semaphore.h>
#include <signal.h>
#include <sys/wait.h>
#include <unistd.h>
#include <pthread.h>

using namespace std;

void *producer(void *item);
void *consumer(void *arg);


sem_t full;
sem_t empty;
pthread_mutex_t mutex;

int main(int argc, char *argv[])
{
    printf("main starting:");

    
    pthread_t pid, pid1;
    pthread_attr_t attr; 
    pthread_attr_init(&attr);
    pthread_attr_setscope(&attr,PTHREAD_SCOPE_SYSTEM);
    
    pthread_create(&pid, &attr, producer, NULL);
    pthread_create(&pid1, &attr, consumer, NULL);
    pthread_join(pid, NULL);
    pthread_join(pid1, NULL);
    
    //printf("\nproducer process ", pid);
    //printf("\nconsumer process ", pid1);

    
    int numbOfPro = 2;
    int numbOfCon = 2;
    
    buf_init(); //initializing buffer
    
    sleep(1);
    exit(0);
}

void *producer(void *val)
{
    int count =0;
    buffer_item item;
    pthread_mutex_init(&mutex,NULL); //creating the mutex
    printf("\nProducer created");
    do{
        item = rand();
        sem_wait(&empty);
        
        pthread_mutex_lock(&mutex);
        insert_item(item);    
        printf("producer produced %d\n", item);
        count++;
        
        pthread_mutex_unlock(&mutex);
        sem_post(&empty);     
    
    } while(true);
    printf("Number of producers produced", count);
    return NULL;
}

void *consumer(void *arg)
{
    int count =0;
    pthread_mutex_init(&mutex,NULL); //creating the mutex
    printf("\nConsumer created");
    do{
        sem_wait(&full);
        pthread_mutex_lock(&mutex); //mutex acquired
        
        int item_Removed =remove_item();
        
        printf("Consumer consumed %d\n", item_Removed);
        count++;
        
        pthread_mutex_unlock(&mutex); //mutex released
        sem_post(&empty);
        count++;
        
    } while(&count!=arg);
    printf("Number of consumers produced ", count);
    return NULL;
}