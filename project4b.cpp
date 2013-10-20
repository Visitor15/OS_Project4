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

static const long ARRAY_SIZE = 10000;

// Global variables
int* num_of_running_processes = 0;
long* p_master_unsorted_list;
long* p_master_sorted_list;

// Arrays
long master_unsorted_list[ARRAY_SIZE];
long master_sorted_list[ARRAY_SIZE];
int* child_processes;

// Local process variables
pid_t pid;
int num_child_processes = 4;
int offset = 2500;
std::vector<long> working_list;

std::vector<long> unsorted_list;

void readInData(std::vector<long> &list);
void initSharedMemoryObjs();
void spawnChildrenProcesses(int num_of_children);
void doSelectionSort(std::vector<long> &unsortedList);

int main() {

	child_processes = new int[num_child_processes];
	readInData(unsorted_list);
	initSharedMemoryObjs();
	for(int i = 0; i < unsorted_list.size(); i++) {
		p_master_unsorted_list[i] = unsorted_list.at(i);
	}

	spawnChildrenProcesses(num_child_processes);

	while (*num_of_running_processes > 0) {
		wait(NULL);
	}

	std::cout << "All processes finished!" << std::endl;

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

	for (int i = 0; i < num_of_children; i++) {
		pid = fork();

		switch (pid) {
		case -1: {
			// Error has occurred

			break;
		}
		case 0: {
			// Child process

			/*
			 * NOTE: Locking and critical sections need to be implemented.
			 */

			sleep(3);

			child_processes[*num_of_running_processes] = getpid();
			*num_of_running_processes = *num_of_running_processes + 1;

			std::cout << "Child " << *num_of_running_processes << " running"
					<< std::endl;
			int j = offset * i;
			int tmp_offset = offset * (i + 1);
			for(; j < tmp_offset; j++) {
				working_list.push_back(p_master_unsorted_list[i]);
			}

			for(j = 0; j < working_list.size(); j++) {
				if(j % 100 == 0) {
					std::cout << std::endl;
				} else {
					std::cout << working_list.at(j) << " ";
				}
			}

			sleep(3);

			*num_of_running_processes = *num_of_running_processes - 1;

			exit(EXIT_SUCCESS);

			break;
		}
		default: {
			// Parent process

			break;
		}
		}
	}
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

