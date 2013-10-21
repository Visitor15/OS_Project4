//  CS3242 Operating Systems
//  Fall 2013
//  Project 4: Process Synchronization, Part 1
//  Nick Champagne and John Mutabazi
//  Date: 10/23/2013
//  File: project4a.cpp

#include <stdlib.h>
#include <semaphore.h>
#ifndef BUFFER_H
#define	BUFFER_H
typedef int buffer_item;


int head;
int tail;
const int MAX_SIZE = 10;
buffer_item buf[MAX_SIZE];


void buf_init(){
    head = 0;
    tail = 0;

}

void insert_item(buffer_item item){
    
    buf[head] = item;
    head = ( head + 1 ) % MAX_SIZE;
        
        
    }
    /*
    if(head!=(tail+1)%MAX_SIZE){
        buf[tail]=*item;
        tail++;    
        return 0;
    }
     * */
   
int remove_item(){
    
    int item = buf[tail];
    tail = ( tail + 1 ) % MAX_SIZE;
 
    return item;
}
#endif	/* BUFFER_H */
