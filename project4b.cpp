/*
 * project4b.cpp
 *
 *  Created on: Oct 19, 2013
 *  Author: visitor15
 */
#include <stdio.h>
#include <iostream>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/mman.h>
#include <sys/wait.h>
#include <fstream>
#include <iterator>
#include <sstream>
#include <string>
#include <vector>
#include <string.h>
#include <semaphore.h>

static const long ARRAY_SIZE = 10000;

// Global variables
int* num_of_running_processes = 0;
long* p_master_unsorted_list;
long* p_master_sorted_list;

//Global semaphore
sem_t* m_semaphore;

// Arrays
long master_unsorted_list[ARRAY_SIZE];
long master_sorted_list[ARRAY_SIZE];
int* child_processes;

// Local process variables
pid_t pid;
int num_child_processes = 4;
int working_list_size = 2500;
int process_index;

std::vector<long> working_list;
std::vector<long> unsorted_list;

void readInData(std::vector<long> &list);
void initSharedMemoryObjs();
void spawnChildrenProcesses(int num_of_children);
void doSelectionSort(std::vector<long> &unsortedList);
std::vector<long> getWorkingList(long* list, int offset, int length);
void logSpawnedChild(int pid);
void doChildProcessWork();
void exitAndCleanChildProcess();
void exitAndCleanParentProcess();
void writeToScreen(std::vector<long> &list);

int main() {

	child_processes = new int[num_child_processes];
	readInData(unsorted_list);
	initSharedMemoryObjs();
	for (int i = 0; i < unsorted_list.size(); i++) {
		p_master_unsorted_list[i] = unsorted_list.at(i);
	}

	spawnChildrenProcesses(num_child_processes);

	while (*num_of_running_processes > 0) {
		wait(NULL);
	}

	std::cout << "All processes finished!" << std::endl;

	exitAndCleanParentProcess();

	return 0;
}

void readInData(std::vector<long> &list) {
	std::ifstream inStream("numbers.txt");
	std::string m_line;
	while (std::getline(inStream, m_line)) {
		//Converting from a string to an long.
		long number = strtol((char*) m_line.c_str(), NULL, 0);

		list.push_back(number);
	}
	inStream.close();
}

void initSharedMemoryObjs() {

	m_semaphore = (sem_t*) mmap(NULL, sizeof(sem_t), PROT_READ | PROT_WRITE,
	MAP_SHARED | MAP_ANONYMOUS, -1, 0);
	sem_init(m_semaphore, 1, 1);

	child_processes = (int*) mmap(NULL, sizeof(child_processes),
	PROT_READ | PROT_WRITE, MAP_SHARED | MAP_ANONYMOUS, -1, 0);

	num_of_running_processes = (int*) mmap(NULL,
			sizeof(*num_of_running_processes),
			PROT_READ | PROT_WRITE,
			MAP_SHARED | MAP_ANONYMOUS, -1, 0);

	p_master_unsorted_list = (long*) mmap(NULL, sizeof(long) * ARRAY_SIZE,
	PROT_READ | PROT_WRITE,
	MAP_SHARED | MAP_ANONYMOUS, -1, 0);

	p_master_sorted_list = (long*) mmap(NULL, sizeof(master_sorted_list),
	PROT_READ | PROT_WRITE,
	MAP_SHARED | MAP_ANONYMOUS, -1, 0);

}

void spawnChildrenProcesses(int num_of_children) {

	for (int i = 1; i <= num_of_children; i++) {
		pid = fork();

		switch (pid) {
		case -1: {
			// Error has occurred

			break;
		}
		case 0: {
			// Child process

			process_index = i;
			logSpawnedChild(getpid());
			doChildProcessWork();

			break;
		}
		default: {
			// Parent process

			break;
		}
		}
	}
}

void logSpawnedChild(int pid) {
	sem_wait(m_semaphore);

	child_processes[*num_of_running_processes] = getpid();
	*num_of_running_processes = *num_of_running_processes + 1;

	std::cout << "Child " << process_index << " running"
			<< std::endl;

	sem_post(m_semaphore);
}

void doChildProcessWork() {
	int offset = ((process_index - 1) * working_list_size) - 1;
	working_list = getWorkingList(p_master_unsorted_list, offset,
			working_list_size);

	sem_wait(m_semaphore);
	for (int i = 0; i < 10; i++) {
		std::cout << "PID: " << process_index << " "
				<< working_list.at(i) << std::endl;
	}
	sem_post(m_semaphore);

	exitAndCleanChildProcess();
}

std::vector<long> getWorkingList(long* list, int offset, int length) {
	std::vector<long> tmp_list;

	for (int i = offset; i < (length + offset); i++) {
		tmp_list.push_back(list[i]);
	}

	return tmp_list;
}

void writeToScreen(std::vector<long> &list) {
	sem_wait(m_semaphore);

	/*
	 * CRITICAL SECTION
	 */
	std::cout << "PID: " << process_index << std::endl;
	for (int i = 0; i < list.size(); i++) {
		std::cout << list.at(i) << std::endl;
	}

	sem_post(m_semaphore);
}

void doSelectionSort(std::vector<long> &unsortedList) {
	long minIndex;
	long tempVal;
	long i;
	long j;

	for (i = 0; i < unsortedList.size() - 1; i++) {
		minIndex = i;

		for (j = i + 1; j < unsortedList.size(); j++) {

			if (unsortedList.at(j) < unsortedList.at(minIndex)) {
				minIndex = j;
			}
		}

		if (minIndex != i) {
			tempVal = unsortedList.at(i);
			unsortedList.at(i) = unsortedList.at(minIndex);
			unsortedList.at(minIndex) = tempVal;
		}
	}
}

void exitAndCleanChildProcess() {
	sem_wait(m_semaphore);

	std::cout << "Child " << process_index << " is finishing"
			<< std::endl;
	*num_of_running_processes = *num_of_running_processes - 1;

	sem_post(m_semaphore);

	exit(EXIT_SUCCESS);
}

void exitAndCleanParentProcess() {
	sem_destroy(m_semaphore);
	munmap(m_semaphore, sizeof(sem_t));
	munmap(p_master_unsorted_list, sizeof(p_master_unsorted_list[0]) * ARRAY_SIZE);
	munmap(p_master_unsorted_list, sizeof(p_master_sorted_list[0]) * ARRAY_SIZE);
	munmap(num_of_running_processes, sizeof(*num_of_running_processes));
	munmap(child_processes, sizeof(child_processes));

	exit(EXIT_SUCCESS);
}

