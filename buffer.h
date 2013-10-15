/* 
 * File:   buffer.h
 * Author: johnniecris
 *
 * Created on October 15, 2013, 12:54 AM
 */
#include <stdlib.h>
#include <semaphore.h>
#ifndef BUFFER_H
#define	BUFFER_H
#define BUFFER_SIZE 10;
typedef int buffer_item;


int head;
int tail;
int MAX_SIZE=BUFFER_SIZE;
buffer_item buf[MAX_SIZE];


void buf_init(){
    head=0;
    tail=0;

}

int insert_item(buffer_item *item){
    if(head!=(tail+1)%MAX_SIZE){
        buf[tail]=item;
        tail++;
            
        return 0;
    }
    return -1;
}
int remove_item(){
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
    return -1;
}
#endif	/* BUFFER_H */

