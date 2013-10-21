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
<<<<<<< HEAD
    head = 0;
    tail = 0;
=======
    head=0;
    tail=0;
>>>>>>> origin/master

}

void insert_item(buffer_item item){
    
    buf[head] = item;
<<<<<<< HEAD
    head = ( head + 1 ) % MAX_SIZE;
=======
    head = (head+1)%MAX_SIZE;
>>>>>>> origin/master
        
        
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
<<<<<<< HEAD
    tail = ( tail + 1 ) % MAX_SIZE;
 
    return item;
}
#endif	/* BUFFER_H */
=======
    tail= (tail+1) % MAX_SIZE;
    
   /* 
    if(head!=tail && tail != -1){
        if(head==tail)
        {
            head=-1;
            tail=-1;
        }else{
            head=(head%1)%MAX_SIZE;
        }
        return 0;
    }
    */ 
    return item;
}
#endif	/* BUFFER_H */

>>>>>>> origin/master
