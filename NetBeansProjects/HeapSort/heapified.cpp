//
//  main.cpp
//  Heapified
//
//  Created by JC on 10/2/13.
//  Copyright (c) 2013 Coders. All rights reserved.
//
#include <iostream>
using namespace std;
void makeheap(int a[],int n);
void sortheap(int a[],int n);
void disp(int a[],int n);
int main(int argc, const char * argv[])
{
    int a[5] = {9,1,93,8,0};
    makeheap(a,5);
    sortheap(a,5);
    cout<<"\nfun learning"<<endl;
}

//from http://codetonics.blogspot.com/2013/09/heap-sort-program-in-c.html
void makeheap(int a[],int n)
{
    int i,temp,k,j;
    for(i=1;i<n;i++)
        
    {
        temp=a[i];
        k=(i-1)/2;
        j=i;
        while(j>0 && a[k]<temp)
        {
            a[j]=a[k];
            j=k;
            k=(j-1)/2;
        }
        a[j]=temp;
    }
}
void disp(int a[],int n)
{
    int i;
    for(i=0;i<n;i++)
    {
        printf("%d,",a[i]);
    }
    printf("\b");
    printf(" ");
}
void sortheap(int a[],int n)
{
    int temp,i,j;
    for(i=n-1;i>=1;i--)
    {
        temp=a[i];
        a[i]=a[0];
        a[0]=temp;
        makeheap(a,i);
        printf("\n-->");
        disp(a,n);
    }
}
