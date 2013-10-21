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
//int rc =sem_init(&full, 1, 0);
sem_t empty;
//int s=sem_init(&empty, 1, 10);
pthread_mutex_t mutex;

//int p=sem_init(&mutex, 1, 1);


int main(int argc, char *argv[])
{
    printf("main starting:");
//    int tts= *((int *)argv[1]); //time to sleep
    
    
    //am cheating
    //*((int *)vptr_value);
    
    pthread_t pid, pid1;
    pthread_attr_t attr; //thread attributes
    pthread_attr_init(&attr);
    pthread_attr_setscope(&attr,PTHREAD_SCOPE_SYSTEM);
    
    pthread_create(&pid, &attr, producer, NULL);
    pthread_create(&pid1, &attr, consumer, NULL);
    pthread_join(pid, NULL);
    pthread_join(pid1, NULL);
    
    printf("producer process ", pid);
    printf("consumer process ", pid1);
    
    
//    int numbOfPro= *((int*)argv[2]); //number of producers
//    int numbOfCon= *((int*)argv[3]);
    
    int numbOfPro = 2;
    int numbOfCon = 2;
    
    buf_init(); //initializing buffer
    
    cout<< "Number of producer produced" << producer(&numbOfPro);
    cout<< "Number of producer produced" << consumer(&numbOfCon);
    usleep(1);
    exit(0);
}

void *producer(void *val)
{
    int count =0;
    buffer_item item;
    printf("Producer created");
    do{
        //will create threads here
        item = rand();
        sem_wait(&empty);
        
        //semaphore
        
        //sem_wait(&mutex);
        pthread_mutex_lock(&mutex);
        insert_item(item);
        
        //printf((result==0?"successfully added":"failed to add"));
        count++;
        
        pthread_mutex_unlock(&mutex);
        sem_post(&empty);     
    }while(true);
    return NULL;
}

void *consumer(void *arg)
{
    int count =0;
    printf("Consumer created");
    do{
        //will create threads here
        sem_wait(&full);
        pthread_mutex_lock(&mutex);
        
        int result =remove_item();
        printf((result != NULL ? "successfully removed":"failed to remove"));
        
        
        
        count++;
        
        pthread_mutex_unlock(&mutex);
        sem_post(&empty);
        count++;
    }while(&count!=arg);
    return NULL;
}



