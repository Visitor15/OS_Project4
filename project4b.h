/*
 * 	CS3442			Operating Systems
 * 	Semester:    Fall 2013
 * 	Project 4:   	Process Synchronization, Part 2
 * 	Name:       		 Nick Champagne and John Mutabazi
 * 	Date:        		10/23/2013
 * 	File:        			project4b.h
 */

#ifndef PROJECT4B_H_
#define PROJECT4B_H_

#include <fstream>
#include <iostream>
#include <semaphore.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <unistd.h>
#include <vector>


/*
 * 	Defined statics
 *
 * 	NOTE: ARRAY_SIZE and WORKING_LIST_SIZE are both calculated at run-time. This
 * 	allows us to not have to hard-code a specific length for the arrays in shared memory.
 */
static long ARRAY_SIZE = 0;
static unsigned int WORKING_LIST_SIZE = 0;
static unsigned int CHILD_PROCESS_COUNT = 4;
static const unsigned int INCREMENT = 10;


/*
 * 	Global variables
 */
unsigned int* num_of_running_processes = 0;
unsigned int* curr_index = 0;
long* p_master_unsorted_list;
long* p_master_list;
bool* should_sort;

/*
 * 	Global semaphore
 */
sem_t* m_semaphore;

/*
 * 	Per-process local variables
 */
unsigned int process_index;
std::vector<long> working_list;
std::vector<long> unsorted_list;
pid_t pid;

/*=============================================
 			FUNCTION PROTOTYPES
 ==============================================*/

/*
 * 	Reads data as longs from numbers.txt. It uses a vector<long> so we can have a dynamic
 * 	number of data to be shared between processes.
 *
 * 	PARAMS:			std::vector<long>						// Reference to a vector<long> list to insert read data.
 */
void readInData(std::vector<long> &list);

/*
 * 	Initializer for shared memory objects.
 */
void initSharedMemoryObjs();

/*
 * 	Function for spawning a dynamic number of child processes. Each child process
 * 	logs itself by incrementing 'num_of_running_processes' and evenly dividing the
 * 	number of elements defined by 'working_list_size'.
 *
 * 	PARAMS:			unsigned int										//  defining the number of child processes to spawn.
 */
void spawnChildrenProcesses(unsigned int num_of_children);

/*
 *		Called to increment the shared unsigned int 'num_of_running_processes'.
 */
void logSpawnedChild();

/*
 *		Helper function called after forking() a child process that assigns and starts work on that process
 */
void doChildProcessWork();

/*
 * 	Worker function  for sorting a child processe' vector<long> working_list
 *
 * 	PARAMS:				std::vector<long>							// Reference to a child process' individual vector<long> working_list
 */
void doSelectionSort(std::vector<long> &unsortedList);

/*
 * 		Worker function used by the parent process to sort the 'p_master_list' array
 *
 * 		PARAMS:			long* list												// List to sort on
 * 										long leftMost									// Left most index of list to sort between
* 											long rightMost									// Right most index of list to sort between
 */
void doQuickSort(long* unsortedList, long leftMost, long rightMost);

/*
 * 		Wrapper function called when a child process has finished processing its data. It
 * 		in turn calls exitAndCleanChildProcess()
 */
void onChildProcessFinished();

/*
 * 		Appends a sorted list[] done by a child process
 *
 * 		PARAMS:				long[]														// Data to be appended to 'p_master_list'
 * 											unsigned int length						// Length of the list[]
 */
void appendElementsToMasterList(long list[], unsigned int length);

/*
 * 		Helper function that writes the contents of the passed in long[] to a file defined
 * 		by std::string fileName
 *
 * 		PARAMS:			long[]															// Data to be written to file
 * 										std::string fileName							// Name of file to be written to
 */
void writeContentsToFile(long list[], std::string fileName);

/*
 * 		Helper function called when a child process has finished its work. Its main purpose
 * 		is to reliably decrement the unsigned int 'num_of_running_processes' before
 * 		having the child exit.
 */
void exitAndCleanChildProcess();

/*
 * 		Helper function called when all child processes have finished and the parent process
 * 		has finished its work. It  is here the global semaphore is destroyed and all mmap-ed
 * 		objects are munmap-ed before having the parent process exit.
 */
void exitAndCleanParentProcess();

/*
 * 		Helper function used to evenly distribute work between child processes.
 *
 * 		PARAMS:			long[]															// List of data to distribute
 * 										int offset													// Offset to start reading data from long[]
 * 										int length													// Length to read from long[]
 *
 * 		RETURNS:		std::vector<long>								// List of partitioned data from long[]
 */
std::vector<long> getWorkingList(long* list, unsigned int offset,
		unsigned int length);

#endif /* PROJECT4B_H_ */
