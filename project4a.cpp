
#include "buffer.h"
#include <stdio.h>
#include <semaphore.h>


void *producer(int *item);
void *consumer (int *arg);


sem_t full;
//int rc =sem_init(&full, 1, 0);
sem_t empty;
//int s=sem_init(&empty, 1, 10);
sem_t mutex;
//int p=sem_init(&mutex, 1, 1);


int main(int argc, char *argv[]){
    int tts= (int)argv[0]; //time to sleep
    
    int numbOfPro= (int)argv[1]; //number of producers
    int numbOfCon= (int)argv[2];
      
    buf_init();
    
    producer(&numbOfPro);
    consumer(&numbOfCon);
    
}

void *producer(int *arg){
    int count =0;
    buffer_item item;
    do{
        //will create threads here
        item = rand();
        sem_wait(&empty);
        sem_wait(&mutex);
        int result =insert_item(&item);
        printf((&result==0?"successfully added":"failed to add"));
        count++;
        signal(&mutex);
        signal(&empty);
        
    }while(&count!=arg);
}

void *consumer(int *arg){
    int count =0;
        do{
            //will create threads here
            sem_wait(&full);
            sem_wait(&mutex);
            
            int result =remove_item();
            printf((result==0?"successfully removed":"failed to remove"));
            count++;
            
            signal(&mutex);
            signal(&empty);
        count++;
    }while(&count!=arg);
}
