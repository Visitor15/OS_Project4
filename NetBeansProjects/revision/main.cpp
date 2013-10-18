//
//  main.cpp
//  Part1_Revised
//
//  Created by Shema on 10/16/13.
//  Copyright (c) 2013 Coders. All rights reserved.
//

#include <cstdlib>
#include <stdlib.h>
#include <iostream>
#include <pthread.h>
#include <queue>
#include <cstdio>
#include "buffer.h"
#include <semaphore.h>
#include <signal.h>
#include <sys/wait.h>
#include <unistd.h>
#include <pthread.h>


void *producer(void *item);
void *consumer(void *arg);


sem_t full;
//int rc =sem_init(&full, 1, 0);
sem_t empty;
//int s=sem_init(&empty, 1, 10);
pthread_mutex_t mutex;
//sem_t mutex;


//int p=sem_init(&mutex, 1, 1);


int main(int argc, char *argv[])
{
    int tts= *((int *)argv[1]); //time to sleep
    
    
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
    
    
    int numbOfPro= *((int*)argv[2]); //number of producers
    int numbOfCon= *((int*)argv[3]);
    
    
    buf_init(); //initializing buffer
    
    producer(&numbOfPro);
    consumer(&numbOfCon);
    usleep(tts);
    exit(0);
    
    
    /*
     pthread_t pid, cid;
     pthread_attr_t attr;
     pthread_attr_init(&attr);
     pthread_attr_setscope(&attr, PTHREAD_SCOPE_SYSTEM);
     
     numIters = atoi(argv[1]);
     sem_init(&empty, SHARED, 1);  / sem empty = 1 /
    sem_init(&full, SHARED, 0);   / sem full = 0  /
    
    printf("main started\n");
    pthread_create(&pid, &attr, Producer, NULL);
    pthread_create(&cid, &attr, Consumer, NULL);
    pthread_join(pid, NULL);
    pthread_join(cid, NULL);
    printf("main done\n");
    
    */
    
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
        int result = insert_item(&item);
        printf((result==0?"successfully added":"failed to add"));
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
        printf((result==0?"successfully removed":"failed to remove"));
        count++;
        
        pthread_mutex_unlock(&mutex);
        sem_post(&empty);
        count++;
    }while(&count!=arg);
    return NULL;
}



